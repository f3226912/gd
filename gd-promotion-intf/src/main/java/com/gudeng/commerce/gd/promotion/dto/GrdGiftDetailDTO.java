package com.gudeng.commerce.gd.promotion.dto;

import javax.persistence.Column;

import com.gudeng.commerce.gd.promotion.annotation.ExcelConf;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftDetailEntity;

public class GrdGiftDetailDTO extends GrdGiftDetailEntity {
	private static final long serialVersionUID = -5641939774519998346L;

	private String  orderTimeStr;
	private double totalMoney;
	private int totalCount;
	private String realName;
	//@ExcelConf(excelHeader="地推手机", sort = 3)
	private String mobile;
	//@ExcelConf(excelHeader="所属市场", sort = 1)
	private String market;
	//@ExcelConf(excelHeader="地推姓名", sort = 2)
	private String name;
	
	//礼品明细中，礼品的单位
	private String unit;
	
	private String description;
	/**
	 * code解析后的格式详情
	 */
	private String codeDetail;

	/**
	 * 类型名字 1表示礼品明细，2表示订单明细,3表示农批注册所发放礼品,4表示农速通注册所发放礼品,5表示发布货源，6表示信息订单,7表示货运订单
	 */
	private String typeName;
	
	/**
	 * 农速通的订单数量，区别 countNo,但是数据库中公用一个countNo字段
	 * */
	private String count;
	
	private String giftNo;
	
	public String getGiftNo() {
		return giftNo;
	}

	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	@Column(name = "realName")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderTimeStr() {
		return orderTimeStr;
	}

	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCodeDetail() {
		return codeDetail;
	}

	public void setCodeDetail(String codeDetail) {
		this.codeDetail = codeDetail;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}