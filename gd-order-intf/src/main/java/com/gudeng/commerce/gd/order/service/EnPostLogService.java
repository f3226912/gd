package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.entity.EnPostLogEntity;



/**
 *功能描述：订单日志
 */
public interface EnPostLogService{
	


	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String,Object> map)throws Exception;
	
	/**
	 * 查询记录
	 * 
	 * @param map
	 * @return 记录
	 * 
	 */
	public List<EnPostLogEntity> getByCondition(Map<String, Object> map) throws Exception;
	

	/**
	 * 添加记录
	 * 
	 * @param map
	 * @return 记录
	 * 
	 */
	public Long insertEnPostLog(Map<String, Object> map) throws Exception;
	
	
	
	/**
	 * 更新记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateEnPostLog(Map<String, Object> map) throws Exception;
	
	
	   /**
     * 查询记录详情
     * 
     * @param map  ID
     * @return 记录详情
     * 
     */
    public EnPostLogEntity getById(Map<String, Object> map) throws Exception;
}
