package com.gudeng.commerce.gd.api.service.impl.order;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.api.dto.input.PayCenterApiBaseResponseDTO;
import com.gudeng.commerce.gd.api.dto.output.VClearDetailDto;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.order.PayCenterApiCommonService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.HttpClients;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

public class PayCenterApiCommonServiceImpl implements PayCenterApiCommonService {

	private static final GdLogger logger = GdLoggerFactory.getLogger(PayCenterApiCommonServiceImpl.class);
	
	@Autowired
	private GdProperties gdProperties;
	
	@Override
	public PayCenterApiBaseResponseDTO sendRequest(Map<String, String> paramsMap, String url)
			throws Exception {
		String responseStr = HttpClients.doPostWithoutEncryption(url, paramsMap);
		logger.info("Response: " + responseStr);
		if(StringUtils.isNotBlank(responseStr)){
			PayCenterApiBaseResponseDTO response = (PayCenterApiBaseResponseDTO) GSONUtils.fromJsonToObject(responseStr, PayCenterApiBaseResponseDTO.class);
			return response;
		}
		
		PayCenterApiBaseResponseDTO response = new PayCenterApiBaseResponseDTO();
		response.setCode(ErrorCodeEnum.PAY_CENTER_API_FAIL.getStatusCode());
		response.setMsg(ErrorCodeEnum.PAY_CENTER_API_FAIL.getStatusMsg()); 
		response.setSuccess(false);
		return response;
	}

	@Override
	public VClearDetailDto getFeeInfoByOrderNo(OrderBaseinfoDTO orderBaseDTO) throws Exception {
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("orderNo", orderBaseDTO.getOrderNo().toString());
		paramsMap.put("memberId", orderBaseDTO.getSellMemberId().toString());
		PayCenterApiBaseResponseDTO response = sendRequest(paramsMap, gdProperties.getCenterPayApiUrl() + "api/getVClearList");
		if(response.getCode().intValue() == 10000){
			JSONArray result = response.getResult();
			logger.info("Result: " + result);
			if(result != null && result.size() > 0){
				logger.info("Result detail: " + result.get(0));
				Map<String, Object> detailMap = (Map<String, Object>) result.get(0);
				VClearDetailDto detail = JSONObject.parseObject(JSONUtils.toJSONString(detailMap), VClearDetailDto.class);
				return detail;
			}
		}
		return null;
	}

	@Override
	public VClearDetailDto getFeeInfoByOrderNo2(OrderBaseinfoDTO orderBaseDTO) throws Exception {
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("orderNo", orderBaseDTO.getOrderNo().toString());
		paramsMap.put("memberId", orderBaseDTO.getSellMemberId().toString());
		PayCenterApiBaseResponseDTO response = sendRequest(paramsMap, gdProperties.getCenterPayApiUrl() + "clear/detail/list");
		if(response.isSuccess()){
			JSONArray result = response.getData();
			logger.info("Result: " + result);
			if(result != null && result.size() > 0){
				logger.info("Result detail: " + result.get(0));
				Map<String, Object> detailMap = (Map<String, Object>) result.get(0);
				VClearDetailDto detail = JSONObject.parseObject(JSONUtils.toJSONString(detailMap), VClearDetailDto.class);
				return detail;
			}
		}
		return null;
	}
}
