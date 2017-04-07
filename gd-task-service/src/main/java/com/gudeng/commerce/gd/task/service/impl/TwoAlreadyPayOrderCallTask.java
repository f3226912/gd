package com.gudeng.commerce.gd.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.task.service.OrderBaseToolService;

/**
 * 查询2小时后已经确认付款，审核通过，但是卖家没有确认
 * @author xiaojun
 *
 */
public class TwoAlreadyPayOrderCallTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(AutoOrderRevokeCallTask.class);
	
	@Autowired
	private OrderBaseToolService orderBaseToolService;
	
	public  void invoke(){
		//
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			List<OrderBaseinfoDTO> list=orderBaseToolService.getTwoAlreadyPayOrderList(map);
			
			for (OrderBaseinfoDTO orderBaseinfoDTO : list) {
				orderBaseinfoDTO.setOrderStatus("10");
				orderBaseToolService.updateByOrderNo(orderBaseinfoDTO);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("订单设置已完成失败");
			e.printStackTrace();
		}
	}
}
