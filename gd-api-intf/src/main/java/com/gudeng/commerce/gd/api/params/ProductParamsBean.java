package com.gudeng.commerce.gd.api.params;


public class ProductParamsBean {
	
	private String productId;
	private String productName;
	private String stockCount;
	private String unit;
	private String price;
	private String picture;
	private String infoLifeDay;
	private String description;
	private String categoryId;
	private String userId;
	private String businessId;
	private String option ;

	private String adProductIds;//广告位推广产品集合
	private String roleType;//产品类型
	private String managementType;//经营类型
	private String marketId;//市场ID

	private String sourceId;
	
	private String productSign;//产品标签
	
	private String cateIds; //传入多个CategoryID进行检索
	
	private Long memberId;
	
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getCateIds() {
		return cateIds;
	}
	public void setCateIds(String cateIds) {
		this.cateIds = cateIds;
	}
	public String getProductSign() {
		return productSign;
	}
	public void setProductSign(String productSign) {
		this.productSign = productSign;
	}
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	public String getManagementType() {
		return managementType;
	}
	public void setManagementType(String managementType) {
		this.managementType = managementType;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getAdProductIds() {
		return adProductIds;
	}
	public void setAdProductIds(String adProductIds) {
		this.adProductIds = adProductIds;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}


	/**
	 * 产地-省
	 */
	private String originProvinceId;
	/**
	 * 产地-市
	 */
	private String originCityId;
	/**
	 * 产地-区/县
	 */
	private String originAreaId;
	
	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getOriginProvinceId() {
		return originProvinceId;
	}

	public void setOriginProvinceId(String originProvinceId) {
		this.originProvinceId = originProvinceId;
	}

	public String getOriginCityId() {
		return originCityId;
	}

	public void setOriginCityId(String originCityId) {
		this.originCityId = originCityId;
	}

	public String getOriginAreaId() {
		return originAreaId;
	}

	public void setOriginAreaId(String originAreaId) {
		this.originAreaId = originAreaId;
	}

	//入库表id
	private String isdId;
	
	public String getIsdId() {
		return isdId;
	}

	public void setIsdId(String isdId) {
		this.isdId = isdId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStockCount() {
		return stockCount;
	}

	public void setStockCount(String stockCount) {
		this.stockCount = stockCount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getInfoLifeDay() {
		return infoLifeDay;
	}

	public void setInfoLifeDay(String infoLifeDay) {
		this.infoLifeDay = infoLifeDay;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "ProductParamsBean [productId=" + productId + ", productName="
				+ productName + ", stockCount=" + stockCount + ", unit=" + unit
				+ ", price=" + price + ", picture=" + picture
				+ ", infoLifeDay=" + infoLifeDay + ", description="
				+ description + ", categoryId=" + categoryId + ", userId="
				+ userId + ", businessId=" + businessId + ", option=" + option
				+ ", originProvinceId=" + originProvinceId + ", originCityId="
				+ originCityId + ", originAreaId=" + originAreaId + ", isdId="
				+ isdId + "]";
	}
	
}
