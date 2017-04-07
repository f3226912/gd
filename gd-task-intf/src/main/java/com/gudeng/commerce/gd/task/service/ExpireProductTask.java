package com.gudeng.commerce.gd.task.service;

/**
 * 定时处理过期产品
 * @author Semon
 *
 */
public interface ExpireProductTask  {
	
	
	/**
	 * 下架过期产品
	 * 先下架过期产品，然后推送信息到平台
	 */
	public void HandleExpireProduct()  throws Exception ;

}
