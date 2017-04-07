package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 干线物流DTO
 * @author xiaojun
 *
 */
public class TrunkAddressDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 干线物流id
	 */
	private Long id;
	/**
	 * 干线货源memberId
	 */
	private Long memberId;
	/**
	 * 联系人姓名
	 */
	private String linkMan;
	/**
	 * 联系人电话
	 */
	private String mobile;
	/**
	 * 当前登录人与发货地距离
	 */
	private Double distance;
	/**
	 * 发货地省份id
	 */
	private Long s_provinceId;
	/**
	 * 发货地城市id	
	 */
	private Long s_cityId;
	/**
	 * 发货地区域id
	 */
	private Long s_areaId;
	/**
	 * 城市+区域（发货地）
	 */
	private String s_trunckAddress;
	
	/**
	 * 收货地省份id
	 */
	private Long f_provinceId;
	/**
	 * 收货地城市id
	 */
	private Long f_cityId;
	/**
	 * 收货地区域id
	 */
	private Long f_areaId;
	/**
	 * 城市+区域（收货地）
	 */
	private String f_trunckAddress;
	/**
	 * 货物类型
	 */
	private Integer goodsType;
	/**
	 * 货物类型String
	 */
	private String goodsTypeString;
	/**
	 * 车辆类型
	 */
	private Integer carType;
	/**
	 * 车辆类型String
	 */
	private String carTypeString;
	/**
	 * 车长
	 */
	private Double carLength;
	/**
	 * 货物总重
	 */
	private Double totalWeight;
	/**
	 * 总体积
	 */
	private Integer totalSize;
	/**
	 * 发运方式(0:零担;1:整车;2:其他)
	 */
	private Integer sendGoodsType;
	/**
	 * 发运方式 字符串
	 */
	private String sendGoodsTypeString;
	/**
	 * 发货时间
	 */
	private String sendDate;
	/**
	 * 发车时间类别(0:上午,1:中午,2:下午,3:晚上)',
	 */
	private Integer sendDateType;
	/**
	 * 发车时间类别字符串
	 */
	private String sendDateTypeString;
	/**
	 * 图片
	 */
	private String andupurl;
	/**
	 * 是否认证
	 */
	private String isCertify;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人 
	 */
	private Long createUserId;
	/**
	 * 当前客户端纬度
	 */
	private Double clat;
	/**
	 * 当前客户端经度
	 */
	private Double clng;
	
	/**
	 * 距离发货时间差
	 */
	private String timeDiffString;
	/**
	 * 查询最小车长
	 */
	private Double minCarLength;
	/**
	 * 查询最大车长
	 */
	private Double maxCarLength;
	/**
	 * 查询最小总重
	 */
	private Double minTotalWeight;
	/**
	 * 查询最大总重
	 */
	private Double maxTotalWeight;
	/**
	 * 查询最小体积
	 */
	private Integer minTotalSize;
	/**
	 * 查询最大体积
	 */
	private Integer maxTotalSize;
	/**
	 * 根据地址查询时,默认的从app端传过来的城市
	 */
	private String locationCity; 
	/**
	 * 会员类型 1 个人  2 企业
	 */ 
	private String userType;
	/**
	 * '0,直发 1,代发'
	 */
	private String nstRule;
	
	public String getNstRule() {
		return nstRule;
	}
	public void setNstRule(String nstRule) {
		this.nstRule = nstRule;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getS_provinceId() {
		return s_provinceId;
	}
	public void setS_provinceId(Long s_provinceId) {
		this.s_provinceId = s_provinceId;
	}
	public Long getS_cityId() {
		return s_cityId;
	}
	public void setS_cityId(Long s_cityId) {
		this.s_cityId = s_cityId;
	}
	public Long getS_areaId() {
		return s_areaId;
	}
	public void setS_areaId(Long s_areaId) {
		this.s_areaId = s_areaId;
	}
	public Long getF_provinceId() {
		return f_provinceId;
	}
	public void setF_provinceId(Long f_provinceId) {
		this.f_provinceId = f_provinceId;
	}
	public Long getF_cityId() {
		return f_cityId;
	}
	public void setF_cityId(Long f_cityId) {
		this.f_cityId = f_cityId;
	}
	public Long getF_areaId() {
		return f_areaId;
	}
	public void setF_areaId(Long f_areaId) {
		this.f_areaId = f_areaId;
	}
	public Integer getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	public Integer getCarType() {
		return carType;
	}
	public void setCarType(Integer carType) {
		this.carType = carType;
	}
	public Double getCarLength() {
		return carLength;
	}
	public void setCarLength(Double carLength) {
		this.carLength = carLength;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Integer getSendGoodsType() {
		return sendGoodsType;
	}
	public void setSendGoodsType(Integer sendGoodsType) {
		this.sendGoodsType = sendGoodsType;
	}
	public String getAndupurl() {
		return andupurl;
	}
	public void setAndupurl(String andupurl) {
		this.andupurl = andupurl;
	}
	public String getIsCertify() {
		return isCertify;
	}
	public void setIsCertify(String isCertify) {
		this.isCertify = isCertify;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
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
	public String getGoodsTypeString() {
		return goodsTypeString;
	}
	public void setGoodsTypeString(String goodsTypeString) {
		this.goodsTypeString = goodsTypeString;
	}
	public String getCarTypeString() {
		return carTypeString;
	}
	public void setCarTypeString(String carTypeString) {
		this.carTypeString = carTypeString;
	}
	public String getTimeDiffString() {
		return timeDiffString;
	}
	public void setTimeDiffString(String timeDiffString) {
		this.timeDiffString = timeDiffString;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getS_trunckAddress() {
		return s_trunckAddress;
	}
	public void setS_trunckAddress(String s_trunckAddress) {
		this.s_trunckAddress = s_trunckAddress;
	}
	public String getF_trunckAddress() {
		return f_trunckAddress;
	}
	public void setF_trunckAddress(String f_trunckAddress) {
		this.f_trunckAddress = f_trunckAddress;
	}
	public Integer getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}
	public Double getMinCarLength() {
		return minCarLength;
	}
	public void setMinCarLength(Double minCarLength) {
		this.minCarLength = minCarLength;
	}
	public Double getMaxCarLength() {
		return maxCarLength;
	}
	public void setMaxCarLength(Double maxCarLength) {
		this.maxCarLength = maxCarLength;
	}
	public Double getMinTotalWeight() {
		return minTotalWeight;
	}
	public void setMinTotalWeight(Double minTotalWeight) {
		this.minTotalWeight = minTotalWeight;
	}
	public Double getMaxTotalWeight() {
		return maxTotalWeight;
	}
	public void setMaxTotalWeight(Double maxTotalWeight) {
		this.maxTotalWeight = maxTotalWeight;
	}
	public Integer getMinTotalSize() {
		return minTotalSize;
	}
	public void setMinTotalSize(Integer minTotalSize) {
		this.minTotalSize = minTotalSize;
	}
	public Integer getMaxTotalSize() {
		return maxTotalSize;
	}
	public void setMaxTotalSize(Integer maxTotalSize) {
		this.maxTotalSize = maxTotalSize;
	}
	public String getLocationCity() {
		return locationCity;
	}
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}
	public String getSendGoodsTypeString() {
		return sendGoodsTypeString;
	}
	public void setSendGoodsTypeString(String sendGoodsTypeString) {
		this.sendGoodsTypeString = sendGoodsTypeString;
	}
	public Integer getSendDateType() {
		return sendDateType;
	}
	public void setSendDateType(Integer sendDateType) {
		this.sendDateType = sendDateType;
	}
	public String getSendDateTypeString() {
		return sendDateTypeString;
	}
	public void setSendDateTypeString(String sendDateTypeString) {
		this.sendDateTypeString = sendDateTypeString;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
}
