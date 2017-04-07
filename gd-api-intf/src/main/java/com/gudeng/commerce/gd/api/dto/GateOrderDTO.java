package com.gudeng.commerce.gd.api.dto;

import java.util.Date;
import java.util.List;

/**
 * 门岗出场订单DTO
 * @author wind
 *
 */
public class GateOrderDTO {
	private int memberId;
	
    private String mobile;
    
    private String shopName;
    
    private String orderNo;
    
    private Date orderTime;
    
    private String realName;
    
    private List<GateOrderDetailDTO> detailList;

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public List<GateOrderDetailDTO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<GateOrderDetailDTO> detailList) {
		this.detailList = detailList;
	}
    
}
