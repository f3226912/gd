package com.gudeng.commerce.gd.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dto.MqAsyncErrorDTO;
import com.gudeng.commerce.gd.task.common.job.AbstractTask;
import com.gudeng.commerce.gd.task.common.job.ExecuteService;
import com.gudeng.commerce.gd.task.service.MqAsyncErrorToolService;
import com.gudeng.commerce.gd.task.util.MqUtil;

/**
 * 计算补贴金额任务
 * 
 * @author panmin
 * @version [版本号, 2014-9-12]
 * @since [产品/模块版本]
 */
public class SendMQTask extends AbstractTask<Boolean> {

	@Autowired
	private MqAsyncErrorToolService mqAsyncErrorToolService;

	private static final Logger logger = LoggerFactory.getLogger(SendMQTask.class);

	@Override
	public Boolean call() {
		try {
			logger.info("===============SendMQTask 任务开始===============");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", 0);
			List<MqAsyncErrorDTO> list = mqAsyncErrorToolService.getList(map);
			if(list!=null){
				for(MqAsyncErrorDTO dto:list){
					if(MqUtil.send(dto.getContent(),dto.getType())){
						dto.setStatus(1);
						mqAsyncErrorToolService.update(dto);
					}
				}
			}
			logger.info("===============SendMQTask 任务结束===============");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


}
