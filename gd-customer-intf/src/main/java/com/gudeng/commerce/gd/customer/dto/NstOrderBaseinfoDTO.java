package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.NstOrderBaseinfoEntity;

/**
 * 农速通运单详情表DTO
 * 
 * @author xiaojun
 * 
 */
public class NstOrderBaseinfoDTO extends NstOrderBaseinfoEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 货主id */
	private Long shipperId;

	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 货物体积
	 */
	private Long totalSize;
	/**
	 * 总重量
	 */
	private Long totalWeight;
	/**
	 * 重量单位(0:吨,1公斤)
	 */
	private Long hundredweight;
	/**
	 * 发运方式(0:零担;1:整车;2:其他)
	 */
	private String sendGoodsTypeString;

	/**
	 * 姓名（会员）
	 */
	private String name;
	/**
	 * 手机号码（会员）
	 */
	private String mobile;
	
	/**
	 * 商品id
	 */
	private Long productId;

	/**
	 * 开始时间
	 */
	private String beginTime;
	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 省份ID(发)
	 */
	private Long s_provinceId;
	/**
	 * 城市ID(发)
	 */
	private Long s_cityId;
	/**
	 * 三级地址ID(发)
	 */
	private Long s_areaId;

	/**
	 * 省份ID(收)
	 */
	private Long f_provinceId;
	/**
	 * 城市ID(收)
	 */
	private Long f_cityId;
	/**
	 * 三级地址ID(收)
	 */
	private Long f_areaId;

	/**
	 * 车主memberId
	 */
	private Long ownerMemberId;

	/**
	 * 车牌号码
	 */
	private String carNumber;

	/**
	 * nst_身份证号码
	 */
	private String nst_idCard;
	/**
	 * nst_身份证照片url
	 */
	private String nst_cardPhotoUrl;
	
	/**
	 * 订单状态总数
	 */
	private long submitCount;
	/**
	 * 农速通行驶证图片地址
	 */
	private String nst_vehiclePhotoUrl;
	/**
	 * 农速通驾驶证图片地址
	 */
	private String nst_driverPhotoUrl ;
	
	private Integer showStatus;
	
	// 评价
	private String evaluateType;

	// 投诉状态
	private String complaintStatus;
	
	// 投诉内容
	private String complaintsDetails;
	
	//投诉回复
	private String reply;
	
	/**
	 * 当前登录ID
	 */
	private Long currentMemberId;
	
	/**
	 * 显示状态字段
	 */
	private String state;
	
	/**
	 * 批量删除传入的orderNo组合String
	 */
	private String orderNoString;
	
	/**
	 * 可以进行删除的标志
	 */
	private String canDelete;
	
	/**
	 * 前端传showRejectAndCal=0  来展示取消和拒绝的单
	 */
	private Integer showRejectAndCal;
	/**
	 * 非农速通一手货源联系人
	 */
	private String contactName;

	/**
	 * 货物来源 1订单 2商品(非农速通货源来源类型)
	 */
	private Integer supplySourceType;

	/**
	 * 同城货源定位发货地址
	 */
	private String s_detail;
	/**
	 * 同城货源定位收货地址
	 */
	private String f_detail;
	
	/**
	 * 同城货源货物类型
	 */
	private String  goodsType;
	/**
	 * 同城货源货物重量
	 */
	private String sameTotalWeight;
	/**
	 * 同城货源定位发货填写详细地址
	 */
	private String s_detailed_address;
	/**
	 * 同城货源定位收货填写详细地址
	 */
	private String f_detailed_address;
	/**
	 * 同城货源货物体积
	 */
	private Integer sameTotalSize;
	/**
	 * 同城货源意向价格
	 */
	private Double price;
	/**
	 * 同城货源备注
	 */
	private String remark;
	
	/**
	 * 货物类型
	 */
	private String goodsTypeString;
	
	/**
	 * 发货地城市+区域
	 */
	private String s_name;
	/**
	 * 收货地城市+区域
	 */
	private String f_name;
	
	/**
	 * 订单时间String
	 */
	private String orderTimeString;
	
	/**
	 * 删除人员id
	 */
	private Long deleteMemberId;
	
	/**
	 * 发货经度
	 */
	private Double s_lng;
	/**
	 * 发货纬度
	 */
	private Double s_lat;
	/**
	 * 发货经度
	 */
	private Double f_lng;
	/**
	 * 发货纬度
	 */
	private Double f_lat;
	
	/**
	 * 车主实际发车时间
	 */
	private String startTime;
	
	/**
	 * 系统时间
	 */
	private String sysTime;
	
	/**
	 * 头像图片
	 */
	private String andupurl;
	/**
	 * 是否认证
	 */
	private String isCertify;
	
	public String getIsCertify() {
		return isCertify;
	}

	public void setIsCertify(String isCertify) {
		this.isCertify = isCertify;
	}

	public String getAndupurl() {
		return andupurl;
	}

	public void setAndupurl(String andupurl) {
		this.andupurl = andupurl;
	}

	public String getSysTime() {
		return sysTime;
	}

	public void setSysTime(String sysTime) {
		this.sysTime = sysTime;
	}

	public String getS_detailed_address() {
		return s_detailed_address;
	}

	public void setS_detailed_address(String s_detailed_address) {
		this.s_detailed_address = s_detailed_address;
	}

	public String getF_detailed_address() {
		return f_detailed_address;
	}

	public void setF_detailed_address(String f_detailed_address) {
		this.f_detailed_address = f_detailed_address;
	}

	public Integer getSupplySourceType() {
		return supplySourceType;
	}

	public void setSupplySourceType(Integer supplySourceType) {
		this.supplySourceType = supplySourceType;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Double getS_lng() {
		return s_lng;
	}

	public void setS_lng(Double s_lng) {
		this.s_lng = s_lng;
	}

	public Double getS_lat() {
		return s_lat;
	}

	public void setS_lat(Double s_lat) {
		this.s_lat = s_lat;
	}

	public Double getF_lng() {
		return f_lng;
	}

	public void setF_lng(Double f_lng) {
		this.f_lng = f_lng;
	}

	public Double getF_lat() {
		return f_lat;
	}

	public void setF_lat(Double f_lat) {
		this.f_lat = f_lat;
	}

	public Long getDeleteMemberId() {
		return deleteMemberId;
	}

	public void setDeleteMemberId(Long deleteMemberId) {
		this.deleteMemberId = deleteMemberId;
	}

	public String getOrderTimeString() {
		return orderTimeString;
	}

	public void setOrderTimeString(String orderTimeString) {
		this.orderTimeString = orderTimeString;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public String getGoodsTypeString() {
		return goodsTypeString;
	}

	public void setGoodsTypeString(String goodsTypeString) {
		this.goodsTypeString = goodsTypeString;
	}

	public String getS_detail() {
		return s_detail;
	}

	public void setS_detail(String s_detail) {
		this.s_detail = s_detail;
	}

	public String getF_detail() {
		return f_detail;
	}

	public void setF_detail(String f_detail) {
		this.f_detail = f_detail;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getSameTotalWeight() {
		return sameTotalWeight;
	}

	public void setSameTotalWeight(String sameTotalWeight) {
		this.sameTotalWeight = sameTotalWeight;
	}

	public Integer getSameTotalSize() {
		return sameTotalSize;
	}

	public void setSameTotalSize(Integer sameTotalSize) {
		this.sameTotalSize = sameTotalSize;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getShowRejectAndCal() {
		return showRejectAndCal;
	}

	public void setShowRejectAndCal(Integer showRejectAndCal) {
		this.showRejectAndCal = showRejectAndCal;
	}

	public String getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(String canDelete) {
		this.canDelete = canDelete;
	}

	public String getOrderNoString() {
		return orderNoString;
	}

	public void setOrderNoString(String orderNoString) {
		this.orderNoString = orderNoString;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getCurrentMemberId() {
		return currentMemberId;
	}

	public void setCurrentMemberId(Long currentMemberId) {
		this.currentMemberId = currentMemberId;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	public String getNst_vehiclePhotoUrl() {
		return nst_vehiclePhotoUrl;
	}

	public void setNst_vehiclePhotoUrl(String nst_vehiclePhotoUrl) {
		this.nst_vehiclePhotoUrl = nst_vehiclePhotoUrl;
	}

	public String getNst_driverPhotoUrl() {
		return nst_driverPhotoUrl;
	}

	public void setNst_driverPhotoUrl(String nst_driverPhotoUrl) {
		this.nst_driverPhotoUrl = nst_driverPhotoUrl;
	}

	public long getSubmitCount() {
		return submitCount;
	}

	public void setSubmitCount(long submitCount) {
		this.submitCount = submitCount;
	}

	public Long getShipperId() {
		return shipperId;
	}

	public void setShipperId(Long shipperId) {
		this.shipperId = shipperId;
	}

	public Long getOwnerMemberId() {
		return ownerMemberId;
	}

	public void setOwnerMemberId(Long ownerMemberId) {
		this.ownerMemberId = ownerMemberId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Long getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Long totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	public Long getHundredweight() {
		return hundredweight;
	}

	public void setHundredweight(Long hundredweight) {
		this.hundredweight = hundredweight;
	}

	public String getSendGoodsTypeString() {
		return sendGoodsTypeString;
	}

	public void setSendGoodsTypeString(String sendGoodsTypeString) {
		this.sendGoodsTypeString = sendGoodsTypeString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public String getNst_idCard() {
		return nst_idCard;
	}

	public void setNst_idCard(String nst_idCard) {
		this.nst_idCard = nst_idCard;
	}

	public String getNst_cardPhotoUrl() {
		return nst_cardPhotoUrl;
	}

	public void setNst_cardPhotoUrl(String nst_cardPhotoUrl) {
		this.nst_cardPhotoUrl = nst_cardPhotoUrl;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getEvaluateType() {
		return evaluateType;
	}

	public void setEvaluateType(String evaluateType) {
		this.evaluateType = evaluateType;
	}

	public String getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public String getComplaintsDetails() {
		return complaintsDetails;
	}

	public void setComplaintsDetails(String complaintsDetails) {
		this.complaintsDetails = complaintsDetails;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	

}
