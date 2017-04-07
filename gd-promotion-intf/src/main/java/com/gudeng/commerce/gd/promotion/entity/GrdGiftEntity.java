package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * GrdGift entity. @author MyEclipse Persistence Tools
 */
/**
 * 地面推广-礼物表
 * 
 * @author lidong
 *
 */
@Entity(name = "grd_gift")
public class GrdGiftEntity implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 245959245325662207L;
	private Integer id;
	private String name;
	private Integer stockTotal;
	private Integer stockAvailable;
	private Integer marketId;
	private Integer giftstoreId;//礼品所属仓库
	private String type;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private String createUserId;
	private String updateUserId;
	private String remark;
	private String unit;
	private String giftNo;
	
	@Column(name = "unit")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "giftstoreId")
	public Integer getGiftstoreId() {
		return giftstoreId;
	}

	public void setGiftstoreId(Integer giftstoreId) {
		this.giftstoreId = giftstoreId;
	}

	// Constructors
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** default constructor */
	public GrdGiftEntity() {
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

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "stock_total")
	public Integer getStockTotal() {
		return this.stockTotal;
	}

	public void setStockTotal(Integer stockTotal) {
		this.stockTotal = stockTotal;
	}

	@Column(name = "stock_available")
	public Integer getStockAvailable() {
		return this.stockAvailable;
	}

	public void setStockAvailable(Integer stockAvailable) {
		this.stockAvailable = stockAvailable;
	}

	@Column(name = "marketId")
	public Integer getMarketId() {
		return this.marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	@Column(name = "type", length = 2)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	@Column(name = "giftNo")
	public String getGiftNo() {
		return giftNo;
	}

	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}

}