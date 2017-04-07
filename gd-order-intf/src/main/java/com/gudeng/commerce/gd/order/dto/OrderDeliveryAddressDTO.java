package com.gudeng.commerce.gd.order.dto;

import com.gudeng.commerce.gd.order.entity.OrderDeliveryAddress;

public class OrderDeliveryAddressDTO extends OrderDeliveryAddress {
	
	private static final long serialVersionUID = 8408373472715004009L;
	
	private String destnation;

	public String getDestnation() {
		destnation = getDistrict1() + getDistrict2() + getDistrict3() + " " + getDetail();
		return destnation;
	}

	public void setDestnation(String destnation) {
		this.destnation = destnation;
	}

}