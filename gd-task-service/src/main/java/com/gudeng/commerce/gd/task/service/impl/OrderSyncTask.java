package com.gudeng.commerce.gd.task.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.task.common.job.AbstractTask;
import com.gudeng.commerce.gd.task.common.job.ExecuteService;

public class OrderSyncTask extends AbstractTask<Boolean> {

	@Resource(name = "orderSyncExecuteService")
	private ExecuteService executeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSyncTask.class);
	
	@Override
	public Boolean call() throws Exception {
		try {
			executeProxy.setExecuteService(executeService);
			executeProxy.execTask(taskInfo);
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage(),e);
			return false;
		}
		return true;
	}

}
