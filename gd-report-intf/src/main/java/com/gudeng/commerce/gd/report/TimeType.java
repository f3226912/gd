package com.gudeng.commerce.gd.report;

/**
 * 时间类型 控制层使用
 * @author Ailen
 *
 */
public enum TimeType {

	SEVENDAY_BEFORE(1, "最近七天"),
	FIFTHEENDAY_BEFORE(2, "最近15天"),
	THIRTYDAY_BEFORE(3, "最近30天"),
	MONTH(4, "本月");

	private String context;
	
	private int code;

	public String getContext() {
		return this.context;
	}
	
	public int getCode() {
		return this.code;
	}

	private TimeType(int code,String context) {
		this.code = code;
		this.context = context;
	}
	
}
