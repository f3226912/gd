package com.gudeng.commerce.gd.task.common.job;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;

/**
 * 任务执行接口
 * 
 * @author  panmin
 * @version  [版本号, 2014-9-13]
 * @since  [产品/模块版本]
 */
public interface ExecuteService {
	
	/** 
	 * 执行任务
	 */
	public void execTask(TaskDTO taskInfo) throws ServiceException;
	
}
