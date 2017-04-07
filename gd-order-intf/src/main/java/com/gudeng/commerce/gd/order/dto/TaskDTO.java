package com.gudeng.commerce.gd.order.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.gudeng.commerce.gd.order.enm.ETaskPriority;
import com.gudeng.commerce.gd.order.enm.ETaskSubType;
import com.gudeng.commerce.gd.order.enm.ETaskType;
import com.gudeng.commerce.gd.order.entity.TaskEntity;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2015年12月7日 下午9:25:50
 */
public class TaskDTO extends TaskEntity {

	private static final long serialVersionUID = 682018944706563805L;
	
	private String qryTaskType; // 任务类型（1补贴 2发送短信 3自动撤单）

	private String qryTaskStatus; // 任务状态（0初始状态 1运行中 2已完成 9失败 11锁定）

	private List<String> qryTaskStatusIn; // 任务状态（0初始状态 1运行中 2已完成 9失败）
	
	private Integer qryMaxFailCount; // 最大失败次数
	
	private Integer orderCount; // 本次查询订单出现次数
	
	private String subLimitRuleFlag; // 补贴限制规则标志 1是 0否
	
	private String payTimeStr; // 支付时间
	
	/**
	 * @Title: getCalcSubAmtTask
	 * @Description: TODO(获取计算补贴金额任务)
	 * @param orderNumber
	 * @return
	 */
	public static TaskDTO getCalcSubAmtTask(String orderNumber) {
		TaskDTO taskInfo = new TaskDTO();
		taskInfo.setTaskType(ETaskType.SUB.getCode());
		taskInfo.setTaskSubType(ETaskSubType.CALC_SUB_AMT.getCode());
		taskInfo.setTaskPriority(ETaskPriority.FOUR.getCode());
		taskInfo.setOrderNumber(orderNumber);
		return taskInfo;
	}
	
	/**
	 * @Title: getSubLimitRuleTask
	 * @Description: TODO(获取补贴限制规则任务)
	 * @param orderNumber
	 * @return
	 */
	public static TaskDTO getSubLimitRuleTask(String orderNumber) {
		TaskDTO taskInfo = new TaskDTO();
		taskInfo.setTaskType(ETaskType.SUB.getCode());
		taskInfo.setTaskSubType(ETaskSubType.SUB_LIMIT_RULE.getCode());
		taskInfo.setTaskPriority(ETaskPriority.FIVE.getCode());
		taskInfo.setOrderNumber(orderNumber);
		return taskInfo;
	}
	
	/**
	 * @Title: getSendEmailTask
	 * @Description: TODO(获取发送邮件任务)
	 * @param subject 主题
	 * @param contacts 收件人
	 * @param sendContent 发送内容
	 * @return
	 */
	public static TaskDTO getSendEmailTask(String subject, String contact, String sendContent) {
		List<String> contacts = new ArrayList<String>();
		contacts.add(contact);
		return getSendEmailTask(subject, contacts, sendContent);
	}
	
	/**
	 * @Title: getSendEmailTask
	 * @Description: TODO(获取发送邮件任务)
	 * @param subject 主题
	 * @param contacts 收件人
	 * @param sendContent 发送内容
	 * @return
	 */
	public static TaskDTO getSendEmailTask(String subject, List<String> contacts, String sendContent) {
		TaskDTO taskInfo = new TaskDTO();
		taskInfo.setTaskType(ETaskType.SEND_EMAIL.getCode());
		taskInfo.setTaskPriority(ETaskPriority.FIVE.getCode());
		taskInfo.setSubject(subject);
		taskInfo.setReceiveUsers(StringUtils.join(contacts, ","));
		taskInfo.setSendContent(sendContent);
		return taskInfo;
	}
	
	/**
	 * @Title: getProductSyncTask
	 * @Description: TODO(获取商品同步任务)
	 * @param productId 商品同步任务
	 * @return
	 */
	public static TaskDTO getProductSyncTask(String productId) {
		TaskDTO taskInfo = new TaskDTO();
		taskInfo.setTaskType(ETaskType.PRODUCT_SYNC.getCode());
		taskInfo.setTaskPriority(ETaskPriority.FIVE.getCode());
		taskInfo.setExtend1(productId);
		return taskInfo;
	}

	public String getQryTaskType() {
		return qryTaskType;
	}

	public void setQryTaskType(String qryTaskType) {
		this.qryTaskType = qryTaskType;
	}

	public String getQryTaskStatus() {
		return qryTaskStatus;
	}

	public void setQryTaskStatus(String qryTaskStatus) {
		this.qryTaskStatus = qryTaskStatus;
	}

	public List<String> getQryTaskStatusIn() {
		return qryTaskStatusIn;
	}

	public void setQryTaskStatusIn(List<String> qryTaskStatusIn) {
		this.qryTaskStatusIn = qryTaskStatusIn;
	}

	public Integer getQryMaxFailCount() {
		return qryMaxFailCount;
	}

	public void setQryMaxFailCount(Integer qryMaxFailCount) {
		this.qryMaxFailCount = qryMaxFailCount;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	
	public String getSubLimitRuleFlag() {
		return subLimitRuleFlag;
	}

	public void setSubLimitRuleFlag(String subLimitRuleFlag) {
		this.subLimitRuleFlag = subLimitRuleFlag;
	}

	public String getPayTimeStr() {
		return payTimeStr;
	}

	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
