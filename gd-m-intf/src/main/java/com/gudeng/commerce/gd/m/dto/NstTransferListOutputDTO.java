package com.gudeng.commerce.gd.m.dto;

import java.io.Serializable;

/**
 * 物流列表对象DTO
 * @author TerryZhang
 */
public class NstTransferListOutputDTO implements Serializable{

	private static final long serialVersionUID = -7688771135573051593L;

	/**
	 * 物流状态
	 */
	private Integer status;
	
	//付款状态:1待付款 2已付款 3已关闭 4已退款
	private Integer payStatus;
	
	/**
	 * 运单号
	 */
	private String orderNo;
	
	/**
	 * 货源id
	 */
	private Integer transferId;
	
	/**
	 * 订单id
	 */
	private Integer orderId;
	
	/**
	 * 版本号
	 */
	private int version;
	
	/**
	 * 是否代发 0否 1是
	 */
	private Integer isProxy;
	
	/**
	 * 装车时间
	 */
	private String goodsDate;
	
	/**
	 * 发货地
	 */
	private String startAddr;
	
	/**
	 * 收货地
	 */
	private String endAddr;
	
	/**
	 * 联系手机号
	 */
	private String mobile;
	
	/**
	 * 车主会员id
	 */
	private String driverMemberId;

	public String getGoodsDate() {
		return goodsDate;
	}

	public void setGoodsDate(String goodsDate) {
		this.goodsDate = goodsDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStartAddr() {
		return startAddr;
	}

	public void setStartAddr(String startAddr) {
		this.startAddr = startAddr;
	}

	public String getEndAddr() {
		return endAddr;
	}

	public void setEndAddr(String endAddr) {
		this.endAddr = endAddr;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getTransferId() {
		return transferId;
	}

	public void setTransferId(Integer transferId) {
		this.transferId = transferId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Integer getIsProxy() {
		return isProxy;
	}

	public void setIsProxy(Integer isProxy) {
		this.isProxy = isProxy;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getDriverMemberId() {
		return driverMemberId;
	}

	public void setDriverMemberId(String driverMemberId) {
		this.driverMemberId = driverMemberId;
	}
}
