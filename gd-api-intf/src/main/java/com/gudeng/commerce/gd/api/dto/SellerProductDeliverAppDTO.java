package com.gudeng.commerce.gd.api.dto;

public class SellerProductDeliverAppDTO {

	private Long productId;
	
	private String productName;
	
	private String picture;
	
	private Double stockCount;
	
	private Double price;
	
	private String showedPrice;
	
	private String unit;
	
	private String unitName;

	private Long orderNo;

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getStockCount() {
		return stockCount;
	}

	public void setStockCount(Double stockCount) {
		this.stockCount = stockCount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getShowedPrice() {
		return showedPrice;
	}

	public void setShowedPrice(String showedPrice) {
		this.showedPrice = showedPrice;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
