package com.gudeng.commerce.gd.task.service;

/**
 * 查询超过三天未付预付款的订单定时任务，通知农速通关闭货运订单
 * @author liufan
 *
 */
public interface OrderAdvanceTaskService {
	
	/**
	 * 处理超过三天未付预付款的订单，通知农速通关闭此货运订单
	 * @throws Exception
	 */
	
	public void HandleExpireOrderAdvance() throws Exception;

}
