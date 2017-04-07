package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.UsercollectShopEntity;

public class UsercollectShopDTO extends UsercollectShopEntity {
	
	private String shopsName;
	
	private String marketName;
	
	private String mobile;
	
	private String memberName;
	
	private Integer status;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6830986873599171494L;

}
