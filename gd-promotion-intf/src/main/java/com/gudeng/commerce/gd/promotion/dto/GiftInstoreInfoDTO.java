package com.gudeng.commerce.gd.promotion.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GiftInstoreInfoDTO implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String giftNO;
	
	private String name;
	
	private String unit;
	
	private String purchaseNO;
	
	private String createUserName;
	
	private String store;
	
	private Integer inCount=0;
	
	private String createTime;
	
	private String inStorageId;

	public String getGiftNO() {
		return giftNO;
	}

	public void setGiftNO(String giftNO) {
		this.giftNO = giftNO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPurchaseNO() {
		return purchaseNO;
	}

	public void setPurchaseNO(String purchaseNO) {
		this.purchaseNO = purchaseNO;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public Integer getInCount() {
		return inCount;
	}

	public void setInCount(Integer inCount) {
		this.inCount = inCount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime =createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getInStorageId() {
		return inStorageId;
	}

	public void setInStorageId(String inStorageId) {
		this.inStorageId = inStorageId;
	}
	
	
	
	

}
