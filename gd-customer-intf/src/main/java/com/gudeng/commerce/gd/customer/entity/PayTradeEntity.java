package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

public class PayTradeEntity  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1713554278700625959L;

	private Integer id;

    private Integer version;

    private String orderNo;

    private String title;

    private String payType;

    private String payCenterNumber;

    private String thirdPayNumber;

    private String timeOut;

    private Date payTime;

    private Date closeTime;

    private String payStatus;

    private String appKey;

    private String notifyStatus;

    private String reParam;

    private String returnUrl;

    private String notifyUrl;

    private Double totalAmt;

    private Double payAmt;

    private Double receiptAmt;

    private Date orderTime;

    private String closeUserId;

    private String thirdPayerAccount;

    private String payerUserId;

    private String payerAccount;

    private String payerName;

    private String payeeUserId;

    private String payeeAccount;

    private String payeeName;

    private String thirdPayeeAccount;

    private String rate;

    private Double feeAmt;

    private String refundUserId;

    private Date refundTime;

    private String extendJson;

    private String requestIp;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;
    
    private String payerMobile;
    
    private String payeeMobile;
    
	private String posClientNo;
	
	private String bankCardNo;
	
	private String requestNo;//1为预付款，2为尾款

	private Integer payCount;//支付笔数

	private String logisticsUserId;//物流公司会员Id

    public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public Integer getPayCount() {
		return payCount;
	}
	public void setPayCount(Integer payCount) {
		this.payCount = payCount;
	}
	public String getLogisticsUserId() {
		return logisticsUserId;
	}
	public void setLogisticsUserId(String logisticsUserId) {
		this.logisticsUserId = logisticsUserId;
	}
	public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    public Integer getVersion(){

        return this.version;
    }
    public void setVersion(Integer version){

        this.version = version;
    }
    public String getOrderNo(){

        return this.orderNo;
    }
    public void setOrderNo(String orderNo){

        this.orderNo = orderNo;
    }
    public String getTitle(){

        return this.title;
    }
    public void setTitle(String title){

        this.title = title;
    }
    public String getPayType(){

        return this.payType;
    }
    public void setPayType(String payType){

        this.payType = payType;
    }
    public String getPayCenterNumber(){

        return this.payCenterNumber;
    }
    public void setPayCenterNumber(String payCenterNumber){

        this.payCenterNumber = payCenterNumber;
    }
    public String getThirdPayNumber(){

        return this.thirdPayNumber;
    }
    public void setThirdPayNumber(String thirdPayNumber){

        this.thirdPayNumber = thirdPayNumber;
    }
    public String getTimeOut(){

        return this.timeOut;
    }
    public void setTimeOut(String timeOut){

        this.timeOut = timeOut;
    }
    public Date getPayTime(){

        return this.payTime;
    }
    public void setPayTime(Date payTime){

        this.payTime = payTime;
    }
    public Date getCloseTime(){

        return this.closeTime;
    }
    public void setCloseTime(Date closeTime){

        this.closeTime = closeTime;
    }
    public String getPayStatus(){

        return this.payStatus;
    }
    public void setPayStatus(String payStatus){

        this.payStatus = payStatus;
    }
    public String getAppKey(){

        return this.appKey;
    }
    public void setAppKey(String appKey){

        this.appKey = appKey;
    }
    public String getNotifyStatus(){

        return this.notifyStatus;
    }
    public void setNotifyStatus(String notifyStatus){

        this.notifyStatus = notifyStatus;
    }
    public String getReParam(){

        return this.reParam;
    }
    public void setReParam(String reParam){

        this.reParam = reParam;
    }
    public String getReturnUrl(){

        return this.returnUrl;
    }
    public void setReturnUrl(String returnUrl){

        this.returnUrl = returnUrl;
    }
    public String getNotifyUrl(){

        return this.notifyUrl;
    }
    public void setNotifyUrl(String notifyUrl){

        this.notifyUrl = notifyUrl;
    }
    public Double getTotalAmt(){

        return this.totalAmt;
    }
    public void setTotalAmt(Double totalAmt){

        this.totalAmt = totalAmt;
    }
    public Double getPayAmt(){

        return this.payAmt;
    }
    public void setPayAmt(Double payAmt){

        this.payAmt = payAmt;
    }
    public Double getReceiptAmt(){

        return this.receiptAmt;
    }
    public void setReceiptAmt(Double receiptAmt){

        this.receiptAmt = receiptAmt;
    }
    public Date getOrderTime(){

        return this.orderTime;
    }
    public void setOrderTime(Date orderTime){

        this.orderTime = orderTime;
    }
    public String getCloseUserId(){

        return this.closeUserId;
    }
    public void setCloseUserId(String closeUserId){

        this.closeUserId = closeUserId;
    }
    public String getThirdPayerAccount(){

        return this.thirdPayerAccount;
    }
    public void setThirdPayerAccount(String thirdPayerAccount){

        this.thirdPayerAccount = thirdPayerAccount;
    }
    public String getPayerUserId(){

        return this.payerUserId;
    }
    public void setPayerUserId(String payerUserId){

        this.payerUserId = payerUserId;
    }
    public String getPayerAccount(){

        return this.payerAccount;
    }
    public void setPayerAccount(String payerAccount){

        this.payerAccount = payerAccount;
    }
    public String getPayerName(){

        return this.payerName;
    }
    public void setPayerName(String payerName){

        this.payerName = payerName;
    }
    public String getPayeeUserId(){

        return this.payeeUserId;
    }
    public void setPayeeUserId(String payeeUserId){

        this.payeeUserId = payeeUserId;
    }
    public String getPayeeAccount(){

        return this.payeeAccount;
    }
    public void setPayeeAccount(String payeeAccount){

        this.payeeAccount = payeeAccount;
    }
    public String getPayeeName(){

        return this.payeeName;
    }
    public void setPayeeName(String payeeName){

        this.payeeName = payeeName;
    }
    public String getThirdPayeeAccount(){

        return this.thirdPayeeAccount;
    }
    public void setThirdPayeeAccount(String thirdPayeeAccount){

        this.thirdPayeeAccount = thirdPayeeAccount;
    }
    public String getRate(){

        return this.rate;
    }
    public void setRate(String rate){

        this.rate = rate;
    }
    public Double getFeeAmt(){

        return this.feeAmt;
    }
    public void setFeeAmt(Double feeAmt){

        this.feeAmt = feeAmt;
    }
    public String getRefundUserId(){

        return this.refundUserId;
    }
    public void setRefundUserId(String refundUserId){

        this.refundUserId = refundUserId;
    }
    public Date getRefundTime(){

        return this.refundTime;
    }
    public void setRefundTime(Date refundTime){

        this.refundTime = refundTime;
    }
    public String getExtendJson(){

        return this.extendJson;
    }
    public void setExtendJson(String extendJson){

        this.extendJson = extendJson;
    }
    public String getRequestIp(){

        return this.requestIp;
    }
    public void setRequestIp(String requestIp){

        this.requestIp = requestIp;
    }
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
    
	public String getPayerMobile() {
		return payerMobile;
	}
	
	public void setPayerMobile(String payerMobile) {
		this.payerMobile = payerMobile;
	}
	
	public String getPayeeMobile() {
		return payeeMobile;
	}
	
	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}
	public String getPosClientNo() {
		return posClientNo;
	}
	public void setPosClientNo(String posClientNo) {
		this.posClientNo = posClientNo;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
    
    
}

