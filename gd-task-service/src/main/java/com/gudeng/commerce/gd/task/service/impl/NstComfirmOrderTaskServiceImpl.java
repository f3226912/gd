package com.gudeng.commerce.gd.task.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.task.service.NstComfirmOrderTaskService;
import com.gudeng.commerce.gd.task.service.NstOrderToolService;

public class NstComfirmOrderTaskServiceImpl implements NstComfirmOrderTaskService{

	private static final Logger logger = LoggerFactory.getLogger(NstComfirmOrderTaskServiceImpl.class); 

	
	@Autowired
	private NstOrderToolService nstOrderToolService;
	
	@Override
	public void call() throws Exception {
		logger.info("===============农速通订单自动确认完成任务开始===============");
		nstOrderToolService.autoComfirmOrder();
		logger.info("===============农速通订单自动确认完成任务结束===============");
	}

}
