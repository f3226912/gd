package com.gudeng.commerce.gd.home.dto.district;

import java.io.Serializable;

/**
 * @Description 页面省市区县所需json格式
 * @Project gd-home-intf
 * @ClassName DistrictProvince.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年11月18日 下午4:39:34
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public class BaseArea implements Serializable {

	private static final long serialVersionUID = -3728562674194063007L;
	private String id;
	private String cityId;
	private String cityName;
	private String areaName;
	private String updateTime = null;
	private String provinceId;
	private String pinYin;
	private String pinYinChar;
	private int isShowWithCity = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getPinYinChar() {
		return pinYinChar;
	}

	public void setPinYinChar(String pinYinChar) {
		this.pinYinChar = pinYinChar;
	}

	public Integer getIsShowWithCity() {
		return isShowWithCity;
	}

	public void setIsShowWithCity(Integer isShowWithCity) {
		this.isShowWithCity = isShowWithCity;
	}

}
