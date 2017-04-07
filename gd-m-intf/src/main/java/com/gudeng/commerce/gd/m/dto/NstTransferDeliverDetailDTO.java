package com.gudeng.commerce.gd.m.dto;

import java.io.Serializable;

/**
 * 物流详细信息DTO
 * @author TerryZhang
 */
public class NstTransferDeliverDetailDTO implements Serializable{

	private static final long serialVersionUID = -4349690812424434731L;

	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 时间
	 */
	private String dateTime;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
