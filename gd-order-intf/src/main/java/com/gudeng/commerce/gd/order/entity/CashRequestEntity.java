package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
/**
 * 提现申请实体类
 * @author xiaojun
 */
@Entity(name = "cash_request")
public class CashRequestEntity  implements java.io.Serializable{
	/**
	 * 申请ID
	 */
    private Integer cashReqId;
    
    /**
     * 提现金额
     */
    private Double cashAmount;
    
    /**
     * 提现人账号
     */
    private Integer reqUid;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 开户行名称
     */
    private String depositBankName;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 会员ID
     */
    private Integer memberId;

    /**
     * 申请时间
     */
    private Date reqTime;
    
    /**
     * 结款时间
     */
    private Date payingTime;
    
    /**
     * 流水号
     */
    private Long transNo;
    
    /**
     * 状态 0待提现 1已结款
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人员id
     */
    private String createUserId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 修改人员id
     */
    private String updateUserId;

    @Column(name = "cashReqId")
    public Integer getCashReqId(){

        return this.cashReqId;
    }
    public void setCashReqId(Integer cashReqId){

        this.cashReqId = cashReqId;
    }
    @Column(name = "cashAmount")
    public Double getCashAmount(){

        return this.cashAmount;
    }
    public void setCashAmount(Double cashAmount){

        this.cashAmount = cashAmount;
    }
    @Column(name = "reqUid")
    public Integer getReqUid(){

        return this.reqUid;
    }
    public void setReqUid(Integer reqUid){

        this.reqUid = reqUid;
    }
    @Column(name = "idCard")
    public String getIdCard(){

        return this.idCard;
    }
    public void setIdCard(String idCard){

        this.idCard = idCard;
    }
    @Column(name = "depositBankName")
    public String getDepositBankName(){

        return this.depositBankName;
    }
    public void setDepositBankName(String depositBankName){

        this.depositBankName = depositBankName;
    }
    @Column(name = "bankCardNo")
    public String getBankCardNo(){

        return this.bankCardNo;
    }
    public void setBankCardNo(String bankCardNo){

        this.bankCardNo = bankCardNo;
    }
    @Column(name = "memberId")
    public Integer getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Integer memberId){

        this.memberId = memberId;
    }
    @Column(name = "reqTime")
    public Date getReqTime(){

        return this.reqTime;
    }
    public void setReqTime(Date reqTime){

        this.reqTime = reqTime;
    }
    @Column(name = "payingTime")
    public Date getPayingTime(){

        return this.payingTime;
    }
    public void setPayingTime(Date payingTime){

        this.payingTime = payingTime;
    }
    @Column(name = "transNo")
    public Long getTransNo(){

        return this.transNo;
    }
    public void setTransNo(Long transNo){

        this.transNo = transNo;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
}

