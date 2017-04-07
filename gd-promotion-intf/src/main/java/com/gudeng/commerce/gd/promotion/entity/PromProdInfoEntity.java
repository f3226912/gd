package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "prom_prod_info")
public class PromProdInfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7651784494366249622L;

	private Integer id;

	private Integer actId;

	private Integer auditId;

	private Integer supplierId;

	private String supplierName;

	private Long prodId;

	private String prodName;

	private String unit;

	private Double supplyQty;

	private Double actPrice;

	private Date createTime;

	private String createUserId;

	private Date updateTime;

	private String updateUserId;

	@Id
	@Column(name = "id")
	public Integer getId() {

		return this.id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	@Column(name = "actId")
	public Integer getActId() {

		return this.actId;
	}

	public void setActId(Integer actId) {

		this.actId = actId;
	}

	@Column(name = "auditId")
	public Integer getAuditId() {

		return this.auditId;
	}

	public void setAuditId(Integer auditId) {

		this.auditId = auditId;
	}

	@Column(name = "supplierId")
	public Integer getSupplierId() {

		return this.supplierId;
	}

	public void setSupplierId(Integer supplierId) {

		this.supplierId = supplierId;
	}

	@Column(name = "supplierName")
	public String getSupplierName() {

		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {

		this.supplierName = supplierName;
	}

	@Column(name = "prodId")
	public Long getProdId() {

		return this.prodId;
	}

	public void setProdId(Long prodId) {

		this.prodId = prodId;
	}

	@Column(name = "prodName")
	public String getProdName() {

		return this.prodName;
	}

	public void setProdName(String prodName) {

		this.prodName = prodName;
	}

	@Column(name = "unit")
	public String getUnit() {

		return this.unit;
	}

	public void setUnit(String unit) {

		this.unit = unit;
	}

	@Column(name = "supplyQty")
	public Double getSupplyQty() {

		return this.supplyQty;
	}

	public void setSupplyQty(Double supplyQty) {

		this.supplyQty = supplyQty;
	}

	@Column(name = "actPrice")
	public Double getActPrice() {

		return this.actPrice;
	}

	public void setActPrice(Double actPrice) {

		this.actPrice = actPrice;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}
}
