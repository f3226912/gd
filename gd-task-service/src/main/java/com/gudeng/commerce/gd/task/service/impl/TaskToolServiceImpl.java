package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.order.service.TaskService;
import com.gudeng.commerce.gd.task.service.TaskToolService;
import com.gudeng.commerce.gd.task.util.GdProperties;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2015年12月8日 下午3:08:33
 */
public class TaskToolServiceImpl implements TaskToolService {
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static TaskService taskService;

	protected TaskService getHessianService() throws ServiceException {
		try {
			String url = gdProperties.getProperties().getProperty("gd.taskService.url");
			if (taskService == null) {
				HessianProxyFactory factory = new HessianProxyFactory();
				factory.setOverloadEnabled(true);
				taskService = (TaskService) factory.create(TaskService.class, url);
			}
			return taskService;
		} catch (MalformedURLException e) {
			throw new ServiceException("获取hessian服务失败", e);
		}
	}

	@Override
	public Integer addTask(TaskDTO taskInfo) throws ServiceException {
		return getHessianService().addTask(taskInfo);
	}

	@Override
	public Integer addTimeTask(TaskDTO taskInfo) throws ServiceException {
		// TODO Auto-generated method stub
		return getHessianService().addTimeTask(taskInfo);
	}

	@Override
	public Integer updateTask(TaskDTO taskInfo) throws ServiceException {
		// TODO Auto-generated method stub
		return getHessianService().updateTask(taskInfo);
	}

	@Override
	public List<TaskDTO> takeOutTask(TaskDTO queryVO) throws ServiceException {
		// TODO Auto-generated method stub
		return getHessianService().takeOutTask(queryVO);
	}

	@Override
	public TaskDTO queryTask(Long taskId) throws ServiceException {
		// TODO Auto-generated method stub
		return getHessianService().queryTask(taskId);
	}

}
