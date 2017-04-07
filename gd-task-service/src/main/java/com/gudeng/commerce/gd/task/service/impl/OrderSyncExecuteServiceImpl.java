package com.gudeng.commerce.gd.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.ServiceTaskFailException;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.task.common.job.AbstractExecuteServiceImpl;
import com.gudeng.commerce.gd.task.service.OrderBaseToolService;
import com.gudeng.commerce.gd.task.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.task.util.MqUtil;

public class OrderSyncExecuteServiceImpl extends AbstractExecuteServiceImpl {
	
	@Resource
	private OrderBaseToolService orderBaseToolService;
	
	@Resource
	private PaySerialnumberToolService paySerialnumberToolService;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSyncExecuteServiceImpl.class);

	@Override
	public void execTask(TaskDTO taskInfo) throws ServiceException {
		if(taskInfo.getOrderNumber() == null){
			throw new ServiceException("订单号不能为空");
		}
		String payCenterNumber = taskInfo.getOrderNumber();
		//查找支付流水
		Map<String,Object> map = new HashMap<>();
		map.put("statementId", payCenterNumber);
		List<PaySerialnumberDTO> list = null;
		try {
			list = paySerialnumberToolService.getListByCondition(map);
		} catch (Exception e1) {
			throw new ServiceException("获取支付流["+payCenterNumber+"]失败");
		}
		if(null == list || list.size() == 0){
			throw new ServiceTaskFailException("支付流水["+payCenterNumber+"]不存在");
		}
//		OrderBaseinfoDTO dto = null;
//		long orderNo = list.get(0).getOrderNo();
//		try {
//			dto = orderBaseToolService.getByOrderNo(orderNo);
//		} catch (Exception e) {
//			throw new ServiceTaskFailException("获取订单["+orderNo+"]失败");
//		}
//		if(dto == null){
//			throw new ServiceException("订单号["+orderNo+"]不存在");
//		}
		//发送数据格式  支付中心流水号#订单号
		long orderNo = list.get(0).getOrderNo();
		String msg = payCenterNumber+"#"+orderNo;
		LOGGER.info("**********************OrderSync开始**********************************");
		if(MqUtil.send(msg, 3)){
			//发送成功
			LOGGER.info("往支付中心发送消息["+msg+"]成功");
		} else {
			LOGGER.error("往支付中心发送消息["+msg+"]失败");
			throw new ServiceTaskFailException("往支付中心发送消息["+msg+"]失败");
		}
		
	}

}
