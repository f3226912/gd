package com.gudeng.commerce.gd.pay.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "order_baseinfo")
public class OrderBaseinfoEntity implements java.io.Serializable {

	private static final long serialVersionUID = 2650982844388105897L;

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

	private Double payAmount;

	private String payType;
	
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

	private Integer marketId;

	private Date createTime;

	private String createUserId;

	private Date updateTime;

	private String updateUserId;
	
	private String upPayFlag;
	
	private Date examineTime;
	
	private Long subRuleId; // 补贴限制规则ID

	@Id
	@Column(name = "persaleId")
	public Integer getPersaleId() {

		return this.persaleId;
	}

	public void setPersaleId(Integer persaleId) {

		this.persaleId = persaleId;
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
	
	
}
