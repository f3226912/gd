package com.gudeng.commerce.gd.api.dto.output;

public class OrderProductDTO {
	/** 订单号 */
	private Long orderNo;

    /** 产品id */
    private Integer productId;

    /** 产品名   */
    private String productName;

    /** 产品购买数量  */
    private Double purQuantity;
    
    /**  产品单位*/
	private String unitName;

	/**  产品价格*/
    private Double price;
    
    /** 格式化后价格 */
	private String formattedPrice;

    /** 产品价格(忽略)*/
    private Double tradingPrice;
    
    /** 产品总额*/
    private Double needToPayAmount;

    /** 产品图片地址*/
	private String imageUrl;
	
	private String hasBuySub; // 是否有买家补贴 1有 0无
	
	private String hasSellSub; // 是否有农批商补贴  1有 0无
	
	private Integer status; //出货状态: 0未出货 1出货中 2已出货

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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public String getHasBuySub() {
		return hasBuySub;
	}

	public void setHasBuySub(String hasBuySub) {
		this.hasBuySub = hasBuySub;
	}

	public String getHasSellSub() {
		return hasSellSub;
	}

	public void setHasSellSub(String hasSellSub) {
		this.hasSellSub = hasSellSub;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
