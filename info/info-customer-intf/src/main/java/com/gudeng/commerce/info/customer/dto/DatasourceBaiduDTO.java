package com.gudeng.commerce.info.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.info.customer.entity.DatasourceBaiduEntity;

public class DatasourceBaiduDTO extends DatasourceBaiduEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 最后更新时间字符串
	 */
	private String lastUpdateTimeStr;
	
	private String avstopStr;

	public String getLastUpdateTimeStr() {
		return lastUpdateTimeStr;
	}

	public void setLastUpdateTimeStr(String lastUpdateTimeStr) {
		this.lastUpdateTimeStr = lastUpdateTimeStr;
	}

	public String getAvstopStr() {
		return avstopStr;
	}

	public void setAvstopStr(String avstopStr) {
		this.avstopStr = avstopStr;
	}

}
