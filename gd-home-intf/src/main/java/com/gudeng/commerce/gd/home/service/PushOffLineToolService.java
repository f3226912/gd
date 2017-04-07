package com.gudeng.commerce.gd.home.service;

import java.util.Map;

import com.gudeng.commerce.gd.customer.entity.PushOffline;

public interface PushOffLineToolService {
	/**
	 * 保存线下推广信息
	 * @param offLinePushEntity
	 * @return
	 * @throws Exception
	 */
	public Long saveOffLinePushInfo(PushOffline pushOffline) throws Exception;
	
	/**
	 * 根据主键获取线下推广信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PushOffline getOffLinePushInCondition(Map<?,?> map) throws Exception;
}
