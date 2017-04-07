package com.gudeng.commerce.gd.home.dto;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class SearchPageDTO implements Serializable {

	private static final long serialVersionUID = 6907289523717591908L;

	private String marketId;
	private String cateId;
	private String isBig;
	private String searchModel;
	private String pageNow;

	private String searchType;
	private String priceOrder;

	public SearchPageDTO() {
	}

	public SearchPageDTO(ServletRequest request) {
		this.marketId = request.getParameter("marketId");
		this.cateId = request.getParameter("cateId");
		this.isBig = request.getParameter("isBig");
		this.searchModel = request.getParameter("searchModel");
		this.pageNow = request.getParameter("pageNow");
		this.searchType = request.getParameter("searchType");
		this.priceOrder = request.getParameter("priceOrder");
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getPriceOrder() {
		return priceOrder;
	}

	public void setPriceOrder(String priceOrder) {
		this.priceOrder = priceOrder;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getIsBig() {
		return isBig;
	}

	public void setIsBig(String isBig) {
		this.isBig = isBig;
	}

	public String getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}

	public String getPageNow() {
		return pageNow;
	}

	public void setPageNow(String pageNow) {
		this.pageNow = pageNow;
	}

}
