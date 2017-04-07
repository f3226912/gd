package com.gudeng.commerce.gd.promotion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.service.CountBaseService;
import com.gudeng.commerce.gd.promotion.util.JSONUtils;
import com.gudeng.commerce.gd.promotion.util.MathUtil;

/**
 * 根据订单金额区间计算佣金
 * @author TerryZhang
 */
public class CountByOrderAmtServiceImpl implements CountBaseService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(CountByOrderAmtServiceImpl.class);
	
	@Override
	public Double countCommission(String ruleJsonStr,
			GdOrderActivityQueryDTO queryDTO, GdProductActInfoDTO productInfo, Double minAmt) {
		Double commsn = 0D;
		Double payAmt = queryDTO != null ? queryDTO.getPayAmount() : 0;
		boolean isReversalOrder = true;  //是否逆向订单
		if(productInfo != null){
			isReversalOrder = false;
			payAmt = productInfo.getProductAmount();
		}
		
		logger.info("Count by order amount, user pay amount: " + payAmt + ", isReversalOrder: " + isReversalOrder);
		JSONArray ruleJsonArr = JSONUtils.parseArray(ruleJsonStr);
		for(int i=0, len = ruleJsonArr.size(); i<len; i++){
			JSONObject jsonObj = (JSONObject)ruleJsonArr.get(i);
			Double startAmt = jsonObj.getDouble("startAmt"); //起始金额
			startAmt = startAmt == null ? 0D : startAmt;
			Double endAmt = jsonObj.getDouble("endAmt"); //结束金额 null则为不限
			Integer isReverse = jsonObj.getInteger("isReverse"); ////0正向规则，1逆向规则
			isReverse = isReverse == null ? 0 : isReverse;
			logger.info("Start amount: " + startAmt + ", end amount: " + endAmt 
					+ ", isReverse: " + isReverse);
			//如果是正向规则和反向订单则不计算
			if(isReverse == 0 && isReversalOrder){
				continue;
			}else if(isReverse == 1 && !isReversalOrder){//如果是反向规则和正向订单则不计算
				continue;
			}
			//支付金额小于起始金额不计算
			if(payAmt.compareTo(startAmt) < 0){
				continue;
			}
			
			//支付金额大于结束金额不计算
			if(endAmt != null && payAmt.compareTo(endAmt) >= 0){
				continue;
			}
			
			//类型 0按照比例 1按照固定金额
			Integer type = jsonObj.getInteger("type");
			logger.info("Count type: 0按照比例 1按照固定金额");
			logger.info("Count type: " + type);
			logger.info("minAmt: " + minAmt);
			switch(type){
				case 0: 
					Double orderLimitAmt = jsonObj.getDouble("orderLimitAmt"); //上限
					Double orderProp = jsonObj.getDouble("orderProp"); //百分比 11.21
					logger.info("Limit amount: " + orderLimitAmt + ", prop: " + orderProp);
					commsn = getPerAmount(payAmt, orderProp, orderLimitAmt);
					commsn = commsn.compareTo(minAmt) < 0 ? minAmt : commsn;
					break;
				case 1: 
					commsn = jsonObj.getDouble("fixedAmt"); //固定金额
					commsn = commsn.compareTo(minAmt) < 0 ? minAmt : commsn;
					logger.info("Fixed amount: " + commsn);
					break;
			}
		}
		
		logger.info("Final count result: " + commsn);
		return commsn;
	}

	private Double getPerAmount(Double amount, Double per, Double limitAmt){
		if(per==null){
			per = 0D;
		}
		
		Double commission = MathUtil.div(MathUtil.mul(amount, per), 100, 2);
		logger.info("Order prop amount: " + commission);
		if(limitAmt == null){
			return commission;
		}
		return commission.compareTo(limitAmt) < 0 ? commission : limitAmt;
	}
}
