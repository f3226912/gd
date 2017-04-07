package com.gudeng.commerce.gd.task.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.service.PushTaskService;
import com.gudeng.commerce.gd.task.service.AdTaskService;

public class AdTaskServiceImpl implements AdTaskService {
	
	public Logger logger = LoggerFactory.getLogger(AdTaskServiceImpl.class);
	
	@Autowired
	private PushTaskService pushTaskService;

	@Override
	public void HandleExpireAd() throws Exception {
		
		Map<String,Integer> resultMap = pushTaskService.processAd(2);
		logger.info("---------------->处理过期广告"+resultMap.get("expiredAd")+"个");
		logger.info("---------------->   上线广告"+resultMap.get("startAd")+"个");
	}

}
