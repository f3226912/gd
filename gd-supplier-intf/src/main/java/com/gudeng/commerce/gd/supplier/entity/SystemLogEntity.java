package com.gudeng.commerce.gd.supplier.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "system_log")
public class SystemLogEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7943744790934097518L;


	private Long systemLogId;
//	 '日志类型 1产品 2会员 3信息 4商铺 5物流 6系统 7补贴 8订单 9钱包10广告',
	private String type;
//	 '日志内容'
	private String content;
//	'日志来源1.运营后台2.web端3.API4.定时任务'
	private String chennel;
	
	private Date createTime;
	
	private String createUserId;
//	'操作数据ID'
	private Long operationId;

	@Id
	@Column(name = "systemLogId", unique = true, nullable = false)
	public Long getSystemLogId() {
		return systemLogId;
	}

	public void setSystemLogId(Long systemLogId) {
		this.systemLogId = systemLogId;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "chennel")
	public String getChennel() {
		return chennel;
	}

	public void setChennel(String chennel) {
		this.chennel = chennel;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "operationId")
	public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}
	
	
	
}
