package com.gudeng.commerce.gd.order.service;

import java.util.List;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;

/**
 * 任务接口类
 * 
 * @author  panmin
 * @version  [版本号, 2014-9-12]
 * @since  [产品/模块版本]
 */
public interface TaskService {
	
	/** 
	 * 添加任务
	 */
	public Integer addTask(TaskDTO taskInfo) throws ServiceException;
	
	/** 
	 * 批量添加任务
	 */
	public Integer addTaskBatch(List<TaskDTO> taskInfos) throws ServiceException;
	
	/** 
	 * 添加定时任务
	 */
	public Integer addTimeTask(TaskDTO taskInfo) throws ServiceException;
	
	/** 
	 * 批量添加定时任务
	 */
	public Integer addTimeTaskBatch(List<TaskDTO> taskInfos) throws ServiceException;
	
	/** 
	 * 更新任务
	 */
	public Integer updateTask(TaskDTO taskInfo) throws ServiceException;
	
	/** 
	 * 取出任务
	 */
	public List<TaskDTO> takeOutTask(TaskDTO queryVO) throws ServiceException;
	
	/** 
	 * 查询任务
	 */
	public TaskDTO queryTask(Long taskId) throws ServiceException;

}
