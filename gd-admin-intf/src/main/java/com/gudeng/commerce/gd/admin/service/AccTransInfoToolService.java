package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.AccTransInfoDTO;

/**
 * 钱包（账户）流水服务
 * @author xiaojun
 *
 */
public interface AccTransInfoToolService {
	
	/**
	 * 根据memberId查询账号（钱包）流水信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AccTransInfoDTO> getAccTransInfoListByMemberId(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据memberId查询账号（钱包）流水信息总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Long getAccTransInfoListTotalByMemberId(Map<String, Object> map) throws Exception;
}
