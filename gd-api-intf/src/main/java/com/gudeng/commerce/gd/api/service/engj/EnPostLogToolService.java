package com.gudeng.commerce.gd.api.service.engj;

import com.gudeng.commerce.gd.api.dto.input.ENongPayNotifyDTO;
import com.gudeng.commerce.gd.api.dto.input.ENongRequestDTO;


public interface EnPostLogToolService {

	/**
	 * 写请求内容
	 * @param reqObj 请求对象
	 * @param id 
	 * @return
	 */
	public Long writeRequestInfo(Object reqObj, Long id);
	
	/**
	 * 写响应内容
	 * @param requestDTO
	 * @param payNotifyDTO
	 * @param message  
	 * @return
	 * @throws Exception
	 */
	public void writeResponseInfo(ENongRequestDTO requestDTO, ENongPayNotifyDTO payNotifyDTO, String message);
}
