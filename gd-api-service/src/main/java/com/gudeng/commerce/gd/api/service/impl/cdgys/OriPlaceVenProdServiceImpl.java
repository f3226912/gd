package com.gudeng.commerce.gd.api.service.impl.cdgys;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.api.service.cdgys.OriPlaceVenProdService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.service.AuditInfoService;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;
import com.gudeng.commerce.gd.supplier.service.ProductCategoryService;
import com.gudeng.commerce.gd.supplier.service.ProductPicService;
import com.gudeng.commerce.gd.supplier.service.ProductService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

public class OriPlaceVenProdServiceImpl implements OriPlaceVenProdService{

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(OriPlaceVenProdServiceImpl.class);
	@Autowired
	public GdProperties gdProperties;

	private static ProductService productService;

	private static ProductCategoryService productCategoryService;

	private static ProductPicService productPicService;

	public GdProperties getGdProperties() {
		return gdProperties;
	}

	protected ProductService getHessianProductService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProductUrl();
		if (productService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productService = (ProductService) factory.create(
					ProductService.class, hessianUrl);
		}
		return productService;
	}

	protected ProductCategoryService getHessianProductCategoryService()
			throws MalformedURLException {
		String url = gdProperties.getProductCategoryUrl();
		if (productCategoryService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productCategoryService = (ProductCategoryService) factory.create(
					ProductCategoryService.class, url);
		}

		return productCategoryService;
	}

	protected ProductPicService getHessianProductPicService()
			throws MalformedURLException {
		String url = gdProperties.getProductPictureUrl();
		if (productPicService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productPicService = (ProductPicService) factory.create(
					ProductPicService.class, url);
		}

		return productPicService;
	}

	public AuditInfoService auditInfoService;

	protected AuditInfoService getHessianAuditInfoService()
			throws MalformedURLException {
		String url = gdProperties.getAuditInfoServiceUrl();
		if (auditInfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			auditInfoService = (AuditInfoService) factory.create(
					AuditInfoService.class, url);
		}
		return auditInfoService;
	}

	public static class Constants {
		/**
		 * 出售中
		 */
		public static final String PRODUCT_LIST_ONSALE = "1";
		/**
		 * 待审核
		 */
		public static final String PRODUCT_LIST_NEEDCHECK = "2";
		/**
		 * 已下架
		 */
		public static final String PRODUCT_LIST_OFF = "3";
		/**
		 * 全部
		 */
		public static final String PRODUCT_LIST_All = "0";
		/**
		 * 制单
		 */
		public static final String PRODUCT_LIST_MAKE_ORDER = "4";

		/**
		 * 下架
		 */
		public static final String PRODUCT_OPTION_OFFLINE = "1";
		/**
		 * 上架
		 */
		public static final String PRODUCT_OPTION_ONLINE = "2";

	}

	@Override
	public List<ProductCategoryDTO> categoryList() throws Exception {
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		List<ProductCategoryDTO> pclist = getHessianProductCategoryService().categoryList(imageHost, "3");
/*		//一级分类
		List<ProductCategoryDTO> pclist = getHessianProductCategoryService().listTopProductCategoryByMarketId(Long.valueOf(3));
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
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
			second = getHessianProductCategoryService().getChildProductCategory(currentCategory.getCategoryId());
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
				third = getHessianProductCategoryService().getChildProductCategory(currentCategory2.getCategoryId());
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
		}*/
		return pclist;
	}

	@Override
	public List<ProductCategoryDTO> getProductList(String option, String marketId, String businessId) throws Exception {
/*		//一级分类
		List<ProductCategoryDTO> pclist = getHessianProductCategoryService().listTopProductCategoryByMarketId(Long.valueOf(marketId));
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
			second = getHessianProductCategoryService().getChildProductCategory(currentCategory.getCategoryId());
			iterator2 = second.iterator();
			while(iterator2.hasNext()){
				currentCategory2 = iterator2.next();
				//三级分类
				third = getHessianProductCategoryService().getChildProductCategory(currentCategory2.getCategoryId());
				iterator3 = third.iterator();
				while(iterator3.hasNext()){
					currentCategory3 = iterator3.next();
					cateIdList.add(currentCategory3.getCategoryId());

				}
			}

			//分类id集合(当前一级分类下面的所有三级分类id)
			params.put("cateIdList", cateIdList);
			params.put("businessId", businessId);
			if (Constants.PRODUCT_LIST_ONSALE.equals(option)){//出售中
				params.put("state", ProductStatusEnum.ON.getkey());
				params.put("stockCountUp", "1");
			} else if (Constants.PRODUCT_LIST_NEEDCHECK.equals(option)){//待审核
				List<String> stateList = new ArrayList<String>();
				stateList.add(ProductStatusEnum.NEEDAUDIT.getkey());
				stateList.add(ProductStatusEnum.REFUSE.getkey());
				params.put("stateList", stateList);
			} else if (Constants.PRODUCT_LIST_OFF.equals(option)){//已下架
				List<String> stateList = new ArrayList<String>();
				stateList.add(ProductStatusEnum.DRAFT.getkey());
				stateList.add(ProductStatusEnum.OFF.getkey());
				params.put("stateList", stateList);
			} else if (Constants.PRODUCT_LIST_All.equals(option)){
				//排除已删除
				List<String> stateList = new ArrayList<String>();
				stateList.add(ProductStatusEnum.DRAFT.getkey());
				stateList.add(ProductStatusEnum.NEEDAUDIT.getkey());
				stateList.add(ProductStatusEnum.REFUSE.getkey());
				stateList.add(ProductStatusEnum.ON.getkey());
				stateList.add(ProductStatusEnum.OFF.getkey());
				params.put("stateList", stateList);
			}
			plist = getHessianProductService().getProductsForOriPlaceVen(params);
			//当前分类下若无产品, 则移除该分类
			if(CommonUtils.isEmpty(plist)){
				iterator.remove();
			}else{
				currentCategory.setProductList(plist);
			}
		}*/
		List<ProductCategoryDTO> pclist = getHessianProductCategoryService().getProductList(option, marketId, businessId);
		return pclist;
	}

	@Override
	public ErrorCodeEnum productUpAndDown(Map<String,Object> map) throws MalformedURLException, Exception {

		String productId = String.valueOf(map.get("productId"));
		String option = String.valueOf(map.get("option"));

		ProductDto old = getHessianProductService().getById(productId);
		Date now = new Date();
		String updateTime = DateUtil.getDate(now, DateUtil.DATE_FORMAT_DATETIME);

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("productId", productId);
		params.put("updateUserId", map.get("userId"));
		params.put("updateTimeString", updateTime);
		if (ProductStatusEnum.NEEDAUDIT.getkey().equals(old.getState())){
			return ErrorCodeEnum.PRODUCT_IS_AUDITING;
		}
		if (Constants.PRODUCT_OPTION_OFFLINE.equals(option)){//	下架
			if(!ProductStatusEnum.ON.getkey().equalsIgnoreCase(old.getState())){
				return ErrorCodeEnum.PRODUCT_NOT_ON_SALE;
			}
			params.put("state", ProductStatusEnum.OFF.getkey());
		}else if (Constants.PRODUCT_OPTION_ONLINE.equals(option)){	//上架
			if (ProductStatusEnum.ON.getkey().equals(old.getState())){
				return ErrorCodeEnum.PRODUCT_ALREADY_ON_SALE;
			}
			if (ProductStatusEnum.DRAFT.getkey().equals(old.getState())
					|| ProductStatusEnum.REFUSE.getkey().equals(old.getState())){
				//对新增的商品进行上架操作, 商品变为待审核状态;
				//对于被驳回的商品, 无论是否改变过关键内容, 进行上架操作都进入待审核状态
				params.put("state", ProductStatusEnum.NEEDAUDIT.getkey());
				params.put("editSign", Constant.Product.PRODUCT_EDIT_SIGN_NO);
			}else if (ProductStatusEnum.OFF.getkey().equals(old.getState())){
				//对下架状态的商品进行上架操作, 如果有关键内容被修改过，则进入待审核状态，否则变为上架状态
				//监测商品关键内容是否更改过
				if (Constant.Product.PRODUCT_EDIT_SIGN_YES.equals(old.getEditSign())){
					params.put("state", ProductStatusEnum.NEEDAUDIT.getkey());
					params.put("editSign", Constant.Product.PRODUCT_EDIT_SIGN_NO);
				}else {
					//未更改, 上架
					params.put("state", ProductStatusEnum.ON.getkey());
				}
			}else{
				logger.info("保持原状态不变: {}", new Object[]{old.getState()});
				params.put("state", old.getState());
			}
			//更新有效期
			String expirationDateString = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIME)
						.format(DateUtil.addDays(now, Integer.valueOf(old.getInfoLifeDay())));
			params.put("expirationDateString", expirationDateString);
		}
		getHessianProductService().updateProductStatus(params);
		//清空编辑标志
		if (!CommonUtils.isEmpty(params.get("editSign"))){
			getHessianProductService().updateProduct(params);
		}
		
		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public int[] productBatchUpAndDown(Map<String, Object> map)
			throws MalformedURLException, Exception {

		String[] productIds = (String[]) map.get("productIds");
		String option = String.valueOf(map.get("option"));
		String updateUserId = String.valueOf(map.get("userId"));

		Date now = new Date();
		String updateTime = DateUtil.getDate(now, DateUtil.DATE_FORMAT_DATETIME);
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();

		List<ProductDto> olds = getHessianProductService().getProductsByIds(productIds);

		for (ProductDto current : olds){
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("updateUserId", updateUserId);
			paramsMap.put("updateTimeString", updateTime);
			paramsMap.put("productId", current.getProductId());

			if (Constants.PRODUCT_OPTION_OFFLINE.equals(option)){//	下架
				if(!ProductStatusEnum.ON.getkey().equalsIgnoreCase(current.getState())){
					throw new Exception("只能对出售中状态的产品进行下架架操作...");
//					result.put("status", "0");
//					result.put("message", "<下架操作>只能操作销售中的产品");
//					return result;
				}
				paramsMap.put("state", ProductStatusEnum.OFF.getkey());
			}else if (Constants.PRODUCT_OPTION_ONLINE.equals(option)){	//上架
				if (ProductStatusEnum.DRAFT.getkey().equals(current.getState())
						|| ProductStatusEnum.REFUSE.getkey().equals(current.getState())){
					//对新增的商品进行上架操作, 商品变为待审核状态;
					//对于被驳回的商品, 无论是否改变过关键内容, 进行上架操作都进入待审核状态
					paramsMap.put("state", ProductStatusEnum.NEEDAUDIT.getkey());
				}else if (ProductStatusEnum.OFF.getkey().equals(current.getState())){
					//对下架状态的商品进行上架操作, 如果有关键内容被修改过，则进入待审核状态，否则变为上架状态
					//监测商品关键内容是否更改过
					if (Constant.Product.PRODUCT_EDIT_SIGN_YES.equals(current.getEditSign())){
						paramsMap.put("state", ProductStatusEnum.NEEDAUDIT.getkey());
					}else {
						paramsMap.put("state", ProductStatusEnum.ON.getkey());
					}
				}
				//有效期
				String expirationDateString = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIME)
							.format(DateUtil.addDays(now, Integer.valueOf(current.getInfoLifeDay())));
				paramsMap.put("expirationDateString", expirationDateString);
			}

			paramList.add(paramsMap);
		}
		return getHessianProductService().batchUpdateProductStatus(paramList);
	}

	@Override
	public Long persistProductViaEntity(ProductEntity productEntity)
			throws Exception {
		return getHessianProductService().persistProductViaEntity(productEntity);
	}

	@Override
	public long addProductPicViaEntity(ProductPictureEntity pictureEntity)
			throws Exception {
		return getHessianProductPicService().addProductPicViaEntity(pictureEntity);
	}

	@Override
	public ProductDto getProductById(String productId) throws Exception {
		return getHessianProductService().getById(productId);
	}

	@Override
	public int updateProduct(ProductDto productDto) throws Exception {
		return getHessianProductService().updateProduct(productDto);
	}

	@Override
	public List<ProductPictureDto> getPicturesByProductId(	List<String> productIdList) throws MalformedURLException, Exception {
		if (CommonUtils.isEmpty(productIdList)){
			return null;
		}
		return getHessianProductPicService().getPictureListByProductId(productIdList);
	}

	@Override
	public List<ProductPictureDto> getPictureByProductId(String productId)
			throws Exception {
		return getHessianProductPicService().getPictureByProductId(productId);
	}

	@Override
	public int deletePicByProductId(String productId) throws Exception {
		return getHessianProductPicService().deletePicByProductId(productId);
	}

	@Override
	public Map<String, AuditInfoDTO> getAuditInfos(List<String> mainIds,
			Map<String, Object> params) throws Exception {
		return getHessianAuditInfoService().getAuditInfos(mainIds, params);
	}

	@Override
	public int updateProductStatus(Map map) throws Exception {
		return getHessianProductService().updateProductStatus(map);
	}

}
