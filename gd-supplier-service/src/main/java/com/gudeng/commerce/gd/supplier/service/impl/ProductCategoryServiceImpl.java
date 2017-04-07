package com.gudeng.commerce.gd.supplier.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.supplier.bo.CacheBo;
import com.gudeng.commerce.gd.supplier.dao.BaseDao;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.supplier.service.ProductCategoryService;
import com.gudeng.commerce.gd.supplier.service.ProductService;
import com.gudeng.commerce.gd.supplier.util.CommonUtils;

/**
 * 产品分类接口实现类
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class ProductCategoryServiceImpl extends BaseService implements ProductCategoryService{
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private CacheBo cacheBo;
	
	@Override
	public Long persistProductCategory(
			ProductCategoryEntity productCategoryEntity) throws Exception{
		//设置产品分类级数
		if(null == productCategoryEntity.getParentId() || "".equals(productCategoryEntity.getParentId())){
			productCategoryEntity.setCurLevel(1);
		}else{
			ProductCategoryEntity _productCategory = this.getProductCategory(productCategoryEntity.getParentId());
			if(null != _productCategory){
				productCategoryEntity.setCurLevel(Integer.valueOf((_productCategory.getCurLevel() + 1)));

				//清除父类缓存
				cacheBo.deleteProductCategoryCacheById(_productCategory.getCategoryId());
			} else {
				productCategoryEntity.setCurLevel(0);
			}
		}
		
		productCategoryEntity.setStatus("1");
		Date curDate = new Date();
		productCategoryEntity.setUpdateTime(curDate);
		productCategoryEntity.setCreateTime(curDate);
		//this.baseDao.execute("ProductCategory.persistProductCategory", productCategoryDTO);
		Long id = (Long)baseDao.persist(productCategoryEntity, Long.class);
		//清除缓存
		if(null != productCategoryEntity.getMarketId()){
			cacheBo.deleteProductCategoryCacheById(productCategoryEntity.getMarketId());
		}
		return id;
		
	}

	@Override
	public String deleteProductCategory(Long id) {
		if(isExistProductByCateId(id)){
			return "分类下存在产品";
		}if(this.getChildProductCategory(id).size() >= 1){
			return "存在子分类";
		}else{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", id);
			this.baseDao.execute("ProductCategory.deleteProductCategory", params);
			
			//清除本节点缓存
			cacheBo.deleteProductCategoryCacheById(id);
			
			ProductCategoryDTO dto = this.getProductCategory(id);
			
			/*
			 * 清楚关联分类数据redis
			 */
			if(dto.getCurLevel()==0) {
				//农批商删除后清除供应商关联
				cacheBo.deleteRefCateSupNpsByCateId(id, "1");
			}
			if(dto.getCurLevel()==2) {
				//农批商删除后清除基础关联
				cacheBo.deleteRefCateSupNpsByCateId(id, "2");
			}
			
			if(null != dto && null != dto.getMarketId()){
				//清除上级目录
				cacheBo.deleteProductCategoryCacheById(dto.getParentId());
				//清除顶层
				cacheBo.deleteProductCategoryCacheById(dto.getMarketId());
			}
			return null;
		}
	}
	
	private boolean isExistProductByCateId(Long cateId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cateId", cateId);
		if(this.baseDao.queryForList("ProductCategory.isExistProductByCateId", params ,ProductEntity.class).size() >= 1){
			return true;
		}
		return false;
	}

	/**
	 * 获取产品分类下所有产品分类id
	 * @param id 产品分类id
	 * @return
	 */
	/*private List<String> appendChild(String id) {
		List<ProductCategory> productCategoryList = this.getChildProductCategory(id);
		for(ProductCategory productCategory : productCategoryList){
			productCategoryListTemp.add(productCategory.getId());
			if(this.getChildProductCategory(productCategory.getId()).size() >= 1){
				//递归调用
				appendChild(productCategory.getId());
			}
		}
		return productCategoryListTemp;
	}*/
	
	@Override
	public ProductCategoryEntity updateProductCategory(
			ProductCategoryEntity productCategoryEntity) {
		if(null != productCategoryEntity && !"".equals(productCategoryEntity.getCategoryId())){
			
			//设置产品分类级数
			if(null == productCategoryEntity.getParentId() || "".equals(productCategoryEntity.getParentId())){
				productCategoryEntity.setCurLevel(1);
			}else{
				ProductCategoryEntity _productCategory = this.getProductCategory(productCategoryEntity.getParentId());
				if(null != _productCategory){
					productCategoryEntity.setCurLevel(Integer.valueOf((_productCategory.getCurLevel() + 1)));
					cacheBo.deleteProductCategoryCacheById(productCategoryEntity.getParentId());
				}
			}
			this.baseDao.execute("ProductCategory.updateProductCategory", productCategoryEntity);
			
			//清除缓存
			cacheBo.deleteProductCategoryCacheById(productCategoryEntity.getCategoryId());
			if(null != productCategoryEntity.getMarketId()){
				cacheBo.deleteProductCategoryCacheById(productCategoryEntity.getMarketId());
			}
			return productCategoryEntity;
		}
		return null;
	}

	/*public List<ProductCategoryDTO> queryProductCategoryPage(
			ProductCategoryDTO productCategoryDTO) throws IllegalArgumentException, IllegalAccessException {
		Map<String,Object> params = new HashMap<String,Object>();
		//反映机制,获取实现属性及对应值
		for(Field f : ProductCategoryDTO.class.getDeclaredFields()){
			f.setAccessible(true);
			params.put(f.getName(), f.get(productCategoryDTO));
		}
		return baseDao.queryForList("ProductCategory.queryProductCategoryPage", params, ProductCategoryDTO.class);
	}*/

	@Override
	public ProductCategoryDTO getProductCategory(Long id) {
		/*Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		return (ProductCategoryDTO) this.baseDao.queryForObject("ProductCategory.getProductCategory", params, ProductCategoryDTO.class);*/
		return cacheBo.getProductCategory(id, baseDao);
	}

	@Override
	public List<ProductCategoryDTO> listProductCategory(Long marketId) {
		/*Map<String,Object> params = new HashMap<String,Object>();
		if(null != marketId){
			params.put("marketId", marketId);
		}
		return this.baseDao.queryForList("ProductCategory.listProductCategory", params ,ProductCategoryDTO.class);*/
		return cacheBo.listProductCategory(marketId, baseDao);
	}

	@Override
	public ProductCategoryDTO getProductCategoryByName(String name) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cateName", name);
		List<ProductCategoryDTO> pcList = this.baseDao.queryForList("ProductCategory.listProductCategoryByName", params ,ProductCategoryDTO.class);
		if(null != pcList && pcList.size() > 0){
			return pcList.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<ProductCategoryDTO> listTopProductCategory() {
		/*Map<String,Object> params = new HashMap<String,Object>();
		
		return this.baseDao.queryForList("ProductCategory.listTopProductCategory", params ,ProductCategoryDTO.class);*/
		
		return cacheBo.listTopProductCategory(baseDao);
	}

	@Override
	public List<ProductCategoryDTO> getChildProductCategory(Long id) {
		/*Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId",id);
		return this.baseDao.queryForList("ProductCategory.listChildProductCategory", params ,ProductCategoryDTO.class);*/
		return cacheBo.getChildProductCategory(id, baseDao);
	}
	
	public List<ProductCategoryDTO> getChildProductCategoryByMarketId(Long id,Long marketId) {
		/*Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId",id);
		params.put("marketId",marketId);
		return this.baseDao.queryForList("ProductCategory.listChildProductCategory", params ,ProductCategoryDTO.class);*/
		return cacheBo.getChildProductCategoryByMarketId(id, marketId, baseDao);
	}
	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ProductCategoryDTO> listTopProductCategoryByMarketId(Long marketId) {
		if(null == marketId){
			Map<String,Object> params = new HashMap<String,Object>();
			return this.baseDao.queryForList("ProductCategory.listTopProductCategory", params ,ProductCategoryDTO.class);
		}else{
			return cacheBo.listTopProductCategoryByMarketId(marketId, baseDao);
		}
	}

	@Override
	public List<ProductCategoryDTO> getCategoryAncestors(Long id) {
		
		List<ProductCategoryDTO> list = new ArrayList<ProductCategoryDTO>();
		list = getCategorys(id, list);
		int len = list.size();
		if (len <= 0) {
			return null ;
		}
		List<ProductCategoryDTO> result = new ArrayList<ProductCategoryDTO>();
		for (int i = len-1; i >=0 ; i--){
			result.add(list.get(i));
		}
		return result;
	}
	
	private List<ProductCategoryDTO> getCategorys(Long id, List<ProductCategoryDTO> list){
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id",id);
		ProductCategoryDTO self = (ProductCategoryDTO) baseDao.queryForObject("ProductCategory.getProductCategory", params, ProductCategoryDTO.class);
		Long parentId = self.getParentId() ;
		if (parentId == null || parentId.longValue() == -1){
			list.add(self);
		}else {
			list.add(self);
			list = getCategorys(parentId, list);
		}
		return list;
	}

	@Override
	public List<ProductCategoryDTO> listProductCategoryByMarketId(Long marketId) {
		/*Map<String,Object> params = new HashMap<String,Object>();
		params.put("marketId", marketId);
		return this.baseDao.queryForList("ProductCategory.listProductCategory", params ,ProductCategoryDTO.class);*/
		return cacheBo.listProductCategory(marketId, baseDao);
	}
	
	/**
	 * @Description listProductCategoryByLvAndMarketId 根据产品类别级别和市场ID查看产品分类信息 
	 * @param map
	 * @return
	 * @CreationDate 2015年10月29日 下午3:44:58
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public List<ProductCategoryDTO> listProductCategoryByLvAndMarketId(Map<String, Object> map){
		return this.baseDao.queryForList("ProductCategory.listProductCategoryByLvAndMarketId", map ,ProductCategoryDTO.class);
	}

	@Override
	public List<ProductCategoryDTO> listAllProductCategory() {
		Map<String,Object> params = new HashMap<String,Object>();
		return this.baseDao.queryForList("ProductCategory.listProductCategory", params ,ProductCategoryDTO.class);
	
	}

	@Override
	public List<ProductCategoryDTO> listAllProductCategoryByMarketId(Long marketId) {
		List<ProductCategoryDTO> pclist = listTopProductCategoryByMarketId(marketId);
		for(ProductCategoryDTO pcdto : pclist){
			List<ProductCategoryDTO> pcclist = getChildProductCategory(pcdto.getCategoryId());
			if(null != pcclist && pcclist.size() > 0){
				for(ProductCategoryDTO pccdto : pcclist){
					List<ProductCategoryDTO> pccclist = getChildProductCategory(pccdto.getCategoryId());
					if(null != pcclist && pcclist.size() > 0){
						pccdto.setChildList(pccclist);
					}
				}
				pcdto.setChildList(pcclist);
			}
		}
		return pclist;
	}

	@Override
	public List<ProductCategoryDTO> getProductList(String option,	String marketId, String businessId) throws Exception {
		//一级分类
		List<ProductCategoryDTO> pclist = this.listTopProductCategoryByMarketId(Long.valueOf(marketId));
		Iterator<ProductCategoryDTO> iterator = pclist.iterator();
		Iterator<ProductCategoryDTO> iterator2 = null;
		Iterator<ProductCategoryDTO> iterator3 = null;
		ProductCategoryDTO currentCategory = null; 
		ProductCategoryDTO currentCategory2 = null; ProductCategoryDTO currentCategory3 = null;
		List<ProductCategoryDTO> second = null; List<ProductCategoryDTO> third = null;
		List<ProductDto> plist = null;
		Map<String, Object> params = new HashMap<>();
		List<Long> cateIdList = new ArrayList<Long>();
		while(iterator.hasNext()){
			params.clear();	cateIdList.clear();
			currentCategory = iterator.next();
			//二级分类
			second = this.getChildProductCategory(currentCategory.getCategoryId());
			iterator2 = second.iterator();
			while(iterator2.hasNext()){
				currentCategory2 = iterator2.next();
				//三级分类
				third = this.getChildProductCategory(currentCategory2.getCategoryId());
				iterator3 = third.iterator();
				while(iterator3.hasNext()){
					currentCategory3 = iterator3.next();
					cateIdList.add(currentCategory3.getCategoryId());
				}
			}
			
			//分类id集合(当前一级分类下面的所有三级分类id)
			params.put("cateIdList", cateIdList);
			params.put("businessId", businessId);
			if ("1".equals(option)){//出售中
				params.put("state", ProductStatusEnum.ON.getkey());
				/*params.put("stockCountUp", "1");*/
			} else if ("2".equals(option)){//待审核
				List<String> stateList = new ArrayList<String>();
				stateList.add(ProductStatusEnum.NEEDAUDIT.getkey());
				stateList.add(ProductStatusEnum.REFUSE.getkey());
				params.put("stateList", stateList);
			} else if ("3".equals(option)){//已下架
				List<String> stateList = new ArrayList<String>();
				stateList.add(ProductStatusEnum.DRAFT.getkey());
				stateList.add(ProductStatusEnum.OFF.getkey());
				params.put("stateList", stateList);
			} else if ("0".equals(option)){
				//排除已删除
				List<String> stateList = new ArrayList<String>();
				stateList.add(ProductStatusEnum.DRAFT.getkey());
				stateList.add(ProductStatusEnum.NEEDAUDIT.getkey());
				stateList.add(ProductStatusEnum.REFUSE.getkey());
				stateList.add(ProductStatusEnum.ON.getkey());
				stateList.add(ProductStatusEnum.OFF.getkey());
				params.put("stateList", stateList);
			}
			plist = productService.getProductsForOriPlaceVen(params);
			//当前分类下若无产品, 则移除该分类
			if(CommonUtils.isEmpty(plist)){
				iterator.remove();
			}else{
				currentCategory.setProductList(plist);
			}
		}
		return pclist;
	}

	@Override
	public List<ProductCategoryDTO> categoryList(String imageHost,
			String marketId) throws Exception {
		//一级分类
		List<ProductCategoryDTO> pclist = this.listTopProductCategoryByMarketId(Long.valueOf(marketId));
		//一级分类迭代器
		Iterator<ProductCategoryDTO> iterator = pclist.iterator();
		Iterator<ProductCategoryDTO> iterator2 = null;
		Iterator<ProductCategoryDTO> iterator3 = null;
		ProductCategoryDTO currentCategory = null; ProductCategoryDTO currentCategory2 = null;
		ProductCategoryDTO currentCategory3 = null;
		List<ProductCategoryDTO> second = null; List<ProductCategoryDTO> third = null;
		
		while(iterator.hasNext()){
			//当前一级分类
			currentCategory = iterator.next();
			//设置一级分类图片前缀
			currentCategory.setTypeIcon(imageHost + currentCategory.getTypeIcon());
			//二级分类
			second = this.getChildProductCategory(currentCategory.getCategoryId());
			//附到一级分类上
			currentCategory.setChildList(second);
			//二级分类迭代器
			iterator2 = second.iterator();
			while(iterator2.hasNext()){
				//当前二级分类
				currentCategory2 = iterator2.next();
				//设置二级分类图片前缀
				currentCategory2.setTypeIcon(imageHost + currentCategory2.getTypeIcon());
				//三级分类
				third = this.getChildProductCategory(currentCategory2.getCategoryId());
				//当前二级分类找不到三级分类, 则将当前二级分类移除; 注意:正常情况下不会存在没有三级分类的情形
				if(CommonUtils.isEmpty(third)){
					iterator2.remove();
					//继续下一个二级分类
					continue ;
				}
				//三级分类附到二级分类上
				currentCategory2.setChildList(third);
				//三级分类迭代器
				iterator3 = third.iterator();
				while(iterator3.hasNext()){
					//当前三级分类
					currentCategory3 = iterator3.next();
					//设置三级分类图片前缀
					currentCategory3.setTypeIcon(imageHost + currentCategory3.getTypeIcon());
				}
			}
		}
		return pclist;
	}

	@Override
	public List<ProductCategoryDTO> getProductListForSeller(List<String> stateList, String userId, String marketId, String businessId) throws Exception {
		//一级分类
		List<ProductCategoryDTO> pclist = this.listTopProductCategoryByMarketId(Long.valueOf(marketId));
		Iterator<ProductCategoryDTO> iterator = pclist.iterator();
		Iterator<ProductCategoryDTO> iterator2 = null;
		Iterator<ProductCategoryDTO> iterator3 = null;
		ProductCategoryDTO currentCategory = null; 
		ProductCategoryDTO currentCategory2 = null; ProductCategoryDTO currentCategory3 = null;
		List<ProductCategoryDTO> second = null; List<ProductCategoryDTO> third = null;
		List<ProductDto> plist = null;
		Map<String, Object> params = new HashMap<>();
		List<Long> cateIdList = new ArrayList<Long>();
		//遍历一级分类
		while(iterator.hasNext()){
			
			params.clear();	cateIdList.clear();
			//当前一级分类
			currentCategory = iterator.next();
			//二级分类
			second = this.getChildProductCategory(currentCategory.getCategoryId());
			iterator2 = second.iterator();
			//遍历二级分类
			while(iterator2.hasNext()){
				currentCategory2 = iterator2.next();
				//三级分类
				third = this.getChildProductCategory(currentCategory2.getCategoryId());
				iterator3 = third.iterator();
				//遍历三级分类
				while(iterator3.hasNext()){
					currentCategory3 = iterator3.next();
					cateIdList.add(currentCategory3.getCategoryId());
				}
			}
			//分类id集合(当前一级分类下面的所有三级分类id)
			params.put("cateIdList", cateIdList);
			params.put("userId", userId);
			params.put("businessId", businessId);
			params.put("stateList", stateList);
			plist = productService.getProductListForSeller(params);
			//当前分类下若无产品, 则移除该分类
			if(CommonUtils.isEmpty(plist)){
				iterator.remove();
			}else{
				//设置当前一级分类下的产品
				currentCategory.setProductList(plist);
			}
		}
		return pclist;
	}

}
