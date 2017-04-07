package com.gudeng.commerce.gd.admin.dto;

/**
 * 供应商电话记录查询bean
 * @author Ailen
 *
 */
public class SuppTelStatQueryBean {
	String startDate;
	String endDate;
	String e_Mobile;	
	String shopName;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getE_Mobile() {
		return e_Mobile;
	}
	public void setE_Mobile(String e_Mobile) {
		this.e_Mobile = e_Mobile;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
