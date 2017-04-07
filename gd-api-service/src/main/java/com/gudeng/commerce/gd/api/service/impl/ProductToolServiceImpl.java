package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.dto.ProductCateResultDTO;
import com.gudeng.commerce.gd.api.dto.ProductListAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.api.service.OrderSubToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.impl.cdgys.OriPlaceVenProdServiceImpl.Constants;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.FormatUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.service.AreaService;
import com.gudeng.commerce.gd.customer.service.AuditInfoService;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.ProductSolrDTO;
import com.gudeng.commerce.gd.search.service.SearchProductService;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductClassDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPictureDto;
import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;
import com.gudeng.commerce.gd.supplier.service.ProductCategoryService;
import com.gudeng.commerce.gd.supplier.service.ProductPicService;
import com.gudeng.commerce.gd.supplier.service.ProductService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Service
public class ProductToolServiceImpl implements ProductToolService {

	private static ProductService productService;
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(ProductToolServiceImpl.class);

	@Autowired
	public GdProperties gdProperties;
	@Autowired
	public OrderSubToolService orderSubToolService;
	
	public SearchProductService searchProductService;
	
	public AuditInfoService auditInfoService;

	public GdProperties getGdProperties() {
		return gdProperties;
	}

	protected SearchProductService getHessianSearchProductService()
			throws MalformedURLException {
		String url = gdProperties.getSearchProductServiceUrl();
		if (searchProductService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			searchProductService = (SearchProductService) factory.create(
					SearchProductService.class, url);
		}
		return searchProductService;
	}
	
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
	
	private static ProductPicService productPicService;
	
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

	private static AreaService areaService;
	
	protected AreaService getHessianAreaService()
			throws MalformedURLException {
		String url = gdProperties.getAreaUrl();
		if (areaService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			areaService = (AreaService) factory.create(
					AreaService.class, url);
		}
		
		return areaService;
	}
	
	private static ProductCategoryService productCategoryService;
	
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
	@Override
	public int auditProduct(Map<String, Object> map) throws Exception {
		return 0;
	}

	@Override
	public List<ProductDto> getList(Map<String, Object> map) throws Exception {
		return null;
	}

	@Override
	public List<ProductCategoryDTO> listTopProductCategory() throws Exception {
		return null;
	}

	@Override
	public List<ProductCategoryDTO> getChildProductCategory(Long id)
			throws Exception {
		return getHessianProductCategoryService().getChildProductCategory(id);
	}

	@Override
	public List<ProductPictureDto> getPictureByProductId(String productId)
			throws Exception {
		return getHessianProductPicService().getPictureByProductId(productId);
	}

	@Override
	public ProductPictureDto addProductPic(ProductPictureDto pictureDto)
			throws Exception {
		return null;
	}

	@Override
	public long addProductPicViaEntity(ProductPictureEntity pictureEntity)
			throws Exception {
		return getHessianProductPicService().addProductPicViaEntity(pictureEntity);
	}

	@Override
	public int updateProductPic(ProductPictureDto pictureDto) throws Exception {
		return 0;
	}

	@Override
	public Long persistProductViaEntity(ProductEntity productEntity)
			throws Exception {
		return hessianProductService().persistProductViaEntity(productEntity);
	}

	@Override
	public ProductDto addProduct(ProductDto productDto) throws Exception {
		return null;
	}

	@Override
	public int updateProduct(ProductDto productDto) throws Exception {
		return hessianProductService().updateProduct(productDto);
	}
	@Override
	public int updateProduct(Map map) throws Exception {
		return hessianProductService().updateProduct(map);
	}
	
	@Override
	public int updateProductStatus(Map map) throws Exception{
		return hessianProductService().updateProductStatus(map);
	}
	
	@Override
	public ProductDto getByProductId(String productId) throws Exception {
		return hessianProductService().getByIdWithoutPicType(productId);
	}

	@Override
	public ProductDto getByProductName(String productName) throws Exception {
		return null;
	}

	@Override
	public List<ProductDto> getProductList(Map<String, Object> map)
			throws Exception {
		return hessianProductService().getProductList(map);
	}

	@Override
	public int getCount(Map<String, Object> map) throws Exception {
		return 0;
	}

