package com.gudeng.commerce.gd.api.service.order;

import java.util.Map;

import com.gudeng.commerce.gd.api.dto.input.PayCenterApiBaseResponseDTO;
import com.gudeng.commerce.gd.api.dto.output.VClearDetailDto;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;

public interface PayCenterApiCommonService {

	/**
	 * 发送请求公共方法
	 * @param paramsMap
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public PayCenterApiBaseResponseDTO sendRequest(Map<String, String> paramsMap, String url) throws Exception;
	
	/**
	 * 根据订单号获取
	 * 订单相关费用
	 * @param orderBaseDTO
	 * @return
	 * @throws Exception
	 */
	public VClearDetailDto getFeeInfoByOrderNo(OrderBaseinfoDTO orderBaseDTO) throws Exception;
	public VClearDetailDto getFeeInfoByOrderNo2(OrderBaseinfoDTO orderBaseDTO) throws Exception;
}
