package com.gudeng.commerce.gd.promotion.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.promotion.entity.ActGiftExchangeApplyEntity;

public class ActGiftExchangeApplyDto extends ActGiftExchangeApplyEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7567293173626809471L;
	
	private String activityName;

	private String giftName ;

	private String account ;

	private String mobile ;

	private int exchangeScore;

	private String exchangeTime ;
	
	private String createUserName;
	
	private String updateUserName;
	
	private Integer scoreLeft;//剩余积分
	
	private Integer costLeft;//剩余库存
	
	/**
	 * 修改礼品兑换信息时，旧礼品的剩余库存值
	 */
	private Integer oldCostLeft;
	
	/**
	 * 修改礼品兑换信息时，旧活动id
	 */
	private Integer oldActivityId;
	
	/**
	 * 修改礼品兑换信息时，旧礼品id
	 */
	private Integer oldGiftId;

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(String exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public int getExchangeScore() {
		return exchangeScore;
	}

	public void setExchangeScore(int exchangeScore) {
		this.exchangeScore = exchangeScore;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Integer getScoreLeft() {
		return scoreLeft;
	}

	public void setScoreLeft(Integer scoreLeft) {
		this.scoreLeft = scoreLeft;
	}

	public Integer getCostLeft() {
		return costLeft;
	}

	public void setCostLeft(Integer costLeft) {
		this.costLeft = costLeft;
	}

	public Integer getOldCostLeft() {
		return oldCostLeft;
	}

	public void setOldCostLeft(Integer oldCostLeft) {
		this.oldCostLeft = oldCostLeft;
	}

	public Integer getOldActivityId() {
		return oldActivityId;
	}

	public void setOldActivityId(Integer oldActivityId) {
		this.oldActivityId = oldActivityId;
	}

	public Integer getOldGiftId() {
		return oldGiftId;
	}

	public void setOldGiftId(Integer oldGiftId) {
		this.oldGiftId = oldGiftId;
	}

}
