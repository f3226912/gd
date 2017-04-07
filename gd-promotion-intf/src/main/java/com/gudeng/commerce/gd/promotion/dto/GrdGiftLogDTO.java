package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GrdGiftLogEntity;

public class GrdGiftLogDTO extends GrdGiftLogEntity {

	private static final long serialVersionUID = 369517416660745594L;
	
	private Integer giftstoreId;
	
	private String giftstoreName;

	
	public Integer getGiftstoreId() {
		return giftstoreId;
	}

	public void setGiftstoreId(Integer giftstoreId) {
		this.giftstoreId = giftstoreId;
	}

	public String getGiftstoreName() {
		return giftstoreName;
	}

	public void setGiftstoreName(String giftstoreName) {
		this.giftstoreName = giftstoreName;
	}

	
}