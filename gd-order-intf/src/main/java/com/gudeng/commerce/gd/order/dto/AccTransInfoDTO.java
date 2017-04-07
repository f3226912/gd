package com.gudeng.commerce.gd.order.dto;

import java.util.Date;

import com.gudeng.commerce.gd.order.entity.AccTransInfoEntity;


public class AccTransInfoDTO extends AccTransInfoEntity {

	
	private int count;
 
	
	private static final long serialVersionUID = -2628248459729189692L;

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
	
	/**
	 * 账号余额
	 */
	private Double balTotal;
	
	public enum PE_TYPE {
		IN("1"), OUT("2");
		
		PE_TYPE(String value){
			this.value = value;
		}
		
		private final String value;
		
		public String getValue(){
			return value;
			}
		}
	
	
//	交易类型 1余额抵扣 2用户补贴 3提现,4余额返还
	public enum ACC_TRANS_TRADE_TYPE {
		YEDK("1"), YHBT("2"), TX("3"),YEFH("4");
		
		ACC_TRANS_TRADE_TYPE(String value){
			this.value = value;
		}
		
		private final String value;
		
		public String getValue(){
			return value;
			}
		}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


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


	public Double getBalTotal() {
		return balTotal;
	}


	public void setBalTotal(Double balTotal) {
		this.balTotal = balTotal;
	}

	
}
