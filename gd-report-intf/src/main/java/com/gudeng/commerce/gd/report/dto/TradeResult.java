package com.gudeng.commerce.gd.report.dto;

import java.io.Serializable;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月14日 上午9:45:12
 */
public class TradeResult implements Serializable {

	private static final long serialVersionUID = -7095692389476589574L;
	private String time; // 小时，例如：04；日期，例如：20160608
	private Long orderNum; // 订单量
	private Double tradeAmt; // 交易额

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Double getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(Double tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

}
