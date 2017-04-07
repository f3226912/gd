package com.gudeng.commerce.gd.pay.service;

import java.util.List;

import com.gudeng.commerce.gd.pay.entity.SystemLogEntity;

/**
 * 日志服务
 *
 */
public interface SystemLogService {

	/**
	 * 记录产品日志
	 * @return
	 * @throws Exception
	 */
	public Long insertLog(SystemLogEntity entity) throws Exception;
	
	/**
	 * @ToDo: 批量插入log日志表
	 * @return
	 * @throws Exception
	 */
	public int[] batchInsertLog(List<SystemLogEntity> entityList) throws Exception;
}
