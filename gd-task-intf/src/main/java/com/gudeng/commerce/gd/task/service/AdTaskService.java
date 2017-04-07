package com.gudeng.commerce.gd.task.service;

/**
 * 广告管理定时任务
 * @author xiehui
 *
 */
public interface AdTaskService {
	
	/**
	 * 处理过期的广告
	 * @throws Exception
	 */
	
	public void HandleExpireAd() throws Exception;

}
