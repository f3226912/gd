package com.gudeng.commerce.gd.task.common.job;

import com.gudeng.commerce.gd.exception.ServiceException;


/**
 * job任务调度接口
 * 
 * @author  panmin
 * @version  [版本号, 2014-9-13]
 * @since  [产品/模块版本]
 */
public interface CallService {
	
	/** 
	 * 调用方法
	 */
	public void invoke() throws ServiceException;
	
}
