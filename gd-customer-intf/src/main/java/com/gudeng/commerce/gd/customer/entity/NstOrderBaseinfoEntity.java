package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 农速通运单详情表
 * 
 * @author xiaojun
 */
@Entity(name = "nst_order_baseinfo")
public class NstOrderBaseinfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 农速通运单，自增主键id
	 */
	private Long id;
	/**
	 * 运单号
	 */
	private String orderNo;
	/**
	 * 货源类型(0干线，1同城)
	 */
	private Byte sourceType;
	/**
	 * 1. 货源运单（根据货源接单）2.线路接单（根据线路接单）
	 */
	private String orderType;
	/**
	 * 发货地（全部）
	 */
	private String f_address_detail;
	/**
	 * 收货地（全部）
	 */
	private String s_address_detail;
	/**
	 * 货源id
	 */
	private Long memberAddressId;
	/**
	 * 同城货源id
	 */
	private Long same_memberAddressId;
	/**
	 * 线路id
	 */
	private Long carLineId;
	/**
	 * 同城线路id
	 */
	private Long same_carLineId;
		
	/**
	 * 接单人员Id（当前登录人memberId）
	 */
	private Long memberId;
	/**
	 * 车辆ID
	 */
	private Long carId;
	/**
	 * 发货人姓名
	 */
	private String shipperName;
	/**
	 * 发货人手机号码
	 */
	private String shipperMobile;
	/**
	 * 车主姓名
	 */
	private String ownerName;
	/**
	 * 车主手机号码
	 */
	private String ownerMobile;
	/**
	 * 运单接单时间
	 */
	private Date orderTime;
	/**
	 * 1.待成交，2.已成交，3.未完成，4.已完成，5.取消订单
	 */
	private Integer orderStatus;
	
	/**
	 * 订单确认memberId
	 */
	private Long confirmMemberId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人员id
	 */
	private String createUserId;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 修改人员id
	 */
	private String updateUserId;
	
	/**
	 * 是否删除(0 和 null:未删除;1:已删除)
	 */
	private String isDeleted;
	

	@Id
	@Column(name = "id")
	public Long getId() {

		return this.id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	@Column(name = "orderNo")
	public String getOrderNo() {

		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {

		this.orderNo = orderNo;
	}
	@Column(name = "sourceType")
	public Byte getSourceType() {
		return sourceType;
	}

	public void setSourceType(Byte sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "orderType")
	public String getOrderType() {

		return this.orderType;
	}

	public void setOrderType(String orderType) {

		this.orderType = orderType;
	}

	@Column(name = "f_address_detail")
	public String getF_address_detail() {

		return this.f_address_detail;
	}

	public void setF_address_detail(String f_address_detail) {

		this.f_address_detail = f_address_detail;
	}

	@Column(name = "s_address_detail")
	public String getS_address_detail() {

		return this.s_address_detail;
	}

	public void setS_address_detail(String s_address_detail) {

		this.s_address_detail = s_address_detail;
	}

	@Column(name = "memberAddressId")
	public Long getMemberAddressId() {

		return this.memberAddressId;
	}

	public void setMemberAddressId(Long memberAddressId) {

		this.memberAddressId = memberAddressId;
	}
	@Column(name = "same_memberAddressId")
	public Long getSame_memberAddressId() {
		return same_memberAddressId;
	}

	public void setSame_memberAddressId(Long same_memberAddressId) {
		this.same_memberAddressId = same_memberAddressId;
	}

	@Column(name = "carLineId")
	public Long getCarLineId() {

		return this.carLineId;
	}

	public void setCarLineId(Long carLineId) {

		this.carLineId = carLineId;
	}
	
	@Column(name = "same_carLineId")
	public Long getSame_carLineId() {
		return same_carLineId;
	}

	public void setSame_carLineId(Long same_carLineId) {
		this.same_carLineId = same_carLineId;
	}

	@Column(name = "memberId")
	public Long getMemberId() {

		return this.memberId;
	}

	public void setMemberId(Long memberId) {

		this.memberId = memberId;
	}
	@Column(name = "carId")
	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	@Column(name = "orderTime")
	public Date getOrderTime() {

		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {

		this.orderTime = orderTime;
	}

	@Column(name = "orderStatus")
	public Integer getOrderStatus() {

		return this.orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {

		this.orderStatus = orderStatus;
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
	@Column(name = "confirmMemberId")
	public Long getConfirmMemberId() {
		return confirmMemberId;
	}

	public void setConfirmMemberId(Long confirmMemberId) {
		this.confirmMemberId = confirmMemberId;
	}
	@Column(name = "shipperName")
	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	@Column(name = "shipperMobile")
	public String getShipperMobile() {
		return shipperMobile;
	}

	public void setShipperMobile(String shipperMobile) {
		this.shipperMobile = shipperMobile;
	}
	@Column(name = "ownerName")
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	@Column(name = "ownerMobile")
	public String getOwnerMobile() {
		return ownerMobile;
	}

	public void setOwnerMobile(String ownerMobile) {
		this.ownerMobile = ownerMobile;
	}
	@Column(name = "isDeleted")
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
}
