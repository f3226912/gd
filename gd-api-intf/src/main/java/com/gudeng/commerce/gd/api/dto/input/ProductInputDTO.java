package com.gudeng.commerce.gd.api.dto.input;

import java.io.Serializable;

public class ProductInputDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9130810320115367828L;
	private String memberId;
	private String categoryId;
	private String district1;
	private String district2;
	private String district3;
	private String area1;
	private String area2;
	private String area3;
	private String manageType;
	private String queryMode;
	private String priceSort;
	private String type;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPriceSort() {
		return priceSort;
	}
	public void setPriceSort(String priceSort) {
		this.priceSort = priceSort;
	}
	public String getQueryMode() {
		return queryMode;
	}
	public void setQueryMode(String queryMode) {
		this.queryMode = queryMode;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getDistrict1() {
		return district1;
	}
	public void setDistrict1(String district1) {
		this.district1 = district1;
	}
	public String getDistrict2() {
		return district2;
	}
	public void setDistrict2(String district2) {
		this.district2 = district2;
	}
	public String getDistrict3() {
		return district3;
	}
	public void setDistrict3(String district3) {
		this.district3 = district3;
	}
	public String getArea1() {
		return area1;
	}
	public void setArea1(String area1) {
		this.area1 = area1;
	}
	public String getArea2() {
		return area2;
	}
	public void setArea2(String area2) {
		this.area2 = area2;
	}
	public String getArea3() {
		return area3;
	}
	public void setArea3(String area3) {
		this.area3 = area3;
	}
	public String getManageType() {
		return manageType;
	}
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	
}
