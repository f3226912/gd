package com.gudeng.commerce.gd.search.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * 产品solr搜索查询根据market统计个数
 * 
 * 包含 产品基础信息  ，ProductSolrDTO 
 * 
 * 和总共个数 count
 * 
 */
public class ProductFacetMarketResultDTO   implements java.io.Serializable{

	/**
	 * 一开始仅仅考虑了商品，现在商铺也需要用，此类搜索商品和商铺时，公用
	 * 
	 */
	private static final long serialVersionUID = -2886557928052104714L;
	
	public Integer count;//所包含商品个数
	public String marketId;//市场Id
	public String marketName;//市场名称
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	

}
