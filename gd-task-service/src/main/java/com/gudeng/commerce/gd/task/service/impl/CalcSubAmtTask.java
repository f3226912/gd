package com.gudeng.commerce.gd.task.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.task.common.job.AbstractTask;
import com.gudeng.commerce.gd.task.common.job.ExecuteService;

/**
 * 计算补贴金额任务
 * 
 * @author panmin
 * @version [版本号, 2014-9-12]
 * @since [产品/模块版本]
 */
public class CalcSubAmtTask extends AbstractTask<Boolean> {

	@Resource(name = "calcSubAmtExecuteService")
	private ExecuteService executeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(CalcSubAmtTask.class);

	@Override
	public Boolean call() {
		try {
			executeProxy.setExecuteService(executeService);
			executeProxy.execTask(taskInfo);
		} catch (ServiceException e) {
			LOGGER.error("计算补贴金额任务错误", e);
			return false;
		}
		return true;
	}

	public void setTaskInfo(TaskDTO taskInfo) {
		this.taskInfo = taskInfo;
	}
	
}
