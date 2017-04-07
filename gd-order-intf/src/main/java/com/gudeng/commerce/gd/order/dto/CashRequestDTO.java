package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.Date;

import com.gudeng.commerce.gd.order.entity.CashRequestEntity;


public class CashRequestDTO extends CashRequestEntity implements Serializable {
	private String transPwd;
	public String getTransPwd() {
		return transPwd;
	}
	public void setTransPwd(String transPwd) {
		this.transPwd = transPwd;
	}
	public enum CASH_REQUEST_STATUS {
		WAITTING_WITHDRAW("0"), WITHDRAWED("1");
		
		CASH_REQUEST_STATUS(String value){
			this.value = value;
		}
		
		private final String value;
		
		public String getValue(){
			return value;
			}
		}
	private static final long serialVersionUID = 1L;
	
	/**
	 * 姓名
	 */
	private String realName;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 会员账号
	 */
	private String account;
	
	
	/**
	 * 每笔流水下对应的账号余额
	 */
	private Double balTotal;
	
	/**
	 * 单个账户对应的账户余额
	 */
	private Double accountTotal;
	
	/**
	 * 单个账户对应的冻结余额
	 */
	private Double accountBlock;
	
	/**
	 * 流水收入
	 */
	private Double income;
	
	/**
	 * 流水支出
	 */
	private Double expense;
	
	/**
	 * 流水号
	 */
	private String flowId;
	
	/**
	 * 时间
	 */
	private Date createFlowTime;
	
	
	/**
	 * 申请时间查询起始时间
	 */
	private String applyBeginTime;
	
	/**
	 * 申请时间查询结束时间
	 */
	private String applyEndTime;
	
	/**
	 * 结款时间查询起始时间
	 */
	private String payBeginTime;
	
	/**
	 * 结款时间查询结束时间
	 */
	private String payEndTime;
	
	/**
	 * 是否农行
	 */
	private String isABC;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getApplyBeginTime() {
		return applyBeginTime;
	}

	public void setApplyBeginTime(String applyBeginTime) {
		this.applyBeginTime = applyBeginTime;
	}

	public String getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(String applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public String getPayBeginTime() {
		return payBeginTime;
	}

	public void setPayBeginTime(String payBeginTime) {
		this.payBeginTime = payBeginTime;
	}

	public String getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(String payEndTime) {
		this.payEndTime = payEndTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setExpense(double expense) {
		this.expense = expense;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
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

	public Double getAccountTotal() {
		return accountTotal;
	}

	public void setAccountTotal(Double accountTotal) {
		this.accountTotal = accountTotal;
	}

	public Double getAccountBlock() {
		return accountBlock;
	}

	public void setAccountBlock(Double accountBlock) {
		this.accountBlock = accountBlock;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIsABC() {
		return isABC;
	}
	public void setIsABC(String isABC) {
		this.isABC = isABC;
	}
	
}
