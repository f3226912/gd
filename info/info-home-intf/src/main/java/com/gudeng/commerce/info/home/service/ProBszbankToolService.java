package com.gudeng.commerce.info.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;
import com.gudeng.commerce.info.customer.dto.ProBszbankDTO;

/**
 * 图表服务
 * @author gcwu
 *
 */
public interface ProBszbankToolService {
	/**
	 * 日交易额
	 * @param map
	 * @return
	 */
	public List<ProBszbankDTO> getTradeByDay(Map<String, Object> map) throws Exception;
	/**
	 * 日订单量
	 * @param parm
	 * @return
	 */
	public List<ProBszbankDTO> getOrderByDay(Map<String, Object> parm)  throws Exception;
	
	
}
