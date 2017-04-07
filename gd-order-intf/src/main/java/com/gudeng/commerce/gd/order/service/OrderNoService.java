package com.gudeng.commerce.gd.order.service;


public interface OrderNoService {
	/**
	 * 获取订单号
	 * @return
	 * @throws Exception
	 */
	public String getOrderNo() throws Exception;

	/**
	 * 根据序列号类型
	 * 查找序列号
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String getOrderNo(String type) throws Exception;
}
