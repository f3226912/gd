package com.gudeng.commerce.gd.home.dto.district;

import java.util.List;

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
public class CityDTO extends BaseCity {

	private static final long serialVersionUID = -3247528559403225422L;

	private boolean success = true;
	private boolean isException = false;
	private boolean exception = false;
	private String successResultValue = null;
	private List<CityDTO> cities;

	private String area = null;
	private String city = null;
	private String cityId = null;
	private String jiageCities = null;
	private String proId = null;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean getIsException() {
		return isException;
	}

	public void setIsException(boolean isException) {
		this.isException = isException;
	}

	public boolean getException() {
		return exception;
	}

	public void setException(boolean exception) {
		this.exception = exception;
	}

	public String getSuccessResultValue() {
		return successResultValue;
	}

	public void setSuccessResultValue(String successResultValue) {
		this.successResultValue = successResultValue;
	}

	public List<CityDTO> getCities() {
		return cities;
	}

	public void setCities(List<CityDTO> cities) {
		this.cities = cities;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getJiageCities() {
		return jiageCities;
	}

	public void setJiageCities(String jiageCities) {
		this.jiageCities = jiageCities;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

}
