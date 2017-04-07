package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;

public class MemberIntegralConversionDTO extends MemberBaseinfoEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 对应的礼物ID*/
	private Integer giftId;
	
	/** 对应联系人*/
	private String contacts;
	
	/** 企业名称*/
	private String companyName;
	
	/** 礼物名称*/
	private String giftName;
	
	/** 对应礼物的积分*/
	private Integer giftIntegral;
	
	/** 对应的活动id*/
	private Integer activityId;
	
	
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}



	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getGiftId() {
		return giftId;
	}

	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Integer getGiftIntegral() {
		return giftIntegral;
	}

	public void setGiftIntegral(Integer giftIntegral) {
		this.giftIntegral = giftIntegral;
	}

}
