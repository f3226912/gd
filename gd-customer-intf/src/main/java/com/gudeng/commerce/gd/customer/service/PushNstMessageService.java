package com.gudeng.commerce.gd.customer.service;

import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;

public interface PushNstMessageService {

	public int setReadStatus(PushNstMessageDTO pushNstMessageDTO) throws Exception;

	public int getNewCount(PushNstMessageDTO pushNstMessageDTO) throws Exception;

	public int delNstPush(Map<String, Object> map)throws Exception;

}
