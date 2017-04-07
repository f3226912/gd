package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.DeliveryAddress;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class DeliveryAddressDTO extends DeliveryAddress {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2603324649420999990L;
	private String destnation;
	private String memberAddressId;

	public String getDestnation() {
		destnation = getDistrict1() + getDistrict2() + getDistrict3();
		return destnation;
	}

	public void setDestnation(String destnation) {
		this.destnation = destnation;
	}

	public String getMemberAddressId() {
		return memberAddressId;
	}

	public void setMemberAddressId(String memberAddressId) {
		this.memberAddressId = memberAddressId;
	}

}