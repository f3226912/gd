package com.gudeng.commerce.gd.promotion.dto;

import java.util.List;

import com.gudeng.commerce.gd.promotion.entity.ActActivityBaseinfoEntity;

public class ActActivityBaseinfoDTO extends ActActivityBaseinfoEntity implements java.io.Serializable{


	/**O
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String createUserName;

	private String effectiveStartTimeStr;

	private String effectiveEndTimeStr;

	private Integer joinCount;

	private Integer shareCount;

	private List<ActReActivitityGiftDto> reActivityGiftList;

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getEffectiveStartTimeStr() {
		return effectiveStartTimeStr;
	}

	public void setEffectiveStartTimeStr(String effectiveStartTimeStr) {
		this.effectiveStartTimeStr = effectiveStartTimeStr;
	}

	public String getEffectiveEndTimeStr() {
		return effectiveEndTimeStr;
	}

	public void setEffectiveEndTimeStr(String effectiveEndTimeStr) {
		this.effectiveEndTimeStr = effectiveEndTimeStr;
	}

	public Integer getJoinCount() {
		return joinCount;
	}

	public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public List<ActReActivitityGiftDto> getReActivityGiftList() {
		return reActivityGiftList;
	}

	public void setReActivityGiftList(List<ActReActivitityGiftDto> reActivityGiftList) {
		this.reActivityGiftList = reActivityGiftList;
	}
}
