package com.gudeng.commerce.gd.order.dto;

public class SubHelpDTO {
	
	/**
	 * 交易日期
	 */
	private String date;

	/**
	 * 用户id
	 */
	private Long memberId;
	
	/**
	 * 交易商铺id
	 */
	private Long businessId;
	
	/**
	 * 交易次数
	 */
	private Integer tradingFrequency;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Integer getTradingFrequency() {
		return tradingFrequency;
	}

	public void setTradingFrequency(Integer tradingFrequency) {
		this.tradingFrequency = tradingFrequency;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
