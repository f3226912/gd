package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 退款日志查询返回对象
 * @author zlb
 * @date 2016年12月13日
 */
public class RefundResponseLogResult implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7478066379154655454L;
	
	private Integer id;
	private String appKey;
	
	private String orderNo;//订单号
	private String payCenterNumber;//支付中心流水号
	private String refundNo; //退款单号;
	private String refundRequestNo;//退款请求号
	private String thirdRefundNo;//第三方支付号
	private String thirdRefundRequestNo;
	
	private String refundUserId;//退款人ID
	private Integer refundType;//退款方式
	private Double refundAmt;//退款金额
	private String refundReason;//退款原因
	private String refundTime; //退款时间
	private String extendJson;
	private String status;//状态
	private String createTime;//创建时间
	private String createUserId;//创建人
	private String updateUserId;//更新人
	private String updateTime;
	//private String feeList; //列表
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getRefundRequestNo() {
		return refundRequestNo;
	}
	public void setRefundRequestNo(String refundRequestNo) {
		this.refundRequestNo = refundRequestNo;
	}
	public String getThirdRefundNo() {
		return thirdRefundNo;
	}
	public void setThirdRefundNo(String thirdRefundNo) {
		this.thirdRefundNo = thirdRefundNo;
	}
	public String getPayCenterNumber() {
		return payCenterNumber;
	}
	public void setPayCenterNumber(String payCenterNumber) {
		this.payCenterNumber = payCenterNumber;
	}
	public Integer getRefundType() {
		return refundType;
	}
	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}
	public Double getRefundAmt() {
		return refundAmt;
	}
	public void setRefundAmt(Double refundAmt) {
		this.refundAmt = refundAmt;
	}
	public String getRefundUserId() {
		return refundUserId;
	}
	public void setRefundUserId(String refundUserId) {
		this.refundUserId = refundUserId;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}
//	public String getFeeList() {
//		return feeList;
//	}
//	public void setFeeList(String feeList) {
//		this.feeList = feeList;
//	}
	public String getThirdRefundRequestNo() {
		return thirdRefundRequestNo;
	}
	public void setThirdRefundRequestNo(String thirdRefundRequestNo) {
		this.thirdRefundRequestNo = thirdRefundRequestNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getExtendJson() {
		return extendJson;
	}
	public void setExtendJson(String extendJson) {
		this.extendJson = extendJson;
	}
	
	
}
