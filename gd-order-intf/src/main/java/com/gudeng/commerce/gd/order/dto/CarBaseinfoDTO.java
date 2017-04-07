package com.gudeng.commerce.gd.order.dto;

import com.gudeng.commerce.gd.order.entity.CarBaseinfoEntity;

public class CarBaseinfoDTO extends CarBaseinfoEntity {
	
	/*
	 * 对应过磅表ID
	 */
	private Long wcId;

	public Long getWcId() {
		return wcId;
	}

	public void setWcId(Long wcId) {
		this.wcId = wcId;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1000454805652891491L;

}
