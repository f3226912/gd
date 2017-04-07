package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 绑定银行卡-数据同步Dto, 对应订单中心的MemberBankcardInfoEntity,(两个类需要保持字段一致)
 */
public class AccBankCardSendDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8795982876401406599L;
	
	/**操作类型：0 创建 1 更新 2删除**/
	private Integer crud;
	private String bankId;
	
	private Long infoId;
	
	private Integer memberId;

	private String realName;

	private String idCard;

	private String depositBankName;

	private String subBankName;

	private String bankCardNo;

	private Integer provinceId;

	private Integer cityId;

	private Integer areaId;

	private Integer accCardType;

	private String status;
	
	private Date updateTime;
	
	private String mobile;
	
	private String account;
	private String areaName;
	private String cityName;
	private String provinceName;
	private String telephone;
	private String regtype;
	private String auditStatus ;
	
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getRegtype() {
		return regtype;
	}
	public void setRegtype(String regtype) {
		this.regtype = regtype;
	}
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getInfoId() {
		return infoId;
	}
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getDepositBankName() {
		return depositBankName;
	}
	public void setDepositBankName(String depositBankName) {
		this.depositBankName = depositBankName;
	}
	public String getSubBankName() {
		return subBankName;
	}
	public void setSubBankName(String subBankName) {
		this.subBankName = subBankName;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCrud() {
		return crud;
	}
	public void setCrud(Integer crud) {
		this.crud = crud;
	}
	public Integer getAccCardType() {
		return accCardType;
	}
	public void setAccCardType(Integer accCardType) {
		this.accCardType = accCardType;
	}
	

}
