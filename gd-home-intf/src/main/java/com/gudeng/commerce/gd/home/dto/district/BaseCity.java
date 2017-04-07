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
public class BaseCity implements Serializable {
	private static final long serialVersionUID = -1632207780529801141L;

	private String name;
	private String id;
	private String cityPinyin;
	private String lastModifyTime = null;
	private String provinceId;
	private boolean hotCity = false;
	private String cityShortPY;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCityPinyin() {
		return cityPinyin;
	}

	public void setCityPinyin(String cityPinyin) {
		this.cityPinyin = cityPinyin;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public boolean isHotCity() {
		return hotCity;
	}

	public void setHotCity(boolean hotCity) {
		this.hotCity = hotCity;
	}

	public String getCityShortPY() {
		return cityShortPY;
	}

	public void setCityShortPY(String cityShortPY) {
		this.cityShortPY = cityShortPY;
	}

}
