package com.gudeng.commerce.gd.api.dto.output;

import java.io.Serializable;

public class TradingDynamicsOutDTO implements Serializable{
	
	private static final long serialVersionUID = 4015245519728264325L;
	private String businessName;
    private String businessId;
    private String productCategory;
    private Double tradingVolume;
    private String realName;
    
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public Double getTradingVolume() {
		return tradingVolume;
	}
	public void setTradingVolume(Double tradingVolume) {
		this.tradingVolume = tradingVolume;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
    
    
}
