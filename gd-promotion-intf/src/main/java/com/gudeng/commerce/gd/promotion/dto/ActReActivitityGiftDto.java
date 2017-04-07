package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.ActReActivitityGiftEntity;

public class ActReActivitityGiftDto extends ActReActivitityGiftEntity implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1549597408042981230L;

	private String giftName;
	
	/**
	 * 活动修改时礼品预算的相差大小
	 */
	private Integer diffCost;
	
	/**
	 * 礼品可用数量
	 */
	private Integer stockAvailable;

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Integer getDiffCost() {
		return diffCost;
	}

	public void setDiffCost(Integer diffCost) {
		this.diffCost = diffCost;
	}

	public Integer getStockAvailable() {
		return stockAvailable;
	}

	public void setStockAvailable(Integer stockAvailable) {
		this.stockAvailable = stockAvailable;
	}
	
}
