package com.gudeng.commerce.gd.pay.service;

import com.gudeng.commerce.gd.pay.dto.ResultDTO;
import com.gudeng.commerce.gd.pay.dto.WangPosPayNotifyDTO;


public interface WangPosService {

	/**
	 * 服务入口
	 * 
	 * @param wangPosPayNotifyDTO
	 * @return ResultDTO
	 * 
	 */
	public ResultDTO execute(WangPosPayNotifyDTO wangPosPayNotifyDTO) throws Exception;
	
}