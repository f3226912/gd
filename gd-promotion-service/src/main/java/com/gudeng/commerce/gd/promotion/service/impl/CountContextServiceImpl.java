package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdPayBankCardInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.service.CountBaseService;
import com.gudeng.commerce.gd.promotion.service.CountContextService;
import com.gudeng.commerce.gd.promotion.util.JSONUtils;
import com.gudeng.commerce.gd.promotion.util.MathUtil;

public class CountContextServiceImpl implements CountContextService {

	/**
	 * 记录日志
	 */
	private static Logger logger = LoggerFactory.getLogger(CountContextServiceImpl.class);

	private CountBaseService countBaseService;

	private Map<String, CountBaseService> countServiceMap;

	public void setCountServiceMap(Map<String, CountBaseService> countServiceMap) {
		this.countServiceMap = countServiceMap;
	}

	public CountBaseService getCountBaseService(String countType) {
		return countBaseService = countServiceMap.get(countType);
	}

	@Override
	public Double getCommission(GdActActivityCommDTO com,
								GdOrderActivityQueryDTO queryDTO, GdProductActInfoDTO productInfo) {
		String jsonStr = com.getRuleJson();
		if (StringUtils.isBlank(jsonStr)) {
			return 0D;
		}

		JSONObject jsonObj = JSONUtils.parseObject(jsonStr);
		String countType = jsonObj.getString("type");
		String ruleJsonStr = jsonObj.getString("detail");
		Double minAmt = jsonObj.getDouble("minAmt") == null ? 0D : jsonObj.getDouble("minAmt");
		return getCountBaseService(countType).countCommission(ruleJsonStr, queryDTO, productInfo, minAmt);
	}

	@Override
	public Double countCommission(String ruleJsonStr,
								  GdOrderActivityQueryDTO queryDTO, GdProductActInfoDTO productInfo, Double minAmt) {
		return countBaseService.countCommission(ruleJsonStr, queryDTO, productInfo, minAmt);
	}

	@Override
	public Double getSubsidy(GdActActivityCommDTO com,
							 GdOrderActivityQueryDTO queryDTO, GdProductActInfoDTO productInfo,
							 GdPayBankCardInfoDTO payCardInfo, boolean isBuyer) {
		if (payCardInfo == null) {
			logger.info("No pay card info...");
			return 0D;
		}

		String jsonStr = com.getRuleJson();
		if (StringUtils.isBlank(jsonStr)) {
			return 0D;
		}

		JSONObject jsonObj = JSONUtils.parseObject(jsonStr);

		//0:全部  1:本行 2:跨行
		Integer busiType1 = jsonObj.getInteger("busiType1");
		//0:全部  1:同城 2:异地
		Integer busiType2 = jsonObj.getInteger("busiType2");
		//0:全部  1:借记卡 1:贷记卡
		Integer cardType = jsonObj.getInteger("cardType");
		//支付渠道
		String payChannel = jsonObj.getString("payChannel");

		logger.info("PayChannel: " + payChannel);
		if (busiType2 != 0 && busiType2.intValue() != payCardInfo.getBusiType2().intValue()) {
			logger.info("busiType2: " + busiType2 + ", input: " + payCardInfo.getBusiType2());
			return 0D;
		}
		if (busiType1 != 0 && busiType1.intValue() != payCardInfo.getBusiType1().intValue()) {
			logger.info("busiType1: " + busiType1 + ", input: " + payCardInfo.getBusiType1());
			return 0D;
		}
		if (cardType != 0 && cardType.intValue() != payCardInfo.getCardType().intValue()) {
			logger.info("cardType: " + cardType + ", input: " + payCardInfo.getCardType());
			return 0D;
		}
		if (payChannel.equals(payCardInfo.getPayChannel())) {
			String ruleJsonStr = jsonObj.getString("rule");
			JSONObject ruleJsonObj = JSONUtils.parseObject(ruleJsonStr);
			//卖家是否存在补贴上线 0：不存在 1：存在
			Integer solderLimitType = ruleJsonObj.getInteger("solderLimitType");
			//卖家补贴上线金额
			Double solderLimitAmt = ruleJsonObj.getDouble("solderLimitAmt");
			logger.info("Already has subsidy: " + payCardInfo.getSellerSubsidyAmt());
			logger.info("SolderLimitType: " + solderLimitType + ", solderLimitAmt: " + solderLimitAmt);
			//检查是否超过上限
			if (isOverSubsidyAmtLimit(solderLimitType, solderLimitAmt, isBuyer, payCardInfo, 0D)) {
				return 0D;
			}

			String countType = ruleJsonObj.getString("type");
			String ruleDetailJsonStr = ruleJsonObj.getString("detail");
			
			Double subsidy = getCountBaseService(countType).countCommission(ruleDetailJsonStr, queryDTO, productInfo, 0D);
			//检查是否超过上限
			if(!"4".equals(countType)){ //不属于按刷卡手续费比例进行补贴
			  if (subsidy.compareTo(0D) > 0 && isOverSubsidyAmtLimit(solderLimitType, solderLimitAmt, isBuyer, payCardInfo, 0D)) {
				return isBuyer ? MathUtil.sub(solderLimitAmt, payCardInfo.getBuyerSubsidyAmt()) :
						MathUtil.sub(solderLimitAmt, payCardInfo.getSellerSubsidyAmt());
			  }
			    return subsidy;
			}else{
				Double orderLimitAmt = JSONUtils.parseObject(ruleDetailJsonStr).getDouble("orderLimitAmt"); //补贴上限
				if(orderLimitAmt != null){
				  logger.info("Subsidized by swing card fee, orderLimitAmt: " + orderLimitAmt);
				  return subsidy.compareTo(orderLimitAmt) > 0 ? orderLimitAmt : subsidy;
				}
				return subsidy;
			}
		}
		return 0D;
	}

	private boolean isOverSubsidyAmtLimit(Integer solderLimitType, Double solderLimitAmt, boolean isBuyer,
										  GdPayBankCardInfoDTO payCardInfo, Double addAmt) {
		if (solderLimitType == 1) {
			Double amount = payCardInfo.getSellerSubsidyAmt();
			if (isBuyer) {
				amount = payCardInfo.getBuyerSubsidyAmt();
			}
			//是否超过补贴上线
			if (solderLimitAmt.compareTo(MathUtil.add(amount, addAmt)) < 0) {
				return true;
			}
		}
		return false;
	}
	
}
