package com.gudeng.commerce.gd.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: TODO(补贴额使用提示配置实体类)
 * @author mpan
 * @date 2015年12月23日 下午8:45:20
 */
public class SubAmountTipConfEntity implements Serializable {

	private static final long serialVersionUID = 3034850201195697964L;
	private Long id; // 主键
	private Integer marketId; // 市场ID
	private Integer tipVal; // 提示值百分比放大1000倍
	private String sendStatus; // 状态1已发送0未发送
	private Date createTime; // 创建时间
	private String createUserId; // 创建人员ID
	private Date updateTime; // 更新时间
	private String updateUserId; // 更新人员ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public Integer getTipVal() {
		return tipVal;
	}

	public void setTipVal(Integer tipVal) {
		this.tipVal = tipVal;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

}
