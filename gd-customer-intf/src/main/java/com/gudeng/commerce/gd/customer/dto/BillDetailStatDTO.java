package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

public class BillDetailStatDTO implements Serializable{
	 
	private static final long serialVersionUID = -5820723168166284555L;
	
	/**
	 * 统计周期 
	 */
	private Integer period;
	
	/**
	 * 金额
	 */
	private Double sumAmount;

	/**
	 * 订单量
	 */
	private Integer sumOrder;

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Double getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}

	public Integer getSumOrder() {
		return sumOrder;
	}

	public void setSumOrder(Integer sumOrder) {
		this.sumOrder = sumOrder;
	}

	public BillDetailStatDTO(){
	}
	
	public BillDetailStatDTO(Integer period, Double sumAmount){
		this.period = period;
		this.sumAmount = sumAmount;
	}
	
	public BillDetailStatDTO(Integer period, Integer sumOrder){
		this.period = period;
		this.sumOrder = sumOrder;
	}
}
