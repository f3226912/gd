package com.gudeng.commerce.gd.api.dto.output;

/**
 * pos机订单产品DTO
 * @author TerryZhang
 */
public class PosOrderProductDTO {
	/** 订单号*/
	private Long orderNo;
	
	/** 产品id*/
    private Integer productId;
    
    /** 产品单位*/
    private String productName;
    
    /** 采购量*/
    private Double purQuantity;
    
    /** 产品单位id*/
    private String unit;
    
    /** 产品交易价格*/
    private Double price;
    
    /** 产品交易价*/
    private Double tradingPrice;
    
    /** 产品总额*/
    private Double needToPayAmount;
    
    /** 产品图片地址*/
	private String imageUrl;
	
	/** 产品单位名*/
	private String unitName;
	
	/** 格式化后价格 */
	private String formattedPrice;

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPurQuantity() {
		return purQuantity;
	}

	public void setPurQuantity(Double purQuantity) {
		this.purQuantity = purQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTradingPrice() {
		return tradingPrice;
	}

	public void setTradingPrice(Double tradingPrice) {
		this.tradingPrice = tradingPrice;
	}

	public Double getNeedToPayAmount() {
		return needToPayAmount;
	}

	public void setNeedToPayAmount(Double needToPayAmount) {
		this.needToPayAmount = needToPayAmount;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}
}
