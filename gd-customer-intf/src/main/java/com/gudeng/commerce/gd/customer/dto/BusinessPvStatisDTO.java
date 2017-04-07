package com.gudeng.commerce.gd.customer.dto;

import java.util.Date;

import com.gudeng.commerce.gd.customer.entity.BusinessPvStatisEntity;

public class BusinessPvStatisDTO extends BusinessPvStatisEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 896564859257235956L;

	//添加的浏览量
	private Integer addPv;
	
	//会员类型
	private Integer memberGrade;
	
	private Long memberId; //对应商铺 用户ID
	
	private String mobile; //用户手机号
	
	private String account; //用户名
	
	private String realName; //真实姓名
	
	private String shopPv;
	
	private String shopsName;
	
	private String marketName;
	
	private String tradeMoney;
	
	private Date tradeDay;
	
	private Integer status;

	public Integer getAddPv() {
		return addPv;
	}

	public void setAddPv(Integer addPv) {
		this.addPv = addPv;
	}

	public Integer getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(Integer memberGrade) {
		this.memberGrade = memberGrade;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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

	public String getShopPv() {
		return shopPv;
	}

	public void setShopPv(String shopPv) {
		this.shopPv = shopPv;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(String tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public Date getTradeDay() {
		return tradeDay;
	}

	public void setTradeDay(Date tradeDay) {
		this.tradeDay = tradeDay;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
