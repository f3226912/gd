package com.gudeng.commerce.gd.notify.pay;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.notify.service.PayResultToolService;
import com.gudeng.commerce.gd.notify.service.TaskToolService;
import com.gudeng.commerce.gd.notify.util.GdProperties;

public class StatisPaymentBusinessTask {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private PayResultToolService payResultToolService;
	
	@Resource
	private TaskToolService taskToolService;
	
	@Resource
	private GdProperties gdProperties;

	public void paymentDayStatis() {
		
	}

}
