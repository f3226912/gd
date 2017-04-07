package com.gudeng.commerce.gd.task.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.task.common.job.AbstractExecuteServiceImpl;
import com.gudeng.commerce.gd.task.service.OrderSubToolService;
import com.gudeng.commerce.gd.task.util.EmailUtil;

/**
 * 发送邮件任务执行实现类
 * 
 * @author  panmin
 * @version  [版本号, 2014-9-13]
 * @since  [产品/模块版本]
 */
public class SendEmailExecuteServiceImpl extends AbstractExecuteServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailExecuteServiceImpl.class);
	
	@Autowired
	private OrderSubToolService orderSubToolService;

	@Override
	public void execTask(TaskDTO taskInfo) throws ServiceException {
		if (StringUtils.isBlank(taskInfo.getReceiveUsers()) || StringUtils.isBlank(taskInfo.getSubject()) || StringUtils.isBlank(taskInfo.getSendContent())) {
			LOGGER.debug("邮件信息不能为空，任务ID=" + taskInfo.getTaskId());
			throw new ServiceException("邮件信息不能为空");
		} else {
			List<String> receiveUsers = Arrays.asList(taskInfo.getReceiveUsers().split(","));
			EmailUtil.doSendHtmlEmail(taskInfo.getSubject(), taskInfo.getSendContent(), receiveUsers);
			
			taskInfo.setErrInfo("已完成");
		}
	}

}
