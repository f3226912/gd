package com.gudeng.commerce.gd.promotion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdPayBankCardInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.service.CountBaseService;
import com.gudeng.commerce.gd.promotion.util.JSONUtils;
import com.gudeng.commerce.gd.promotion.util.MathUtil;
/**
 * 根据刷卡手续费计算补贴
 * @author youj
 */
public class CountByCardFeeServiceImpl implements CountBaseService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(CountByCardFeeServiceImpl.class);
	
	@Override
	public Double countCommission(String ruleJsonStr,
			GdOrderActivityQueryDTO queryDTO, GdProductActInfoDTO productInfo, Double minAmt) {
		Double proportion = JSONUtils.parseObject(ruleJsonStr).getDouble("proportion");
		Double commsn = 0D;
		Double tradingFee = 0D;
		GdPayBankCardInfoDTO payCardInfo = queryDTO.getPayCardInfo();
		if(payCardInfo != null){
		   tradingFee = payCardInfo.getTradingFee();
		}
		commsn =  MathUtil.div(MathUtil.mul(tradingFee, proportion), 100, 2);
		logger.info("Count by card fee: tradingFee: " + tradingFee + ", pro: " + proportion
				+", commsn: " + commsn);
		return commsn.compareTo(minAmt) < 0 ? minAmt : commsn;
	}

}
