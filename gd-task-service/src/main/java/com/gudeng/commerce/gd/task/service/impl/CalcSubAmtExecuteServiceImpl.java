package com.gudeng.commerce.gd.task.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.task.common.job.AbstractExecuteServiceImpl;
import com.gudeng.commerce.gd.task.service.OrderSubToolService;

/**
 * 计算补贴金额任务执行实现类
 * 
 * @author  panmin
 * @version  [版本号, 2014-9-13]
 * @since  [产品/模块版本]
 */
public class CalcSubAmtExecuteServiceImpl extends AbstractExecuteServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CalcSubAmtExecuteServiceImpl.class);
	
	@Autowired
	private OrderSubToolService orderSubToolService;

	@Override
	public void execTask(TaskDTO taskInfo) throws ServiceException {
		if (StringUtils.isBlank(taskInfo.getOrderNumber())) {
			LOGGER.debug("订单号不能为空，任务ID=" + taskInfo.getTaskId());
			throw new ServiceException("订单号不能为空");
		} else {
			orderSubToolService.handleOrderSubAmtToDB(Long.valueOf(taskInfo.getOrderNumber()));
			
			taskInfo.setErrInfo("已完成");
		}
	}

}
