package com.gudeng.commerce.gd.api.dto;

import java.util.Date;

/**
 * app端产品列表信息DTO
 * @author TerryZhang
 */
public class ProductListAppDTO {

	/**产品描述*/
	private String description;
	
	/** 格式化后价格 */
	private String formattedPrice;
	
	/**  是否关注*/
	private Integer hasFocused;
	
	/**  市场id*/
	private Long marketId;
	
	/**  价格*/
	private Double price;
	
	/**  产品id*/
	private Long productId;
	
	/**  产品名字*/
	private String productName;
	
	/**  库存*/
	private Double stockCount;
	
	/**  格式化后库存*/
	private String formattedStock;
	
	/**  产品单位*/
	private String unitName;
	
	/**  图片链接*/
	private String urlOrg;
	
	/**
	 * 是否有补贴, 0 为无， 1为有
	 */
	private Integer hasSub;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	private String originProvinceNam="";
	private String originCityName="";
	private String originAreaName="";
	private String createTimeStr = "";
	
	private String certifstatus ;
	
	public String getCertifstatus() {
		return certifstatus;
	}

	public void setCertifstatus(String certifstatus) {
		this.certifstatus = certifstatus;
	}

	//是否参加活动，0：否，1 是
	private Integer promotion = 0;
	
	private Integer platform;	//是否平台配送，0：否，1 是

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}

	public Integer getHasFocused() {
		return hasFocused;
	}

	public void setHasFocused(Integer hasFocused) {
		this.hasFocused = hasFocused;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public String getFormattedStock() {
		return formattedStock;
	}

	public void setFormattedStock(String formattedStock) {
		this.formattedStock = formattedStock;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUrlOrg() {
		return urlOrg;
	}

	public void setUrlOrg(String urlOrg) {
		this.urlOrg = urlOrg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getHasSub() {
		return hasSub;
	}

	public void setHasSub(Integer hasSub) {
		this.hasSub = hasSub;
	}

	public String getOriginProvinceNam() {
		return originProvinceNam;
	}

	public void setOriginProvinceNam(String originProvinceNam) {
		this.originProvinceNam = originProvinceNam;
	}

	public String getOriginCityName() {
		return originCityName;
	}

	public void setOriginCityName(String originCityName) {
		this.originCityName = originCityName;
	}

	public String getOriginAreaName() {
		return originAreaName;
	}

	public void setOriginAreaName(String originAreaName) {
		this.originAreaName = originAreaName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Integer getPromotion() {
		return promotion;
	}

	public void setPromotion(Integer promotion) {
		this.promotion = promotion;
	}
	
	
}
