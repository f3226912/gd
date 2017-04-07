package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;

public class OrderOutmarketImageDTO implements Serializable{

	private static final long serialVersionUID = 5762457184406321441L;
	
	private Long orderNo;			// 订单编号
	private String carNumberImage;	// 车辆出场时的照片，用"|"分割，最多4张照片
	private String carNumber;		// 车牌号，如"粤B3721Q"
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public String getCarNumberImage() {
		return carNumberImage;
	}
	public void setCarNumberImage(String carNumberImage) {
		this.carNumberImage = carNumberImage;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	

}
