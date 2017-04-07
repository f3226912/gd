package com.gudeng.commerce.gd.order.bo;

import java.util.HashMap;
import java.util.Map;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;

public class OrderCacheBo {

	/********************** 订单类缓存 start****************************/
	/**
	 * 根据订单号查找
	 * @param orderNo
	 * @param baseDao
	 * @return
	 * @throws Exception
	 */
	public OrderBaseinfoDTO getByOrderNo(Long orderNo, BaseDao<?> baseDao) throws Exception{
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("orderNo", orderNo);
		return (OrderBaseinfoDTO) baseDao.queryForObject("OrderBaseinfo.getByOrderNo", map, OrderBaseinfoDTO.class);
	}
	
	/**
	 * 删除订单缓存
	 * @param id
	 */
	public void deleteByOrderNo(Long orderNo){
		
	}
	
	/********************** 订单类缓存 end****************************/
}
