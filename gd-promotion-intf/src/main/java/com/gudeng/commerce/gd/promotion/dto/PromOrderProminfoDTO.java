package com.gudeng.commerce.gd.promotion.dto;

import javax.persistence.Entity;

import com.gudeng.commerce.gd.promotion.entity.PromOrderProminfoEntity;

@Entity(name = "prom_order_prominfo")
public class PromOrderProminfoDTO extends PromOrderProminfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8190334457985925551L;

	/**
	 * 活动id
	 */
	private Integer actId;
	
	/**
	 * 活动名称
	 */
	private String actName; 
	
	/**
	 * 活动类型
	 */
	private String actType;

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}
}
