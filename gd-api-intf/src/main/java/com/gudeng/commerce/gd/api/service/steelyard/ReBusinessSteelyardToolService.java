package com.gudeng.commerce.gd.api.service.steelyard;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.ReBusinessSteelyardDTO;

/**
 * 商铺智能秤和
 * 商铺关联service类
 */
public interface ReBusinessSteelyardToolService {

	/**
	 * 根据商铺ID获取智能秤信息
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public List<ReBusinessSteelyardDTO> getMacByBusId(String businessId) throws Exception;
}
