package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.entity.PushAdInfoEntity;

public class PushAdInfoDTO extends PushAdInfoEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 开始时间(字符串)
	 */
	private String startTimeStr;
	
	/**
	 * 结束时间(字符串)
	 */
	private String endTimeStr;
	
	/**
	 * 创建时间(字符串)
	 */
	private String createTimeStr;
	
	/**
	 * 修改时间(字符串)
	 */
	private String updateTimeStr;
	
	/**
	 * 产品类目1
	 */
	private Long categoryId1;
	/**
	 * 产品类目2
	 */
	private Long categoryId2;
	/**
	 * 产品类目3
	 */
	private Long categoryId3;
	

	/**
	 * keyWord
	 * 
	 * 检索关键字，目前用于搜索推荐产品+产品列表，其他地方可拓展使用
	 * 
	 */
	private String keyWord;
	
	/**
	 * customerId
	 * 
	 * 农商友登录的用户id
	 */
	private Long memberId;
	
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCategoryId1() {
		return categoryId1;
	}

	public void setCategoryId1(Long categoryId1) {
		this.categoryId1 = categoryId1;
	}

	public Long getCategoryId2() {
		return categoryId2;
	}

	public void setCategoryId2(Long categoryId2) {
		this.categoryId2 = categoryId2;
	}

	public Long getCategoryId3() {
		return categoryId3;
	}

	public void setCategoryId3(Long categoryId3) {
		this.categoryId3 = categoryId3;
	}

}
