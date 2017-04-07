package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.MyAddress;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class MyAddressDTO extends MyAddress {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6978974688688631477L;
	private String district1Name;
	private String district2Name;
	private String district3Name;

	public String getDistrict1Name() {
		return district1Name;
	}

	public void setDistrict1Name(String district1Name) {
		this.district1Name = district1Name;
	}

	public String getDistrict2Name() {
		return district2Name;
	}

	public void setDistrict2Name(String district2Name) {
		this.district2Name = district2Name;
	}

	public String getDistrict3Name() {
		return district3Name;
	}

	public void setDistrict3Name(String district3Name) {
		this.district3Name = district3Name;
	}

}