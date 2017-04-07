package com.gudeng.commerce.gd.m.util;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

import cn.gdeng.weixin.base.bean.BaseData;
import cn.gdeng.weixin.base.conf.BaseConf;
import cn.gdeng.weixin.base.conf.JSSDKConf;
import cn.gdeng.weixin.base.core.BaseAPI;
import cn.gdeng.weixin.base.core.JSSDKAPI;

@Service
public class BaseWXUtil {

	private static Logger logger = LoggerFactory.getLogger(BaseWXUtil.class);
	private static final String ACCESS_TOKEN = "access_token";
	private static final String TICKET_NAME = "jsApiTicket";
	private static final int EXPIRE_TIME = 3600;

	private static String ACCESS_TOKEN_KEY;
	private static String TICKET_TOKEN_KEY;

	@Autowired
	private IGDBinaryRedisClient redisClient;
	private BaseConf config = new BaseConf();
	@Autowired
	private GdProperties gdProperties;
	private String weiXinConf;

	public BaseWXUtil() {

	}

	@PostConstruct
	public void init() {
		weiXinConf = gdProperties.getWeiXinConf();
		String[] ghs = weiXinConf.split(";");
		String[] wxconfs = ghs[0].split("\\|");
		config.setGdId(wxconfs[0]);
		config.setAppId(wxconfs[1]);
		config.setAppSecret(wxconfs[2]);
		config.setToken(wxconfs[3]);
		config.setEncodingAESKey(wxconfs[4]);
		if (StringUtils.isEmpty(ACCESS_TOKEN_KEY)) {
			ACCESS_TOKEN_KEY = config.getAppId() + "_" + ACCESS_TOKEN;
		}
		if (StringUtils.isEmpty(TICKET_TOKEN_KEY)) {
			TICKET_TOKEN_KEY = config.getAppId() + "_" + TICKET_NAME;
		}
	}

	public BaseConf getWeiXinConf() {
		return config;
	}

	public String getAccessToken() {
		Serializable serializable = redisClient.get(ACCESS_TOKEN_KEY);
		if (serializable == null) {
			refreshToken();
		} else {
			logger.info("access_token redis获取成功");
		}
		return (String) JSONObject.toJSON(redisClient.get(ACCESS_TOKEN_KEY));
	}

	public String getJsApiToken() {
		Serializable serializable = redisClient.get(TICKET_TOKEN_KEY);
		if (serializable == null) {
			refreshJsApiToken();
		} else {
			logger.info("jsapiToken redis获取成功");
		}
		return (String) JSONObject.toJSON(redisClient.get(TICKET_TOKEN_KEY));
	}

	private void refreshToken() {
		BaseData baseData = new BaseData();
		baseData.setAppId(config.getAppId());
		baseData.setAppSecret(config.getAppSecret());
		baseData = BaseAPI.getAccess_token(baseData);
		if (StringUtils.isNotEmpty(baseData.getAccess_token())) {
			redisClient.set(ACCESS_TOKEN_KEY, baseData.getAccess_token());
			redisClient.expire(ACCESS_TOKEN_KEY, EXPIRE_TIME);
			logger.info("access_token刷新成功");
		} else {
			redisClient.del(ACCESS_TOKEN_KEY);
			logger.info("access_token刷新异常");
		}
	}

	private void refreshJsApiToken() {
		JSSDKConf jssdkConf = new JSSDKConf();
		jssdkConf.setAccess_token(getAccessToken());
		jssdkConf = JSSDKAPI.getJSSDKConf(jssdkConf);
		if (StringUtils.isNotEmpty(jssdkConf.getTicket())) {
			redisClient.set(TICKET_TOKEN_KEY, jssdkConf.getTicket());
			redisClient.expire(TICKET_TOKEN_KEY, EXPIRE_TIME);
			logger.info("jsapiToken刷新成功");
		} else {
			redisClient.del(TICKET_TOKEN_KEY);
			logger.info("jsapiToken刷新异常");
		}
	}
}
