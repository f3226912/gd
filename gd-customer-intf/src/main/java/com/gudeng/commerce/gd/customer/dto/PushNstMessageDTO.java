package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

public class PushNstMessageDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long memberId;
	//推送消息
	private Long messageId;
	private String messageType;
	private Long mbId;
	private String createTime;
	private String mCity;
	private Long cityId;
	private String readed;
	private String deleted;
	private String messageIdString;
	private String sourceType;//1干线 2同城
	
	
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getMessageIdString() {
		return messageIdString;
	}
	public void setMessageIdString(String messageIdString) {
		this.messageIdString = messageIdString;
	}
	public String getReaded() {
		return readed;
	}
	public void setReaded(String readed) {
		this.readed = readed;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getmCity() {
		return mCity;
	}
	public void setmCity(String mCity) {
		this.mCity = mCity;
	}
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Integer getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}
	private Long clId;
	private Integer messageCount;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMbId() {
		return mbId;
	}
	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}
	public Long getClId() {
		return clId;
	}
	public void setClId(Long clId) {
		this.clId = clId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
}
