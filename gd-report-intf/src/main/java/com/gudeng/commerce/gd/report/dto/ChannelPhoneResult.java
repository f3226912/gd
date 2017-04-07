package com.gudeng.commerce.gd.report.dto;

import java.io.Serializable;

/**
 * 页面电话统计结果
 * @author TerryZhang
 */
public class ChannelPhoneResult implements Serializable{

	private static final long serialVersionUID = -37755429802526399L;
	
	/**
	 * 页面类型
	 */
	private String pageType;
	
	/**
	 * 页面名称
	 */
	private String pageName;
	
	/**
	 * 拨打次数
	 */
	private Long count;

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	} 
}
