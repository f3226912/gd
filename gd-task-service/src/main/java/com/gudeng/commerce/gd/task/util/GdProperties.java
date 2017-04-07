package com.gudeng.commerce.gd.task.util;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 参数属性;
 * 
 */
public class GdProperties {

	private Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/** demo的地址 */
	public String getDemoUrl() {
		return properties.getProperty("gd.demo.url");
	}
	
	/** n天内推送过的就不用推送 */
	public int getPushFilterDaycn() {
		int daycn = 0;
		String filter =  properties.getProperty("gd.push.filter.daycn");
		daycn =StringUtils.isBlank(filter)?0:Integer.parseInt(filter);
		return daycn;
	}
	/**
	 * 获取要生产静态页的urls
	 * @return
	 */
	public String getHtmlUrls() {
		return properties.getProperty("gd.html.urls");
	}
	
	public String getHtmlDir() {
		return properties.getProperty("gd.html.dir");
	}
	
	/** 交易账单 */
	public String getOrderBillUrl(){
		return properties.getProperty("gd.orderBillService.url");
	}
	
	/** 交易账单 文件目录*/
	public String getOrderBillDir(){
		return properties.getProperty("gd.orderBill.dir");
	}
	/** 交易账单 文件目录*/
	public String getOrderBillTOMail(){
		return properties.getProperty("gd.orderBill.TOMail");
	}
	
	public String getPreSaleServiceDir() {
		return properties.getProperty("gd.preSaleService.url");
	}
	//交易账单导入日志记录服务 
	public String getOrderBillImportLogService() {
		return properties.getProperty("gd.orderBillImportLogService.url");
	}
	
	public String getSystemLogUrl() {
		return properties.getProperty("gd.systemlog.url");
	}

	public String getNstOrderBaseinfoUrl() {
		return properties.getProperty("gd.nstOrderService.url");
	}
	
	public String getMqAsyncErrorServiceUrl() {
		return properties.getProperty("gd.mqAsyncErrorService.url");
	}
	public String getMqMemberTopic(){
		return properties.getProperty("gd.MQ.member.Topic");
	}
	public String getMqAccBankTopic(){
		return properties.getProperty("gd.MQ.accBank.Topic");
	}
}
