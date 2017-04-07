package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.ActivityEntity;
import com.gudeng.commerce.gd.customer.entity.CallstatiSticsEntity;
import com.gudeng.commerce.gd.customer.entity.GiftEntity;

public class ActivityDTO extends ActivityEntity{


	/**
	 * 
	 */
	private static final long serialVersionUID = -541560949303320102L;
	
	private String strStartTime;
	
	private String strEndTime;

	public String getStrStartTime() {
		return strStartTime;
	}

	public void setStrStartTime(String strStartTime) {
		this.strStartTime = strStartTime;
	}

	public String getStrEndTime() {
		return strEndTime;
	}

	public void setStrEndTime(String strEndTime) {
		this.strEndTime = strEndTime;
	}
	
	
}
