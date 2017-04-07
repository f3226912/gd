package com.gudeng.commerce.info.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.ProBszbankDTO;

/**
 * 交易流水图表服务
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
	
	/**
	 * (根据id)统计报表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<ProBszbankDTO> sumReport(Map<String, Object> map) throws Exception;
}
