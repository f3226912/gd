package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "member_message")
public class MemberMessageEntity implements java.io.Serializable {

	private static final long serialVersionUID = -2376349509598885564L;

	private Long id;

	/* 会员ID */
	private Long memberId;

	/* 短信类型(1:广告上线 2:注册成功 3:达到浏览量) */
	private Integer type;

	/* 短信内容 */
	private String content;

	/* 发送时间 */
	private Date sendTime;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	@Id
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "memberId")
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "sendTime")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}