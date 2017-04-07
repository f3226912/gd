package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.entity.MemberChangeLogEntity;


public class MemberChangeLogDTO extends MemberChangeLogEntity implements Serializable {

	private static final long serialVersionUID = 6252815082761790221L;

	private String createUserName;

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}
