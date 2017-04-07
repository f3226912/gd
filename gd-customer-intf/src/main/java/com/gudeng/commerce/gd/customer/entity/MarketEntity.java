package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 街市entity
 * @author xxzhou
 *
 */
@Entity(name = "market")
public class MarketEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9048968728391838967L;

	private Long id;

	private String marketName;
	
	private String marketType;
	
	private  Long provinceId;

	private  Long cityId;

	private Long areaId;

	private String status;

	private String description;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;

	private Double lon;

	private Double lat;

	private String address;
	/**
	 * 市场简称
	 */
	private String marketShortName;
	/**
	 * 占地面积
	 */
	private String marketArea;
	/**
	 * 年交易量
	 */
	private String tradeAmount;
	/**
	 * 年交易额
	 */
	private String tradeMoney;
	/**
	 * 市场配图
	 */
	private String marketImg;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 入驻商户
	 */
	private String businessAmount;
	/**
	 * 入驻商户分类
	 */
	private String businessClassAmount;
	
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "marketName")
	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	@Column(name = "marketType")
	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	@Column(name = "provinceId")
	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	@Column(name = "cityId")
	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	@Column(name = "areaId")
	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "lon")
	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	@Column(name = "lat")
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "marketShortName")
	public String getMarketShortName() {
		return marketShortName;
	}

	public void setMarketShortName(String marketShortName) {
		this.marketShortName = marketShortName;
	}
	@Column(name = "marketArea")
	public String getMarketArea() {
		return marketArea;
	}

	public void setMarketArea(String marketArea) {
		this.marketArea = marketArea;
	}
	@Column(name = "tradeAmount")
	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	@Column(name = "tradeMoney")
	public String getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(String tradeMoney) {
		this.tradeMoney = tradeMoney;
	}
	@Column(name = "marketImg")
	public String getMarketImg() {
		return marketImg;
	}

	public void setMarketImg(String marketImg) {
		this.marketImg = marketImg;
	}
	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Column(name = "businessAmount")
	public String getBusinessAmount() {
		return businessAmount;
	}

	public void setBusinessAmount(String businessAmount) {
		this.businessAmount = businessAmount;
	}
	@Column(name = "businessClassAmount")
	public String getBusinessClassAmount() {
		return businessClassAmount;
	}

	public void setBusinessClassAmount(String businessClassAmount) {
		this.businessClassAmount = businessClassAmount;
	}

}
