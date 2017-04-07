package com.gudeng.commerce.gd.admin.dto;

import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.ProductPriceDto;

public class FriendsLinksParamsBean{
	
	private String linkCate;
	private String linkType;
	private String linkName;
	private String linkUrl;
	private String status;
	
	public String getLinkCate() {
		return linkCate;
	}
	public void setLinkCate(String linkCate) {
		this.linkCate = linkCate;
	}
	public String getLinkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
