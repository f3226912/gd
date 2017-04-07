package com.gudeng.commerce.gd.order.dto;

import com.gudeng.commerce.gd.order.entity.ProductDeliveryDetailEntity;

public class ProductDeliveryDetailDTO extends ProductDeliveryDetailEntity {

	private static final long serialVersionUID = 1466802864662013951L;
	
	private String unitName;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}
