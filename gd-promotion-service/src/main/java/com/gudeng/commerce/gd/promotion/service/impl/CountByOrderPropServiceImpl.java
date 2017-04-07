package com.gudeng.commerce.gd.promotion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.service.CountBaseService;
import com.gudeng.commerce.gd.promotion.util.JSONUtils;
import com.gudeng.commerce.gd.promotion.util.MathUtil;

/**
 * 按订单比例收佣金
 * @author TerryZhang
 */
public class CountByOrderPropServiceImpl implements CountBaseService {
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(CountByOrderPropServiceImpl.class);

	@Override
	public Double countCommission(String ruleJsonStr,
			GdOrderActivityQueryDTO queryDTO, GdProductActInfoDTO productInfo, Double minAmt) {
		JSONObject ruleJsonObj = JSONUtils.parseObject(ruleJsonStr);
		Double orderLimitAmt = ruleJsonObj.getDouble("orderLimitAmt"); //上限
		Double orderProp = ruleJsonObj.getDouble("orderProp"); //百分比 11.21
		logger.info("Count by order prop: limit amount: " + orderLimitAmt + ", prop: " + orderProp);
		//按参加活动的单个商品订单总额比例收取
		if(productInfo != null){
			logger.info("Product amount: " + productInfo.getProductAmount());
			return getPerAmount(productInfo.getProductAmount(), orderProp, orderLimitAmt);
		}else{//按商铺订单总额比例收取
			logger.info("Order amount: " + queryDTO.getPayAmount());
			return getPerAmount(queryDTO.getPayAmount(), orderProp, orderLimitAmt);
		}
	}

	private Double getPerAmount(Double amount, Double per, Double limitAmt){
		Double commission = MathUtil.div(MathUtil.mul(amount, per), 100, 2);
		logger.info("Order prop amount: " + commission);
		if(limitAmt == null){
			return commission;
		}
		return commission.compareTo(limitAmt) < 0 ? commission : limitAmt;
	}
}
