package com.gudeng.commerce.gd.api.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * 卖家订单列表DTO
 * @author TerryZhang
 *
 */
public class SellerOrderListDTO {

	/**
	 * 订单号
	 */
	private Long orderNo;

	/**
	 * 订单创建时间
	 */
	private String createDate;
	
	/**
	 * 产品名称列表
	 */
	private String productsName;
	
	/**
	 * 产品名称数组
	 */
	private String[] productNameArr;
	
	/**
	 * 买主名
	 */
	private String buyerName;
	
	/**
	 * 产品个数
	 */
	private Integer productNum;
	
	/**
	 * 订单总额
	 */
	private Double orderAmount;
	
	/**
	 * 格式化后价格
	 */
	private String formattedPrice;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 审核状态:0待审核 1审核通过 2审核驳回
	 */
	private String auditStatus;
	
	/**
	 * 审核原因
	 */
	private String auditDesc;
	
	/**
	 *二维码 
	 */
	private String qrCode;

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = StringUtils.isBlank(buyerName) ? "农商友用户": buyerName;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public String[] getProductNameArr() {
		return productNameArr;
	}

	public void setProductNameArr(String[] productNameArr) {
		this.productNameArr = productNameArr;
	}
}
