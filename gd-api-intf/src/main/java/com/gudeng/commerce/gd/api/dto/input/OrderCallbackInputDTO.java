package com.gudeng.commerce.gd.api.dto.input;

import java.io.Serializable;

/**
 * @purpose 弄速通回调更新订单状态参数类
 * @author zlb
 * @date 2016年12月7日
 */
public class OrderCallbackInputDTO implements Serializable {


	private static final long serialVersionUID = 7671902627006142906L;
	
	private String orderNo;//货源ID
	private Integer type;//类型
	private String cancelReason;//取消原因
	private String userId;//更新人
	private String nstOrderNo;//运单号
	private String distributeMode;//配送方式
	private Integer companyId;//物流公司ID
	private Integer driverId;//司机ID
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNstOrderNo() {
		return nstOrderNo;
	}
	public void setNstOrderNo(String nstOrderNo) {
		this.nstOrderNo = nstOrderNo;
	}
	public String getDistributeMode() {
		return distributeMode;
	}
	public void setDistributeMode(String distributeMode) {
		this.distributeMode = distributeMode;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getDriverId() {
		return driverId;
	}
	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	
	
}
