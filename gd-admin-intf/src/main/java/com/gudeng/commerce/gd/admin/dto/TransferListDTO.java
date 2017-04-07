package com.gudeng.commerce.gd.admin.dto;

import java.util.Date;

public class TransferListDTO {

	/**
	 * 收入
	 */
	private Double income;
	
	/**
	 * 支出
	 */
	private Double expense;
	
	/**
	 *流水创建时间
	 */
	private Date createFlowTime;

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getExpense() {
		return expense;
	}

	public void setExpense(Double expense) {
		this.expense = expense;
	}

	public Date getCreateFlowTime() {
		return createFlowTime;
	}

	public void setCreateFlowTime(Date createFlowTime) {
		this.createFlowTime = createFlowTime;
	}
}
