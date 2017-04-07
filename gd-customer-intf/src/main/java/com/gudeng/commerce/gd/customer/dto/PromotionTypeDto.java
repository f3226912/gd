package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.PromotionTypeEntity;

public class PromotionTypeDto extends PromotionTypeEntity {

	private static final long serialVersionUID = 582617248758605488L;
	
	private String updateTimeString;

	public String getUpdateTimeString() {
		return updateTimeString;
	}

	public void setUpdateTimeString(String updateTimeString) {
		this.updateTimeString = updateTimeString;
	}
	
}
