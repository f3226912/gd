package com.gudeng.commerce.gd.admin.util.systemlog;

import java.net.MalformedURLException;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@Component
@Aspect
public class OrderLogAop {
	@Autowired
	public GdProperties gdProperties;
	
	private static SystemLogService systemLogService;
	
	/**
	 * 功能描述:日志服务
	 * 
	 * @param
	 * @return
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
	
	
	// 审核订单
	@AfterReturning(returning = "rvt", pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.OrderBaseinfoToolServiceImpl.orderExamine(..))")
	public void updateOrder(JoinPoint jp, Object rvt) {
		try {
			Integer resultFlag = (Integer)rvt;
			if(resultFlag > 0){
				Object[] objects = jp.getArgs();
				if (objects.length > 0) {
					Long orderNo = (Long) objects[1];
					String type = (String) objects[2];
					String description = (String) objects[3];
					String statementId = (String) objects[4];
					String updateUserId = (String) objects[5];
					String tempstr = "";
					if("1".equals(type)){
						tempstr = "订单审核通过,银行流水号:" + statementId;
					}else if("2".equals(type)){
						tempstr = "订单审核驳回,驳回原因:" + description;
					}else if("3".equals(type)){
						tempstr = "订单修改流水号,流水号:" + statementId;
					}
					
					String content = "订单号:" + orderNo + "," + tempstr;
					SystemLogEntity entity = new SystemLogEntity();
					entity.setOperationId(orderNo);
					entity.setChennel("1");
					entity.setContent(content);
					entity.setCreateTime(new Date());
					entity.setCreateUserId(updateUserId);
					entity.setType(SystemLogTypeEnum.ORDER.getKey());
					getHessianSystemLogService().insertLog(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
