package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.entity.FarmersMarketEntity;

public class FarmersMarketDTO extends FarmersMarketEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 开始时间(字符串)
	 */
	private String startTimeStr;
	
	/**
	 * 结束时间(字符串)
	 */
	private String endTimeStr;
	
	/**
	 * 创建时间(字符串)
	 */
	private String createTimeStr;
	
	/**
	 * 修改时间(字符串)
	 */
	private String updateTimeStr;
	

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

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

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
