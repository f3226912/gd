package com.gudeng.commerce.gd.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.order.enm.EHasSubBalance;
import com.gudeng.commerce.gd.order.enm.ETaskStatus;
import com.gudeng.commerce.gd.order.enm.ETaskType;
import com.gudeng.commerce.gd.order.entity.SubAmountEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.SubAmountService;
import com.gudeng.commerce.gd.order.service.TaskService;
import com.gudeng.commerce.gd.order.util.DateUtil;
import com.gudeng.commerce.gd.order.util.SpringContextUtil;
import com.gudeng.framework.dba.util.DalUtils;

/**
 * 任务实现类
 * 
 * @author panmin
 * @version [版本号, 2014-9-12]
 * @since [产品/模块版本]
 */
public class TaskServiceImpl implements TaskService {

	@Autowired
	private BaseDao<Integer> baseDao;
	
	@Autowired
	private SubAmountService subAmountService;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Override
	public Integer addTask(TaskDTO taskInfo) throws ServiceException {
		if (ETaskType.SUB.getCode().equals(taskInfo.getTaskType()) && StringUtils.isNotBlank(taskInfo.getOrderNumber())) {
			try {
				OrderBaseinfoService orderBaseinfoService = (OrderBaseinfoService) SpringContextUtil.getBean("orderBaseinfoService");
				OrderBaseinfoDTO orderBase = orderBaseinfoService.getByOrderNo(Long.valueOf(taskInfo.getOrderNumber()));
				SubAmountEntity subAmount = subAmountService.getByMarketId(orderBase.getMarketId());
				if (EHasSubBalance.NO.getCode().equals(subAmount.getHasSubBalance())) {
					LOGGER.info("市场补贴额已发放完毕，不能新增补贴任务" + taskInfo.getOrderNumber());
					return null;
				}
			} catch (Exception e) {
				LOGGER.error("查询市场补贴额信息失败", e);
			}
		}
		if (taskInfo != null)
			taskInfo.setTaskStatus(ETaskStatus.INIT.getCode());
		if (taskInfo.getPayTime() != null) {
			taskInfo.setPayTimeStr(DateUtil.toString(taskInfo.getPayTime(), DateUtil.DATE_FORMAT_DATETIME));
		}
		return baseDao.execute("Task.save", taskInfo);
	}
	
	@Override
	public Integer updateTask(TaskDTO taskInfo) throws ServiceException {
		return baseDao.execute("Task.update", transMap(taskInfo));
	}
	
	private Map<String, Object> transMap(TaskDTO taskInfo) {
		Map<String, Object> map = DalUtils.convertToMap(taskInfo);
		if (CollectionUtils.isNotEmpty(taskInfo.getQryTaskStatusIn())) {
			map.put("qryTaskStatusIn", taskInfo.getQryTaskStatusIn());
		}
		return map;
	}
	
	@Override
	public List<TaskDTO> takeOutTask(TaskDTO queryVO) throws ServiceException {
		return baseDao.queryForList("Task.queryTaskList", transMap(queryVO), TaskDTO.class);
	}

	@Override
	public TaskDTO queryTask(Long taskId) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		return baseDao.queryForObject("Task.queryTaskList", map, TaskDTO.class);
	}

	@Override
	public Integer addTaskBatch(List<TaskDTO> taskInfos) throws ServiceException {
		return addTaskBatch(taskInfos, ETaskStatus.INIT.getCode());
	}
	
	@Override
	public Integer addTimeTask(TaskDTO taskInfo) throws ServiceException {
		if (taskInfo != null)
			taskInfo.setTaskStatus(ETaskStatus.LOCK.getCode());
		return baseDao.execute("Task.save", taskInfo);
	}

	@Override
	public Integer addTimeTaskBatch(List<TaskDTO> taskInfos) throws ServiceException {
		return addTaskBatch(taskInfos, ETaskStatus.LOCK.getCode());
	}
	
	private Integer addTaskBatch(List<TaskDTO> taskInfos, String taskStatus) throws ServiceException {
		if (CollectionUtils.isNotEmpty(taskInfos)) {
			for (TaskDTO taskInfo : taskInfos) {
				taskInfo.setTaskStatus(taskStatus);
			}
			List<Map<String, Object>> batchValues= new ArrayList<Map<String, Object>>();
			for (TaskDTO taskInfo : taskInfos) {
				batchValues.add(DalUtils.convertToMap(taskInfo));
			}
			return baseDao.batchUpdate("Task.save", batchValues.toArray(new Map[0])).length;
		}
		return 0;
	}

}
