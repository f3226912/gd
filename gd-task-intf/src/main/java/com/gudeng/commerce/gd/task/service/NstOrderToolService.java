package com.gudeng.commerce.gd.task.service;

public interface NstOrderToolService {

	/**
	 * 修改7天后已成交订单状态为已完成
	 * @return
	 * @throws Exception 
	 */
	public int autoComfirmOrder() throws Exception;
	
	/**
	 * 同城货源20分钟没有被接单 自动取消
	 * @return
	 * @throws Exception
	 */
	public Integer autoCancelOrderBySameCity() throws Exception;
}
