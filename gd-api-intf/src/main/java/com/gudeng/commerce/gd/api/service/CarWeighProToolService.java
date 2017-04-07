
package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.CarWeighProDTO;


public interface CarWeighProToolService {

	/**
	 * 获取状态为启用的记录
	 * @return
	 * @throws Exception 
	 */
	public List<CarWeighProDTO> getValidCarWeighProList(Map<String, Object> map) throws Exception;
}
