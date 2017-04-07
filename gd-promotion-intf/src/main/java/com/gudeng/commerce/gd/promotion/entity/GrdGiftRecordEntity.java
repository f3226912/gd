package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * GrdGiftRecord entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "grd_gift_record")
public class GrdGiftRecordEntity implements java.io.Serializable {


    private static final long serialVersionUID = -6818930223042741460L;
	// Fields

	private Integer id;
	private Long giftId;
	private String type;//范围：1表示农批，2表示农速通
	private String memberId;
	private String mobile;
	private String realName;
	private String level;
	private Integer marketid;
	private String status;//0表示未发放，1表示已发放
	private Integer count;
	private String grantUserId;
	private Date grantTime;
	private String carPicture;
	private String carNo;
	private Date createTime;
	private Date updateTime;
	private String createUserId;
	private String updateUserId;
	private Integer giftstoreId;//所属礼品仓库
	// Constructors

	/** default constructor */
	public GrdGiftRecordEntity() {
	}

	// Property accessors
	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "giftId")
	public Long getGiftId() {
		return this.giftId;
	}

	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}

	@Column(name = "type", length = 2)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "mobile", length = 32)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "realName", length = 32)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "level", length = 2)
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(name = "marketid")
	public Integer getMarketid() {
		return this.marketid;
	}

	public void setMarketid(Integer marketid) {
		this.marketid = marketid;
	}

	@Column(name = "status", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "count")
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "grantUserId", length = 32)
	public String getGrantUserId() {
		return this.grantUserId;
	}

	public void setGrantUserId(String grantUserId) {
		this.grantUserId = grantUserId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "grantTime", length = 10)
	public Date getGrantTime() {
		return this.grantTime;
	}

	public void setGrantTime(Date grantTime) {
		this.grantTime = grantTime;
	}

	@Column(name = "carPicture", length = 500)
	public String getCarPicture() {
		return this.carPicture;
	}

	public void setCarPicture(String carPicture) {
		this.carPicture = carPicture;
	}

	@Column(name = "carNo", length = 32)
	public String getCarNo() {
		return this.carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createTime", length = 20)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updateTime", length = 10)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "createUserId", length = 32)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "updateUserId", length = 32)
	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	@Column(name = "giftstoreId")
	public Integer getGiftstoreId() {
		return giftstoreId;
	}

	public void setGiftstoreId(Integer giftstoreId) {
		this.giftstoreId = giftstoreId;
	}
	@Column(name = "memberId")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
}