package com.gudeng.commerce.gd.admin.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.BusinessPvStatisDTO;
import com.gudeng.commerce.gd.customer.enums.MessageTemplateEnum;

/**
 * 功能描述：
 *
 */
public interface PvStatisticBusinessToolService {

	/**
	 * 发送短信
	 * @param memberId
	 * @param mobile
	 * @param alidauCode
	 * @param template
	 * @param params
	 */
	public void sendMsg(Long memberId, String mobile, Integer alidauCode, MessageTemplateEnum template,
			Map<String, Object> params) throws MalformedURLException;
	
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
	public List<BusinessPvStatisDTO> getBySearch(Map map) throws Exception;
	
	/**
	 * 根据map 集合，统计个数
	 *
	 *
	 * @return int
	 *
	 */
	public int getAmountTotal(Map map) throws Exception;
	
	/**
	 * 根据多条件查询
	 *
	 * @return list
	 *
	 */
	public List<BusinessPvStatisDTO> getAmountBySearch(Map map) throws Exception;
	
	/**
	 * 添加商铺浏览量
	 * 
	 */
	public void updatePv(Map map) throws Exception;
}