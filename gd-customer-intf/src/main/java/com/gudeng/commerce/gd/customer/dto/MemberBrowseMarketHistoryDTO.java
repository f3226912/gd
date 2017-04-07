package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.MemberBrowseMarketHistoryEntity;

public class MemberBrowseMarketHistoryDTO extends MemberBrowseMarketHistoryEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 市场名字
	 */
	private String marketName;
	
	private String browseTimeStr;

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getBrowseTimeStr() {
		return browseTimeStr;
	}

	public void setBrowseTimeStr(String browseTimeStr) {
		this.browseTimeStr = browseTimeStr;
	}
}
