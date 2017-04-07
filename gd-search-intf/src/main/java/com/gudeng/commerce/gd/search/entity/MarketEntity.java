package com.gudeng.commerce.gd.search.entity;

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

}
