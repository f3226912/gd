package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

public class FollowProductDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2069140160261813671L;
	
	/**
	 * 用户id
	 */
	private long userId;
	
	/**
	 * 分来id
	 */
	private long cateId;
	
	/**
	 * 产品id
	 */
	private long productId=0;
	
	/**
	 * 用户的推送deviceTokens
	 */
	private String deviceTokens;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCateId() {
		return cateId;
	}

	public void setCateId(long cateId) {
		this.cateId = cateId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDeviceTokens() {
		return deviceTokens;
	}

	public void setDeviceTokens(String deviceTokens) {
		this.deviceTokens = deviceTokens;
	}
	
	

}
