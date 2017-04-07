package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PageStatisMemberDTO;

/**
 * 功能描述：
 *
 */
public interface MemberPageStatisticToolService {
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
	public List<PageStatisMemberDTO> getBySearch(Map map) throws Exception;

}