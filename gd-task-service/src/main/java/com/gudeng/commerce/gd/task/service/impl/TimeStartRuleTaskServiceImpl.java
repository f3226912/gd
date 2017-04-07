package com.gudeng.commerce.gd.task.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.service.SubLimitRuleService;
import com.gudeng.commerce.gd.order.service.SubPayRuleService;
import com.gudeng.commerce.gd.task.service.TimeStartRuleTaskService;

public class TimeStartRuleTaskServiceImpl implements TimeStartRuleTaskService {

	public Logger logger = LoggerFactory.getLogger(TimeStartRuleTaskServiceImpl.class);
	@Autowired
	private SubPayRuleService subRulePaytService;
	
	@Autowired
	private SubLimitRuleService subLimitRuleService;
	
	@Override
	public void timeStartRule() throws Exception {
		
		int count = subRulePaytService.startRule();
		logger.info(StringUtils.center(count+"",30, "="));
	}

	/**
	 * 过期补贴限制规则
	 */
	@Override
	public void ExpireLimitRule() throws Exception {
		int ExpireCount = subLimitRuleService.ExpireLimitRule();
		logger.info(StringUtils.center(ExpireCount+"",30, "="));
	}

	
	
	

}
