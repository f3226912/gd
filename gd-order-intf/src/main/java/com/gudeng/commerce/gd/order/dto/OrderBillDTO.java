package com.gudeng.commerce.gd.order.dto;

import java.util.List;

import com.gudeng.commerce.gd.order.entity.OrderBillEntity;

/**
 * @Description 谷登账单
 * @Project gd-order-intf
 * @ClassName OrderBillEntity.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月14日 上午11:19:27
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public class OrderBillDTO extends OrderBillEntity {
	
	private static final long serialVersionUID = -5268858581420024619L;
	// 交易时间
	private String tradeDayStr;
	// 交易年月日
	private String dayStr;
	// 交易时分秒
	private String timeStr;
	
	private String createTimeStr;
	private String updateTimeStr;
	private List<String> sysRefeNos; // 系统参考号
	
	/**
	 * 开始时间
	 */
	private String billBeginTime;
	/**
	 * 结束时间
	 */
	private String billEndTime;
	/**
	 * 所属市场名称
	 */
	private String marketName;
	
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getTradeDayStr() {
		return tradeDayStr;
	}
	public void setTradeDayStr(String tradeDayStr) {
		this.tradeDayStr = tradeDayStr;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getUpdateTimeStr() {
		return updateTimeStr;
	}
	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	public List<String> getSysRefeNos() {
		return sysRefeNos;
	}
	public void setSysRefeNos(List<String> sysRefeNos) {
		this.sysRefeNos = sysRefeNos;
	}
	public String getBillBeginTime() {
		return billBeginTime;
	}
	public void setBillBeginTime(String billBeginTime) {
		this.billBeginTime = billBeginTime;
	}
	public String getBillEndTime() {
		return billEndTime;
	}
	public void setBillEndTime(String billEndTime) {
		this.billEndTime = billEndTime;
	}

}
