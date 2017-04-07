package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.NstOrderComplaintEntity;

public class NstOrderComplaintDTO extends NstOrderComplaintEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2177497826823951527L;
	
	private String createUserName;

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
}
