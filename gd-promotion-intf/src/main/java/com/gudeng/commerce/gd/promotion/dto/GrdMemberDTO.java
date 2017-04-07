package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GrdMemberEntity;

public class GrdMemberDTO extends GrdMemberEntity {
	private static final long serialVersionUID = 5514904225402687181L;
	
	/**邀请注册客户总数
	 * 
	 */
	private Integer registerAmount;
	/**
	 * 促成订单总数
	 */
	private Integer orderAmount;
	
	/**
	 * 订单总金额
	 */
	private Double orderPriceAmount;
	
	private Integer giftstoreId;//地推人员所属礼品仓库id
	
	private String giftteamName;
	
	private Integer userType;//1表示农批的地推用户，2表示农速通的地推用户，3表示即是农批又是农速通地推用户

	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getGiftstoreId() {
		return giftstoreId;
	}
	public void setGiftstoreId(Integer giftstoreId) {
		this.giftstoreId = giftstoreId;
	}
	public Integer getRegisterAmount() {
		return registerAmount;
	}
	public void setRegisterAmount(Integer registerAmount) {
		this.registerAmount = registerAmount;
	}
	public Integer getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Double getOrderPriceAmount() {
		return orderPriceAmount;
	}
	public void setOrderPriceAmount(Double orderPriceAmount) {
		this.orderPriceAmount = orderPriceAmount;
	}
	public String getGiftteamName() {
		return giftteamName;
	}
	public void setGiftteamName(String giftteamName) {
		this.giftteamName = giftteamName;
	}
	
}