package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.entity.EnPostLogEntity;


public interface EnPostLogManageService {
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询记录
	 * 
	 * @param map
	 * @return 记录
	 * 
	 */
	public List<EnPostLogEntity> getByCondition(Map<String, Object> map) throws Exception;
    
	/**
     * 查询记录详情
     * 
     * @param map ID
     * @return 记录
     * 
     */
	  public EnPostLogEntity getById(Map<String, Object> map) throws Exception;


}
