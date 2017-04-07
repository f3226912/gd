package com.gudeng.commerce.gd.api.service.nst;

import com.gudeng.commerce.gd.api.dto.input.NstBaseResponseDTO;

public interface NstApiCommonService {

	/**
	 * 发送农速通请求
	 * 公共方法
	 * @param obj
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public NstBaseResponseDTO sendNstRequest(Object obj, String url) throws Exception;
	
	/**
	 * 设置农速通的token
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public String setNstToken(String memberId) throws Exception;
	
	/**
	 * 获取农速通的token
	 * @param memberId
	 * @return
	 */
	public String getNstToken(String memberId) throws Exception;
	
	/**
	 * 获取农速通
	 * 是否支持平台配送
	 * @param memberId
	 * @return 0不支持 1支持
	 * @throws Exception
	 */
	public int getCustomerPlatform(Integer memberId) throws Exception;
}
