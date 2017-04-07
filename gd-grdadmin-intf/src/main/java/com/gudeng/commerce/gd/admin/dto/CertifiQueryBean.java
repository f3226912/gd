package com.gudeng.commerce.gd.admin.dto;

public class CertifiQueryBean {
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	String startDate;
	String endDate;
	String status;	
	String account;
	String marketId;
	
}
