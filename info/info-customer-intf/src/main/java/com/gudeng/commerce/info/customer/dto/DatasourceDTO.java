package com.gudeng.commerce.info.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.info.customer.entity.DatasourceEntity;

public class DatasourceDTO extends DatasourceEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 最后更新时间字符串
	 */
	private String lastUpdateTimeStr;

	public String getLastUpdateTimeStr() {
		return lastUpdateTimeStr;
	}

	public void setLastUpdateTimeStr(String lastUpdateTimeStr) {
		this.lastUpdateTimeStr = lastUpdateTimeStr;
	}

}
