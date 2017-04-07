package com.gudeng.commerce.gd.api.dto.output;

import java.io.Serializable;

public class VClearDetailDto implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private String orderNo;
	
	private String userType;
	
	private String memberId;
	
	private Double orderAmt;
	
	private Double commissionAmt;//佣金 
	
	private Double feeAmt;//手续费
	
	private Double subsidyAmt;//补贴

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Double getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(Double orderAmt) {
		this.orderAmt = orderAmt;
	}

	public Double getCommissionAmt() {
		return commissionAmt;
	}

	public void setCommissionAmt(Double commissionAmt) {
		this.commissionAmt = commissionAmt;
	}

	public Double getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(Double feeAmt) {
		this.feeAmt = feeAmt;
	}

	public Double getSubsidyAmt() {
		return subsidyAmt;
	}

	public void setSubsidyAmt(Double subsidyAmt) {
		this.subsidyAmt = subsidyAmt;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
