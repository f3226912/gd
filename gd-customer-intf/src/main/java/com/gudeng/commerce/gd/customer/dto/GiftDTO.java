package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.CallstatiSticsEntity;
import com.gudeng.commerce.gd.customer.entity.GiftEntity;

public class GiftDTO extends GiftEntity{


	/**
	 * 
	 */
	private static final long serialVersionUID = -541560949303320102L;
	
	private String actName;

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}
	
	
}
