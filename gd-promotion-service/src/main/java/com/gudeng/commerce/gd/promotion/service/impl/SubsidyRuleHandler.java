package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.promotion.common.BaseRuleHandler;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActivityInfoRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActivityRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdPayBankCardInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.service.CountContextService;
import com.gudeng.commerce.gd.promotion.util.JSONUtils;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 补贴规则处理
 * @author TerryZhang
 */
public class SubsidyRuleHandler extends BaseRuleHandler {
	private static final GdLogger logger = GdLoggerFactory.getLogger(SubsidyRuleHandler.class);

	@Autowired
	private CountContextService countContextService;
	
	@Override
	public void doHandler(GdOrderActivityQueryDTO queryDTO, 
			GdOrderActivityDetailDTO actInfo, GdActivityRedisDTO activityRedisDTO, 
			boolean isBuyer)
			throws Exception {
		int actType = 1;
		GdActivityInfoRedisDTO subsidyInfo = activityRedisDTO.getSubsidyInfo();
		Integer businessActId = checkBusinessActInfo(queryDTO, subsidyInfo, actType, isBuyer);
		if(businessActId != null){
			logger.info("Business id: " +queryDTO.getBusinessId()+ ", subsidy actId:  " + businessActId);
//			设置商铺补贴活动信息
//			setProductActInfo(actInfo, queryDTO.getBusinessId(), businessActId, actType);
			Map<String, Object> subsidyInfoRuleMap = subsidyInfo.getActRuleMap().get(businessActId);
			if(subsidyInfoRuleMap != null && !subsidyInfoRuleMap.isEmpty()){
				//设置商铺补贴
				@SuppressWarnings("unchecked")
				List<GdActActivityCommDTO> comList = (List<GdActActivityCommDTO>) subsidyInfoRuleMap.get("sellerSubsidyRule");
				if(comList != null && comList.size() > 0){
					logger.info("Has business subsidy rule.");
					Double subsidy = 0D;
					//补贴取最大值
					for(GdActActivityCommDTO com : comList){
						Double tmp = countContextService.getSubsidy(com, queryDTO, null, queryDTO.getPayCardInfo(), isBuyer);
						subsidy = tmp.compareTo(subsidy) > 0 ? tmp : subsidy;
					}
					actInfo.setHasSellerSub(true);
					actInfo.setSubsidy(getSubsidy(subsidy, queryDTO.getPayCardInfo()));
				}
			}
		}
		
		if(getNextHandler() != null){
			getNextHandler().doHandler(queryDTO, actInfo, activityRedisDTO, isBuyer);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doHandler(GdProductActInfoDTO productInfo,
			GdOrderActivityDetailDTO productActInfo,
			GdActivityRedisDTO activityRedisDTO,
			GdOrderActivityQueryDTO queryDTO, boolean isBuyer)
			throws Exception {
		int actType = 1;
		GdActivityInfoRedisDTO subsidyInfo = activityRedisDTO.getSubsidyInfo();
		Integer productActId = checkProductActInfo(queryDTO, subsidyInfo, productInfo, actType, isBuyer);
		if(productActId != null){
			logger.info("Product id: " +productInfo.getProductId()+ ", subsidy actId:  " + productActId);
//			设置商品补贴活动信息
			if(!isBuyer){
				setProductActInfo(productActInfo, productInfo.getProductId(), productActId, actType);
			}
			Map<String, Object> productRuleMap = subsidyInfo.getActRuleMap().get(productActId);
			if(productRuleMap != null && !productRuleMap.isEmpty()){
				List<GdActActivityCommDTO> comList = null;
				if(isBuyer){
					//设置买家补贴规则
					logger.info("Getting buyer subsidy info.");
					comList = (List<GdActActivityCommDTO>) productRuleMap.get("buyerSubsidyRule");
				}else{
					//设置卖家补贴规则
					logger.info("Getting seller subsidy info.");
					comList = (List<GdActActivityCommDTO>) productRuleMap.get("sellerSubsidyRule");
				}
				
				if(comList != null && comList.size() > 0){
					logger.info("Has product subsidy rule.");
					Double subsidy = 0D;
					for(GdActActivityCommDTO com : comList){
						Double tmp = 0D;
						String jsonStr = com.getRuleJson();
						if(jsonStr != null){
						     JSONObject jsonObj = JSONUtils.parseObject(jsonStr);
						     String ruleJsonStr = jsonObj.getString("rule");
						     JSONObject ruleJsonObj = JSONUtils.parseObject(ruleJsonStr);
						     String type = ruleJsonObj.getString("type");
						     if(type != null && "4".equals(type)){
						    	 tmp = countContextService.getSubsidy(com, queryDTO, productInfo, queryDTO.getPayCardInfo(), isBuyer);
						     }else{
						    	 tmp =  countContextService.getSubsidy(com, null, productInfo, queryDTO.getPayCardInfo(), isBuyer);
						     }
						}
						subsidy = tmp.compareTo(subsidy) > 0 ? tmp : subsidy;
					}
					productActInfo.setHasSellerSub(true);
					productActInfo.setSubsidy(getSubsidy(subsidy, queryDTO.getPayCardInfo()));
				}
			}
		}
		
		if(getNextHandler() != null){
			getNextHandler().doHandler(productInfo, productActInfo, activityRedisDTO, queryDTO, isBuyer);
		}
	}

	private Double getSubsidy(Double subsidy, GdPayBankCardInfoDTO payCardInfo) {
		if(payCardInfo != null){
			subsidy = subsidy.compareTo(payCardInfo.getTradingFee()) > 0 ? payCardInfo.getTradingFee() : subsidy;
		}
		return subsidy;
	}
}
