package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.annotation.ExcelConf;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftDataEntity;

public class GrdGdGiftDataDTO extends GrdGdGiftDataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1234261774421981008L;

	
	
	
	/**
	 * 最新单价 展示使用
	 */
	private String newPriceStr;
	
	/**
	 * 货值   展示使用
	 */
	private String priceStr;

	public String getNewPriceStr() {
		return newPriceStr;
	}

	public void setNewPriceStr(String newPriceStr) {
		this.newPriceStr = newPriceStr;
	}

	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}
	
	
}
