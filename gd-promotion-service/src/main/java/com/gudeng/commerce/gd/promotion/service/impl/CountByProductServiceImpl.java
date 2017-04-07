package com.gudeng.commerce.gd.promotion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.service.CountBaseService;
import com.gudeng.commerce.gd.promotion.util.JSONUtils;

/**
 * 按商品计算佣金
 * @author TerryZhang
 */
public class CountByProductServiceImpl implements CountBaseService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(CountByProductServiceImpl.class);
	
	@Override
	public Double countCommission(String ruleJsonStr,
			GdOrderActivityQueryDTO queryDTO, GdProductActInfoDTO productInfo, Double minAmt) {
		Double commsn = JSONUtils.parseObject(ruleJsonStr).getDouble("prodLimitAmt");
		logger.info("Count by product, amount: " + commsn + ", minAmt: " + minAmt);
		commsn = commsn == null ? 0D : commsn;
		return commsn.compareTo(minAmt) < 0 ? minAmt : commsn;
	}

}
