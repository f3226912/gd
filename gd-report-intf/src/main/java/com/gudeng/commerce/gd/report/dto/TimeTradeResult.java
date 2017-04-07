package com.gudeng.commerce.gd.report.dto;

import java.io.Serializable;

/**
 * 交易额排名，时间顺序排列
 * @author Ailen
 *
 */
public class TimeTradeResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8991104977774526243L;

	private String time;
	
	private Double tradeAmt;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(Double tradeAmt) {
		this.tradeAmt = tradeAmt;
	}
	
	
}
