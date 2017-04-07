package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;

/**
 * 订单清分明细
 * @date 2016年12月21日
 */
public class OrderClearDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3270931148693988788L;
	private String orderNo;		       //订单号
	private String userType;	       //用户类型
	private String memberId;		    //会员Id号
	private Double orderAmt;			//货款
	private Double commissionAmt;		//市场佣金
	private Double feeAmt;			    //手续费
	private Double subsidyAmt;			//补贴
	private Double platCommissionAmt;	//平台佣金
	private Double penaltyAmt;		//违约金
	private Double refundAmt;		//退款
	
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
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
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
	public Double getPlatCommissionAmt() {
		return platCommissionAmt;
	}
	public void setPlatCommissionAmt(Double platCommissionAmt) {
		this.platCommissionAmt = platCommissionAmt;
	}
	public Double getPenaltyAmt() {
		return penaltyAmt;
	}
	public void setPenaltyAmt(Double penaltyAmt) {
		this.penaltyAmt = penaltyAmt;
	}
	public Double getRefundAmt() {
		return refundAmt;
	}
	public void setRefundAmt(Double refundAmt) {
		this.refundAmt = refundAmt;
	}
	
	
	

}
