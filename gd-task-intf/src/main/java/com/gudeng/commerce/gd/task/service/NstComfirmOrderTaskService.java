package com.gudeng.commerce.gd.task.service;

/**
 * 农速通已成交订单7天后自动完成
 *
 */
public interface NstComfirmOrderTaskService {

	public void call() throws Exception;
}
