package com.gudeng.commerce.gd.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 任务实体类
 * 
 * @author panmin
 * @version [版本号, 2014-9-12]
 * @since [产品/模块版本]
 */
@Entity(name = "task_pool")
public class TaskEntity implements Serializable {

	private static final long serialVersionUID = 680171303775441205L;
	private Long taskId; // 任务编号
	private String taskType; // 任务类型（1补贴 2发送短信 3自动撤单）
	private String taskSubType; // 任务子类型（1补贴限制规则 2计算补贴金额）
	private String taskStatus; // 任务状态（0初始状态 1运行中 2已完成 9失败 11锁定）
	private Date runTime; // 任务运行时间
	private Integer taskPriority; // 优先级1-9（默认5，值越小，优先级越高）
	private Integer count; // 次数
	private String errInfo; // 错误信息
	private String orderNumber; // 订单编号
	private String smsContent; // 短信内容
	private String recvNum; // 手机号码
	private Date payTime; // 支付时间
	private String extend1; // 扩展字段1
	private String receiveUsers; // 收件人 多个以竖线分隔
	private String subject; // 主题
	private String sendContent; // 发送内容
	private Date createTime; // 创建时间
	private Date updatedTime; // 更新时间

	@Id
	@Column(name = "taskId")
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@Column(name = "taskType")
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	@Column(name = "taskSubType")
	public String getTaskSubType() {
		return taskSubType;
	}

	public void setTaskSubType(String taskSubType) {
		this.taskSubType = taskSubType;
	}

	@Column(name = "taskStatus")
	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Column(name = "runTime")
	public Date getRunTime() {
		return runTime;
	}

	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}

	@Column(name = "taskPriority")
	public Integer getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(Integer taskPriority) {
		this.taskPriority = taskPriority;
	}

	@Column(name = "count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "errInfo")
	public String getErrInfo() {
		return errInfo;
	}

	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}

	@Column(name = "orderNumber")
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name = "smsContent")
	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	@Column(name = "recvNum")
	public String getRecvNum() {
		return recvNum;
	}

	public void setRecvNum(String recvNum) {
		this.recvNum = recvNum;
	}

	@Column(name = "payTime")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	@Column(name = "extend1")
	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}

	@Column(name = "receiveUsers")
	public String getReceiveUsers() {
		return receiveUsers;
	}

	public void setReceiveUsers(String receiveUsers) {
		this.receiveUsers = receiveUsers;
	}

	@Column(name = "subject")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "sendContent")
	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updatedTime")
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
}
