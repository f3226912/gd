package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.NstOrderCommentEntity;

public class NstOrderCommentDTO extends NstOrderCommentEntity{


    /**
	 * 
	 */
	private static final long serialVersionUID = -5663611045694823939L;

	private String createTimeString;

	/**
	 * 评论信息类型：1：评论别人；2：被别人评论
	 */
	private String commentType;
	
	private String memberName;
	
	private String createUserName;
	
	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
    
	
}
