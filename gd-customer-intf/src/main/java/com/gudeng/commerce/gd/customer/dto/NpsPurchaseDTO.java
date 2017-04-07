package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.NpsPurchaseEntity;

public class NpsPurchaseDTO extends NpsPurchaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7032829632620297097L;
	private double newPrice;
	private double lowPrice;
	private long surplusDays;
	private String userAcc;
	private double offerPrice;
	private String endStr;
	private String releaseTimeStr;
	private String shopsName;
	private String img;
	private String endTimeStr;
	private String marketName;
	private String realName;
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getEndStr() {
		return endStr;
	}

	public void setEndStr(String endStr) {
		this.endStr = endStr;
	}

	public double getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(double offerPrice) {
		this.offerPrice = offerPrice;
	}

	public String getUserAcc() {
		return userAcc;
	}

	public void setUserAcc(String userAcc) {
		this.userAcc = userAcc;
	}

	public long getSurplusDays() {
		return surplusDays;
	}

	public void setSurplusDays(long surplusDays) {
		this.surplusDays = surplusDays;
	}

	public double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
	}

	public double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getReleaseTimeStr() {
		return releaseTimeStr;
	}

	public void setReleaseTimeStr(String releaseTimeStr) {
		this.releaseTimeStr = releaseTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	
}
