package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;
import com.gudeng.commerce.gd.customer.entity.AppChannelLink;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class AppChannelLinkDTO extends AppChannelLink {
	private static final long serialVersionUID = -538624794290681115L;
	@ExcelConf(excelHeader="客户端名称",sort=1)
	private String clientChannelName;
	@ExcelConf(excelHeader="平台",sort=3)
	private String platformName;

	public String getClientChannelName() {
		return clientChannelName;
	}

	public void setClientChannelName(String clientChannelName) {
		this.clientChannelName = clientChannelName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

}