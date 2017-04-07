package com.gudeng.commerce.gd.order.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "order_baseinfo")
public class OrderBaseinfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4209150095749978829L;

	private Integer persaleId;

	private Long orderNo;

	private String orderSource;

	private String channel;

	private String orderType;

	private Double orderAmount;

	private Double discountAmount;

	private Double subAmount; // 买家补贴额
	
	private Double sellSubAmount; // 卖家补贴额
	
	private Double suppSubAmount; // 供应商补贴额

	private Double payAmount; // 实收金额

	private String payType; // 支付方式
	
	private String paySubType; // 支付子类型
	
	private String orderStatus; // 订单状态
	
	@Deprecated
	private String subStatus; // 补贴状态，暂时没用
	
	private String examineStatus; //审核状态
	
	private String buySubStatus; // 买家补贴状态
	
	private String sellSubStatus; // 卖家补贴状态
	
	private String suppSubStatus; // 供应商补贴状态
	
	private String outmarkStatus; // 出场状态 0：未出场 1：已出场

	private Integer memberId; // 买家ID
	
	private Integer sellMemberId; // 卖家ID
	
	private Integer suppMemberId; // 供应商ID
	
	private Date orderTime;
	
	private String shopName;

	private Integer businessId;

	private Integer marketId;	// 此处后台查询时 农批商采购订单 查询的是买家市场 农商友采购订单 卖家市场

	private Date createTime;

	private String createUserId;

	private Date updateTime;

	private String updateUserId;
	
	private String upPayFlag;
	
	private Date examineTime;
	
	private Long subRuleId; // 补贴限制规则ID
	
	private Date closeTime;  //关闭时间
	
	private Date deliverTime;  //发货时间

	private String closeUserId; //关闭人
	
	private Integer isLock; //是否加锁
	
	private Date lockTime;  //加锁时间
	
	private String message;//买家留言
	
	/** 活动类型:0:未参加活动 1:参加活动 */
	private String promType;
	
	private String distributeMode;//配送方式
	
	/**
	 * 是否为补充订单 0不是 1是
	 */
	private String hasCustomer;
	
	/** 总共支付金额 */
	private Double totalPayAmt;
	
	/** 取消原因 */
	private String cancelReason;
	
	/** 关联Pos信息 */
	private String validPosNum;
	
	/**尾款*/
	private Double restAmt;
	
	/**订单重量*/
	private Double orderWeight;
	
	/**活动类型1无活动2现场采销*/
	private String activityType;
	/**活动获取积分*/
	private Integer activityIntegral;
	
	@Column(name="orderWeight")
	public Double getOrderWeight() {
		return orderWeight;
	}

	public void setOrderWeight(Double orderWeight) {
		this.orderWeight = orderWeight;
	}

	@Column(name="restAmt")
	public Double getRestAmt() {
		return restAmt;
	}

	public void setRestAmt(Double restAmt) {
		this.restAmt = restAmt;
	}

	@Column(name="hasCustomer")
	public String getHasCustomer() {
		return hasCustomer;
	}

	public void setHasCustomer(String hasCustomer) {
		this.hasCustomer = hasCustomer;
	}

	@Column(name="distributeMode")
	public String getDistributeMode() {
		return distributeMode;
	}

	public void setDistributeMode(String distributeMode) {
		this.distributeMode = distributeMode;
	}
	
	@Id
	@Column(name = "persaleId")
	public Integer getPersaleId() {

		return this.persaleId;
	}

	public void setPersaleId(Integer persaleId) {

		this.persaleId = persaleId;
	}
	
	@Column(name = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name="outmarkStatus")
	public String getOutmarkStatus() {
		return outmarkStatus;
	}

	public void setOutmarkStatus(String outmarkStatus) {
		this.outmarkStatus = outmarkStatus;
	}

	@Column(name = "orderNo")
	public Long getOrderNo() {

		return this.orderNo;
	}

	public void setOrderNo(Long orderNo) {

		this.orderNo = orderNo;
	}

	@Column(name = "orderSource")
	public String getOrderSource() {

		return this.orderSource;
	}

	public void setOrderSource(String orderSource) {

		this.orderSource = orderSource;
	}

	@Column(name = "channel")
	public String getChannel() {

		return this.channel;
	}

	public void setChannel(String channel) {

		this.channel = channel;
	}

	@Column(name = "orderType")
	public String getOrderType() {

		return this.orderType;
	}

	public void setOrderType(String orderType) {

		this.orderType = orderType;
	}

	@Column(name = "orderAmount")
	public Double getOrderAmount() {

		return this.orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {

		this.orderAmount = orderAmount;
	}

	@Column(name = "discountAmount")
	public Double getDiscountAmount() {

		return this.discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {

		this.discountAmount = discountAmount;
	}

	@Column(name = "subAmount")
	public Double getSubAmount() {

		return this.subAmount;
	}

	public void setSubAmount(Double subAmount) {

		this.subAmount = subAmount;
	}

	@Column(name = "payAmount")
	public Double getPayAmount() {

		return this.payAmount;
	}

	public void setPayAmount(Double payAmount) {

		this.payAmount = payAmount;
	}

	@Column(name = "orderStatus")
	public String getOrderStatus() {

		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {

		this.orderStatus = orderStatus;
	}

	@Column(name = "memberId")
	public Integer getMemberId() {

		return this.memberId;
	}

	public void setMemberId(Integer memberId) {

		this.memberId = memberId;
	}

	@Column(name = "orderTime")
	public Date getOrderTime() {

		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {

		this.orderTime = orderTime;
	}

	@Column(name = "shopName")
	public String getShopName() {

		return this.shopName;
	}

	public void setShopName(String shopName) {

		this.shopName = shopName;
	}

	@Column(name = "businessId")
	public Integer getBusinessId() {

		return this.businessId;
	}

	public void setBusinessId(Integer businessId) {

		this.businessId = businessId;
	}

	@Column(name = "marketId")
	public Integer getMarketId() {

		return this.marketId;
	}

	public void setMarketId(Integer marketId) {

		this.marketId = marketId;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}

	@Column(name = "payType")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "paySubType")
	public String getPaySubType() {
		return paySubType;
	}

	public void setPaySubType(String paySubType) {
		this.paySubType = paySubType;
	}

	@Column(name = "sellSubAmount")
	public Double getSellSubAmount() {
		return sellSubAmount;
	}

	public void setSellSubAmount(Double sellSubAmount) {
		this.sellSubAmount = sellSubAmount;
	}

	@Column(name = "sellMemberId")
	public Integer getSellMemberId() {
		return sellMemberId;
	}

	public void setSellMemberId(Integer sellMemberId) {
		this.sellMemberId = sellMemberId;
	}

	@Deprecated
	@Column(name = "subStatus")
	public String getSubStatus() {
		return subStatus;
	}

	@Deprecated
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	@Column(name = "sellSubStatus")
	public String getSellSubStatus() {
		return sellSubStatus;
	}

	public void setSellSubStatus(String sellSubStatus) {
		this.sellSubStatus = sellSubStatus;
	}

	@Column(name = "suppSubAmount")
	public Double getSuppSubAmount() {
		return suppSubAmount;
	}

	public void setSuppSubAmount(Double suppSubAmount) {
		this.suppSubAmount = suppSubAmount;
	}

	@Column(name = "buySubStatus")
	public String getBuySubStatus() {
		return buySubStatus;
	}

	public void setBuySubStatus(String buySubStatus) {
		this.buySubStatus = buySubStatus;
	}

	@Column(name = "suppSubStatus")
	public String getSuppSubStatus() {
		return suppSubStatus;
	}

	public void setSuppSubStatus(String suppSubStatus) {
		this.suppSubStatus = suppSubStatus;
	}

	@Column(name = "suppMemberId")
	public Integer getSuppMemberId() {
		return suppMemberId;
	}

	public void setSuppMemberId(Integer suppMemberId) {
		this.suppMemberId = suppMemberId;
	}

	@Column(name = "examineStatus")
	public String getExamineStatus() {
		return examineStatus;
	}

	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
	}

	@Column(name = "upPayFlag")
	public String getUpPayFlag() {
		return upPayFlag;
	}

	public void setUpPayFlag(String upPayFlag) {
		this.upPayFlag = upPayFlag;
	}

	@Column(name = "examineTime")
	public Date getExamineTime() {
		return examineTime;
	}

	public void setExamineTime(Date examineTime) {
		this.examineTime = examineTime;
	}

	@Column(name = "subRuleId")
	public Long getSubRuleId() {
		return subRuleId;
	}

	public void setSubRuleId(Long subRuleId) {
		this.subRuleId = subRuleId;
	}

	@Column(name = "closeTime")
	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	@Column(name = "closeUserId")
	public String getCloseUserId() {
		return closeUserId;
	}

	public void setCloseUserId(String closeUserId) {
		this.closeUserId = closeUserId;
	}

	@Column(name = "isLock")
	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	@Column(name = "lockTime")
	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	@Column(name = "promType")
	public String getPromType() {
		return promType;
	}

	public void setPromType(String promType) {
		this.promType = promType;
	}

	@Column(name = "deliverTime")
	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	@Column(name="cancelReason")
	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	@Column(name="totalPayAmt")
	public Double getTotalPayAmt() {
		return totalPayAmt;
	}

	public void setTotalPayAmt(Double totalPayAmt) {
		this.totalPayAmt = totalPayAmt;
	}

	@Column(name="validPosNum")
	public String getValidPosNum() {
		return validPosNum;
	}

	public void setValidPosNum(String validPosNum) {
		this.validPosNum = validPosNum;
	}
	@Column(name = "activityType")
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	@Column(name = "activityIntegral")
	public Integer getActivityIntegral() {
		return activityIntegral;
	}

	public void setActivityIntegral(Integer activityIntegral) {
		this.activityIntegral = activityIntegral;
	}
	
}
