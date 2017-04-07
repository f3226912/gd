package com.gudeng.commerce.gd.task.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.task.service.NstAutoCancleOrderTaskService;
import com.gudeng.commerce.gd.task.service.NstOrderToolService;

public class NstAutoCancleOrderTaskServiceImpl implements NstAutoCancleOrderTaskService {

private static final Logger logger = LoggerFactory.getLogger(NstComfirmOrderTaskServiceImpl.class); 

	@Autowired
	private NstOrderToolService nstOrderToolService;
	
	@Override
	public void call() throws Exception {
		logger.info("===============农速通订单20分钟自动取消任务开始===============");
		nstOrderToolService.autoCancelOrderBySameCity();
		logger.info("===============农速通订单20分钟自动取消任务结束===============");
	}
}
