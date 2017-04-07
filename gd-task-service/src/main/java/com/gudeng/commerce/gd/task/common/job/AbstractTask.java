/*
 * 文 件 名:  OrderSyncTask.java
 * 版    权:  Tydic Technologies Co., Ltd. Copyright 1993-2012,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  panmin
 * 修改时间:  2014-9-12
 * 跟踪单号:  <需求跟踪单号>
 * 修改单号:  <需求修改单号>
 * 修改内容:  <修改内容>
 */
package com.gudeng.commerce.gd.task.common.job;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dto.TaskDTO;

/**
 * 抽象任务
 * 
 * @author panmin
 * @version [版本号, 2014-9-12]
 * @since [产品/模块版本]
 */
public abstract class AbstractTask<T> implements Callable<T> {

	protected TaskDTO taskInfo;
	
	@Autowired
	protected ExecuteProxy executeProxy;

	public void setTaskInfo(TaskDTO taskInfo) {
		this.taskInfo = taskInfo;
	}

}
