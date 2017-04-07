package com.gudeng.commerce.gd.order.dto;

import com.gudeng.commerce.gd.order.entity.OrderRefundRecordEntity;

public class OrderRefundRecordDTO extends OrderRefundRecordEntity {

	private static final long serialVersionUID = -6217180693360037423L;
	
	/**
	 * 买家手机号码
	 */
	private String mobile;
	
	/**
	 * 买家账号
	 */
	private String account;

	
	private String refundTimeStr;//退款时间
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRefundTimeStr() {
		return refundTimeStr;
	}

	public void setRefundTimeStr(String refundTimeStr) {
		this.refundTimeStr = refundTimeStr;
	}
	
	
	
    
}
