package com.gudeng.commerce.gd.pay.util;

import java.net.MalformedURLException;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gudeng.commerce.gd.pay.enm.SystemLogTypeEnum;
import com.gudeng.commerce.gd.pay.entity.SystemLogEntity;
import com.gudeng.commerce.gd.pay.service.SystemLogService;

@Aspect
@Component
public class OrderAopUtil {
	
	@Autowired
	private SystemLogService systemLogService;
	
	/**
	 * 订单任务操作日志
	 * @param jp
	 */										
	//@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.pay.service.impl.WangPosServiceImpl.execute(..))")
	public void orderTaskPay(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		Long orderNo = (Long)objects[0];
		try {
			String content ="系统SYS自动为订单表订单号是"+orderNo+"状态设置已完成";
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId("SYS");
			entity.setOperationId(orderNo);
			entity.setType(SystemLogTypeEnum.ORDER.getKey());
			systemLogService.insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
