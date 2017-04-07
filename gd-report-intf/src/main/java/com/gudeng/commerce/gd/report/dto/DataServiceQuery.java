package com.gudeng.commerce.gd.report.dto;


/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月14日 上午10:24:57
 */
public class DataServiceQuery extends DataBaseQuery {

	private static final long serialVersionUID = 3893400290224600699L;
	private Integer timeType;
	private String timeStr;

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

}
