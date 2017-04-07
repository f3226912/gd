package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftteamEntity;

public class GrdGdGiftteamDTO extends GrdGdGiftteamEntity {
	
	
	
	
	/**
	 * 团队名称
	 */
	private String teamName;
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	/**
	 * 市场名称
	 */
    private String marketName;
    /**
     * 礼品仓库名称
     */
    private String giftStoreName;
    
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getGiftStoreName() {
		return giftStoreName;
	}
	public void setGiftStoreName(String giftStoreName) {
		this.giftStoreName = giftStoreName;
	}
}