package com.gudeng.commerce.gd.customer.dto;


import com.gudeng.commerce.gd.customer.entity.ComplaintEntity;
/**
 * 投诉建议
 */
public class ComplaintEntityDTO extends ComplaintEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8921453700407302956L;

	private Integer userType;//角色类型
	
	private String createTimeStart;
	
	private String createTimeEnd;
	
	private String marketName;
	
	private String level;
	
	private String mobile;
	
	private String memberId;
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	 
	
	
}
