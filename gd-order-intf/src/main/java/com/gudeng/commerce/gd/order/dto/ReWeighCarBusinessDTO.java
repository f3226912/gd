package com.gudeng.commerce.gd.order.dto;

import com.gudeng.commerce.gd.order.entity.ReWeighCarBusinessEntity;

public class ReWeighCarBusinessDTO extends ReWeighCarBusinessEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -345798924474116532L;

	private String businessIds;

	public String getBusinessIds() {
		return businessIds;
	}

	public void setBusinessIds(String businessIds) {
		this.businessIds = businessIds;
	}
	
}
