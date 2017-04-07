package com.gudeng.commerce.gd.m.dto;

import java.io.Serializable;

/**
 * 物流公司信息DTO
 * @author TerryZhang
 */
public class NstTransferCompanyDetailDTO implements Serializable{

	private static final long serialVersionUID = -6395871020150466173L;

	/**
	 * 物流公司名字
	 */
	private String name;
	
	/**
	 * 物流公司头像
	 */
	private String photoUrl;;
	
	/**
	 * 联系电话
	 */
	private String mobile;
	
	/**
	 * 是否认证 0否 1是
	 */
	private Integer isIdentify;

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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}
