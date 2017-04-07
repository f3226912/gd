package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "business_baseinfo")
public class BusinessBaseinfoEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5531278128951641785L;

	private Long businessId;

	private Long userId;

	private String name;
	
	private String mainProduct;

	private Integer staffCount;

	private Integer businessModel;

	private String companyProfile;

	private String companyPicture;

	private String founded;

	private String legalRepresentative;

	private String shopsName;

	private String shopsDesc;

	private String shopsUrl;

	private Long provinceId;

	private Long cityId;

	private Long areaId;

	private String address;

	private Double lon;

	private Double lat;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;

	private Long browseCount;

	private String zipCode;

	private String banelImgUrl;
	
	private String userImgUrl;
	
	private String posNumber;//由一个变为多个，此字段废弃，用 re_business_pos 关联获取
	
	private String businessNo;
	
	private Integer managementType;//经营类型,1表示种养大户，2表示合作社，3表示基地

	private String BunkCode;//铺位编号
	
	private Integer offlineStatus;//线下认证状态
	
	@Column(name = "offlineStatus")
	public Integer getOfflineStatus() {
		return offlineStatus;
	}

	public void setOfflineStatus(Integer offlineStatus) {
		this.offlineStatus = offlineStatus;
	}

	@Column(name = "BunkCode")
	public String getBunkCode() {
		return BunkCode;
	}

	public void setBunkCode(String bunkCode) {
		BunkCode = bunkCode;
	}
	@Column(name = "businessNo")
	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	
	@Column(name = "managementType")
	public Integer getManagementType() {
		return managementType;
	}

	public void setManagementType(Integer managementType) {
		this.managementType = managementType;
	}

	@Column(name = "posNumber")
	public String getPosNumber() {
		return posNumber;
	}

	public void setPosNumber(String posNumber) {
		this.posNumber = posNumber;
	}

	@Column(name = "banelImgUrl")
	public String getBanelImgUrl() {
		return banelImgUrl;
	}

	public void setBanelImgUrl(String banelImgUrl) {
		this.banelImgUrl = banelImgUrl;
	}
	
	@Column(name = "userImgUrl")
	public String getUserImgUrl() {
		return userImgUrl;
	}

	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}

	@Column(name = "zipCode")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Long getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(Long browseCount) {
		this.browseCount = browseCount;
	}

	@Id
	@Column(name = "businessId")
	public Long getBusinessId() {

		return this.businessId;
	}

	public void setBusinessId(Long businessId) {

		this.businessId = businessId;
	}

	@Column(name = "userId")
	public Long getUserId() {

		return this.userId;
	}

	public void setUserId(Long userId) {

		this.userId = userId;
	}

	@Column(name = "name")
	public String getName() {

		return this.name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Column(name = "staffCount")
	public Integer getStaffCount() {

		return this.staffCount;
	}

	public void setStaffCount(Integer staffCount) {

		this.staffCount = staffCount;
	}

	@Column(name = "businessModel")
	public Integer getBusinessModel() {

		return this.businessModel;
	}

	public void setBusinessModel(Integer businessModel) {

		this.businessModel = businessModel;
	}

	@Column(name = "companyProfile")
	public String getCompanyProfile() {

		return this.companyProfile;
	}

	public void setCompanyProfile(String companyProfile) {

		this.companyProfile = companyProfile;
	}

	@Column(name = "companyPicture")
	public String getCompanyPicture() {

		return this.companyPicture;
	}

	public void setCompanyPicture(String companyPicture) {

		this.companyPicture = companyPicture;
	}

	@Column(name = "founded")
	public String getFounded() {

		return this.founded;
	}

	public void setFounded(String founded) {

		this.founded = founded;
	}

	@Column(name = "legalRepresentative")
	public String getLegalRepresentative() {

		return this.legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {

		this.legalRepresentative = legalRepresentative;
	}

	@Column(name = "shopsName")
	public String getShopsName() {

		return this.shopsName;
	}

	public void setShopsName(String shopsName) {

		this.shopsName = shopsName;
	}

	@Column(name = "shopsDesc")
	public String getShopsDesc() {

		return this.shopsDesc;
	}

	public void setShopsDesc(String shopsDesc) {

		this.shopsDesc = shopsDesc;
	}

	@Column(name = "shopsUrl")
	public String getShopsUrl() {

		return this.shopsUrl;
	}

	public void setShopsUrl(String shopsUrl) {

		this.shopsUrl = shopsUrl;
	}

	@Column(name = "provinceId")
	public Long getProvinceId() {

		return this.provinceId;
	}

	public void setProvinceId(Long provinceId) {

		this.provinceId = provinceId;
	}

	@Column(name = "cityId")
	public Long getCityId() {

		return this.cityId;
	}

	public void setCityId(Long cityId) {

		this.cityId = cityId;
	}

	@Column(name = "areaId")
	public Long getAreaId() {

		return this.areaId;
	}

	public void setAreaId(Long areaId) {

		this.areaId = areaId;
	}

	@Column(name = "address")
	public String getAddress() {

		return this.address;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	@Column(name = "lon")
	public Double getLon() {

		return this.lon;
	}

	public void setLon(Double lon) {

		this.lon = lon;
	}

	@Column(name = "lat")
	public Double getLat() {

		return this.lat;
	}

	public void setLat(Double lat) {

		this.lat = lat;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	@Column(name = "mainProduct")
	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}
	
	
}
