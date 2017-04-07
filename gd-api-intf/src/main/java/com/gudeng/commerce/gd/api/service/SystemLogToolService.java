package com.gudeng.commerce.gd.api.service;

import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;

public interface SystemLogToolService {

	Long insertLog(SystemLogEntity entity) throws Exception;
	
	/**
	 * 订单API接口记录日志
	 * @return
	 * @throws Exception
	 */
	Long insertOrderApiLog(String content,String orderNo) throws Exception;
}
