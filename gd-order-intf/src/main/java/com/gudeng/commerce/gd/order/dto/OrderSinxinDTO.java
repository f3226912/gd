package com.gudeng.commerce.gd.order.dto;

import java.util.List;

import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;

public class OrderSinxinDTO extends OrderBaseinfoEntity {

	private static final long serialVersionUID = -4991626673546602456L;
	
	private List<OrderProductDetailEntity> jProductDetails;

	private String transTime; // 交易时间

	private List<String> orderNos; // 多个订单号
	
	private String macAddr; // 秤mac
	
	private String payStatus; // 支付状态 0未支付 1已支付 9支付失败
	
	private String payTime; // 支付时间 格式：yyyy-mm-dd HH:mm:ss
	
	public List<OrderProductDetailEntity> getjProductDetails() {
		return jProductDetails;
	}

	public void setjProductDetails(List<OrderProductDetailEntity> jProductDetails) {
		this.jProductDetails = jProductDetails;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public List<String> getOrderNos() {
		return orderNos;
	}

	public void setOrderNos(List<String> orderNos) {
		this.orderNos = orderNos;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

}
