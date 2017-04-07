package com.gudeng.commerce.gd.api.service;

import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;

public interface PushNstMessageApiService {
    
	/**
	 * 修改已经读状态,
	 * @param pushNstMessageDTO
	 * @return
	 */
	public int setReadStatus(PushNstMessageDTO pushNstMessageDTO)throws Exception;
    
	/**
	 * 获取当前用户未读信息数量
	 * @param pushNstMessageDTO
	 * @return
	 * @throws Exception
	 */
	public int getNewCount(PushNstMessageDTO pushNstMessageDTO)throws Exception;

	public int delNstPush(Map<String, Object> map)throws Exception;

}
