package com.gudeng.commerce.gd.order.dto;

import com.gudeng.commerce.gd.order.entity.AccInfoEntity;


public class AccInfoDTO  extends AccInfoEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String verifyCode;
	private String account;
	private Integer hasPwd;
	private Integer  subShow;
	private String  hasBankCards;
	

	public enum ACC_STATUS {
		VALIDATE("1"), INVALIDATE("0");
		
		ACC_STATUS(String value){
			this.value = value;
		}
		
		private final String value;
		
		public String getValue(){
			return value;
			}
	}
	
	public enum HAS_BANK_CARDS {
		HAS("1"), NOT_HAS("0");
		
		HAS_BANK_CARDS(String value){
			this.value = value;
		}
		
		private final String value;
		
		public String getValue(){
			return value;
			}
	}
	public Integer getSubShow() {
		return subShow;
	}

	public void setSubShow(Integer subShow) {
		this.subShow = subShow;
	}

	public Integer getHasPwd() {
		return hasPwd;
	}

	public void setHasPwd(Integer hasPwd) {
		this.hasPwd = hasPwd;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getHasBankCards() {
		return hasBankCards;
	}

	public void setHasBankCards(String hasBankCards) {
		this.hasBankCards = hasBankCards;
	}
	
	
	
	
	
	
	

}
