package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.SystemLogToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;


public class SystemLogToolServiceImpl implements SystemLogToolService{
	
	@Autowired
	private GdProperties gdProperties;
	
	private static SystemLogService systemLogService;
	
	/** 记录日志 */
	private final static Logger logger = LoggerFactory.getLogger(SystemLogToolServiceImpl.class);
	
	public SystemLogService getHessianSystemLogService() throws MalformedURLException{
		if(systemLogService == null){
			String url = gdProperties.getSystemLogServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			systemLogService = (SystemLogService) factory.create(SystemLogService.class, url);
		}
		return systemLogService;
	}

	@Override
	public Long insertLog(SystemLogEntity entity) throws Exception {
		try {
			Long id = getHessianSystemLogService().insertLog(entity);
			return id;
		} catch (Exception e) {
			//失败，不处理
			logger.error("记录系统日志失败，原因:"+e.getMessage(),e);
		}
		return null;
	}

	@Override
	public Long insertOrderApiLog(String content,String orderNo) throws Exception {
		SystemLogEntity entity = new SystemLogEntity();
		entity.setChennel("3");//日志来源1.运营后台2.web端3.API4.定时任务
		entity.setContent(content);
		entity.setCreateTime(new Date());
		entity.setCreateUserId("SYS");
		entity.setOperationId(Long.parseLong(orderNo));
		entity.setType("8"); //日志类型 1产品 2会员 3信息 4商铺 5物流 6系统 7补贴 8订单 9钱包
		return insertLog(entity);
	}


}
