package com.gudeng.commerce.gd.search.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 
 * 包含 business 基础信息
 * 
 * 
 */
public class BusinessSolrDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9211192903347111862L;

	 
	//商铺ID
	@Field("businessId")
	public Integer businessId;
	


	//公司名称
	@Field("name")
	public String name;
	//经营模式
	@Field("businessModel")
	public Integer businessModel;
	//经营类型
	@Field("managementType")
	public Integer managementType;
	
	//所属市场ID
	@Field("marketId")
	public Integer marketId;
	
	//所属市场名称
	@Field("marketName")
	public String marketName;
	
	//是否已经认证
	@Field("status")
	public Integer status;
	
 
	//商铺介绍
	@Field("companyProfile")
	public Long companyProfile;
	//商铺名称
	@Field("shopsName")
	public String shopsName;
	//主营商品
	@Field("mainProduct")
	public String mainProduct;
	
	//商铺描述
	@Field("shopDesc")
	public String shopDesc;
	//商铺url
	@Field("shopsUrl")
	public String shopsUrl;
	
	//经营分类id
	@Field("categoryId")
	List<Long> categoryId;
	
	//经营分类名称
	@Field("cateName")
	List<String> cateName;
	
	//产品id
	@Field("productId")
	List<Long> productId;
	
	//产品原图地址
	@Field("urlOrg")
	List<String> urlOrg;
	
	/**
	 * 省份ID
	 * 
	 * */
	@Field("provinceId")
	public String provinceId;
	
	/**
	 * 城市ID
	 * 
	 * */
	@Field("cityId")
	public String cityId;
	
	/**
	 * 县ID
	 * 
	 * */
	@Field("areaId")
	public String areaId;
	
	/**
	 * 详细地址
	 * 
	 * */
	@Field("address")
	public String address;
	/**
	 * 
	 * 会员类别（1谷登农批，2农速通，3农批宝，4产地供应商，余待补）
	 * 
	 * 
	 * 普通买家找 谷登农批的店铺，   谷登农批找产地供应商店铺
	 * 
	 * 
	 * */
	@Field("level")
	public Integer level;
	/**
	 * 
	 * 联系方式
	 * 
	 * */
	@Field("mobile")
	public String mobile;
	
	/**
	 * 会员等级 1黄金会员 
	 */
	@Field("memberGrade")
	public Integer memberGrade;
	
	@Field("price")
	public List<Double>  price;
	
	@Field("unit")
	public  List<String>  unit;
	
	@Field("productName")
	public List<String> productName;
	
	/**
	 * 企业认证
	 */
	@Field("comstatus")
	public Integer comstatus;
	/**
	 * 合作社认证
	 */
	@Field("ccstatus")
	public Integer ccstatus;
	/**
	 * 基地认证 
	 */
	@Field("cbstatus")
	public Integer cbstatus;
	
	public Integer getComstatus() {
		return comstatus;
	}
	public void setComstatus(Integer comstatus) {
		this.comstatus = comstatus;
	}
	public Integer getCcstatus() {
		return ccstatus;
	}
	public void setCcstatus(Integer ccstatus) {
		this.ccstatus = ccstatus;
	}
	public Integer getCbstatus() {
		return cbstatus;
	}
	public void setCbstatus(Integer cbstatus) {
		this.cbstatus = cbstatus;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getMemberGrade() {
		return memberGrade;
	}
	public void setMemberGrade(Integer memberGrade) {
		this.memberGrade = memberGrade;
	}
	public List<Double> getPrice() {
		return price;
	}
	public void setPrice(List<Double> price) {
		this.price = price;
	}
	public List<String> getUnit() {
		return unit;
	}
	public void setUnit(List<String> unit) {
		this.unit = unit;
	}
	public List<String> getProductName() {
		return productName;
	}
	public void setProductName(List<String> productName) {
		this.productName = productName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBusinessModel() {
		return businessModel;
	}
	public void setBusinessModel(Integer businessModel) {
		this.businessModel = businessModel;
	}
	public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getCompanyProfile() {
		return companyProfile;
	}
	public void setCompanyProfile(Long companyProfile) {
		this.companyProfile = companyProfile;
	}
	public String getShopsName() {
		return shopsName;
	}
	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}
	public String getShopDesc() {
		return shopDesc;
	}
	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}
	public String getShopsUrl() {
		return shopsUrl;
	}
	public void setShopsUrl(String shopsUrl) {
		this.shopsUrl = shopsUrl;
	}
	public List<Long> getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(List<Long> categoryId) {
		this.categoryId = categoryId;
	}
	public List<String> getCateName() {
		return cateName;
	}
	public void setCateName(List<String> cateName) {
		this.cateName = cateName;
	}
	public List<Long> getProductId() {
		return productId;
	}
	public void setProductId(List<Long> productId) {
		this.productId = productId;
	}
	public List<String> getUrlOrg() {
		return urlOrg;
	}
	public void setUrlOrg(List<String> urlOrg) {
		this.urlOrg = urlOrg;
	}
	
	public String getMainProduct() {
		return mainProduct;
	}
	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
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
	public Integer getManagementType() {
		return managementType;
	}
	public void setManagementType(Integer managementType) {
		this.managementType = managementType;
	}
	
	

	
	

}
