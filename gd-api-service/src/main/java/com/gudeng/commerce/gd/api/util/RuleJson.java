package com.gudeng.commerce.gd.api.util;

public class RuleJson {

	private Integer minIntegral; //单日首单积分奖励
	private Integer dayMaxIntegral;//单日积分上线
	private String detail; //详细
	public Integer getMinIntegral() {
		return minIntegral;
	}
	public void setMinIntegral(Integer minIntegral) {
		this.minIntegral = minIntegral;
	}
	public Integer getDayMaxIntegral() {
		return dayMaxIntegral;
	}
	public void setDayMaxIntegral(Integer dayMaxIntegral) {
		this.dayMaxIntegral = dayMaxIntegral;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}



}
