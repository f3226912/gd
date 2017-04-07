package com.gudeng.commerce.gd.task.dto;

/**
 * 用户信息DTO
 * 用于app端交互
 * @author TerryZhang
 *
 */
public class MemberInfoAppDTO {

	private Long memberId;
	
	private String realName;
	
	private String mobile;
	
	private String account;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
