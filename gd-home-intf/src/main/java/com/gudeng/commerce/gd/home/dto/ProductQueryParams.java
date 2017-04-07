package com.gudeng.commerce.gd.home.dto;

import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;

public class ProductQueryParams{
	
	private String productId;
	private String productName;
	private String productTypeId;
	private String businessId;
	private String stockCount;
	private String unit;
	private Double price;
	private String priceType;
	private String startDate;
	private String endDate;
	private String channel;
	private String mobile;
	
	private String  roleType;
	private String masterPicture;
	private String multiplePicture;
	private String webPicture;
	private String appPicture;
	
	private String infoLifeDay;
	private String description;
	
    private String provinceId;
    private String cityId;
    private String areaId;
    private String address;
	
	private String account;
	private String categoryId;
	private String state;
	
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
	
	
    public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMasterPicture() {
		return masterPicture;
	}
	public void setMasterPicture(String masterPicture) {
		this.masterPicture = masterPicture;
	}
	public String getMultiplePicture() {
		return multiplePicture;
	}
	public void setMultiplePicture(String multiplePicture) {
		this.multiplePicture = multiplePicture;
	}
	public String getWebPicture() {
		return webPicture;
	}
	public void setWebPicture(String webPicture) {
		this.webPicture = webPicture;
	}
	public String getAppPicture() {
		return appPicture;
	}
	public void setAppPicture(String appPicture) {
		this.appPicture = appPicture;
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
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	private String  marketId;
    
    private List<ProductPriceDto> priceDtoList;//用于设置多价订购的区间价格相关参数
    
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getStockCount() {
		return stockCount;
	}
	public void setStockCount(String stockCount) {
		this.stockCount = stockCount;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
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
	public List<ProductPriceDto> getPriceDtoList() {
		return priceDtoList;
	}
	public void setPriceDtoList(List<ProductPriceDto> priceDtoList) {
		this.priceDtoList = priceDtoList;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
}
