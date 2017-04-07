package com.gudeng.commerce.gd.api.dto.output;

/**
 * 店铺详情DTO
 * @author TerryZhang
 *
 */
public class BusinessDetailAppDTO {

	/** 店主id  */
	private Long userId;
	
	/** 店主真实姓名  */
	private String realName;
	
	/** 店铺id  */
	private Long businessId;
	
	/** 店铺名称  */
	private String shopsName;
	
	/** 店铺描述 */
	private String shopsDesc;
	
	/** 主营商品  */
	private String mainProduct;
	
	/** 市场id  */
	private Long marketId;
	
	/** 市场名字  */
	private String marketName;
	
	/** 经营模式  */
	private String businessModelStr;
	
	/** 店主手机号  */
	private String mobile;
	
	/** 是否关注, 0:否, 1: 是 */
	private Integer isFocus;
	
	/** 详细地址  */
	private String address;
	
	/** 省份id  */
	private Long provinceId;
	
	/** 省份名字  */
	private String provinceName;
	
	/** 城市id  */
	private Long cityId;
	
	/** 城市名字  */
	private String cityName;
	
	/** 区县id  */
	private Long countyId;
	
	/** 区县名字  */
	private String countyName;
	
	/** 经营范围  */
	private String[] BusinessScope;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
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

	public String getBusinessModelStr() {
		return businessModelStr;
	}

	public void setBusinessModelStr(String businessModelStr) {
		this.businessModelStr = businessModelStr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsFocus() {
		return isFocus;
	}

	public void setIsFocus(Integer isFocus) {
		this.isFocus = isFocus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShopsDesc() {
		return shopsDesc;
	}

	public void setShopsDesc(String shopsDesc) {
		this.shopsDesc = shopsDesc;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String[] getBusinessScope() {
		return BusinessScope;
	}

	public void setBusinessScope(String[] businessScope) {
		BusinessScope = businessScope;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
}