	@Override
	public int deleteProduct(String productId) throws Exception {
		//删除商品价格区间信息
		hessianProductService().batchDeletePrices(productId);
		//逻辑删除产品
		return hessianProductService().deleteProduct(productId);
	}

	@Override
	public int[] batchDeleteProduct(String[] productIds) throws Exception {
		return null;
	}

	@Override
	public List<?> getByCateId(Long cateId) {
		try {
			List<?> listProducts = hessianProductService().getByCateId(cateId);
			return listProducts;
		} catch (MalformedURLException e) {
			logger.warn("根据分类ID获取产品异常",e);
		}
		return null;

	}

	private ProductService hessianProductService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getProductUrl();
		if (productService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productService = (ProductService) factory.create(
					ProductService.class, hessianUrl);
		}
		return productService;
	}

	@Override
	public List<ProductListAppDTO> getShopProductList(Map<String, Object> map)
			throws Exception {
		List<ProductDto> list = hessianProductService().getShopProductList(map);
		List<ProductListAppDTO> appList = new ArrayList<ProductListAppDTO>();
		String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
		for (ProductDto productDTO : list) {
			Double oringialPrice=productDTO.getPrice();
			String formatedPrice=MoneyUtil.format(oringialPrice);
			ProductListAppDTO appDTO = new ProductListAppDTO();
//			appDTO.setDescription(productDTO.getDescription());
			appDTO.setFormattedPrice(formatedPrice);
			appDTO.setFormattedStock(FormatUtils.formattedStock(productDTO.getStockCount(), productDTO.getUnitName()));
			appDTO.setHasFocused(productDTO.getHasFocused());
			appDTO.setMarketId(productDTO.getMarketId());
			appDTO.setPrice(productDTO.getPrice());
			appDTO.setProductId(productDTO.getProductId());
			appDTO.setProductName(productDTO.getProductName());
			appDTO.setStockCount(productDTO.getStockCount());
			appDTO.setUnitName(productDTO.getUnitName());
			appDTO.setUrlOrg(imageHost + productDTO.getUrlOrg());
			appDTO.setCreateTime(productDTO.getUpdateTime());
			appList.add(appDTO);
		}
		return addSubIntoProduct(appList);
	}
	
	@Override
	public List<ProductListAppDTO> getShopProductListNew(Map<String, Object> map)
			throws Exception {
		List<ProductDto> list = hessianProductService().getShopProductListNew(map);
		List<ProductListAppDTO> appList = new ArrayList<ProductListAppDTO>();
		String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd");
		for (ProductDto productDTO : list) {
			Double oringialPrice=productDTO.getPrice();
			String formatedPrice=MoneyUtil.format(oringialPrice);
			ProductListAppDTO appDTO = new ProductListAppDTO();
//			appDTO.setDescription(productDTO.getDescription());
			appDTO.setFormattedPrice(formatedPrice);
			appDTO.setFormattedStock(FormatUtils.formattedStock(productDTO.getStockCount(), productDTO.getUnitName()));
			appDTO.setHasFocused(productDTO.getHasFocused());
			appDTO.setMarketId(productDTO.getMarketId());
			appDTO.setPrice(productDTO.getPrice());
			appDTO.setProductId(productDTO.getProductId());
			appDTO.setProductName(productDTO.getProductName());
			appDTO.setStockCount(productDTO.getStockCount());
			appDTO.setUnitName(productDTO.getUnitName());
			appDTO.setUrlOrg(imageHost + productDTO.getUrlOrg());
			appDTO.setCreateTime(productDTO.getUpdateTime());
			appDTO.setCreateTimeStr(format1.format(productDTO.getUpdateTime()==null?productDTO.getCreateTime():productDTO.getUpdateTime()));
			appDTO.setOriginProvinceNam(productDTO.getOriginProvinceName());
			appDTO.setOriginCityName(productDTO.getOriginCityName());
			appDTO.setOriginAreaName(productDTO.getOriginAreaName());
			appDTO.setCertifstatus(productDTO.getCertifstatus());
			appList.add(appDTO);
		}
		return addSubIntoProduct(appList);
	}	

	/**
	 * 添加是否有补贴进产品列表
	 * @param plist
	 * @throws Exception
	 */
	private List<ProductListAppDTO> addSubIntoProduct(List<ProductListAppDTO> appList) throws Exception{
		if (appList == null || appList.isEmpty()){
			return appList;
		}
		List<OrderProductDetailDTO> orderProductList = new ArrayList<OrderProductDetailDTO>();
		for (ProductListAppDTO appDTO :appList){
			OrderProductDetailDTO detailDTO = new OrderProductDetailDTO();
			detailDTO.setProductId(appDTO.getProductId().intValue());
			orderProductList.add(detailDTO);
		}
		List<OrderProductDetailDTO> resultList = orderSubToolService.queryProductSubList(orderProductList);
		for (ProductListAppDTO appDTO :appList){
			for(OrderProductDetailDTO pDTO : resultList){
				if(pDTO.getProductId() == appDTO.getProductId().intValue()){
					appDTO.setHasSub(Integer.parseInt(pDTO.getHasBuySub()));
				}
			}
		}
		
		return appList;
	}
	
	@Override
	public int getShopsProductTotal(Map<String, Object> map) throws Exception {
		return hessianProductService().getShopsProductTotal(map);
	}
	
	@Override
	public int getShopsProductTotalNew(Map<String, Object> map) throws Exception {
		return hessianProductService().getShopsProductTotalNew(map);
	}


	/**
	 * 根据店铺名称搜索 
	 * 
	 * @param string 
	 * @return ProductSolrDTO
	 * 
	 */
	public List<ProductSolrDTO> getBySearch(String pName)throws Exception{
		return getHessianSearchProductService().getBySearch(pName);
	}
	

	/**
	 * 根据productQueryBean 搜索 
	 * 
	 * @param productQueryBean 
	 * @return ProductSolrDTO
	 * 
	 */
	public List<ProductSolrDTO> getByQueryBean(ProductQueryBean productQueryBean)throws Exception{
		return getHessianSearchProductService().getByQueryBean( productQueryBean);
	}
	
		
	/**
	 * 根据商铺分类，获取所有的产品分页列表
	 * 
	 * */
	public List<ProductCateResultDTO> getProductCateResult(ProductQueryBean productQueryBean) throws Exception{
		
		List<ProductCateResultDTO> resultList=new ArrayList<ProductCateResultDTO>();	
		List<ProductCategoryDTO> allCateList =getHessianProductCategoryService().listTopProductCategoryByMarketId(productQueryBean.getMarketId()); 
		for (ProductCategoryDTO productCategoryDTO : allCateList) {
			ProductCateResultDTO pdt=new ProductCateResultDTO();
			pdt.setCateName(productCategoryDTO.getCateName());
			pdt.setCateId(productCategoryDTO.getCategoryId().toString());
			if(productQueryBean.getCateId().longValue()==productCategoryDTO.getCategoryId().longValue()){
				pdt.setIsChecked(true);
			}
//			productQueryBean.setCateId(productCategoryDTO.getCategoryId());
//			ProductSearchResultDTO psrdto=getHessianSearchProductService().getByProductQueryBean(productQueryBean);
//			CommonPageDTO pageDTO = new CommonPageDTO(productQueryBean.getPageNum(), productQueryBean.getPageSize());
//			pageDTO.setRecordCount(psrdto.getCount());//设置翻页
//			pageDTO.initiatePage(psrdto.getCount().intValue());
//			List<PushProductDTO> pushAdInfoList =new ArrayList<PushProductDTO>();
//			pageDTO.setRecordList(translate(psrdto.getList(),pushAdInfoList) );	
//			pdt.setPageDTO(pageDTO);
			resultList.add(pdt);
		}
		return resultList;
	}
	
	
	
	
	/**
	 * 根据productQueryBean 搜索 
	 * 
	 * @param productQueryBean 
	 * @return ProductSolrDTO
	 * 
	 */
	public ProductSearchResultDTO getByProductQueryBean(ProductQueryBean productQueryBean) throws Exception{
		return getHessianSearchProductService().getByProductQueryBean(productQueryBean);
	}
	
	
	public List<PushProductDTO> translate(List<ProductSolrDTO> list,
			List<PushProductDTO> pushAdInfoList) throws Exception{
		areaService=getHessianAreaService();
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		if(list!=null && list.size()>0){
			for(ProductSolrDTO psdto:list){
				PushProductDTO ppdto=new PushProductDTO();
				ppdto.setBusinessId(Long.valueOf(psdto.getBusinessId()));
				ppdto.setFormattedPrice(MoneyUtil.format(psdto.getPrice()));
//				ppdto.setImageUrl(imageHost+psdto.getUrlOrg());//app图片地址
//				if(psdto.getAddCount()!=null && psdto.getAddCount()>0){
//					ppdto.setImageUrl(imageHost+psdto.getAddPicUrl());//app图片地址
//				}else{
//					ppdto.setImageUrl(imageHost+psdto.getUrl170());//app图片地址
//				}
				ppdto.setImageUrl(imageHost+psdto.getUrl170());//app图片地址

				ppdto.setMarketId(Long.valueOf(psdto.getMarketId()));
				ppdto.setMarketName(psdto.getMarketName());
				ppdto.setMobile(psdto.getMobile());
				ppdto.setMemberGrade(psdto.getMemberGrade() == null ? "0" : psdto.getMemberGrade().toString());
				ppdto.setPrice(psdto.getPrice());
				ppdto.setProductId(Long.valueOf(psdto.getId()));
				ppdto.setProductName(psdto.getName());
				ppdto.setShopsName(psdto.getShopsName());
				ppdto.setStartTime(DateUtil.toString(psdto.getCreateTime(),DateUtil.DATE_FORMAT_DATETIME));
				ppdto.setUpdatePriceTime(DateUtil.toString(psdto.getUpdatePriceTime(),DateUtil.DATE_FORMAT_DATETIME));
				ppdto.setUpdateTime(DateUtil.toString(psdto.getUpdateTime(),DateUtil.DATE_FORMAT_DATETIME));
//				ppdto.setUnitName("吨");//solr中增加单位名称
				ppdto.setUnitName(psdto.getUnitName());
				ppdto.setManagementType(psdto.getManagementType());
				ProductDto productInfo = hessianProductService().getById(psdto.getId()+"");
				ppdto.setStockCount(productInfo == null ? 0 : productInfo.getStockCount());
				psdto.getProvinceId();
				psdto.getCityId();
				psdto.getAreaId();
				String originAddress="";
				if(psdto.getProvinceId()!=null && psdto.getProvinceId()!=0 && psdto.getProvinceId()!=-1){
				    originAddress+=areaService.getArea(String.valueOf(psdto.getProvinceId())).getArea();
					if(psdto.getCityId()!=null && psdto.getCityId()!=0 && psdto.getCityId()!=-1){
						originAddress+=areaService.getArea(String.valueOf(psdto.getCityId())).getArea();
						if(psdto.getAreaId()!=null && psdto.getAreaId()!=0 && psdto.getAreaId()!=-1){
							originAddress+=areaService.getArea(String.valueOf(psdto.getAreaId())).getArea();
						}
					}
				}
				ppdto.setOriginAddress(originAddress);
				//2016年9月19日 增加			
				/*
				<field column="certifstatus" name="certifstatus"/>  <!-- 产地标识认证 -->
				<field column="comstatus" name="comstatus"/>   <!-- 企业认证 -->
				<field column="ccstatus" name="ccstatus"/>  <!-- 合作社认证 -->
				<field column="cbstatus" name="cbstatus"/>  <!-- 基地认证 -->
				*/
				ppdto.setCertifstatus(psdto.getCertifstatus());
				ppdto.setComstatus(psdto.getComstatus());
				ppdto.setCcstatus(psdto.getCcstatus());
				ppdto.setCbstatus(0);
				
//				psdto.getUpdatePriceTime()
				pushAdInfoList.add(ppdto);
			}
		}
		
		return pushAdInfoList;

	}
	

	@Override
	public List<ProductCategoryDTO> listTopProductCategoryByMarketId(
			Long marketId) throws Exception {
		return getHessianProductCategoryService().listTopProductCategoryByMarketId(marketId);
	}

	@Override
	public int deletePicByProductId(String productId) throws Exception {
		return getHessianProductPicService().deletePicByProductId(productId);
	}
	@Override
	public int batchUpdateStockCount(List<Map<String, Object>> stockList)
			throws Exception {
		return hessianProductService().batchUpdateStockCount(stockList);
	}
	@Override
	public ProductPictureDto getPicture(String productId, String picType)
			throws Exception {
		String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
		ProductPictureDto pictureDto = getHessianProductPicService().getPicture(productId, picType);
		pictureDto.setUrlOrg(imageHost + pictureDto.getUrlOrg());
		return pictureDto;
	}
	@Override
	public ProductDto getProductById(String productId) throws Exception {
		return hessianProductService().getById(productId);
	}
	@Override
	public List<ProductCategoryDTO> getCategoryAncestors(Long id) throws Exception {
		return getHessianProductCategoryService().getCategoryAncestors(id);
	}
	@Override
	public List<ProductDto> getShopProducts(Map<String, Object> map)
			throws Exception {
		return hessianProductService().getShopProductList(map);
	}
	@Override
	public List<ProductDto> getShopProductTotalList(Map<String, Object> map)
			throws Exception {
		List<ProductDto> plist = hessianProductService().getShopProductTotalList(map);
		//补贴判断+设置图片路径+排序+审核原因
		plist = subsidy(plist);
		return plist;
	}
	/**
	 * 补贴判断+设置图片路径
	 * @param plist
	 * @throws Exception
	 */
	private List<ProductDto> subsidy(List<ProductDto> plist) throws Exception{
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		if (plist == null || plist.isEmpty()){
			return null;
		}
		//审核不通过的产品
		List<String> refusedProductIds = new ArrayList<String>();
		List<OrderProductDetailDTO> orderProductDetails = new ArrayList<OrderProductDetailDTO>();
		for (ProductDto item :plist){
			OrderProductDetailDTO detailDTO = new OrderProductDetailDTO();
			detailDTO.setProductId(item.getProductId().intValue());
			orderProductDetails.add(detailDTO);
			//设置原始图片路径给app使用(不带ip+端口)
			item.setUrl400(item.getUrlOrg());
			//设置图片路径
			item.setUrlOrg(imageHost + item.getUrlOrg());
			if ("2".equalsIgnoreCase(item.getState())){
				refusedProductIds.add(item.getProductId().toString());
			}
		}
		//获取审核不通过的产品的审核信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "1");
		params.put("status", "0");
		Map<String, AuditInfoDTO> auditInfos = getHessianAuditInfoService().getAuditInfos(refusedProductIds, params);
		ProductDto currentProduct = null; OrderProductDetailDTO currentResult = null; AuditInfoDTO currentInfo = null;
		//补贴判定结果
		List<OrderProductDetailDTO> result = orderSubToolService.queryProductSubList(orderProductDetails);
		if (logger.isInfoEnabled()){
			if (result == null || result.isEmpty()){
				logger.info("orderProductDetails result is null", new Object[]{});
			}else{
				String format = "orderProductDetails init productIds : {}, after call, result.size : {}, messages : {}";
				String messages = "[ ";
				for(OrderProductDetailDTO item : result){
					messages += " { productId :" + item.getProductId() + " , hasBuySub : " + item.getHasBuySub()
							+ ", hasSellSub" + item.getHasSellSub() + "} "; 
				}
				messages += "]";
				String ids = "[";
				for(OrderProductDetailDTO item : orderProductDetails){
					ids += item.getProductId()+ ", ";
				}
				ids += "]";
				logger.info(format, new Object[]{ids, result.size(), messages});
			}
		}
		
		List<ProductDto> uplist = new ArrayList<ProductDto>();
		List<ProductDto> needChecklist = new ArrayList<ProductDto>();
		List<ProductDto> unpasslist = new ArrayList<ProductDto>();
		List<ProductDto> downlist = new ArrayList<ProductDto>();
		for (int i = 0, len = plist.size(); i < len ; i++){
			currentProduct = plist.get(i); 
			if (auditInfos != null){
				//设置不通过的产品的审核原因
				currentInfo = auditInfos.get(currentProduct.getProductId().toString());
				if ( currentInfo != null){
					List<Object> infos = new ArrayList<Object>();
					if (currentInfo.getOtherReason() == null){
						infos.add("");
					}else{
						infos.add(currentInfo.getOtherReason());
					}
					if (currentInfo.getReason() == null){
						infos.add("");
					}else{
						infos.add(currentInfo.getReason());
					}
					currentProduct.setAuditInfos(infos);
				}
			}
			for (int j = 0 ; j < len; j++){
				currentResult = result.get(j) ;
				if (String.valueOf(currentProduct.getProductId())
						.equalsIgnoreCase(String.valueOf(currentResult.getProductId()))){
					currentProduct.setHasBuySub(currentResult.getHasBuySub());
					currentProduct.setHasSellSub(currentResult.getHasSellSub());
					break ;
				}
			}
			if (ProductStatusEnum.ON.getkey().equalsIgnoreCase(currentProduct.getState())){
				uplist.add(currentProduct);
			}else if (ProductStatusEnum.NEEDAUDIT.getkey().equalsIgnoreCase(currentProduct.getState())){
				needChecklist.add(currentProduct);
			}else if (ProductStatusEnum.REFUSE.getkey().equalsIgnoreCase(currentProduct.getState())){
				unpasslist.add(currentProduct);
			}else if (ProductStatusEnum.OFF.getkey().equalsIgnoreCase(currentProduct.getState())){
				downlist.add(currentProduct);
			}
		}
		plist.clear();
		plist.addAll(uplist);
		plist.addAll(needChecklist);
		plist.addAll(unpasslist);
		plist.addAll(downlist);
/*		Map<String,String> map = new HashMap<String,String>();
		for (OrderProductDetailDTO item : result){
			map.put(item.getProductId().toString(), item.getHasBuySub());
		}
		for (ProductDto item :plist){
			item.setHasBuySub(map.get(item.getProductId().toString()));
		}*/
		return plist;
		
	}
	@Override
	public List<ProductDto> getListByIds(List<Long> productIdList)
			throws Exception {
		return hessianProductService().getListByIds(productIdList);
	}
	
	@Override
	public List<ProductPriceDto> getLadderPriceByProductId(String productId)
			throws Exception {
		return hessianProductService().getLadderPriceByProductId(productId);
	}

	@Override
	public List<ProductCategoryDTO> getCategoryAndProductListForSeller(List<String> stateList, String userId, String marketId, String businessId) throws Exception {
		//查询一级分类以及分类下的产品
		List<ProductCategoryDTO> pclist = getHessianProductCategoryService().getProductListForSeller(stateList, userId, marketId, businessId);
		List<ProductDto> allProducts = new ArrayList<ProductDto>();
		for (Iterator<ProductCategoryDTO> it = pclist.iterator(); it.hasNext();){
			allProducts.addAll(it.next().getProductList());
		}
		List<OrderProductDetailDTO> orderProductDetails = new ArrayList<OrderProductDetailDTO>();
		for (ProductDto item : allProducts){
			OrderProductDetailDTO detailDTO = new OrderProductDetailDTO();
			detailDTO.setProductId(item.getProductId().intValue());
			orderProductDetails.add(detailDTO);
		}
		if (!orderProductDetails.isEmpty()){
			//补贴判定结果
			List<OrderProductDetailDTO> result = orderSubToolService.queryProductSubList(orderProductDetails);
			OrderProductDetailDTO currentResult = null;
			//设置产品是否补贴
			for (ProductDto item : allProducts){
				for (int j = 0, len = result.size() ; j < len; j++){
					currentResult = result.get(j) ;
					if (String.valueOf(item.getProductId()).equalsIgnoreCase(String.valueOf(currentResult.getProductId()))){
						item.setHasBuySub(currentResult.getHasBuySub());
						item.setHasSellSub(currentResult.getHasSellSub());
						break ;
					}
				}
			}
		}
		return pclist;
	}

	@Override
	public List<ProductPictureDto> getPicturesByProductId(
			List<String> productIdList) throws MalformedURLException, Exception {
		if (CommonUtils.isEmpty(productIdList)){
			return null;
		}
		return getHessianProductPicService().getPictureListByProductId(productIdList);
	}

	@Override
	public Map<String, AuditInfoDTO> getAuditInfos(List<String> mainIds,
			Map<String, Object> params) throws Exception {
		return getHessianAuditInfoService().getAuditInfos(mainIds, params);
	}

	@Override
	public List<ProductCategoryDTO> categoryList(String marketId)
			throws Exception {
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		List<ProductCategoryDTO> pclist = getHessianProductCategoryService().categoryList(imageHost, marketId);
		return pclist;
	}
	
	@Override
	public ErrorCodeEnum productUpAndDown(Map<String,Object> map) throws MalformedURLException, Exception {
		
		String productId = String.valueOf(map.get("productId"));
		String option = String.valueOf(map.get("option"));
		
		ProductDto old = hessianProductService().getById(productId);
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
		hessianProductService().updateProductStatus(params);
		//清空编辑标志
		if (!CommonUtils.isEmpty(params.get("editSign"))){
			hessianProductService().updateProduct(params);
		}
		
		return ErrorCodeEnum.SUCCESS;
	}


	@Override
	public List<ProductCategoryDTO> getListTopProductCategoryByMarketId(Long marketId) throws Exception {
		return getHessianProductCategoryService().listTopProductCategoryByMarketId(marketId);
	}

	@Override
	public List<ProductCateResultDTO> getProductAreaResult(ProductQueryBean productQueryBean) throws Exception {
		List<ProductCateResultDTO> resultList=new ArrayList<ProductCateResultDTO>();	
		List<ProductCategoryDTO> allCateList =getHessianProductCategoryService().listTopProductCategoryByMarketId(productQueryBean.getMarketId()); 
		int count=0;
		for (ProductCategoryDTO productCategoryDTO : allCateList) {
			ProductCateResultDTO pdt=new ProductCateResultDTO();
			pdt.setCateName(productCategoryDTO.getCateName());
			pdt.setCateId(productCategoryDTO.getCategoryId().toString());
//			if(productQueryBean.getCateId()==productCategoryDTO.getCategoryId()){
//				pdt.setIsChecked(true);
//			}
//			productQueryBean.setCateId(productCategoryDTO.getCategoryId());
//			ProductSearchResultDTO psrdto=getHessianSearchProductService().getByProductQueryBean(productQueryBean);
//			if(psrdto.getCount()!=null && psrdto.getCount()>0){
//				CommonPageDTO pageDTO = new CommonPageDTO(productQueryBean.getPageNum(), productQueryBean.getPageSize());
//				pageDTO.setRecordCount(psrdto.getCount());//设置翻页
//				pageDTO.initiatePage(psrdto.getCount().intValue());
//				List<PushProductDTO> pushAdInfoList =new ArrayList<PushProductDTO>();
//				pageDTO.setRecordList(translate(psrdto.getList(),pushAdInfoList) );	
//				pdt.setPageDTO(pageDTO);
				resultList.add(pdt);
//				count++;
//			}
		}
//		if(count>=2){
			ProductCateResultDTO pdt=new ProductCateResultDTO();
			pdt.setCateName("全部");
			pdt.setCateId("");
			pdt.setIsChecked(true);
//			productQueryBean.setCateId(null);
//			ProductSearchResultDTO psrdto=getHessianSearchProductService().getByProductQueryBean(productQueryBean);
//			if(psrdto.getCount()!=null && psrdto.getCount()>0){
//				CommonPageDTO pageDTO = new CommonPageDTO(productQueryBean.getPageNum(), productQueryBean.getPageSize());
//				pageDTO.setRecordCount(psrdto.getCount());//设置翻页
//				pageDTO.initiatePage(psrdto.getCount().intValue());
//				List<PushProductDTO> pushAdInfoList =new ArrayList<PushProductDTO>();
//				pageDTO.setRecordList(translate(psrdto.getList(),pushAdInfoList) );			
//				pdt.setPageDTO(pageDTO);
				resultList.add(0, pdt);
//			}
//		}else if(count>0){
//			resultList.get(0).setIsChecked(true);
//		}
		return resultList;
	}

	@Override
	public List<ProductClassDTO> getCateNameByBusinessId(Map<String, Object> param) throws Exception {
		return hessianProductService().getCateNameByBusinessId(param);
	}

	@Override
	public List<ProductDto> querySpProductListForCertif(Map<String, Object> param) throws Exception {
		return hessianProductService().querySpProductListForCertif(param);
	}

	@Override
	public Integer querySpProductTotalForCertif(Map<String, Object> param) throws Exception {
		return hessianProductService().querySpProductTotalForCertif(param);
	}
	@Override
	public int getProductCountByBusinessId(Map<String, Object> map) throws Exception {
		return hessianProductService().getProductCountByBusinessId(map);
	}

	@Override
	public int validProductAliveByBusinessId(String productId) throws Exception {
		
		return hessianProductService().validProductAliveByBusinessId(productId);
	}
}
