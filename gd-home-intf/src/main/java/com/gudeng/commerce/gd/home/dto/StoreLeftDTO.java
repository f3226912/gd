package com.gudeng.commerce.gd.home.dto;

import java.io.Serializable;
import java.util.List;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessProducttypeDTO;

public class StoreLeftDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 863706563665548828L;

	private BusinessBaseinfoDTO busiInfo;
	
	private List<BusinessProducttypeDTO> busiProducttypeList;
	
	private boolean isLogin;
	
	private Integer busiProductTotal;
	
	private boolean isFocusStore;
	
	/**
	 * 收藏的数量
	 */
	private Integer number;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public BusinessBaseinfoDTO getBusiInfo() {
		return busiInfo;
	}

	public void setBusiInfo(BusinessBaseinfoDTO busiInfo) {
		this.busiInfo = busiInfo;
	}

	public List<BusinessProducttypeDTO> getBusiProducttypeList() {
		return busiProducttypeList;
	}

	public void setBusiProducttypeList(
			List<BusinessProducttypeDTO> busiProducttypeList) {
		this.busiProducttypeList = busiProducttypeList;
	}

	public boolean getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public Integer getBusiProductTotal() {
		return busiProductTotal;
	}

	public void setBusiProductTotal(Integer busiProductTotal) {
		this.busiProductTotal = busiProductTotal;
	}

	public boolean getIsFocusStore() {
		return isFocusStore;
	}

	public void setIsFocusStore(boolean isFocusStore) {
		this.isFocusStore = isFocusStore;
	}
	
}
