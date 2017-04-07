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
public class ProvinceDTO extends BaseProvince {

	private static final long serialVersionUID = -3247528559403225422L;

	private boolean success = true;
	private boolean isException = false;
	private boolean exception = false;
	private String province = null;
	private String provinceId = null;
	private String successResultValue = null;
	private List<ProvinceDTO> provinces;

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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getSuccessResultValue() {
		return successResultValue;
	}

	public void setSuccessResultValue(String successResultValue) {
		this.successResultValue = successResultValue;
	}

	public List<ProvinceDTO> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<ProvinceDTO> provinces) {
		this.provinces = provinces;
	}
}
