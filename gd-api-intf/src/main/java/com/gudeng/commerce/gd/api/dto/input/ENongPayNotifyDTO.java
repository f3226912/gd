package com.gudeng.commerce.gd.api.dto.input;

/**
 * E农平台支付结果通知对象
 * @author humy
 */
public class ENongPayNotifyDTO extends ENongRequestParamsBaseDTO{
	/** 谷登订单号 */
	private String orderno;
	
	/** 交易类型（0—订单支付 1—刷卡消费）*/
	private String transype;
	
	/** 订单金额*/
	private String orderfee;
	
	/** 手续费（1—15，单位分）*/
	private String ratefee;
	
	/** 支付金额（1—15，单位分）*/
	private String payfee;
	
	/** 支付响应码 */
	private String payresultcode;
	
	/** 支付响应码说明 */
	private String payresultmsg;
	
	/** 付款卡号 */
	private String paycardno;
	
	/** 交易日期（yyyyMMdd） */
	private String transdate;
	
	/** 交易时间（HHmmss） */
	private String transtime;
	
	/** 交易流水号（1—22） */
	private String transseqno;

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getTransype() {
		return transype;
	}

	public void setTransype(String transype) {
		this.transype = transype;
	}

	public String getOrderfee() {
		return orderfee;
	}

	public void setOrderfee(String orderfee) {
		this.orderfee = orderfee;
	}

	public String getRatefee() {
		return ratefee;
	}

	public void setRatefee(String ratefee) {
		this.ratefee = ratefee;
	}

	public String getPayfee() {
		return payfee;
	}

	public void setPayfee(String payfee) {
		this.payfee = payfee;
	}

	public String getPayresultcode() {
		return payresultcode;
	}

	public void setPayresultcode(String payresultcode) {
		this.payresultcode = payresultcode;
	}

	public String getPayresultmsg() {
		return payresultmsg;
	}

	public void setPayresultmsg(String payresultmsg) {
		this.payresultmsg = payresultmsg;
	}

	public String getPaycardno() {
		return paycardno;
	}

	public void setPaycardno(String paycardno) {
		this.paycardno = paycardno;
	}

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public String getTranstime() {
		return transtime;
	}

	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}

	public String getTransseqno() {
		return transseqno;
	}

	public void setTransseqno(String transseqno) {
		this.transseqno = transseqno;
	}
}
