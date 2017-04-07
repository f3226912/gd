package com.gudeng.commerce.gd.promotion.dto;

import java.util.Date;

import java.util.List;

import com.gudeng.commerce.gd.promotion.entity.GrdGiftRecordEntity;
public class GrdGiftRecordDTO extends GrdGiftRecordEntity {
	private static final long serialVersionUID = -8436179779743644932L;
	/**
	 * 类型名称
	 */
	private String typeName;
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 礼品发放时间
	 */
	private Date giftIssusedDate;  
	
	/**
	 * 礼品开单时间
	 */
	private Date giftBillDate;
	
	/**
	 * 礼品发放数量
	 */
	private Integer countNo;
	
	/**礼品发放人姓名
	 * 
	 */
	private String grantUser;
	/**开单人(地推)姓名
	 * 
	 */
	private String createUser;
	
	/**开单人(地推)姓名
	 * 
	 */
	private String createUserMobile;
	
	/**
	 * 所属市场名称
	 */
	private String marketName;
	
	/**邀请用户注册时间
	 * 
	 */
	private Date inviteUserRegDate;


	/**车辆图片路径数组。每个元素对应一张图片。
	 * 
	 */
	private String[] carPictures;
	
	private List<GrdGiftDetailDTO> detailDTOs;
	
	private String grantTimeStr;
	
	private String createTimeStr;
	
	/**
	 * 订单总数
	 */
	private Integer orderAmount;
	
	/**
	 * 订单总金额
	 */
	private Double orderPriceAmount;
	
	private String giftstoreName;
	
	public String getCreateUserMobile() {
		return createUserMobile;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public void setCreateUserMobile(String createUserMobile) {
		this.createUserMobile = createUserMobile;
	}
	public String getGrantTimeStr() {
		return grantTimeStr;
	}

	public void setGrantTimeStr(String grantTimeStr) {
		this.grantTimeStr = grantTimeStr;
	}

	public Date getGiftIssusedDate() {
		return giftIssusedDate;
	}

	public void setGiftIssusedDate(Date giftIssusedDate) {
		this.giftIssusedDate = giftIssusedDate;
	}

	public Date getGiftBillDate() {
		return giftBillDate;
	}

	public void setGiftBillDate(Date giftBillDate) {
		this.giftBillDate = giftBillDate;
	}

	public Integer getCountNo() {
		return countNo;
	}

	public void setCountNo(Integer countNo) {
		this.countNo = countNo;
	}

	public String getGrantUser() {
		return grantUser;
	}

	public void setGrantUser(String grantUser) {
		this.grantUser = grantUser;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String[] getCarPictures() {
		return carPictures;
	}

	public void setCarPictures(String[] carPictures) {
		this.carPictures = carPictures;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public List<GrdGiftDetailDTO> getDetailDTOs() {
		return detailDTOs;
	}

	public void setDetailDTOs(List<GrdGiftDetailDTO> detailDTOs) {
		this.detailDTOs = detailDTOs;
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

	public Date getInviteUserRegDate() {
		return inviteUserRegDate;
	}

	public void setInviteUserRegDate(Date inviteUserRegDate) {
		this.inviteUserRegDate = inviteUserRegDate;
	}

	public String getGiftstoreName() {
		return giftstoreName;
	}

	public void setGiftstoreName(String giftstoreName) {
		this.giftstoreName = giftstoreName;
	}
	
	
}