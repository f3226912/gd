package com.gudeng.commerce.gd.customer.dto;

import java.util.List;

import com.gudeng.commerce.gd.customer.entity.NstSameCityAddressEntity;
/**
 * 农速通同城货源发布DTO
 * @author xiaojun
 */
public class NstSameCityAddressDTO extends NstSameCityAddressEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8864592697035571511L;
	/**
	 * 日分配上限
	 */
	private Integer dayAssignMax;
	/**
	 * 月分配上限
	 */
	private Integer monthAssignMax;
	/**
	 * 日总数
	 */
	private Integer dayCount;
	/**
	 * 月总数
	 */
	private Integer monthCount;
	/**
	 * 客户端传值判断是否进行按理我最近排序
	 */
	private String closest;
	/**
	 * 客户端纬度(查理我最近)
	 */
	private Double clat;
	/**
	 * 客户端经度(查理我最近)
	 */
	private Double clng;
	/**
	 * 常用城市
	 */
	private String mcity;
	/**
	 * 距离发货时间差
	 */
	private String timeDiffString;
	/**
	 * 同城线路id
	 */
	private String sameCitycarLineId;
	
	/**
	 * 计算所得距离
	 */
	private String distance;
	/**
	 * 发货地城市+区域名称
	 */
	private String s_name;
	/**
	 * 收货地城市+区域名称
	 */
	private String f_name;
	/**
	 * 货物类型中午
	 */
	private String goodsTypeString;
	/**
	 * 车辆类型
	 */
	private String needCarTypeString;
	/**
	 * 货主联系人
	 */
	private String linkMan;
	/**
	 * 货主联系电话
	 */
	private String mobile;
	
	/**
	 * 用户类型
	 */
	private String userType;
	
	/**
	 *企业名称
	 */
	private String companyName;
	
	/**
	 *企业电话
	 */
	private String companyMobile;
	
	/**
	 * 总重+单位
	 */
	private String totalWeightString;
	
	/**
	 * 我要找货中，标示 货源是否已经接单
	 */
	private String showGoodsStatus;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	
	/**
	 * 订单是否删除(0:未删除;1:已删除)
	 */
    private String isOrderDeleted;
	
	/**
	 * 判断是否显示已接单的查询  如果值为大写  Y 则表示显示 
	 */
	private String showAlready;
	/**
	 * 头像图片
	 */
	private String andupurl;
	
	/**
	 * 农速通认证状态
	 */
	private String isCertify;
	
	/**
	 * 推送信息，线路字表cl_id的集合
	 */
	private List<Long> carLineIds;
	
	
    private String s_city_area;//始发地 城市+区
	
	private String e_city_area;//目的地 城市+区
	
	private String realName;//真实姓名
	
	/**
	 * 前端 判断 接单按钮是否置灰
	 */
	private String canMakeOrder;
	/**
	 * 当前登录人memberId
	 */
	private String currentMemberId;
	
	public String getCurrentMemberId() {
		return currentMemberId;
	}
	public void setCurrentMemberId(String currentMemberId) {
		this.currentMemberId = currentMemberId;
	}
	public String getCanMakeOrder() {
		return canMakeOrder;
	}
	public void setCanMakeOrder(String canMakeOrder) {
		this.canMakeOrder = canMakeOrder;
	}
	public String getIsCertify() {
		return isCertify;
	}
	public void setIsCertify(String isCertify) {
		this.isCertify = isCertify;
	}
	
	private String carLength;
	

	public String getAndupurl() {
		return andupurl;
	}
	public void setAndupurl(String andupurl) {
		this.andupurl = andupurl;
	}
	public String getShowAlready() {
		return showAlready;
	}
	public void setShowAlready(String showAlready) {
		this.showAlready = showAlready;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getShowGoodsStatus() {
		return showGoodsStatus;
	}
	public void setShowGoodsStatus(String showGoodsStatus) {
		this.showGoodsStatus = showGoodsStatus;
	}
	public String getTotalWeightString() {
		return totalWeightString;
	}
	public void setTotalWeightString(String totalWeightString) {
		this.totalWeightString = totalWeightString;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getNeedCarTypeString() {
		return needCarTypeString;
	}
	public void setNeedCarTypeString(String needCarTypeString) {
		this.needCarTypeString = needCarTypeString;
	}
	public String getGoodsTypeString() {
		return goodsTypeString;
	}
	public void setGoodsTypeString(String goodsTypeString) {
		this.goodsTypeString = goodsTypeString;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getSameCitycarLineId() {
		return sameCitycarLineId;
	}
	public void setSameCitycarLineId(String sameCitycarLineId) {
		this.sameCitycarLineId = sameCitycarLineId;
	}
	public String getTimeDiffString() {
		return timeDiffString;
	}
	public void setTimeDiffString(String timeDiffString) {
		this.timeDiffString = timeDiffString;
	}
	public String getMcity() {
		return mcity;
	}
	public void setMcity(String mcity) {
		this.mcity = mcity;
	}
	public Double getClat() {
		return clat;
	}
	public void setClat(Double clat) {
		this.clat = clat;
	}
	public Double getClng() {
		return clng;
	}
	public void setClng(Double clng) {
		this.clng = clng;
	}
	public String getClosest() {
		return closest;
	}
	public void setClosest(String closest) {
		this.closest = closest;
	}
	public Integer getDayAssignMax() {
		return dayAssignMax;
	}
	public void setDayAssignMax(Integer dayAssignMax) {
		this.dayAssignMax = dayAssignMax;
	}
	public Integer getMonthAssignMax() {
		return monthAssignMax;
	}
	public void setMonthAssignMax(Integer monthAssignMax) {
		this.monthAssignMax = monthAssignMax;
	}
	public Integer getDayCount() {
		return dayCount;
	}
	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}
	public Integer getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(Integer monthCount) {
		this.monthCount = monthCount;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyMobile() {
		return companyMobile;
	}
	public void setCompanyMobile(String companyMobile) {
		this.companyMobile = companyMobile;
	}
	public String getIsOrderDeleted() {
		return isOrderDeleted;
	}
	public void setIsOrderDeleted(String isOrderDeleted) {
		this.isOrderDeleted = isOrderDeleted;
	}
	public String getCarLength() {
		return carLength;
	}
	public void setCarLength(String carLength) {
		this.carLength = carLength;
	}
	public List<Long> getCarLineIds() {
		return carLineIds;
	}
	public void setCarLineIds(List<Long> carLineIds) {
		this.carLineIds = carLineIds;
	}
	public String getS_city_area() {
		return s_city_area;
	}
	public void setS_city_area(String s_city_area) {
		this.s_city_area = s_city_area;
	}
	public String getE_city_area() {
		return e_city_area;
	}
	public void setE_city_area(String e_city_area) {
		this.e_city_area = e_city_area;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	 
	
}
