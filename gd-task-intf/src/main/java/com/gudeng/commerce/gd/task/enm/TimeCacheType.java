package com.gudeng.commerce.gd.task.enm;

/**
 * 缓存redis对应枚举和KEY值
 * @author Ailen
 *
 */
public enum TimeCacheType {
	
	//天缓存
	DAY_CACHE("hh",24*60*60L),
	//小时缓存 暂时支持当前小时需求
	HOUR_CACHE("", 1*60*60L),
	//当天缓存 除去当前小时
	TODAY_CACHE("", 1*60*60L),
	//最近七天缓存 除去当天
	SEVEN_CACHE("week", 24*60*60L),
	//最近半月 15天缓存 除去当天
	HALF_MONTH_CACHE("half_month", 24*60*60L),
	//本月缓存 除去当天
	MONTH_CACHE("month", 24*60*60L),
	//最近三十天 除去当天
	THIRTY_CACHE("30", 24*60*60L),
	//某月
	MONTH_BEFORE_CACHE("month_before", 365*24*60*60L);
	
	private String cacheCode;
	
	private Long expirt;
	
	private TimeCacheType(String cacheCode,Long expirt) {
		this.cacheCode = cacheCode;
		this.expirt = expirt;
	}

	public String getCacheCode() {
		return cacheCode;
	}

	public void setCacheCode(String cacheCode) {
		this.cacheCode = cacheCode;
	}

	public Long getExpirt() {
		return expirt;
	}

	public void setExpirt(Long expirt) {
		this.expirt = expirt;
	}
	
}
