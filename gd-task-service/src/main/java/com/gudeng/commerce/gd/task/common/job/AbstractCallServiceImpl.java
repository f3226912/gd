package com.gudeng.commerce.gd.task.common.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.task.service.TaskToolService;

/**
 * 任务执行抽象类
 * 
 * @author panmin
 * @version [版本号, 2014-9-13]
 * @since [产品/模块版本]
 */
public abstract class AbstractCallServiceImpl implements CallService {

	@Autowired
	protected TaskToolService taskToolService;

}
