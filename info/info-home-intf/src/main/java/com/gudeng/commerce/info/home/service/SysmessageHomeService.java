package com.gudeng.commerce.info.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.SysmessageDTO;

public interface SysmessageHomeService {
	/**
	 * 未读消息记录总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getUnReadMessageCount(String userID) throws Exception;
	
	/**
	 * 根据条件查询消息列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<SysmessageDTO> getMessageListByUser(Map<String, Object> map) throws Exception;
	
	/**
	 * 消息详细信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public SysmessageDTO getMessageDetail(Map<String,Object> map) throws Exception;
	
	
	
}
