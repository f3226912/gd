package com.gudeng.commerce.gd.m.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author dli@gdeng.cn
 * @Description
 * @date 2016/11/18 17:51
 */
@Component
public class BeanConfig {
	@Bean(name = "taskExecutor")
	public TaskExecutor getTaskExcutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(50);
		taskExecutor.setQueueCapacity(100);
		return taskExecutor;
	}
}
