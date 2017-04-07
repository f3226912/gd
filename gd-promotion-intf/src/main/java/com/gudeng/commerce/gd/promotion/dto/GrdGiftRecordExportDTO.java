package com.gudeng.commerce.gd.promotion.dto;

import java.util.Date;

import com.gudeng.commerce.gd.promotion.entity.GrdGiftRecordEntity;

public class GrdGiftRecordExportDTO extends GrdGiftRecordEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -117362210209088737L;
	
	private Integer recordId;
	private String grantName;
	private String createUserName;
	private String orderNo;
	private Date orderTime;
	private String orderTimeStr;
	private String grantTimeStr;
	private String shopsName;
	private String orderPrice;
	private String giftName;
	private String countNo;
	private String giftstoreName;
	
	public String getOrderTimeStr() {
		return orderTimeStr;
	}
	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}
	public String getGrantTimeStr() {
		return grantTimeStr;
	}
	public void setGrantTimeStr(String grantTimeStr) {
		this.grantTimeStr = grantTimeStr;
	}
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public String getGrantName() {
		return grantName;
	}
	public void setGrantName(String grantName) {
		this.grantName = grantName;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getShopsName() {
		return shopsName;
	}
	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	public String getCountNo() {
		return countNo;
	}
	public void setCountNo(String countNo) {
		this.countNo = countNo;
	}
	public String getGiftstoreName() {
		return giftstoreName;
	}
	public void setGiftstoreName(String giftstoreName) {
		this.giftstoreName = giftstoreName;
	}
	
}
