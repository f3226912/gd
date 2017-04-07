package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.AccBankCardInfoEntity;

public class AccBankCardInfoDTO extends AccBankCardInfoEntity{

 
	private static final long serialVersionUID = 3121302663023088665L;
	
	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 城市名
	 */
	private String cityName;
	/**
	 * 区域名称
	 */
	private String areaName;
	
	/**
	 * 卡类型
	 */
	private String cardType;
	
/*	private String  regtype ;
	
	public String getRegtype() {
		return regtype;
	}

	public void setRegtype(String regtype) {
		this.regtype = regtype;
	}*/

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * 会员账号
	 */
	private String account;
	
	/**
	 * 用户手机号
	 */
	private String userMobile;
	
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public enum BANK_CARD_STATUS {
		VALIDATE("1"), INVALIDATE("0");

		BANK_CARD_STATUS(String value) {
			this.value = value;
		}

		private final String value;

		public String getValue() {
			return value;
		}
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
}
