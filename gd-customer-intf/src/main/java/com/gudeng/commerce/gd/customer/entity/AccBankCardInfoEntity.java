package com.gudeng.commerce.gd.customer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "acc_bank_card_info")
public class AccBankCardInfoEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -141352884699031066L;
 
	private Long infoId;
	private Long accountId;
	private Long memberId;
	private String realName;
	 
	private String idCard;
	private String bankId;
	private String depositBankName;
	private String bankCardNo;
	private String status;
	private Date createTime;
	private String createUserId;
	private Date updateTime;
	private String updateUserId;
 
	private Long provinceId;
	private Long cityId;
	private String regtype;
	private String auditStatus;
	
	@Column(name = "auditStatus")
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	@Column(name = "regtype")
	public String getRegtype() {
		return regtype;
	}
	public void setRegtype(String regtype) {
		this.regtype = regtype;
	}
	/**
	 * 开户行支行名称
	 */
	private String subBankName ;
	/**
	 * '1 普通账号 2 默认收款账号'
	 */
	private Integer accCardType ;
	/**
	 * 区域id
	 */
	private Long areaId;
	
	/**
	 * 银行卡预留手机号
	 */
	private String mobile;
	
	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Id
	@Column(name = "infoId")
	public Long getInfoId() {
		return infoId;
	}
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
	
	@Column(name = "subBankName")
	public String getSubBankName() {
		return subBankName;
	}
	public void setSubBankName(String subBankName) {
		this.subBankName = subBankName;
	}
	@Column(name = "accCardType")
	public Integer getAccCardType() {
		return accCardType;
	}
	public void setAccCardType(Integer accCardType) {
		this.accCardType = accCardType;
	}
	@Column(name = "areaId")
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	@Column(name = "accountId")
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Column(name = "memberId")
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
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
	@Column(name = "bankId")
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	@Column(name = "depositBankName")
	public String getDepositBankName() {
		return depositBankName;
	}
	public void setDepositBankName(String depositBankName) {
		this.depositBankName = depositBankName;
	}
	@Column(name = "bankCardNo")
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	@Column(name = "provinceId")
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	@Column(name = "cityId")
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	@Override
	public String toString() {
		return "AccBankCardInfoEntity [infoId=" + infoId + ", accountId=" + accountId + ", memberId=" + memberId
				+ ", realName=" + realName + ", idCard=" + idCard + ", bankId=" + bankId + ", depositBankName="
				+ depositBankName + ", bankCardNo=" + bankCardNo + ", status=" + status + ", createTime=" + createTime
				+ ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId
				+ ", provinceId=" + provinceId + ", cityId=" + cityId + ", regtype=" + regtype + ", auditStatus="
				+ auditStatus + ", subBankName=" + subBankName + ", accCardType=" + accCardType + ", areaId=" + areaId
				+ ", mobile=" + mobile + "]";
	}
	
}
