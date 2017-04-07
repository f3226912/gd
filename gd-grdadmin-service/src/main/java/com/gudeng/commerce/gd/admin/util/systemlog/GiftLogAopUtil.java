package com.gudeng.commerce.gd.admin.util.systemlog;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.GiftDTO;
import com.gudeng.commerce.gd.customer.entity.GiftEntity;
import com.gudeng.commerce.gd.order.service.CashRequestService;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

/**
 * 礼品信息日志AOP
 * @author liuliu
 */
@Aspect
@Component
public class GiftLogAopUtil {

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
	 * 礼品添加操作日志
	 * @param jp
	 */
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.GiftToolServiceImpl.add(com.gudeng.commerce.gd.customer.entity.GiftEntity))")
	public void giftAdd(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		GiftEntity giftEntity=(GiftEntity)objects[0];
		try {
			String content ="id为" + getMember().getUserID() 
					+ "的系统用户新增了礼品信息,添加信息如下，礼品名称:"+giftEntity.getName()+ ",礼品积分:"
					+giftEntity.getIntegral()+ ",状态:"
					+giftEntity.getType()+ ",活动:"
					+giftEntity.getActivityId();
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			Long l=getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 礼品添加修改日志
	 * @param jp
	 */
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.GiftToolServiceImpl.update(com.gudeng.commerce.gd.customer.dto.GiftDTO))")
	public void giftEdit(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		GiftDTO giftDTO=(GiftDTO)objects[0];
		try {
			String content ="id为" + getMember().getUserID() 
					+ "的系统用户修改了礼品信息,修改信息如下，礼品名称:"+giftDTO.getName()+ ",礼品积分:"
					+giftDTO.getIntegral()+ ",状态:"
					+giftDTO.getType()+ ",活动:"
					+giftDTO.getActivityId();
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			Long l=getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private SysRegisterUser getMember() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Object user= request.getSession().getAttribute(com.gudeng.commerce.gd.authority.sysmgr.util.Constant.SYSTEM_SMGRLOGINUSER);
		return (SysRegisterUser)user;
	}
}
