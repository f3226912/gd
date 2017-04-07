package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Date;

import com.gudeng.commerce.gd.customer.entity.MemberCertifiEntity;

public class MemberCertifiDTO extends MemberCertifiEntity implements Serializable{

	private static final long serialVersionUID = 2640668364905046238L;

	/**
	 * 增加updateTime的String类型，用于 mybaties操作数据库Date类型的insert和更新
	 */
	private String updateTime_string;

	/**
	 * 增加createTime的String类型，用于 mybaties操作数据库Date类型的insert和更新
	 */
	public String createTime_string;

	/**
	 * 关联查询会员表的用户来源
	 */
	private String level;

	/**
	 * 关联查询会员表的用户账号
	 */
	private String account;

	/**
	 * 关联查询会员昵称
	 */
	private String nickName;

	/**
	 * 关联查询会员 是否启用状态
	 */
	private String isActivi;

	/**
	 * 关联查询会员 手机号码
	 */
	private String memberMobile;

	/**
	 * 市场id
	 */
	private Long marketId;

	/**
	 * 市场名称
	 */
	private String marketName;

	/**
	 * 农速通会员所属区域
	 */
	private String areaName;

	/**
	 * 农速通行驶证图片地址
	 */
	private String nst_vehiclePhotoUrl;

	/**
	 * 农速通驾驶证图片地址
	 */
	private String nst_driverPhotoUrl;

	/**
	 * 操作时间（记录审核时间）
	 */
	private Date auditTime;

	/**
	 * 操作人姓名（记录审核人的姓名）
	 */
	private String memberName;

	/**
	 * 农速通认证车牌
	 */
	private String carNumber;

	/**
	 * 身份证图片相对地址
	 */
	private String cardPhotoUrl_relative;

	/**
	 * 营业执照图片相对地址
	 */
	private String bzlPhotoUrl_relative;

	/**
	 * 组织机构代码照片(url)相对地址
	 */
	private String orgCodePhotoUrl_relative;

	private String nst_CompanyAddress;

	public String getCommitTime_string() {
		return commitTime_string;
	}

	public void setCommitTime_string(String commitTime_string) {
		this.commitTime_string = commitTime_string;
	}

	private String commitTime_string;

	private String certificationTime_string;

	public String getCertificationTime_string() {
		return certificationTime_string;
	}

	public void setCertificationTime_string(String certificationTime_string) {
		this.certificationTime_string = certificationTime_string;
	}

	// 增加birthday的String类型，用于 mybaties操作数据库Date类型的insert和更新
	public String birthday_string;

	public String getBirthday_string() {
		return birthday_string;
	}

	public void setBirthday_string(String birthday_string) {
		this.birthday_string = birthday_string;
	}

	public String getUpdateTime_string() {
		return updateTime_string;
	}

	public void setUpdateTime_string(String updateTime_string) {
		this.updateTime_string = updateTime_string;
	}

	public String getCreateTime_string() {
		return createTime_string;
	}

	public void setCreateTime_string(String createTime_string) {
		this.createTime_string = createTime_string;
	}

	public String getNst_CompanyAddress() {
		return nst_CompanyAddress;
	}

	public void setNst_CompanyAddress(String nst_CompanyAddress) {
		this.nst_CompanyAddress = nst_CompanyAddress;
	}

	public String getCardPhotoUrl_relative() {
		return cardPhotoUrl_relative;
	}

	public void setCardPhotoUrl_relative(String cardPhotoUrl_relative) {
		this.cardPhotoUrl_relative = cardPhotoUrl_relative;
	}

	public String getBzlPhotoUrl_relative() {
		return bzlPhotoUrl_relative;
	}

	public void setBzlPhotoUrl_relative(String bzlPhotoUrl_relative) {
		this.bzlPhotoUrl_relative = bzlPhotoUrl_relative;
	}

	public String getOrgCodePhotoUrl_relative() {
		return orgCodePhotoUrl_relative;
	}

	public void setOrgCodePhotoUrl_relative(String orgCodePhotoUrl_relative) {
		this.orgCodePhotoUrl_relative = orgCodePhotoUrl_relative;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIsActivi() {
		return isActivi;
	}

	public void setIsActivi(String isActivi) {
		this.isActivi = isActivi;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getNst_vehiclePhotoUrl() {
		return nst_vehiclePhotoUrl;
	}

	public void setNst_vehiclePhotoUrl(String nst_vehiclePhotoUrl) {
		this.nst_vehiclePhotoUrl = nst_vehiclePhotoUrl;
	}

	public String getNst_driverPhotoUrl() {
		return nst_driverPhotoUrl;
	}

	public void setNst_driverPhotoUrl(String nst_driverPhotoUrl) {
		this.nst_driverPhotoUrl = nst_driverPhotoUrl;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}
}
