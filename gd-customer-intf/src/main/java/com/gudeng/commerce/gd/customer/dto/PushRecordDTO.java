package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;

public class PushRecordDTO extends PushRecordEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5953896510677538678L;
	
	private String account;
	
	private String realName;
	
	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	
	


	
	
	
	
	

}
