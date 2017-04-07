package com.gudeng.commerce.gd.order.dto;

import com.gudeng.commerce.gd.order.entity.InStoreDetailEntity;

public class InStoreDetailDTO extends InStoreDetailEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6353257122863588902L;
	/**
	 * 
	 */
	/**
	 * 货主入场商品表Id
	 */
	private Long pwdId;
	
	/**
	 * 供应商账号
	 */
	private String supplierAccount;
	
	/**
	 * 供应商姓名
	 */
	private String supplierName;
	
	/**
	 * 商品类目
	 */
	private String cateName;
	
	/**
	 * 产品创建时间
	 */
	private String productTime;
	
	/**
	 * 创建人
	 */
	private String createName;
	
	/**
	 * 批发商账号
	 */
	private String specialAccount;
	/**
	 * 批发商姓名
	 */
	private String specialName;
	
	/**
	 * 入库量（吨）
	 */
	private String instoreTime;
	
	/**
	 * 入库状态
	 */
	private String istoreStatus;
	
	/**
	 * 产品开始查询时间
	 */
	private String productStartTime;
	
	/**
	 * 产品结束查询时间
	 */
	private String productEndTime;
	

	public Long getPwdId() {
		return pwdId;
	}

	public void setPwdId(Long pwdId) {
		this.pwdId = pwdId;
	}

	public String getSupplierAccount() {
		return supplierAccount;
	}

	public void setSupplierAccount(String supplierAccount) {
		this.supplierAccount = supplierAccount;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getProductTime() {
		return productTime;
	}

	public void setProductTime(String productTime) {
		this.productTime = productTime;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getSpecialAccount() {
		return specialAccount;
	}

	public void setSpecialAccount(String specialAccount) {
		this.specialAccount = specialAccount;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getInstoreTime() {
		return instoreTime;
	}

	public void setInstoreTime(String instoreTime) {
		this.instoreTime = instoreTime;
	}

	public String getIstoreStatus() {
		return istoreStatus;
	}

	public void setIstoreStatus(String istoreStatus) {
		this.istoreStatus = istoreStatus;
	}

	public String getProductStartTime() {
		return productStartTime;
	}

	public void setProductStartTime(String productStartTime) {
		this.productStartTime = productStartTime;
	}

	public String getProductEndTime() {
		return productEndTime;
	}

	public void setProductEndTime(String productEndTime) {
		this.productEndTime = productEndTime;
	}
	
	
}
