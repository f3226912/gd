package com.gudeng.commerce.gd.notify.dto;

import java.io.Serializable;
import java.util.Date;

public class PosPayNotifyDto implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Integer id;

    private String payCenterNumber;

    private String businessNo;

    private String transType;

    private String orderNo;

    private Double orderAmt;

    private Double payAmt;

    private Double rateAmt;

    private String bankCardNo;

    private String posClientNo;

    private Date transDate;

    private String transNo;

    private String payChannelCode;

    private String status;
    
    private String gdBankCardNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPayCenterNumber() {
		return payCenterNumber;
	}

	public void setPayCenterNumber(String payCenterNumber) {
		this.payCenterNumber = payCenterNumber;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(Double orderAmt) {
		this.orderAmt = orderAmt;
	}

	public Double getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(Double payAmt) {
		this.payAmt = payAmt;
	}

	public Double getRateAmt() {
		return rateAmt;
	}

	public void setRateAmt(Double rateAmt) {
		this.rateAmt = rateAmt;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getPosClientNo() {
		return posClientNo;
	}

	public void setPosClientNo(String posClientNo) {
		this.posClientNo = posClientNo;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getPayChannelCode() {
		return payChannelCode;
	}

	public void setPayChannelCode(String payChannelCode) {
		this.payChannelCode = payChannelCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGdBankCardNo() {
		return gdBankCardNo;
	}

	public void setGdBankCardNo(String gdBankCardNo) {
		this.gdBankCardNo = gdBankCardNo;
	}

    
}
