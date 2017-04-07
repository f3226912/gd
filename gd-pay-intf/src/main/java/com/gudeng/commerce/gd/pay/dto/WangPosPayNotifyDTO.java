package com.gudeng.commerce.gd.pay.dto;

import java.io.Serializable;

public class WangPosPayNotifyDTO implements Serializable {

	private static final long serialVersionUID = -8570045510083288129L;
	
	private Long orderNo; // 订单号
	
	private String bpId; // BP业务ID（退款使用）
	
	private String cashierTradeNo; // 收银系统订单号
	
	private String tradeStatus;  // 交易状态 
	
	private String payInfo;  // 支付结果信息
	
	private String payType; // 支付方式

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getCashierTradeNo() {
		return cashierTradeNo;
	}

	public void setCashierTradeNo(String cashierTradeNo) {
		this.cashierTradeNo = cashierTradeNo;
	}

	public String getBpId() {
		return bpId;
	}

	public void setBpId(String bpId) {
		this.bpId = bpId;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String toString(){
		return "orderNo=" + orderNo + ",bpId=" + bpId + ",cashierTradeNo=" + cashierTradeNo + 
				",tradeStatus=" + tradeStatus + ",payInfo=" + payInfo;
	}
	
}
