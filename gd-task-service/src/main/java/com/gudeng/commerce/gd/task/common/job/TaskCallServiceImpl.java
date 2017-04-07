package com.gudeng.commerce.gd.task.common.job;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.order.enm.ETaskStatus;
import com.gudeng.commerce.gd.order.enm.ETaskSubType;
import com.gudeng.commerce.gd.order.enm.ETaskType;
import com.gudeng.commerce.gd.task.util.GdProperties;
import com.gudeng.commerce.gd.task.util.SpringContextUtil;

/**
 * 任务队列实现类
 * 
 * @author  panmin
 * @version  [版本号, 2014-9-15]
 * @since  [产品/模块版本]
 */
public class TaskCallServiceImpl extends AbstractCallServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskCallServiceImpl.class);
	
	private static final String MAXIMUM_FAIL_COUNT = "TASK_MAXIMUM_FAIL_COUNT"; // 失败尝试次数
	
	@SuppressWarnings("unchecked")
	@Override
	public void invoke() throws ServiceException {
		try {
			Thread.sleep(30000);
			// 获取线程池
			ExecutorService service = ThreadPoolSingleton.getThreadPoolInstance();
			ExecutorService singletonService = ThreadPoolSingleton.getSingletonThreadPoolInstance();
			// 获取池中的当前线程数
			ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) service;
			LOGGER.debug("任务池中的当前线程数:" + threadPoolExecutor.getPoolSize());
			// 获取队列剩余的容量
			BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
			int capacity = queue.remainingCapacity();
			LOGGER.debug("任务队列中的剩余容量:" + capacity);
			
			GdProperties gdProperties = (GdProperties) SpringContextUtil.getBean("gdProperties");
			// 任务执行条件
			List<String> taskStatusIn = new ArrayList<String>();
			taskStatusIn.add(ETaskStatus.INIT.getCode());
			taskStatusIn.add(ETaskStatus.FAIL.getCode());
			int maximumFailCount = Integer.parseInt(gdProperties.getProperties().getProperty(MAXIMUM_FAIL_COUNT));
			// 创建DTO更新对象
			TaskDTO taskDTO = new TaskDTO();
			taskDTO.setTaskStatus(ETaskStatus.RUNNING.getCode());
			taskDTO.setQryMaxFailCount(maximumFailCount);
			taskDTO.setQryTaskStatusIn(taskStatusIn);
			// 先锁定需执行的任务
			Integer total = taskToolService.updateTask(taskDTO);
			if (total < 1) {
				LOGGER.info("没有需要执行的任务");
				return;
			}
			// 再获取执行的任务列表
			TaskDTO queryVO = new TaskDTO();
			queryVO.setQryTaskStatus(ETaskStatus.RUNNING.getCode());
			List<TaskDTO> tasks = taskToolService.takeOutTask(queryVO);
			if (CollectionUtils.isEmpty(tasks)) {
				LOGGER.info("任务列表为空");
				return;
			}
			String serviceName = null;
			for (TaskDTO taskInfo : tasks) {
				serviceName = ETaskType.getServiceNameByCode(taskInfo.getTaskType());
				if (StringUtils.isBlank(serviceName)) {
					serviceName = ETaskSubType.getServiceNameByCode(taskInfo.getTaskSubType());
				}
				if (StringUtils.isNotBlank(serviceName)) {
					AbstractTask<Boolean> parentTask = (AbstractTask<Boolean>) SpringContextUtil.getBean(serviceName);
					parentTask.setTaskInfo(taskInfo);
					// 考虑到安全性问题，补贴规则验证任务使用单线程模式
					if (ETaskType.SUB.getCode().equals(taskInfo.getTaskType())) {
						singletonService.submit(parentTask);
					} else {
						service.submit(parentTask);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("job任务出现错误", e);
		}
	}
	
}
