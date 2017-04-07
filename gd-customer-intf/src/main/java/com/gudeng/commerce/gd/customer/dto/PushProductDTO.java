package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Date;

public class PushProductDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long productId;
	
	private Long marketId;
	
	private String marketName;
	
	private String productName;
	
	private Double price;
	
	/**
	 * 单价的单位
	 */
	private String unitName;
	
	private Long businessId;
	
	private String shopsName;
	
	private String mobile;
	
	private String memberGrade;
	
	private String startTime;
	
	private String imageUrl;
	
	private String unit;
	
	private Integer managementType; 
	
	private String originAddress;
	
	private String updatePriceTime;
	
	private String updateTime;
	
	//是否参加活动，0：否，1 是
	private Integer promotion = 0;
	
	private Integer platform;
	
	public Integer getPlatform() {
		return platform;
	}
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	public Integer getPromotion() {
		return promotion;
	}
	public void setPromotion(Integer promotion) {
		this.promotion = promotion;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdatePriceTime() {
		return updatePriceTime;
	}

	public void setUpdatePriceTime(String updatePriceTime) {
		this.updatePriceTime = updatePriceTime;
	}

	public String getOriginAddress() {
		return originAddress;
	}

	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}

	public Integer getManagementType() {
		return managementType;
	}

	public void setManagementType(Integer managementType) {
		this.managementType = managementType;
	}

	/**
	 * 库存
	 */
	private Double stockCount;
	
	private String formattedPrice;
	
	private Date expirationDate;
	
	private String state;
	
	private Integer status;
	
	/**
	 * 是否有活动
	 */
	private Integer hasActivity;
	
	
	/**
	 * 产地标识认证
	 */
	private Integer certifstatus;
	/**
	 * 企业认证
	 */
	private Integer comstatus;
	/**
	 * 合作社认证
	 */
	private Integer ccstatus;
	/**
	 * 基地认证 
	 */
	private Integer cbstatus;
	
	public Integer getCertifstatus() {
		return certifstatus;
	}
	public void setCertifstatus(Integer certifstatus) {
		this.certifstatus = certifstatus;
	}
	public Integer getComstatus() {
		return comstatus;
	}
	public void setComstatus(Integer comstatus) {
		this.comstatus = comstatus;
	}
	public Integer getCcstatus() {
		return ccstatus;
	}
	public void setCcstatus(Integer ccstatus) {
		this.ccstatus = ccstatus;
	}
	public Integer getCbstatus() {
		return cbstatus;
	}
	public void setCbstatus(Integer cbstatus) {
		this.cbstatus = cbstatus;
	}
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getStockCount() {
		return stockCount;
	}

	public void setStockCount(Double stockCount) {
		this.stockCount = stockCount;
	}

	public Integer getHasActivity() {
		return hasActivity;
	}

	public void setHasActivity(Integer hasActivity) {
		this.hasActivity = hasActivity;
	}
	public String getMemberGrade() {
		return memberGrade;
	}
	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}
}
