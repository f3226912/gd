package com.gudeng.commerce.gd.task.common.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.order.enm.ETaskStatus;
import com.gudeng.commerce.gd.order.enm.ETaskType;

/**
 * 定时任务实现类
 * 
 * @author  panmin
 * @version  [版本号, 2014-9-25]
 * @since  [产品/模块版本]
 */
public class TimeTaskCallServiceImpl extends AbstractCallServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeTaskCallServiceImpl.class); 

	@Override
	public void invoke() throws ServiceException {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setQryTaskType(ETaskType.SEND_SMS.getCode());
		taskDTO.setQryTaskStatus(ETaskStatus.LOCK.getCode());
		taskDTO.setTaskStatus(ETaskStatus.INIT.getCode());
		
		Integer total = taskToolService.updateTask(taskDTO);
		LOGGER.info("定时任务启动数量=" + total);
	}

}
