package com.gudeng.commerce.gd.order.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "pre_weighCar_detail")
public class PreWeighCarDetailEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1795752650678886240L;

	private Long pwdId;

	private Long memberId;
	
	private Long weighCarId;
	
	private Long productId;

	private String productName;

	private Long cateId;
	
	private Integer purQuantity;

	private Double price;

	private Double tradingPrice;

	private Double needToPayAmount;

	private Date createTime;

	private String createUserId;

	private Date updateTime;

	private String updateUserId;

	private Double weigh;
	
	private String type;

	private String unit;
	
	private Double marginWeigh;
	
	private Double subMoney; //补贴金额
	
	private String paymentStatus; //结算状态:1.未补贴,2.已补贴
	
	@Column(name="subMoney")
	public Double getSubMoney() {
		return subMoney;
	}

	public void setSubMoney(Double subMoney) {
		this.subMoney = subMoney;
	}
	@Column(name="paymentStatus")
	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Column(name="marginWeigh")
	public Double getMarginWeigh() {
		return marginWeigh;
	}

	public void setMarginWeigh(Double marginWeigh) {
		this.marginWeigh = marginWeigh;
	}

	@Id
	@Column(name = "pwdId")
	public Long getPwdId() {

		return this.pwdId;
	}

	public void setPwdId(Long pwdId) {

		this.pwdId = pwdId;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "weighCarId")
	public Long getWeighCarId() {

		return this.weighCarId;
	}

	public void setWeighCarId(Long weighCarId) {

		this.weighCarId = weighCarId;
	}

	@Column(name = "productId")
	public Long getProductId() {

		return this.productId;
	}

	public void setProductId(Long productId) {

		this.productId = productId;
	}
	@Column(name = "cateId")
	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	@Column(name = "productName")
	public String getProductName() {

		return this.productName;
	}

	public void setProductName(String productName) {

		this.productName = productName;
	}

	@Column(name = "purQuantity")
	public Integer getPurQuantity() {

		return this.purQuantity;
	}

	public void setPurQuantity(Integer purQuantity) {

		this.purQuantity = purQuantity;
	}

	@Column(name = "price")
	public Double getPrice() {

		return this.price;
	}

	public void setPrice(Double price) {

		this.price = price;
	}

	@Column(name = "tradingPrice")
	public Double getTradingPrice() {

		return this.tradingPrice;
	}

	public void setTradingPrice(Double tradingPrice) {

		this.tradingPrice = tradingPrice;
	}

	@Column(name = "needToPayAmount")
	public Double getNeedToPayAmount() {

		return this.needToPayAmount;
	}

	public void setNeedToPayAmount(Double needToPayAmount) {

		this.needToPayAmount = needToPayAmount;
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

	@Column(name = "weigh")
	public Double getWeigh() {

		return this.weigh;
	}

	public void setWeigh(Double weigh) {

		this.weigh = weigh;
	}

	@Column(name = "unit")
	public String getUnit() {

		return this.unit;
	}

	public void setUnit(String unit) {

		this.unit = unit;
	}
	@Column(name = "memberId")
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
}
