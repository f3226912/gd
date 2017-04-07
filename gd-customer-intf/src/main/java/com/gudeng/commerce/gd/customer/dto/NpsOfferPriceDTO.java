package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.NpsOfferPriceEntity;

public class NpsOfferPriceDTO extends NpsOfferPriceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3814511018275086157L;
	private double newPrice;
	private double lowPrice;
	
	private String realName;
	private String provinceName;
	private String cityName;
	private String areaName;
	private int memberGrade;		//会员等级(0:普通会员，1:金牌供应商)
	private String purchaseUnit;
	private String purchaseStatus; //询价状态
	private String purchaseEndTime;//询价结束时间
	private long businessId;
	private String account;//用户账号
	
	
	public long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(long businessId) {
		this.businessId = businessId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(int memberGrade) {
		this.memberGrade = memberGrade;
	}

	public double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
	}

	public double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getPurchaseUnit() {
		return purchaseUnit;
	}

	public void setPurchaseUnit(String purchaseUnit) {
		this.purchaseUnit = purchaseUnit;
	}

	public String getPurchaseStatus() {
		return purchaseStatus;
	}

	public void setPurchaseStatus(String purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}

	public String getPurchaseEndTime() {
		return purchaseEndTime;
	}

	public void setPurchaseEndTime(String purchaseEndTime) {
		this.purchaseEndTime = purchaseEndTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	
}
