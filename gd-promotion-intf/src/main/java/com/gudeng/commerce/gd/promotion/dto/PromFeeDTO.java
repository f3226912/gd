package com.gudeng.commerce.gd.promotion.dto;

import java.util.Date;

import javax.persistence.Entity;

import com.gudeng.commerce.gd.promotion.entity.PromFeeEntity;

@Entity(name = "prom_fee")
public class PromFeeDTO extends PromFeeEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4783362735448241577L;
	
	private Date endTime;

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
