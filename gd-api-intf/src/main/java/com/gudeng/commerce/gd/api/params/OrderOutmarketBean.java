package com.gudeng.commerce.gd.api.params;

import java.io.Serializable;

public class OrderOutmarketBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * app传过来的一次出场的所有订单号以,隔开
	 */
	private String orderNoList;
	
	/**
	 * 查验员ID
	 */
	private String controllerId;
	
	/**
	 * 车牌图片(四张图片 | 隔开)
	 */
	private String carNumberImage;
	
	/**
	 * 车过磅ID
	 */
	private String weighCarId;
	
	/**
	 * 订单总重
	 */
	private Double orderWeigh;
	
	/**
	 * 出场商品id，多个已,分隔
	 */
	private String productIds;
	
	private Double weight;
	
	private Long memberId;
	
	private String loginUserId;
	
	public Double getOrderWeigh() {
		return orderWeigh;
	}
	public void setOrderWeigh(Double orderWeigh) {
		this.orderWeigh = orderWeigh;
	}
	public String getOrderNoList() {
		return orderNoList;
	}
	public void setOrderNoList(String orderNoList) {
		this.orderNoList = orderNoList;
	}
	public String getControllerId() {
		return controllerId;
	}
	public void setControllerId(String controllerId) {
		this.controllerId = controllerId;
	}
	public String getCarNumberImage() {
		return carNumberImage;
	}
	public void setCarNumberImage(String carNumberImage) {
		this.carNumberImage = carNumberImage;
	}
	public String getWeighCarId() {
		return weighCarId;
	}
	public void setWeighCarId(String weighCarId) {
		this.weighCarId = weighCarId;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

}
