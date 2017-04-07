package com.gudeng.commerce.gd.customer.dto;

/**
 * 
 * @author Ailen
 *
 */
public class DataCacheQuery extends DataBaseQuery {
	
	private static final long serialVersionUID = -5964088837968092405L;

	/**
	 * 对应时间类型 
	 * //天缓存
		DAY_CACHE("hh",24L),
		//小时缓存 暂时支持当前小时需求
		HOUR_CACHE("", 1L),
		//当天缓存 除去当前小时
		TODAY_CACHE("", 1L),
		//最近七天缓存 除去当天
		SEVEN_CACHE("week", 24L),
		//最近半月 15天缓存 除去当天
		HALF_MONTH_CACHE("half_month", 24L),
		//本月缓存 除去当天
		MONTH_CACHE("month", 24L),
		//最近三十天 除去当天
		THIRTY_CACHE("30", 24L),
		//某月
		MONTH_BEFORE_CACHE("month_before", 24L);
	 */
	private TimeCacheType timetype;
	
	/**
	 * 报表类型 ref class ReportType
	 * 
	 */
	private String reportType;
	
	/**
	 * MMdd
	 * yyyyMM
	 */
	private String timeString;

	public TimeCacheType getTimetype() {
		return timetype;
	}

	public void setTimetype(TimeCacheType timetype) {
		this.timetype = timetype;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
	
}
