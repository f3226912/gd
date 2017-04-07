package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
/**
 * 农速通订单统计页面DTO
 * @author xiaojun
 */
public class NstOrderCountDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 农速通运单，自增主键id
	 */
	private Long id;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 发货地（全部）
	 */
	private String f_address_detail;
	/**
	 * 收货地（全部）
	 */
	private String s_address_detail;
	/**
	 * 省份ID(发)
	 */
	private String s_provinceId;
	/**
	 * 城市ID(发)
	 */
	private String s_cityId;
	/**
	 * 三级地址ID(发)
	 */
	private String s_areaId;
	/**
	 * 省份ID(收)
	 */
	private String f_provinceId;
	/**
	 * 城市ID(收)
	 */
	private String f_cityId;
	/**
	 * 三级地址ID(收)
	 */
	private String f_areaId;
	/**
	 * 1.待成交，2.已成交，3.已完成,4.未完成,5.取消接单
	 */
	private Integer orderStatus;
	/**
	 * 1. 货源运单（根据货源接单）2.线路接单（根据线路接单）
	 */
	private String orderType;
	/**
	 * 货物类型(0:常温货；1：冷藏；2：鲜活水产；3：其他)
	 */
	private Integer goodsType;
	/**
	 * 总重量
	 */
	private Double totalWeight;
	/**
	 * 重量单位(0:吨,1公斤)
	 */
	private Long hundredweight;
	/**
	 * 总体积
	 */
	private Long totalSize;
	/**
	 * 车牌号码
	 */
	private String carNumber;
	/**
	 * 订单接单时间
	 */
	private String orderTime;
	/**
	 * 订单接单开始时间
	 */
	private String orderBeginTime;
	/**
	 * 订单接单结束时间
	 */
	private String orderEndTime;
	/**
	 * 订单确认时间
	 */
	private String order_confirmTime;
	/**
	 * 订单确认开始时间
	 */
	private String order_confirmBeginTime;
	/**
	 * 订单确认结束时间
	 */
	private String order_confirmEndTime;
	/**
	 * 订单完成时间
	 */
	private String order_completeTime;
	/**
	 * 订单完成开始时间
	 */
	private String order_completeBeginTime;
	/**
	 * 订单完成结束时间
	 */
	private String order_completeEndTime;
	/**
	 * 接单者电话
	 */
	private String receiveMobile;
	/**
	 * 接单者姓名
	 */
	private String receiveName;
	/**
	 * 发布者电话
	 */
	private String releaseMobile;
	/**
	 * 发布者姓名
	 */
	private String releaseName;
	/**
	 * 发布者类型
	 */
	private String releaseUserType;
	/**
	 * 发布者推荐人电话
	 */
	private String releaseMobile_ed;
	/**
	 * 发布者推荐人姓名
	 */
	private String releaseName_ed;
	/**
	 * 发布者推荐人所属区域
	 */
	private String releaseAreaName;
	/**
	 * 接单者推荐人电话
	 */
	private String receiveMobile_ed;
	/**
	 * 接单者推荐人姓名
	 */
	private String receiveName_ed;
	/**
	 * 接单者推荐人所属区域
	 */
	private String receiveAreaName;
	
	
	/**
	 * 接单者认证状态
	 */
	private String receiveNstStatus;
	
	/**
	 * 发布人认证状态
	 */
	private String releaseNstStatus;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getS_provinceId() {
		return s_provinceId;
	}

	public void setS_provinceId(String s_provinceId) {
		this.s_provinceId = s_provinceId;
	}

	public String getS_cityId() {
		return s_cityId;
	}

	public void setS_cityId(String s_cityId) {
		this.s_cityId = s_cityId;
	}

	public String getS_areaId() {
		return s_areaId;
	}

	public void setS_areaId(String s_areaId) {
		this.s_areaId = s_areaId;
	}

	public String getF_provinceId() {
		return f_provinceId;
	}

	public void setF_provinceId(String f_provinceId) {
		this.f_provinceId = f_provinceId;
	}

	public String getF_cityId() {
		return f_cityId;
	}

	public void setF_cityId(String f_cityId) {
		this.f_cityId = f_cityId;
	}

	public String getF_areaId() {
		return f_areaId;
	}

	public void setF_areaId(String f_areaId) {
		this.f_areaId = f_areaId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Long getHundredweight() {
		return hundredweight;
	}

	public void setHundredweight(Long hundredweight) {
		this.hundredweight = hundredweight;
	}

	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderBeginTime() {
		return orderBeginTime;
	}

	public void setOrderBeginTime(String orderBeginTime) {
		this.orderBeginTime = orderBeginTime;
	}

	public String getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(String orderEndTime) {
		this.orderEndTime = orderEndTime;
	}

	public String getOrder_confirmTime() {
		return order_confirmTime;
	}

	public void setOrder_confirmTime(String order_confirmTime) {
		this.order_confirmTime = order_confirmTime;
	}

	public String getOrder_confirmBeginTime() {
		return order_confirmBeginTime;
	}

	public void setOrder_confirmBeginTime(String order_confirmBeginTime) {
		this.order_confirmBeginTime = order_confirmBeginTime;
	}

	public String getOrder_confirmEndTime() {
		return order_confirmEndTime;
	}

	public void setOrder_confirmEndTime(String order_confirmEndTime) {
		this.order_confirmEndTime = order_confirmEndTime;
	}

	public String getOrder_completeTime() {
		return order_completeTime;
	}

	public void setOrder_completeTime(String order_completeTime) {
		this.order_completeTime = order_completeTime;
	}

	public String getOrder_completeBeginTime() {
		return order_completeBeginTime;
	}

	public void setOrder_completeBeginTime(String order_completeBeginTime) {
		this.order_completeBeginTime = order_completeBeginTime;
	}

	public String getOrder_completeEndTime() {
		return order_completeEndTime;
	}

	public void setOrder_completeEndTime(String order_completeEndTime) {
		this.order_completeEndTime = order_completeEndTime;
	}

	public String getReceiveMobile() {
		return receiveMobile;
	}

	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReleaseMobile() {
		return releaseMobile;
	}

	public void setReleaseMobile(String releaseMobile) {
		this.releaseMobile = releaseMobile;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public String getReleaseUserType() {
		return releaseUserType;
	}

	public void setReleaseUserType(String releaseUserType) {
		this.releaseUserType = releaseUserType;
	}

	public String getReleaseMobile_ed() {
		return releaseMobile_ed;
	}

	public void setReleaseMobile_ed(String releaseMobile_ed) {
		this.releaseMobile_ed = releaseMobile_ed;
	}

	public String getReleaseName_ed() {
		return releaseName_ed;
	}

	public void setReleaseName_ed(String releaseName_ed) {
		this.releaseName_ed = releaseName_ed;
	}

	public String getReleaseAreaName() {
		return releaseAreaName;
	}

	public void setReleaseAreaName(String releaseAreaName) {
		this.releaseAreaName = releaseAreaName;
	}

	public String getReceiveMobile_ed() {
		return receiveMobile_ed;
	}

	public void setReceiveMobile_ed(String receiveMobile_ed) {
		this.receiveMobile_ed = receiveMobile_ed;
	}

	public String getReceiveName_ed() {
		return receiveName_ed;
	}

	public void setReceiveName_ed(String receiveName_ed) {
		this.receiveName_ed = receiveName_ed;
	}

	public String getReceiveAreaName() {
		return receiveAreaName;
	}

	public void setReceiveAreaName(String receiveAreaName) {
		this.receiveAreaName = receiveAreaName;
	}

	public String getF_address_detail() {
		return f_address_detail;
	}

	public void setF_address_detail(String f_address_detail) {
		this.f_address_detail = f_address_detail;
	}

	public String getS_address_detail() {
		return s_address_detail;
	}

	public void setS_address_detail(String s_address_detail) {
		this.s_address_detail = s_address_detail;
	}

	public String getReceiveNstStatus() {
		return receiveNstStatus;
	}

	public void setReceiveNstStatus(String receiveNstStatus) {
		this.receiveNstStatus = receiveNstStatus;
	}

	public String getReleaseNstStatus() {
		return releaseNstStatus;
	}

	public void setReleaseNstStatus(String releaseNstStatus) {
		this.releaseNstStatus = releaseNstStatus;
	}
	
	
	
}
