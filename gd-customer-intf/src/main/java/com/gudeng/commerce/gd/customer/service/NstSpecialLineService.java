package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NstSpecialLineDTO;

/**
 * 农速通专线服务
 * @author xiaojun
 */
public interface NstSpecialLineService {
	
	/**
	 * 根据查询条件获取专线
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstSpecialLineDTO> getSpecialLineByCondition(Map<String, Object> map) throws Exception;
}
