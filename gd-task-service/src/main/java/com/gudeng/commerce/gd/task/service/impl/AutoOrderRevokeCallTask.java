package com.gudeng.commerce.gd.task.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dto.PreSaleDTO;
import com.gudeng.commerce.gd.task.service.PreSaleToolService;
import com.gudeng.commerce.gd.task.util.GdProperties;
import com.gudeng.commerce.gd.task.util.SpringContextUtil;

/**
 * 定时跑48小时后的订单，状态为待付款，自动设为已作废
 * @author xiaojun
 *
 */
public class AutoOrderRevokeCallTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(AutoOrderRevokeCallTask.class);
	
	@Autowired
	private PreSaleToolService preSaleToolService;
	
	private static final String TASK_OVER_TIME = "TASK_OVER_TIME";
	

	public  void invoke(){
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			//获取properties配置文件
			GdProperties gdProperties = (GdProperties) SpringContextUtil.getBean("gdProperties");
			int overtime = Integer.parseInt(gdProperties.getProperties().getProperty(TASK_OVER_TIME));
			//获取当天减掉设置的日期
			String afterSubTime=dateSubDay(overtime);
			
			if (StringUtils.isNotEmpty(afterSubTime)) {
				
				map.put("afterSubTime", afterSubTime);
				
				//查出预销售表中48小时没有扫单的
				List<PreSaleDTO> list =preSaleToolService.getOverPreSale(map);
				
				
				if (list!=null) {
					for (PreSaleDTO obj : list) {
						
						//设置状态为3 已取消
						obj.setOrderStatus("3");
						
						//更新
						preSaleToolService.updateDTO(obj);
					}
				}
				/*List<OrderBaseinfoDTO> list=orderBaseToolService.getOverOrderInfoList(map);
				
				for (OrderBaseinfoDTO orderBaseinfoDTO : list) {
					
					//更新查询后的每个订单的状态
					orderBaseToolService.cancelOverOrderByOrderNo(orderBaseinfoDTO.getOrderNo());
				}*/
			}
		} catch (Exception e) {
			LOGGER.error("预售单48小时未被自动失效 失败！！");
			e.printStackTrace();
		}
		
	}

	/**
	 * 当前日期减天数
	 * @param overtime
	 * @return
	 */
	private String dateSubDay(int overtime){
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - overtime);
		String afterSubTime=dft.format(date.getTime());
		return afterSubTime;
	}
}
