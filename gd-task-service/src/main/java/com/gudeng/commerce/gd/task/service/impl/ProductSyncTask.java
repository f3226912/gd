package com.gudeng.commerce.gd.task.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.task.common.job.AbstractTask;
import com.gudeng.commerce.gd.task.common.job.ExecuteService;

/**
 * @Description: TODO(商品同步任务)
 * @author mpan
 * @date 2016年3月24日 下午3:35:33
 */
public class ProductSyncTask extends AbstractTask<Boolean> {

	@Resource(name = "productSyncExecuteService")
	private ExecuteService executeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductSyncTask.class);

	@Override
	public Boolean call() {
		try {
			executeProxy.setExecuteService(executeService);
			executeProxy.execTask(taskInfo);
		} catch (ServiceException e) {
			LOGGER.error("商品同步任务错误", e);
			return false;
		}
		return true;
	}

	public void setTaskInfo(TaskDTO taskInfo) {
		this.taskInfo = taskInfo;
	}
	
}
