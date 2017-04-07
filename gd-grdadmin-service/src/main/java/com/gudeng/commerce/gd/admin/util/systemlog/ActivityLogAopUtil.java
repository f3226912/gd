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
import com.gudeng.commerce.gd.customer.dto.ActivityDTO;
import com.gudeng.commerce.gd.customer.dto.GiftDTO;
import com.gudeng.commerce.gd.customer.entity.GiftEntity;
import com.gudeng.commerce.gd.order.service.CashRequestService;
import com.gudeng.commerce.gd.supplier.entity.ProductEntity;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

/**
 * 活动信息日志AOP
 * @author liuliu
 */
@Aspect
@Component
public class ActivityLogAopUtil {

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
	 * 活动添加操作日志
	 * @param jp
	 */
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ActivityToolServiceImpl.add(com.gudeng.commerce.gd.customer.dto.ActivityDTO))")
	public void activityAdd(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		ActivityDTO activityDTO=(ActivityDTO)objects[0];
		try {
			String content ="id为" + getMember().getUserID() 
					+ "的系统用户新增了活动信息,添加信息如下，积分名称:"+activityDTO.getName()+ ",积分上限:"
					+activityDTO.getLimitIntegral()+ ",活动说明:"
					+activityDTO.getDescription()+ ",活动起始时间:"
					+activityDTO.getStrStartTime()+ ",活动结束时间:"
					+activityDTO.getStrEndTime();
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
	 * 活动修改操作日志
	 * @param jp
	 */
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ActivityToolServiceImpl.update(com.gudeng.commerce.gd.customer.dto.ActivityDTO))")
	public void activityEdit(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		ActivityDTO activityDTO=(ActivityDTO)objects[0];
		try {
			String content ="id为" + getMember().getUserID() 
					+ "的系统用户修改了活动信息,修改信息如下，积分名称:"+activityDTO.getName()+ ",积分上限:"
					+activityDTO.getLimitIntegral()+ ",活动说明:"
					+activityDTO.getDescription()+ ",活动起始时间:"
					+activityDTO.getStrStartTime()+ ",活动结束时间:"
					+activityDTO.getStrEndTime();
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
