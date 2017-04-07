package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.Date;

public class WeighCarOrderDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long orderNo;
	
	private String account;
	
	private String memberName;
	
	private String carNumber;
	
	private Double tareWeight;
	
	private Double totalWeight;
	
	private Double netWeight;
	
	private Date createTime;

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public Double getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
