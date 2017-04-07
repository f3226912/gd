package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.UsercollectProductEntity;


public class UsercollectProductDTO extends UsercollectProductEntity{

	private String productIds;

	private String marketName;

	private int startRow=0;

	private int endRow=5;

	/**
	 * 单位(展示用)
	 */
	private String showedUnit;

	/**
	 * 单位
	 */
	private int unit ;

	/**
	 * 被关注的产品所属店铺的店铺名称
	 */
	private String shopsName;
	/**
	 * 被关注的产品所属用户的手机号
	 */
	private String mobile;
	/**
	 * 被关注产品图片Url
	 */
	private String appListImg;

	/**
	 * 被关注产品的单价
	 */
	private Double price;

	private String showedPrice ;

	private String concernTime ;

	private String productUpdateTime;
	/**
	 * 是否平台配送
	 */
	private Integer platform;

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getProductUpdateTime() {
		return productUpdateTime;
	}

	public void setProductUpdateTime(String productUpdateTime) {
		this.productUpdateTime = productUpdateTime;
	}

	public String getShowedUnit() {
		return showedUnit;
	}

	public void setShowedUnit(String showedUnit) {
		this.showedUnit = showedUnit;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getConcernTime() {
		return concernTime;
	}

	public void setConcernTime(String concernTime) {
		this.concernTime = concernTime;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -7012175169532989599L;

	/**
	 * 产品名字
	 */
	private String productName;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
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

	public String getAppListImg() {
		return appListImg;
	}

	public void setAppListImg(String appListImg) {
		this.appListImg = appListImg;
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

}
