package com.gudeng.commerce.gd.promotion.dto;

import java.util.Date;

import javax.persistence.Entity;

import com.gudeng.commerce.gd.promotion.entity.PromProdInfoEntity;

@Entity(name = "prom_prod_info")
public class PromProdInfoDTO extends PromProdInfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7651784494366249622L;
	
	private Date startTime;
	
	private Date endTime;
	
	/**
	 * 用户类型
	 */
	private Integer supplierType;
	
	/**
	 * 用户类型名称
	 */
	private String supplierTypeName;
	
	/**
	 * 用户电话
	 */
	private String mobile;
	
	/**
	 * 单位名称
	 */
	private String unitName;
	
	/**
	 * 库存
	 */
	private Double stockCount;
	
	/**
	 * 商品原价
	 */
	private Double price;
	
	/**
	 * 审核人
	 */
	private String memberId;
	
	/**
	 * 审核人姓名
	 */
	private String memberName;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 0:待审核(暂未参加活动，默认)1:已审核（参加活动)2:不通过(不参加活动) 3 已取消
	 */
	private Integer auditStatus;
	
	/**
	 * 导出供应商商品时使用
	 */
	private String auditStatusName;

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(Integer supplierType) {
		this.supplierType = supplierType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getStockCount() {
		return stockCount;
	}

	public void setStockCount(Double stockCount) {
		this.stockCount = stockCount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getSupplierTypeName() {
		return supplierTypeName;
	}

	public void setSupplierTypeName(String supplierTypeName) {
		this.supplierTypeName = supplierTypeName;
	}

	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}

}
