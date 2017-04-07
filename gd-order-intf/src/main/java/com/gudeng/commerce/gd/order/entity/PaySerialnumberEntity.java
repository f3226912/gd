package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "pay_serialnumber")
public class PaySerialnumberEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6711583413723177142L;

	private Integer payId;

    private Long orderNo;

    private String statementId;

    private Date payTime;

    private String payType;

    private String payStatus;

    private String paymentAcc;

    private String recipientAcc;

    private Double tradeAmount;

    private String remark;

    private String payImage;

    private Date createTime;

    private String createuserid;

    private Date updatetime;

    private String updateuserid;
    
    private Integer memberId;
    
    private String payBank;
    
    /**
     * 上传图片时间
     */
    private Date upImageTime;
    
    private String posNumber; // POS终端号
    
    private String thirdStatementId; //第三方支付流水号 
    
    private String paySubType; //支付子方式
    
    private String payAreaId;//支付地区

    private String serialType; //'1预付款流水，2尾款流水
    
    @Column(name = "serialType")
    public String getSerialType() {
		return serialType;
	}
	public void setSerialType(String serialType) {
		this.serialType = serialType;
	}
	@Id
    @Column(name = "payId")
    public Integer getPayId(){

        return this.payId;
    }
    public void setPayId(Integer payId){

        this.payId = payId;
    }
    @Column(name = "orderNo")
    public Long getOrderNo(){

        return this.orderNo;
    }
    public void setOrderNo(Long orderNo){

        this.orderNo = orderNo;
    }
    @Column(name = "statementId")
    public String getStatementId(){

        return this.statementId;
    }
    public void setStatementId(String statementId){

        this.statementId = statementId;
    }
    @Column(name = "payTime")
    public Date getPayTime(){

        return this.payTime;
    }
    public void setPayTime(Date payTime){

        this.payTime = payTime;
    }
    @Column(name = "payType")
    public String getPayType(){

        return this.payType;
    }
    public void setPayType(String payType){

        this.payType = payType;
    }
    @Column(name = "payStatus")
    public String getPayStatus(){

        return this.payStatus;
    }
    public void setPayStatus(String payStatus){

        this.payStatus = payStatus;
    }
    @Column(name = "paymentAcc")
    public String getPaymentAcc(){

        return this.paymentAcc;
    }
    public void setPaymentAcc(String paymentAcc){

        this.paymentAcc = paymentAcc;
    }
    @Column(name = "recipientAcc")
    public String getRecipientAcc(){

        return this.recipientAcc;
    }
    public void setRecipientAcc(String recipientAcc){

        this.recipientAcc = recipientAcc;
    }
    @Column(name = "tradeAmount")
    public Double getTradeAmount(){

        return this.tradeAmount;
    }
    public void setTradeAmount(Double tradeAmount){

        this.tradeAmount = tradeAmount;
    }
    @Column(name = "remark")
    public String getRemark(){

        return this.remark;
    }
    public void setRemark(String remark){

        this.remark = remark;
    }
    @Column(name = "payImage")
    public String getPayImage(){

        return this.payImage;
    }
    public void setPayImage(String payImage){

        this.payImage = payImage;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "createuserid")
    public String getCreateuserid(){

        return this.createuserid;
    }
    public void setCreateuserid(String createuserid){

        this.createuserid = createuserid;
    }
    @Column(name = "updatetime")
    public Date getUpdatetime(){

        return this.updatetime;
    }
    public void setUpdatetime(Date updatetime){

        this.updatetime = updatetime;
    }
    @Column(name = "updateuserid")
    public String getUpdateuserid(){

        return this.updateuserid;
    }
    public void setUpdateuserid(String updateuserid){

        this.updateuserid = updateuserid;
    }
	
    
    @Column(name = "memberid")
    public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	@Column(name = "upImageTime")
	public Date getUpImageTime() {
		return upImageTime;
	}
	public void setUpImageTime(Date upImageTime) {
		this.upImageTime = upImageTime;
	}
	
	@Column(name = "posNumber")
	public String getPosNumber() {
		return posNumber;
	}
	public void setPosNumber(String posNumber) {
		this.posNumber = posNumber;
	}
	
	@Column(name = "thirdStatementId")
	public String getThirdStatementId() {
		return thirdStatementId;
	}
	public void setThirdStatementId(String thirdStatementId) {
		this.thirdStatementId = thirdStatementId;
	}
	public String getPayBank() {
		return payBank;
	}
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	@Column(name = "paySubType")
	public String getPaySubType() {
		return paySubType;
	}
	public void setPaySubType(String paySubType) {
		this.paySubType = paySubType;
	}
	@Column(name = "payAreaId")
	public String getPayAreaId() {
		return payAreaId;
	}
	public void setPayAreaId(String payAreaId) {
		this.payAreaId = payAreaId;
	}

    
}
