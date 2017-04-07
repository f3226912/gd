package com.gudeng.commerce.gd.notify.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberChangeLogDTO;

/**
 * 功能描述：MemberChangeLog增删改查实现类
 *
 */
public interface MemberChangeLogToolService {

	/**
	 * 插入数据库
	 *
	 * @param map
	 * @return int
	 *
	 */
	public void addMemberChangeLog(Map map) throws Exception;

	/**
	 * 根据map 集合，统计个数
	 *
	 *
	 * @return int
	 *
	 */
	public int getTotal(Map map) throws Exception;

	/**
	 * 根据多条件查询
	 *
	 * @return list
	 *
	 */
	public List<MemberChangeLogDTO> getBySearch(Map map) throws Exception;
}