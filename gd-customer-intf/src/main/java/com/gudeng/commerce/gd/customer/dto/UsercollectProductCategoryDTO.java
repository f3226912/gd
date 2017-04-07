package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.UsercollectProductCategoryEntity;


public class UsercollectProductCategoryDTO extends UsercollectProductCategoryEntity{

	private static final long serialVersionUID = -7012175169532989599L;
	
	private String categoryIds;
	private String cateName;
	private Integer curLevel;
	
	private Long marketId;
	
	private String marketName;
	
	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Integer getCurLevel() {
		return curLevel;
	}

	public void setCurLevel(Integer curLevel) {
		this.curLevel = curLevel;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
}
