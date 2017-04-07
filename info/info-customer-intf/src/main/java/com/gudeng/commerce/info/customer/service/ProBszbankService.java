package com.gudeng.commerce.info.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;
import com.gudeng.commerce.info.customer.dto.ProBszbankDTO;

/**
 * 图表服务
 * @author gcwu
 *
 */
public interface ProBszbankService {
	/**
	 * 日交易额
	 * @return
	 */
	public List<ProBszbankDTO> getTradeByDay(Map<String, Object> map) throws Exception;
	
	/**
	 * 日订单
	 * @return
	 */
	public List<ProBszbankDTO> getOrderByDay(Map<String, Object> map) throws Exception;

	
	
	/**
	 * （根据id）统计报表数据
	 * @param map
	 * @return
	 */
	public List<ProBszbankDTO> sumReport(Map<String, Object> map);
}
