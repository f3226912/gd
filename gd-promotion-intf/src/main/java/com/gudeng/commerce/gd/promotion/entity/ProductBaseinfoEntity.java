package com.gudeng.commerce.gd.promotion.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "product_baseinfo")
public class ProductBaseinfoEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7053362904169283276L;

	private Long productId;

	private Long cateId;

	private Long businessId;

	private String productName;

	private Double price;

	private String priceType;

	private String state;

	private String unit;

	private Long marketId;

	private Long provinceId;

	private Long cityId;

	private Long areaId;

	private String address;

	private Date expirationDate;

	private String infoLifeDay;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;

	private String description;

	private String content;

	private String keyword;

	private Integer visitCount;

	private String logisticsRemark;

	private Double stockCount;

	private Double minBuyCount;

	private String channel;
	
	private String roleType;

	//private List<ProductPriceDto> priceDtoList;// 用于设置多价订购的区间价格相关参数
	
	private Date updatePriceTime;
	
	private String productSign;
	
	@Column(name = "productSign")
	public String getProductSign() {
		return productSign;
	}

	public void setProductSign(String productSign) {
		this.productSign = productSign;
	}

	@Column(name = "updatePriceTime")
	public Date getUpdatePriceTime() {
		return updatePriceTime;
	}

	public void setUpdatePriceTime(Date updatePriceTime) {
		this.updatePriceTime = updatePriceTime;
	}

	/**
	 * 关键内容修改标识
	 */
	private String editSign ;
	
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
	
	@Column(name = "originProvinceId")
	public String getOriginProvinceId() {
		return originProvinceId;
	}

	public void setOriginProvinceId(String originProvinceId) {
		this.originProvinceId = originProvinceId;
	}
	@Column(name = "originCityId")
	public String getOriginCityId() {
		return originCityId;
	}

	public void setOriginCityId(String originCityId) {
		this.originCityId = originCityId;
	}
	
	@Column(name = "originAreaId")
	public String getOriginAreaId() {
		return originAreaId;
	}

	public void setOriginAreaId(String originAreaId) {
		this.originAreaId = originAreaId;
	}
	@Column(name = "editSign")
	public String getEditSign() {
		return editSign;
	}
	public void setEditSign(String editSign) {
		this.editSign = editSign;
	}
	
	@Column(name = "roleType")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Id
	@Column(name = "productId", unique = true, nullable = false)
	public Long getProductId() {

		return this.productId;
	}

	public void setProductId(Long productId) {

		this.productId = productId;
	}

	@Column(name = "marketId")
	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	@Column(name = "priceType")
	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	@Column(name = "cateId")
	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	@Column(name = "businessId")
	public Long getBusinessId() {

		return this.businessId;
	}

	public void setBusinessId(Long businessId) {

		this.businessId = businessId;
	}

	@Column(name = "price")
	public Double getPrice() {

		return this.price;
	}

	public void setPrice(Double price) {

		this.price = price;
	}

	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "unit")
	public String getUnit() {

		return this.unit;
	}

	public void setUnit(String unit) {

		this.unit = unit;
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

	@Column(name = "expirationDate")
	public Date getExpirationDate() {

		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {

		this.expirationDate = expirationDate;
	}

	@Column(name = "infoLifeDay")
	public String getInfoLifeDay() {

		return this.infoLifeDay;
	}

	public void setInfoLifeDay(String infoLifeDay) {

		this.infoLifeDay = infoLifeDay;
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

	@Column(name = "updateUserId", nullable = true)
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime", nullable = true)
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	@Column(name = "description")
	public String getDescription() {

		return this.description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	@Column(name = "content")
	public String getContent() {

		return this.content;
	}

	public void setContent(String content) {

		this.content = content;
	}

	@Column(name = "keyword")
	public String getKeyword() {

		return this.keyword;
	}

	public void setKeyword(String keyword) {

		this.keyword = keyword;
	}

	@Column(name = "visitCount")
	public Integer getVisitCount() {

		return this.visitCount;
	}

	public void setVisitCount(Integer visitCount) {

		this.visitCount = visitCount;
	}

	@Column(name = "logisticsRemark")
	public String getLogisticsRemark() {

		return this.logisticsRemark;
	}

	public void setLogisticsRemark(String logisticsRemark) {

		this.logisticsRemark = logisticsRemark;
	}

	@Column(name = "stockCount")
	public Double getStockCount() {

		return this.stockCount;
	}

	public void setStockCount(Double stockCount) {

		this.stockCount = stockCount;
	}

	@Column(name = "minBuyCount")
	public Double getMinBuyCount() {

		return this.minBuyCount;
	}

	public void setMinBuyCount(Double minBuyCount) {

		this.minBuyCount = minBuyCount;
	}

	@Column(name = "channel")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	/*public List<ProductPriceDto> getPriceDtoList() {
		return priceDtoList;
	}

	public void setPriceDtoList(List<ProductPriceDto> priceDtoList) {
		this.priceDtoList = priceDtoList;
	}*/

	@Column(name = "name")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
