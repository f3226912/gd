package com.gudeng.commerce.gd.customer.service;

import java.util.Map;


/**
 * 
 * @author semon
 *
 */
public interface PushTaskService {
	
	/**
	 * 更新要推送的内容
	 * @return
	 */
	public int addPushProductContent();
	
	/**
	 * 删除当天更新过的内容
	 * @return
	 */
	public int deletePushProductContentByTime();
	
	/**
	 * 当前时间是否可以推送
	 * @return
	 */
	public boolean isPushTime();
	
	/**
	 * 推送的用户和关联产品
	 * @return
	 */
	public void PushUsers(int daycn);
	
	/**
	 * 批量添加
	 * @param maps
	 * @return
	 */
	public int[] addPushRecord(Map<String,Object>[] maps);
	
	/**
	 * 广告 过期处理，和上线处理
	 * @param hour 小时数
	 */
	public Map<String,Integer> processAd(int hour); 

}
