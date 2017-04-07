package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.ActGiftBaseinfoEntity;

public class ActGiftBaseinfoDTO extends ActGiftBaseinfoEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String createUserName;
	
	private Integer countApply;

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Integer getCountApply() {
		return countApply;
	}

	public void setCountApply(Integer countApply) {
		this.countApply = countApply;
	}
	

}
