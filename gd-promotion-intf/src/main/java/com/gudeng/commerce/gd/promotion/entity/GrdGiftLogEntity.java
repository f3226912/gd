package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * GrdGiftLog entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "grd_gift_log")
public class GrdGiftLogEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Long giftId;
	private Integer orignValue;
	private Integer realValue;
	private String reason;
	private Date createTime;
	private Date updateTime;
	private String createUserId;
	private String updateUserId;
	/**
	 * 采购单号
	 */
	private String purchaseNO;

	/**
	 * 入库单号
	 */
	private String inStorageId;

	/**
	 * 数量:库数量或出库数量,orignValue >realValue ,表示出库数量，orignValue < realValue 表示入库数量
	 */
	private Integer balance;

	/**
	 * 入出库标识，1为入库，2为出库
	 */
	private String type;

	// Constructors

	/** default constructor */
	public GrdGiftLogEntity() {
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

	@Column(name = "orignValue")
	public Integer getOrignValue() {
		return this.orignValue;
	}

	public void setOrignValue(Integer orignValue) {
		this.orignValue = orignValue;
	}

	@Column(name = "realValue")
	public Integer getRealValue() {
		return this.realValue;
	}

	public void setRealValue(Integer realValue) {
		this.realValue = realValue;
	}

	@Column(name = "reason", length = 200)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createTime", length = 10)
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
	
    @Column(name = "purchaseNO")
	public String getPurchaseNO() {
		return purchaseNO;
	}

	public void setPurchaseNO(String purchaseNO) {
		this.purchaseNO = purchaseNO;
	}
	
    @Column(name = "inStorageId")
	public String getInStorageId() {
		return inStorageId;
	}
    
	public void setInStorageId(String inStorageId) {
		this.inStorageId = inStorageId;
	}
	
    @Column(name = "balance")
	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	
    @Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}