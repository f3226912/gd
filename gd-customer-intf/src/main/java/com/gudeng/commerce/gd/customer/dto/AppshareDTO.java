package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.AppshareEntity;

public class AppshareDTO extends AppshareEntity{
	/**
     * 
     */
    private static final long serialVersionUID = -3699012932821853302L;
    
    private String visitorIp;
    
    private String startDate;
    
    private String endDate;
    
    private String marketName;
    
    private String updateUserName;

	public String getVisitorIp() {
		return visitorIp;
	}

	public void setVisitorIp(String visitorIp) {
		this.visitorIp = visitorIp;
	}

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

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

    
}
