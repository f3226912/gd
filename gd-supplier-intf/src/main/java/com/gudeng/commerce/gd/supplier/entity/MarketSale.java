package com.gudeng.commerce.gd.supplier.entity;

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
 * @Description 市场交易额
 * @Project gd-supplier-intf
 * @ClassName MarketSale.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年11月10日 下午5:45:36
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Entity
@Table(name = "market_sale", catalog = "gudeng")
public class MarketSale implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 7582579440490005653L;
	private Integer id;// 主键
	private Integer marketId;// 市场ID
	private Date recordDate;// 记录时间
	private Double yestodaySale;// 昨日交易额
	private Double todaySale;// 今日交易额
	private String createUserId;// 创建者ID
	private Date createTime;// 创建时间
	private String updateUserId;// 修改人ID
	private Date updateTime;// 修改时间

	// Constructors

	/** default constructor */
	public MarketSale() {
	}

	/** full constructor */
	public MarketSale(Integer marketId, Date recordDate, Double yestodaySale, Double todaySale, String createUserId, Date createTime, String updateUserId, Date updateTime) {
		this.marketId = marketId;
		this.recordDate = recordDate;
		this.yestodaySale = yestodaySale;
		this.todaySale = todaySale;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.updateUserId = updateUserId;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "marketId")
	public Integer getMarketId() {
		return this.marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "recordDate", length = 10)
	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name = "yestodaySale", precision = 10)
	public Double getYestodaySale() {
		return this.yestodaySale;
	}

	public void setYestodaySale(Double yestodaySale) {
		this.yestodaySale = yestodaySale;
	}

	@Column(name = "todaySale", precision = 10)
	public Double getTodaySale() {
		return this.todaySale;
	}

	public void setTodaySale(Double todaySale) {
		this.todaySale = todaySale;
	}

	@Column(name = "createUserId", length = 32)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updateUserId", length = 32)
	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}