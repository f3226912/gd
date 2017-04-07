package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.entity.PushNoticeEntity;

public class PushNoticeDTO extends PushNoticeEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 创建时间(字符串)
	 */
	private String createTimeStr;
	
	/**
	 * 修改时间(字符串)
	 */
	private String updateTimeStr;
	
	private String isread;
	
	private String userID;
	
	private Integer startRow;
	
	private Integer endRow;
	
	private String isdel;
	
	

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}


}
