package com.gudeng.commerce.gd.m.dto;

import java.io.Serializable;
import java.util.List;


/**
 * 物流详情DTO
 * @author TerryZhang
 */
public class NstTransferDetailOutputDTO implements Serializable{

	private static final long serialVersionUID = 7308020130570845603L;

	/**
	 * 物流状态
	 */
	private Integer status;
	
	//付款状态:1待付款 2已付款 3已关闭 4已退款
	private Integer payStatus;
	
	/**
	 * 订单id
	 */
	private Integer orderId;
	
	/**
	 * 运单号
	 */
	private String orderNo;
	
	/**
	 * 运费
	 */
	private String freight;
	
	/**
	 * 支付流水号
	 */
	private String paySerialNo;
	
	/**
	 * 货源id
	 */
	private Integer transferId;
	
	/**
	 * 版本号
	 */
	private int version;
	
	/**
	 * 是否代发 0否 1是
	 */
	private Integer isProxy;
	
	/**
	 * 最迟接受司机接单时间
	 */
	private String acceptTime;
	
	/**
	 * 物流公司信息
	 */
	private NstTransferCompanyDetailDTO companyInfo;
	
	/**
	 * 司机信息
	 */
	private NstTransferDriverDetailDTO driverInfo;
	
	/**
	 * 货物信息
	 */
	private NstTransferGoodsDetailDTO goodsInfo;
	
	/**
	 * 物流细节信息
	 */
	private List<NstTransferDeliverDetailDTO> deliverInfo;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public NstTransferCompanyDetailDTO getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(NstTransferCompanyDetailDTO companyInfo) {
		this.companyInfo = companyInfo;
	}

	public NstTransferDriverDetailDTO getDriverInfo() {
		return driverInfo;
	}

	public void setDriverInfo(NstTransferDriverDetailDTO driverInfo) {
		this.driverInfo = driverInfo;
	}

	public NstTransferGoodsDetailDTO getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(NstTransferGoodsDetailDTO goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public List<NstTransferDeliverDetailDTO> getDeliverInfo() {
		return deliverInfo;
	}

	public void setDeliverInfo(List<NstTransferDeliverDetailDTO> deliverInfo) {
		this.deliverInfo = deliverInfo;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getPaySerialNo() {
		return paySerialNo;
	}

	public void setPaySerialNo(String paySerialNo) {
		this.paySerialNo = paySerialNo;
	}
}
