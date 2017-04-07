package com.gudeng.commerce.gd.m.dto;

import java.io.Serializable;

/**
 * 物流司机信息DTO
 * @author TerryZhang
 */
public class NstTransferDriverDetailDTO  implements Serializable{

	private static final long serialVersionUID = 5987330847862895434L;

	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 车主会员id
	 */
	private String driverMemberId;
	
	/**
	 * 头像地址
	 */
	private String photoUrl;
	
	/**
	 * 联系电话
	 */
	private String mobile;
	
	/**
	 * 是否认证 0否 1是
	 */
	private Integer isIdentify;
	
	/**
	 * 车牌号
	 */
	private String carNo;
	
	/**
	 * 车类型
	 */
	private String carTypeName;
	
	/**
	 * 车长
	 */
	private String carLength;
	
	/**
	 * 车载重
	 */
	private String carLoad;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsIdentify() {
		return isIdentify;
	}

	public void setIsIdentify(Integer isIdentify) {
		this.isIdentify = isIdentify;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getCarLength() {
		return carLength;
	}

	public void setCarLength(String carLength) {
		this.carLength = carLength;
	}

	public String getCarLoad() {
		return carLoad;
	}

	public void setCarLoad(String carLoad) {
		this.carLoad = carLoad;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}

	public String getDriverMemberId() {
		return driverMemberId;
	}

	public void setDriverMemberId(String driverMemberId) {
		this.driverMemberId = driverMemberId;
	}
}
