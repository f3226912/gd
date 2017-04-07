package com.gudeng.commerce.gd.report.dto;


/**
 * @Description: 只用于存储查询的时间范围
 * 其他查询条件 请写入 DataCacheQuery的对应子类中
 * @author mpan
 * @date 2016年6月14日 上午10:24:57
 */
public class DataDBQuery extends DataBaseQuery {

	private static final long serialVersionUID = -4666745680177696652L;
	private String startTime; // 开始时间yyyymmddhhmmss
	private String endTime; // 结束时间yyyymmddhhmmss

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
