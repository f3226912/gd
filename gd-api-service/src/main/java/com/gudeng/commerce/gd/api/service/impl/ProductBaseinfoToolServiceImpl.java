package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.common.XPath.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.BusinessAppDTO;
import com.gudeng.commerce.gd.api.dto.input.NstBaseResponseDTO;
import com.gudeng.commerce.gd.api.dto.output.ProductOutputDetailDTO;
import com.gudeng.commerce.gd.api.enums.NstApiRequestV1Enum;
import com.gudeng.commerce.gd.api.service.GdOrderActivityBaseToolService;
import com.gudeng.commerce.gd.api.service.ProductBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.nst.NstApiCommonService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.api.util.ParamsUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.customer.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.customer.service.ProductBaseinfoService;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActivityQueryDTO;

public class ProductBaseinfoToolServiceImpl implements ProductBaseinfoToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	@Autowired
	private NstApiCommonService nstApiCommonService;
	
	@Autowired
	private GdOrderActivityBaseToolService gdOrderActivityBaseToolService;
	
	private static ProductBaseinfoService productBaseinfoService;

	protected ProductBaseinfoService getHessianProductBaseinfoService() throws MalformedURLException {
		String url = gdProperties.getProductBaseinfoServiceUrl();
		if (productBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productBaseinfoService = (ProductBaseinfoService) factory.create(ProductBaseinfoService.class, url);
		}
		return productBaseinfoService;
	}

	@Override
	public List<ProductOutputDetailDTO> getSupplierProduct(Map<String, Object> param) throws Exception {
		List<ProductBaseinfoDTO> productList = getHessianProductBaseinfoService().getSupplierProduct(param);
		List<ProductOutputDetailDTO> detailList = null;
		if (CollectionUtils.isNotEmpty(productList)) {
			detailList = new ArrayList<ProductOutputDetailDTO>(productList.size());
			setOutputDetail(productList, detailList);
		}
		return detailList;
	}

	public void setOutputDetail(List<ProductBaseinfoDTO> productList, List<ProductOutputDetailDTO> detailList) {
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		for (int i = 0; i < productList.size(); i++) {
			ProductBaseinfoDTO product = productList.get(i);
			ProductOutputDetailDTO detail = new ProductOutputDetailDTO();
			detail.setProductName(product.getName());
			detail.setProductId(product.getProductId());
			detail.setPrice(MoneyUtil.formatMoney(product.getPrice()));
			detail.setImageUrl(imageHost + product.getUrl170());
			detail.setHits(product.getVisitCount());
			detail.setMemberGrade(product.getMemberGrade());
			detail.setCbstatus("0");
			detail.setCcstatus(product.getCcstatus());
			detail.setCertifstatus(product.getCertifstatus());
			detail.setComstatus(product.getComstatus());
			detail.setBusinessName(product.getBusinessName());
			detail.setBusinessId(product.getBusinessId());
			detail.setUnitName(product.getUnitName());
			detail.setStockCount(ParamsUtil.getStringFromDouble(product.getStockCount(), "0"));
			if(StringUtils.isNotBlank(product.getOriginProvinceName())
					&& StringUtils.isNotBlank(product.getOriginCityName())){
				detail.setProduceArea(product.getOriginProvinceName()+product.getOriginCityName());
			}else{
				detail.setProduceArea("");
			}
			
			if(product.getUpdatePriceTime() != null){
				detail.setUpdateDate(DateUtil.getDate(product.getUpdatePriceTime(), DateUtil.DATE_FORMAT_MONTH_AND_DAY));
			}else{
				detail.setUpdateDate(DateUtil.getDate(product.getUpdateTime(), DateUtil.DATE_FORMAT_MONTH_AND_DAY));
			}
			
			detailList.add(detail);
		}
	}

	@Override
	public int getSupplierProductTotal(Map<String, Object> param) throws Exception {
		return getHessianProductBaseinfoService().getSupplierProductTotal(param);
	}

	@Override
	public List<ProductOutputDetailDTO> getSupplierProductByPrecise(Map<String, Object> param) throws Exception {
		List<ProductBaseinfoDTO> productList = getHessianProductBaseinfoService().getSupplierProductByPrecise(param);
		List<ProductOutputDetailDTO> detailList = null;
		if (CollectionUtils.isNotEmpty(productList)) {
			detailList = new ArrayList<ProductOutputDetailDTO>(productList.size());
			setOutputDetail(productList, detailList);
		}
		return detailList;
	}

	@Override
	public int getSupplierProductTotalByPrecise(Map<String, Object> param) throws Exception {
		return getHessianProductBaseinfoService().getSupplierProductTotalByPrecise(param);
	}

	@Override
	public int getSupplierProductTotalRecommend(Map<String, Object> param) throws Exception {
		return getHessianProductBaseinfoService().getSupplierProductTotalByRecommend(param);
	}

	@Override
	public List<ProductOutputDetailDTO> getSupplierProductByRecommend(Map<String, Object> param) throws Exception {
		List<ProductBaseinfoDTO> productList = getHessianProductBaseinfoService().getSupplierProductByRecommend(param);
		List<ProductOutputDetailDTO> detailList = null;
		if (CollectionUtils.isNotEmpty(productList)) {
			detailList = new ArrayList<ProductOutputDetailDTO>(productList.size());
			setOutputDetail(productList, detailList);
		}
		return detailList;
	}

	@Override
	public PageQueryResultDTO<ProductOutputDetailDTO> getListSearchAccurateProduct(
			Map<String, Object> paramMap) throws Exception {
		PageQueryResultDTO<ProductBaseinfoDTO> pageResult = getHessianProductBaseinfoService().getListSearchAccurateProduct(paramMap);
		List<ProductBaseinfoDTO> productList = pageResult.getDataList();
		List<ProductOutputDetailDTO> detailList = null;
		if (pageResult.getTotalCount() > 0) {
			detailList = new ArrayList<>(productList.size());
			setOutputDetail(productList, detailList);
		}
		PageQueryResultDTO<ProductOutputDetailDTO> returnPageResult = new PageQueryResultDTO<ProductOutputDetailDTO>();
		returnPageResult.setDataList(detailList);
		returnPageResult.setTotalCount(pageResult.getTotalCount()); 
		return returnPageResult;
	}

	@Override
	public PageQueryResultDTO<BusinessAppDTO> getListSearchAccurateShop(
			Map<String, Object> paramMap) throws Exception {
		
		PageQueryResultDTO<BusinessBaseinfoDTO> pageResult = getHessianProductBaseinfoService().getListSearchAccurateShop(paramMap);
		List<BusinessBaseinfoDTO> businessList = pageResult.getDataList();
		List<BusinessAppDTO> detailList = null;
		if (pageResult.getTotalCount() > 0) {
			detailList = new ArrayList<>(businessList.size());
			setBusinessInfoDetail(businessList, detailList);
		}
		PageQueryResultDTO<BusinessAppDTO> returnPageResult = new PageQueryResultDTO<BusinessAppDTO>();
		returnPageResult.setDataList(detailList);
		returnPageResult.setTotalCount(pageResult.getTotalCount()); 
		return returnPageResult;
	}

	private void setBusinessInfoDetail(List<BusinessBaseinfoDTO> businessList,
			List<BusinessAppDTO> detailList) {
		for(BusinessBaseinfoDTO business : businessList){
			BusinessAppDTO appDTO = new BusinessAppDTO();
			appDTO.setBusinessId(business.getBusinessId());
			appDTO.setShopsName(business.getShopsName());
			appDTO.setProductCount(business.getProdCount());
			appDTO.setMainProduct(ParamsUtil.getStringFromString(business.getMainProduct()));
			appDTO.setBusinessModel(business.getBusinessModel());
			appDTO.setManagementType(business.getManagementType());
			appDTO.setMobile(business.getMobile());
			appDTO.setCbstatus(0);
			appDTO.setCcstatus(Integer.parseInt(business.getCcstatus()));
			appDTO.setComstatus(Integer.parseInt(business.getComstatus()));
			appDTO.setMemberGrade(business.getMemberGrade());
			appDTO.setUserId(business.getUserId());
			appDTO.setOriginAddress(ParamsUtil.getStringFromString(business.getProvince()) 
					+ ParamsUtil.getStringFromString(business.getCity()) 
					+ ParamsUtil.getStringFromString(business.getArea()));
			detailList.add(appDTO);
		}
	}
	
	@Override
	public ProductBaseinfoDTO getProductById(Long productId) throws Exception {
		return getHessianProductBaseinfoService().getProductById(productId);
	}
	
	@Override
	public List<ProductBaseinfoDTO> getProductList(List<Long> productIdList) throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productIds", productIdList);
		return getHessianProductBaseinfoService().getProductList(paramMap);
	}

	@Override
	public int checkProductActivity(Long productId, Long customerId, Long sellerId,Long marketId) throws Exception {
		int platform=0;
		Long businessId=null;
		try{
			if(customerId==null || customerId.longValue()==0l){
				return platform;
			}
			if(sellerId==null || sellerId.longValue()==0l ){
				ProductBaseinfoDTO product = getHessianProductBaseinfoService().getProductById(productId);
				sellerId=product.getMemberId().longValue();
				if(marketId == null ){
					marketId=product.getMarketId();
				}
				businessId=product.getBusinessId();
			}
			//增加货源请求
			String url = gdProperties.getNstApiUrl() + NstApiRequestV1Enum.GET_NST_VALIDATEASSIGN.getUri();
			String token = nstApiCommonService.getNstToken(customerId+"");
			Map<String, Object> requestParamMap = new HashMap<String, Object>();
			requestParamMap.put("memberIdShipper", customerId);
			requestParamMap.put("token", token);
			NstBaseResponseDTO nstResponse = nstApiCommonService.sendNstRequest(requestParamMap, url);
			//不成功则返回错误信息
			if(nstResponse.getCode().intValue() != 10000){
				return platform;
			}
			//获取物流id
			int isAssign = Integer.parseInt(nstResponse.getResult().get("isAssign"));
			if(isAssign == 0){
				return platform;
			}else{
				GdProductActivityQueryDTO queryDTO =new GdProductActivityQueryDTO();
				queryDTO.setProductId(productId==null?null:productId.intValue());
				queryDTO.setMarketId(marketId==null?null:marketId.intValue());
				queryDTO.setBuyerId(customerId==null?null:customerId.intValue());
				queryDTO.setBusinessId(businessId==null?null:businessId.intValue());
				// 调用爱兵接口，判断当前商品、买家、卖家是否符合同一个平台配送活动规则
				queryDTO=gdOrderActivityBaseToolService.queryProductAct(queryDTO);
				if(queryDTO.isHasPlatMode()){
					platform=1;
				}
			}
		}catch(Exception e ){
			//do nothing
		}
		return platform;
	}

}