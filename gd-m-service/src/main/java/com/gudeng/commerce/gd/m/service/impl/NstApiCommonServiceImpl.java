package com.gudeng.commerce.gd.m.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.gudeng.commerce.gd.m.dto.NstBaseResponseDTO;
import com.gudeng.commerce.gd.m.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.m.enums.NstApiRequestV1Enum;
import com.gudeng.commerce.gd.m.redis.RedisKeys.RedisKey;
import com.gudeng.commerce.gd.m.service.NstApiCommonService;
import com.gudeng.commerce.gd.m.util.Des3;
import com.gudeng.commerce.gd.m.util.GSONUtils;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.m.util.HttpClients;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * 农速通相关工具类
 * @author TerryZhang
 */
public class NstApiCommonServiceImpl implements NstApiCommonService{
	
	private static final GdLogger logger = GdLoggerFactory.getLogger(NstApiCommonServiceImpl.class);
	
	private final String loginUri = NstApiRequestV1Enum.GET_NST_TOKEN.getUri();
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	@Autowired
	private GdProperties gdProperties;
	
	@Override
	public NstBaseResponseDTO sendNstRequest(Object obj, String url) throws Exception{
		String paramValue = null;
		if(obj instanceof String){
			paramValue = obj.toString();
		}else{
			paramValue = Des3.encode(new Gson().toJson(obj));
		}
		String sNstApiResult = HttpClients.doPost(url, paramValue);
		if(StringUtils.isNotBlank(sNstApiResult)){
			//得到农速通接口结果
			String sNstRepJson = Des3.decode(sNstApiResult);
			NstBaseResponseDTO nstResponse = (NstBaseResponseDTO) GSONUtils.fromJsonToObject(sNstRepJson, NstBaseResponseDTO.class);
			return nstResponse;
		}
		
		NstBaseResponseDTO nstResponse = new NstBaseResponseDTO();
		nstResponse.setCode(ErrorCodeEnum.NST_FAIL.getStatusCode());
		nstResponse.setMsg(ErrorCodeEnum.NST_FAIL.getStatusMsg()); 
		nstResponse.setSuccess(false);
		return nstResponse;
	}
	
	@Override
	public String getNstToken(String memberId) throws Exception{
		String token = redisClient.get(RedisKey.NST_TOKEN_KEY.value + memberId);
		if(StringUtils.isNotBlank(token)){
			return token;
		}
		
		return setNstToken(memberId);
	}

	@Override
	public String setNstToken(String memberId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		String url = gdProperties.getNstApiUrl() + loginUri;
		NstBaseResponseDTO nstResponse = sendNstRequest(paramMap, url);
		if(nstResponse.getCode().intValue() == 10000){
			Map<String, Object> nstResultMap = nstResponse.getResult();
			String token = nstResultMap.get("token").toString();
			redisClient.set(RedisKey.NST_TOKEN_KEY.value + memberId, token);
			logger.info("NST token info: " + "memberId: " + memberId + ", token: " + token);
			return token;
		}else{
			logger.warn("NST Response: " + "code: " + nstResponse.getCode() + ", msg: " + nstResponse.getMsg());
			return "";
		}
	}
}
