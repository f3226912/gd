package com.gudeng.commerce.gd.task.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.order.enm.ETaskStatus;
import com.gudeng.commerce.gd.order.enm.ETaskType;
import com.gudeng.commerce.gd.task.common.job.AbstractCallServiceImpl;

/**
 * @Description: TODO(补贴规则审核定时服务实现类)
 * @author mpan
 * @date 2015年12月22日 上午11:07:12
 */
public class SubLimitRuleTimeServiceImpl extends AbstractCallServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubLimitRuleTimeServiceImpl.class); 

	@Override
	public void invoke() throws ServiceException {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setSubLimitRuleFlag("1"); // 补贴限制规则标志 1是 0否
		taskDTO.setQryTaskType(ETaskType.SUB.getCode());
		taskDTO.setQryTaskStatus(ETaskStatus.LOCK.getCode());
		taskDTO.setTaskStatus(ETaskStatus.INIT.getCode());
		
		Integer total = taskToolService.updateTask(taskDTO);
		LOGGER.info("补贴定时任务启动数量=" + total);
	}

}
