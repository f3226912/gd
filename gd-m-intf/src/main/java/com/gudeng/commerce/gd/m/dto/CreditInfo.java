package com.gudeng.commerce.gd.m.dto;

import java.io.Serializable;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年5月25日 下午2:26:00
 */
public class CreditInfo implements Serializable {

	private static final long serialVersionUID = -8585635064683419342L;
	
	private Double tradeAmount; // 交易金额
	
	private Integer level; // 等级

	public Double getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
