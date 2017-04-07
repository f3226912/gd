package com.gudeng.commerce.gd.task.common.job;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.ServiceTaskFailException;
import com.gudeng.commerce.gd.exception.ServiceTaskLockTimeException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.order.enm.ETaskStatus;
import com.gudeng.commerce.gd.task.util.StringUtil;

/**
 * 任务执行代理实现类
 * 
 * @author  panmin
 * @version  [版本号, 2014-9-13]
 * @since  [产品/模块版本]
 */
public class ExecuteProxy extends AbstractExecuteServiceImpl {
	
	private ExecuteService executeService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteProxy.class);

	public void setExecuteService(ExecuteService executeService) {
		this.executeService = executeService;
	}

	@Override
	public void execTask(TaskDTO task) throws ServiceException {
		TaskDTO taskInfo = null;
		try {
			taskInfo = taskToolService.queryTask(task.getTaskId());
			taskInfo.setRunTime(new Date());
			if (taskInfo.getCount() == null) {
				taskInfo.setCount(1);
			} else {
				taskInfo.setCount(taskInfo.getCount() + 1);
			}
			LOGGER.debug("执行任务，次数加1，任务ID=" + taskInfo.getTaskId());
			taskToolService.updateTask(taskInfo);
			
			// 任务处理
			executeService.execTask(taskInfo);
			
			taskInfo.setTaskStatus(ETaskStatus.FINISH.getCode());
		} catch (ServiceTaskFailException e) {
			processException(e, taskInfo);
			taskInfo.setTaskStatus(ETaskStatus.FAIL.getCode());
			throw e;
		} catch (ServiceTaskLockTimeException e) {
			processException(e, taskInfo);
			taskInfo.setTaskStatus(ETaskStatus.LOCK.getCode());
			throw e;
		}  catch (ServiceException e) {
			processException(e, taskInfo);
			taskInfo.setTaskStatus(ETaskStatus.EXCEPTION.getCode());
			throw e;
		} catch (Exception e) {
			processException(e, taskInfo);
			taskInfo.setTaskStatus(ETaskStatus.EXCEPTION.getCode());
			throw new ServiceException(e);
		} finally {
			LOGGER.debug("执行任务，更新任务状态值，任务ID=" + taskInfo.getTaskId());
			taskToolService.updateTask(taskInfo);
		}
	}
	
	private void processException(Exception e, TaskDTO taskInfo) {
		Exception ex = (Exception) getDeepestCause(e);
		if (ex instanceof ServiceException) {
			ServiceException sex = (ServiceException) ex;
			taskInfo.setErrInfo(StringUtil.replaceEmpty(sex.getErrCode()) + sex.getMessage());
		} else {
			taskInfo.setErrInfo(ex.toString());
		}
	}
	
	/** 
	 * 返回此异常的根本原因
	 */
	private Throwable getDeepestCause(Throwable throwable) {
		if (throwable == null)
			return null;
		Throwable cause = throwable.getCause();
		if (cause == null)
			return throwable;
		return getDeepestCause(cause);
	}

}
