package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.IntegralEntity;

public class IntegralDTO extends IntegralEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String memberAccount;//会员账号（推荐人）
	
	private String userType;//会员类型：1个人；2企业
	
	private String companyName;
	
	private String linkMan;//联系人
	
	private String memberedAccount;//被推荐人账号
	
	private String activityName;
	
	private String giftName;//兑换奖品
	
	private String createUserAccount;

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getMemberedAccount() {
		return memberedAccount;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public void setMemberedAccount(String memberedAccount) {
		this.memberedAccount = memberedAccount;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getCreateUserAccount() {
		return createUserAccount;
	}

	public void setCreateUserAccount(String createUserAccount) {
		this.createUserAccount = createUserAccount;
	}

}
