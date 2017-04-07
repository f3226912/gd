package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.PageStatisMemberEntity;

public class PageStatisMemberDTO extends PageStatisMemberEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 959008338043255117L;
	
	private String shopsName;
	
	private String account;
	
	private String mobile;
	
	private String cateName;
	
	private String status;
	
	private Integer memberGrade;

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(Integer memberGrade) {
		this.memberGrade = memberGrade;
	}

}
