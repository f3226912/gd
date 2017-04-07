package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "farmersmarket")
public class FarmersMarketEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 市场名称
	 */
	private String name;

	/**
	 * 省份ID
	 */
	private  Long provinceId;

	/**
	 * 城市ID
	 */
	private  Long cityId;

	/**
	 * 区县ID
	 */
	private Long areaId;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 说明
	 */
	private String description;

	/**
	 * 创建人员ID
	 */
	private String createUserId;
	
	/**
	 * 创建人员name
	 */
	private String createUserName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人员ID
	 */
	private String updateUserId;
	
	/**
	 * 修改人员ID
	 */
	private String updateUserName;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 经度
	 */
	private Long lon;

	/**
	 * 纬度
	 */
	private Long lat;

	/**
	 * 详细地址
	 */
	private String address;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Column(name = "createUserName")
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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

	@Column(name = "updateUserName")
	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "lon")
	public Long getLon() {
		return lon;
	}

	public void setLon(Long lon) {
		this.lon = lon;
	}

	@Column(name = "lat")
	public Long getLat() {
		return lat;
	}

	public void setLat(Long lat) {
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
