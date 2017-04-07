package com.gudeng.commerce.gd.promotion.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "prom_act_supplier_ref")
public class PromActSupplierRefEntity implements Serializable {
	
	private static final long serialVersionUID = 7053362904169283246L;
	
	private Long id;
	
	private Integer actId;
	
	private Integer supplierId;
	
	private String supplierName;
	
	private String status;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String createUserId;
	
	private String  updateUserId;
	
	@Id
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "actId")
	public Integer getActId() {
		return actId;
	}
	public void setActId(Integer actId) {
		this.actId = actId;
	}
	
	@Column(name = "supplierId")
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	
	@Column(name = "supplierName")
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	
	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
}
