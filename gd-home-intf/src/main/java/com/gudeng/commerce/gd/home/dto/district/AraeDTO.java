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
public class AraeDTO extends BaseArea {

	private static final long serialVersionUID = 2984367031831660521L;
	private boolean success = true;
	private boolean isException = false;
	private boolean exception = false;
	private String successResultValue = null;
	private List<AraeDTO> areas;

	private String area = null;
	private String areaId = null;
	private String areaName = null;
	private String cityId = null;
	private String updateTime = null;

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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public List<AraeDTO> getAreas() {
		return areas;
	}

	public void setAreas(List<AraeDTO> areas) {
		this.areas = areas;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
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

}
