package com.gudeng.commerce.gd.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gudeng.commerce.gd.api.redis.RedisService;
import com.gudeng.commerce.gd.api.service.LoginService;
import com.gudeng.commerce.gd.api.util.JavaMd5;
import com.gudeng.commerce.gd.api.util.UserSettingPropertyUtils;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private RedisService redisUtil;
	
	private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Override
	public String handleLogin(MemberBaseinfoDTO memberBaseinfoDTO)
			throws Exception {
		String sid = "";
		String timeStamp = String.valueOf(System.currentTimeMillis());
		StringBuilder encryptedStr = new StringBuilder();
		encryptedStr.append(memberBaseinfoDTO.getDevice_tokens()).append("-")
				.append(memberBaseinfoDTO.getMemberId()).append("-")
				.append(timeStamp);
		String key = UserSettingPropertyUtils.getEncrytphrase("gd.encrypt.key");
		sid = JavaMd5.getMD5(encryptedStr + key).toUpperCase();

		redisUtil.setex(sid, JSON.toJSONString(memberBaseinfoDTO), 10000l);
		return sid;
	}

	@Override
	public void handleLogout(String sid) throws Exception {
		redisUtil.delete(sid);
	}

}
