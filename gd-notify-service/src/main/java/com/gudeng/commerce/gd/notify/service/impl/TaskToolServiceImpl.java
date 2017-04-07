package com.gudeng.commerce.gd.notify.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.notify.service.TaskToolService;
import com.gudeng.commerce.gd.notify.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.order.service.TaskService;

@Service
public class TaskToolServiceImpl implements TaskToolService{

	@Autowired
	private GdProperties gdProperties;
	
	private static TaskService taskService;
	
	public TaskService getHessianTaskService() throws MalformedURLException{
		if(taskService == null){
			String url = gdProperties.getTaskServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			taskService = (TaskService) factory.create(TaskService.class, url);
		}
		return taskService;
	}

	@Override
	public int addTask(TaskDTO taskInfo) throws Exception {
		return getHessianTaskService().addTask(taskInfo);
	}

}
