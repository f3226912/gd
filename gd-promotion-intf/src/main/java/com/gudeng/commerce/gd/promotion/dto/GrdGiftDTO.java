package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GrdGiftEntity;

public class GrdGiftDTO extends GrdGiftEntity {

	private static final long serialVersionUID = 4927024560892126487L;
	
	private String marketName;
	private String oldName;
	
	private Integer stockTotalOld;
	private Integer stockAvailableOld;
	private String noCount;//未发放数
	
	private String giftstoreName;

	public String getNoCount() {
		return noCount;
	}

	public void setNoCount(String noCount) {
		this.noCount = noCount;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public Integer getStockTotalOld() {
		return stockTotalOld;
	}

	public void setStockTotalOld(Integer stockTotalOld) {
		this.stockTotalOld = stockTotalOld;
	}

	public Integer getStockAvailableOld() {
		return stockAvailableOld;
	}

	public void setStockAvailableOld(Integer stockAvailableOld) {
		this.stockAvailableOld = stockAvailableOld;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getGiftstoreName() {
		return giftstoreName;
	}

	public void setGiftstoreName(String giftstoreName) {
		this.giftstoreName = giftstoreName;
	}
	
}