package com.gudeng.commerce.gd.m.dto;

import java.io.Serializable;

public class PromotionActivityInputDTO implements Serializable {
	private static final long serialVersionUID = -1524368559179756478L;

	private String userId;
	private Integer actId;
    private String marketId;
    private String productType;

	

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	private String activityId;
	private String supplierId;
	private String supplierName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	private String productDetails;
}
