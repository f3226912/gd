package com.gudeng.commerce.gd.api.dto;

import java.io.Serializable;
import java.util.Date;

public class PushProductAppDTO implements Serializable{

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
	
	private String startTime;
	
	private String imageUrl;
	
	private String formattedPrice;
	
	private Date expirationDate;
	
	private String state;

	/**
	 * 库存
	 */
	private Double stockCount;
	
	/**
	 * 格式化后库存
	 */
	private String formattedStock;
	
	/**
	 * 是否有活动
	 */
	private Integer hasActivity;
	
	
	private Integer platform;	//是否平台配送，0：否，1 是

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
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

	public String getFormattedStock() {
		return formattedStock;
	}

	public void setFormattedStock(String formattedStock) {
		this.formattedStock = formattedStock;
	}

	public Integer getHasActivity() {
		return hasActivity;
	}

	public void setHasActivity(Integer hasActivity) {
		this.hasActivity = hasActivity;
	}
}
