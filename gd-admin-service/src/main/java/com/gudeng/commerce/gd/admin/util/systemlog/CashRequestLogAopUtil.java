package com.gudeng.commerce.gd.admin.util.systemlog;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

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

/**
 * 提现日志AOP
 * @author xiaojun
 */
@Aspect
@Component
public class CashRequestLogAopUtil {

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

	/**
	 * 提款操作日志
	 * @param jp
	 */
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.CashRequestToolServiceImpl.flowDisposeAmt(java.util.Map))")
	public void logAccTransInfoAdd(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		Map<String, Object> map=(Map<String, Object>) objects[0];
		String createUserId=(String) map.get("createUserId");
		Integer accid=(Integer) map.get("accId");
		Double cashAmount=(Double) map.get("cashAmount");
		String crId= (String) map.get("cashRequestId");
		Long cashRequestId= Long.parseLong(crId);
		try {
			String content ="id为" + createUserId + "的系统用户对账户为"+accid+"进行了提款操作," +"提款金额为："+cashAmount+"元";
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(createUserId);
			entity.setOperationId(cashRequestId);
			entity.setType(SystemLogTypeEnum.WALLET.getKey());
			Long l=getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
