package com.gudeng.commerce.gd.task.util.systemlog;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.PreSaleDTO;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;
import com.gudeng.commerce.gd.task.util.GdProperties;

@Aspect
@Component
public class OrderAopUtil {
	
	@Autowired
	public GdProperties gdProperties;
	
	private static SystemLogService systemLogService;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(OrderAopUtil.class);
	
	/**
	 * 功能描述:日志服务
	 */
	protected SystemLogService getHessianSystemLogService()
			throws MalformedURLException {
		String url = gdProperties.getSystemLogUrl();
		if (systemLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			systemLogService = (SystemLogService) factory.create(
					SystemLogService.class, url);
		}
		return systemLogService;
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.task.service.impl.OrderSubToolServiceImpl.handleOrderSubAmtToDB(..))")
	public void handleOrderSubAmtToDB(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			String argsStr = objects == null ? "" : Arrays.toString(objects);
			String content = "触发计算订单补贴额，订单信息" + argsStr;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("4");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId("SYS");
			entity.setType(SystemLogTypeEnum.ORDER.getKey());
			entity.setOperationId(Long.valueOf(argsStr));
			getHessianSystemLogService().insertLog(entity);
		} catch (Exception e) {
			LOGGER.error("记录业务日志失败", e);
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.task.service.impl.OrderSubToolServiceImpl.checkOrderSubRuleToDB(..))")
	public void checkOrderSubRuleToDB(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		try {
			String argsStr = objects == null ? "" : Arrays.toString(objects);
			String content = "触发补贴规则审核，订单信息" + argsStr;
			
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("4");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId("SYS");
			entity.setType(SystemLogTypeEnum.ORDER.getKey());
			entity.setOperationId(Long.valueOf(argsStr));
			getHessianSystemLogService().insertLog(entity);
		} catch (Exception e) {
			LOGGER.error("记录业务日志失败", e);
		}
	}
	
	/**
	 * 订单任务操作日志
	 * @param jp
	 */										
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.task.service.impl.OrderBaseToolServiceImpl.updateByOrderNo(com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO))")
	public void orderTaskPay(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		OrderBaseinfoDTO order=(OrderBaseinfoDTO) objects[0];
		Long orderNo=order.getOrderNo();
		Long id=Long.valueOf(order.getPersaleId());
		try {
			String content ="系统SYS自动为订单表订单号是"+orderNo+"状态设置已完成";
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("4");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId("SYS");
			entity.setOperationId(id);
			entity.setType(SystemLogTypeEnum.ORDER.getKey());
			Long l=getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 订单任务操作日志
	 * @param jp
	 */
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.task.service.impl.PreSaleToolServiceImpl.updateDTO(..))")
	public void orderTaskAutoOrderRevoke(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		PreSaleDTO preSale=(PreSaleDTO) objects[0];
		Long orderNo=preSale.getOrderNo();
		Long id=Long.valueOf(preSale.getId());
		try {
			String content ="系统SYS自动为预销售表订单号是"+orderNo+"状态设置已取消";
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("4");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId("SYS");
			entity.setOperationId(id);
			entity.setType(SystemLogTypeEnum.ORDER.getKey());
			Long l=getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
