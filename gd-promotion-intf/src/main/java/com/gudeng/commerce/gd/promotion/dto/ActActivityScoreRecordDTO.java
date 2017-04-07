package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.ActActivityScoreRecordEntity;

public class ActActivityScoreRecordDTO extends ActActivityScoreRecordEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String activityName;
	
	private String account;
		
	private Integer userScore;
	
	private Integer joinTimesLeft;
	
	private String createUserName;
	
	private String updateUserName;
	
	private String typeValue;
	
	private String srMobile;//积分来源手机号

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	
	public Integer getUserScore() {
		return userScore;
	}

	public void setUserScore(Integer userScore) {
		this.userScore = userScore;
	}

	public Integer getJoinTimesLeft() {
		return joinTimesLeft;
	}

	public void setJoinTimesLeft(Integer joinTimesLeft) {
		this.joinTimesLeft = joinTimesLeft;
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

	public String getTypeValue() {
		if(getType()!=null){
			if(getType().equals("1")){
				this.typeValue="首登积分";
			}
			else if(getType().equals("2")){
				this.typeValue="分享积分";
			}
			else if(getType().equals("3")){
				this.typeValue="点赞积分";
			}
			else if(getType().equals("4")){
				this.typeValue=getSrMobile()+"注册积分";
			}
			else if(getType().equals("7")){
				this.typeValue=getSrMobile()+"注册积分";
			}
			
			else if(getType().equals("5")){
				this.typeValue=getSrMobile()+"金牌会员积分";
			}
			else if(getType().equals("6")){
				this.typeValue=getSrMobile()+"金牌会员积分";
			}
			
		}else{
			this.typeValue="";
		}
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getSrMobile() {
		return srMobile;
	}

	public void setSrMobile(String srMobile) {
		this.srMobile = srMobile;
	}
	

}
