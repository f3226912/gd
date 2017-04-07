package com.gudeng.commerce.gd.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "acc_info")
public class AccInfoEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5833625847566461277L;

//	accIdint(11) NOT NULL账户ID
//	memberIdint(11) NULL会员ID
//	realNamevarchar(32) NULL真实姓名
//	idCardvarchar(32) NULL身份证号码
//	transPwdvarchar(64) NULL交易密码
//	balTotaldecimal(10,2) NULL账户余额
//	balAvailabledecimal(10,2) NULL可用余额
//	balBlockdecimal(10,2) NULL冻结余额
//	subAmount Stay decimal(10,2) NULL待补贴金额
//	subAmountPaid decimal(10,2) NULL已补贴金额
//	accStatuschar(2) NULL账户状态 1有效 0无效
//	transPwdQue varchar(64) NULL交易密码问题
//	transPwdAnswer varchar(64) NULL交易密码答案
//	createTime datetime NOT NULL创建时间
//	createuserid varchar(32) NULL创建人员id
//	updatetime datetime NOT NULL更新时间
//	updateuserid varchar(32) NULL修改人员id
	
	
	private Integer  accId;
	private Integer  memberId;
	private String realName;
	private String idCard;
	private String transPwd;
	private Double balTotal=0.0;
	private Double balAvailable=0.0;
	private Double balblock=0.0;
	private Double subAmountStay;
	private Double subAmountPaid;
	private String transPwdQue;
	private String transPwdAnswer;
	private Date createTime;
	private String createuserId;
	private Date updateTime;
	private String updateuserId;
	private String accStatus;
	
    @Column(name = "accStatus")
	public String getAccStatus() {
		return accStatus;
	}
	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}
    @Id
    @Column(name = "accId")
	public Integer getAccId() {
		return accId;
	}
	public void setAccId(Integer accId) {
		this.accId = accId;
	}
    @Column(name = "memberId")
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
    @Column(name = "realName")
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
    @Column(name = "idCard")
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
    @Column(name = "transPwd")
	public String getTransPwd() {
		return transPwd;
	}
	public void setTransPwd(String transPwd) {
		this.transPwd = transPwd;
	}
    @Column(name = "balTotal")
	public Double getBalTotal() {
		return balTotal;
	}
	public void setBalTotal(Double balTotal) {
		this.balTotal = balTotal;
	}
    @Column(name = "balAvailable")
	public Double getBalAvailable() {
		return balAvailable;
	}
	public void setBalAvailable(Double balAvailable) {
		this.balAvailable = balAvailable;
	}
    @Column(name = "balblock")
	public Double getBalblock() {
		return balblock;
	}
	public void setBalblock(Double balblock) {
		this.balblock = balblock;
	}
    @Column(name = "subAmountStay")
	public Double getSubAmountStay() {
		return subAmountStay;
	}
	public void setSubAmountStay(Double subAmountStay) {
		this.subAmountStay = subAmountStay;
	}
    @Column(name = "subAmountPaid")
	public Double getSubAmountPaid() {
		return subAmountPaid;
	}
	public void setSubAmountPaid(Double subAmountPaid) {
		this.subAmountPaid = subAmountPaid;
	}
    @Column(name = "transPwdQue")
	public String getTransPwdQue() {
		return transPwdQue;
	}
	public void setTransPwdQue(String transPwdQue) {
		this.transPwdQue = transPwdQue;
	}
    @Column(name = "transPwdAnswer")
	public String getTransPwdAnswer() {
		return transPwdAnswer;
	}
	public void setTransPwdAnswer(String transPwdAnswer) {
		this.transPwdAnswer = transPwdAnswer;
	}
    @Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    @Column(name = "createuserId")
	public String getCreateuserId() {
		return createuserId;
	}
	public void setCreateuserId(String createuserId) {
		this.createuserId = createuserId;
	}
    @Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    @Column(name = "updateuserId")
	public String getUpdateuserId() {
		return updateuserId;
	}
	public void setUpdateuserId(String updateuserId) {
		this.updateuserId = updateuserId;
	}
	
	
	
	
}
