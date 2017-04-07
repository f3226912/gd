package com.gudeng.commerce.gd.promotion.service.impl;


import com.gudeng.commerce.gd.promotion.common.BaseRuleHandler;
import com.gudeng.commerce.gd.promotion.dto.GdActivityRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.service.GdOrderActivityFeeProxyService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

public class GdOrderActivityFeeProxyServiceImpl implements
		GdOrderActivityFeeProxyService {
	
	private BaseRuleHandler baseRuleHandler;

	public void setBaseRuleHandler(BaseRuleHandler baseRuleHandler) {
		this.baseRuleHandler = baseRuleHandler;
	}

	private static final GdLogger logger = GdLoggerFactory.getLogger(GdOrderActivityFeeProxyServiceImpl.class);
	
	@Override
	public void setBuyerActInfo(
			GdActivityRedisDTO activityRedisDTO, GdProductActInfoDTO productInfo,
			GdOrderActivityQueryDTO queryDTO)
			throws Exception {
		GdOrderActivityDetailDTO productActInfo = productInfo.getActInfo();
		if(productActInfo == null){
			productActInfo = new GdOrderActivityDetailDTO();
			productInfo.setActInfo(productActInfo);
		}
		logger.info("Setting buyer product act info, productId: " + productInfo.getProductId());
		baseRuleHandler.doHandler(productInfo, productActInfo, activityRedisDTO, queryDTO, true);
	}

	@Override
	public void setSellerActInfo(
			GdActivityRedisDTO activityRedisDTO, GdProductActInfoDTO productInfo,
			GdOrderActivityQueryDTO queryDTO)
			throws Exception {
		GdOrderActivityDetailDTO tmpActInfo = productInfo.getActInfo();
		GdOrderActivityDetailDTO productActInfo = new GdOrderActivityDetailDTO();
		productInfo.setActInfo(productActInfo);
		if(tmpActInfo != null){
			productActInfo.setProductActInfo(tmpActInfo.getProductActInfo());;
		}
		logger.info("Setting seller product act info, productId: " + productInfo.getProductId());
		baseRuleHandler.doHandler(productInfo, productActInfo, activityRedisDTO, queryDTO, false);
	}

	@Override
	public void setBusinessActInfo(
			GdActivityRedisDTO activityRedisDTO, 
			GdOrderActivityQueryDTO queryDTO, GdOrderActivityResultDTO resultDTO)
			throws Exception {
		GdOrderActivityDetailDTO businessActInfo = new GdOrderActivityDetailDTO();
		resultDTO.setSellerActInfo(businessActInfo);
		logger.info("Setting seller business act info, businessId: " + queryDTO.getBusinessId());
		baseRuleHandler.doHandler(queryDTO, businessActInfo, activityRedisDTO, false);
		logger.info("business act info: " + businessActInfo.toString());
	}
}
