package com.gudeng.commerce.gd.customer.service.statis;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.BusinessPvStatisDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.enums.MessageTemplateEnum;

/**
 * 商铺浏览量业务
 * 
 * @author Ailen
 *
 */
public interface PvStatisticBusinessService {

	/**
	 * 添加商铺浏览量
	 * 
	 * @param businessPvStatisDTO
	 */
	public void addPv(BusinessPvStatisDTO businessPvStatisDTO);

	/**
	 * 添加商品浏览量
	 */
	public void addPvForProduct(Long productId, Integer addPv);

	/**
	 * 发送短信
	 * @param memberId
	 * @param mobile
	 * @param alidauCode
	 * @param template
	 * @param params
	 */
	public void sendMsg(Long memberId, String mobile, Integer alidauCode, MessageTemplateEnum template,
			Map<String, Object> params);
	
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
