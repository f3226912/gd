package com.gudeng.commerce.gd.order.dto;

import java.util.List;
import java.math.BigInteger;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;

public class WeighCarDTO extends WeighCarEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8042420423712724668L;
	
	private String memberName;
	
	/*
	 * 用户手机号
	 */
	private String memberPhone;
	/**
	 * 车牌号码
	 */
	private String carNumber;
	
	private String account;
	
	private String tareMember;
	
	private String totalMember;
	
	private String tareStartTime;
	
	private String tareEndTime;
	
	private String totalStartTime;
	
	private String totalEndTime;
	
	private Long omId; //出场ID
	

	/**
	 * 产品重量
	 */
	private Double weigh;
	
	/**
	 * 误差率
	 */
	private Double rates;
	/**
	 * 过磅人员
	 */
	private String weighMember;

	public String getWeighMember() {
		return weighMember;
	}

	public void setWeighMember(String weighMember) {
		this.weighMember = weighMember;
	}

	public Double getRates() {
		return rates;
	}

	public void setRates(Double rates) {
		this.rates = rates;
	}

	public Double getWeigh() {
		return weigh;
	}

	public void setWeigh(Double weigh) {
		this.weigh = weigh;
	}


	private String carNumberImageOut; //出场图片
	
	private String carType; //车类型
	
	private String orderNo; //查询使用 订单号

	private List<OrderBaseinfoDTO> orders; 

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarNumberImageOut() {
		return carNumberImageOut;
	}

	public void setCarNumberImageOut(String carNumberImageOut) {
		this.carNumberImageOut = carNumberImageOut;
	}

	public Long getOmId() {
		return omId;
	}

	public void setOmId(Long omId) {
		this.omId = omId;
	}

	public List<OrderBaseinfoDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderBaseinfoDTO> orders) {
		this.orders = orders;
	}

	public String getTareStartTime() {
		return tareStartTime;
	}

	public void setTareStartTime(String tareStartTime) {
		this.tareStartTime = tareStartTime;
	}

	public String getTareEndTime() {
		return tareEndTime;
	}

	public void setTareEndTime(String tareEndTime) {
		this.tareEndTime = tareEndTime;
	}

	public String getTotalStartTime() {
		return totalStartTime;
	}

	public void setTotalStartTime(String totalStartTime) {
		this.totalStartTime = totalStartTime;
	}

	public String getTotalEndTime() {
		return totalEndTime;
	}

	public void setTotalEndTime(String totalEndTime) {
		this.totalEndTime = totalEndTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTareMember() {
		return tareMember;
	}

	public void setTareMember(String tareMember) {
		this.tareMember = tareMember;
	}

	public String getTotalMember() {
		return totalMember;
	}

	public void setTotalMember(String totalMember) {
		this.totalMember = totalMember;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	
	private String goodsPhone;//货主号码

	public String getGoodsPhone() {
		return goodsPhone;
	}
	private String goolsName;//货主姓名

	public String getGoolsName() {
		return goolsName;
	}

	public void setGoolsName(String goolsName) {
		this.goolsName = goolsName;
	}

	public void setGoodsPhone(String goodsPhone) {
		this.goodsPhone = goodsPhone;
	}
	
	private BigInteger userId;//会员id

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	
	

}
