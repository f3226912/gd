package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Date;

public class CustInfoDTO implements Serializable {


	private static final long serialVersionUID = 4902433956029372987L;
	
	private String businessId;
	
	private String memberId;
	
	private String realName;
	
	private String mobile;
	
	private String shopsName;
	
	private String address;//详细地址
	
	private String  area;//省市区
	
	private String mainProduct;
	
	private Date datatime;
	
	private String marketName;

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getDatatime() {
		return datatime;
	}

	public void setDatatime(Date datatime) {
		this.datatime = datatime;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}


}
