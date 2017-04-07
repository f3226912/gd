package com.gudeng.commerce.gd.api.params;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BusinessParamsBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1456134708550981113L;

	/**
	 *  公司名称
	 *  
	 */
	private String name;
	
	/**
	 *  主营商品
	 */
	private String  mainProduct;
	
	private String province;
	
	private Long provinceId;
	
	private String city;
	
	private Long cityId;

	private String area;
	
	private Long areaId;
	
	private Long businessId;
	
	private String marketId;
	
	private String marketName;
	
	private Long userId;

	private Integer businessModel;
	
	/**
	 * 店铺类型，4 为产地供应商，1为农批商
	 * 
	 * */
	private Integer level;

	/**
	 * 商铺名称
	 * */
	private String shopsName;
	
	/**
	 * 合作社认证状态
	 */
	private String ccstatus;
	
	/**
	 * 基地认证状态
	 */
	private String cbstatus;
	
	/**
	 * 个人认证状态
	 */
	private String cpstatus;
	
	/**
	 * 企业认证状态
	 */
	private String comstatus;
	
	/**
	 * 实体认证状态
	 */
	private String shopstatus;
	
	/** 
	 * 会员等级(0:普通会员，1:金牌供应商)
	 * */
	private String memberGrade;
	/** 
	 * 浏览量
	 * */
	private Long browseCount;

	public Long getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(Long browseCount) {
		this.browseCount = browseCount;
	}

	public String getCcstatus() {
		return ccstatus;
	}

	public void setCcstatus(String ccstatus) {
		this.ccstatus = ccstatus;
	}

	public String getCbstatus() {
		return cbstatus;
	}

	public void setCbstatus(String cbstatus) {
		this.cbstatus = cbstatus;
	}

	public String getComstatus() {
		return comstatus;
	}

	public void setComstatus(String comstatus) {
		this.comstatus = comstatus;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	private String shopsDesc;

	private String address;
	
	private String categoryIds;

	private Integer managementType;//经营类型,1表示种养大户，2表示合作社，3表示基地

	private String mobile;
	private List<Map<String,Object>> allCategory;
	
	private String shopsUrl;
	
	public String getShopsUrl() {
		return shopsUrl;
	}

	public void setShopsUrl(String shopsUrl) {
		this.shopsUrl = shopsUrl;
	}

	public List<Map<String, Object>> getAllCategory() {
		return allCategory;

	}

	public void setAllCategory(List<Map<String, Object>> allCategory) {
		this.allCategory = allCategory;
	}

	private List<Map<String,Object>> zyCategory;//主营
	
	private List<Map<String,Object>> jyCategory;//兼营
	
	public List<Map<String, Object>> getJyCategory() {
		return jyCategory;
	}

	public void setJyCategory(List<Map<String, Object>> jyCategory) {
		this.jyCategory = jyCategory;
	}

	public List<Map<String, Object>> getZyCategory() {
		return zyCategory;
	}

	public void setZyCategory(List<Map<String, Object>> zyCategory) {
		this.zyCategory = zyCategory;
	}

	private String areaType;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Integer getManagementType() {
		return managementType;
	}

	public void setManagementType(Integer managementType) {
		this.managementType = managementType;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	
	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Integer getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(Integer businessModel) {
		this.businessModel = businessModel;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getShopsDesc() {
		return shopsDesc;
	}

	public void setShopsDesc(String shopsDesc) {
		this.shopsDesc = shopsDesc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}

	public String getCpstatus() {
		return cpstatus;
	}

	public void setCpstatus(String cpstatus) {
		this.cpstatus = cpstatus;
	}

	public String getShopstatus() {
		return shopstatus;
	}

	public void setShopstatus(String shopstatus) {
		this.shopstatus = shopstatus;
	}
	
}
