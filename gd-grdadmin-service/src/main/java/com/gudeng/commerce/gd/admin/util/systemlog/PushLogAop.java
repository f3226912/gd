package com.gudeng.commerce.gd.admin.util.systemlog;

import java.net.MalformedURLException;
import java.util.Date;

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
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.customer.entity.PushAdInfoEntity;
import com.gudeng.commerce.gd.customer.entity.PushNoticeEntity;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@Component
@Aspect
public class PushLogAop {
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
	
	
	// 添加广告
	@AfterReturning(returning = "rvt", pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.PushAdInfoToolServiceImpl.insertEntity(..))")
	public void insertAdInfo(JoinPoint jp, Object rvt) {
		try {
			Long resultFlag = (Long)rvt;
			if(resultFlag > 0){
				Object[] objects = jp.getArgs();
				if (objects.length > 0) {
					PushAdInfoEntity obj = (PushAdInfoEntity) objects[0];
					HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
					SysRegisterUser user= (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
					String content = "添加广告:广告名称:" + obj.getAdName() + ",广告渠道:" + obj.getAdCanal() + 
							",广告类型:" + obj.getAdType() + ",市场ID:" + obj.getMarketId() + ",排序:" + obj.getSort();
					SystemLogEntity entity = new SystemLogEntity();
					entity.setOperationId(obj.getId());
					entity.setChennel("1");
					entity.setContent(content);
					entity.setCreateTime(new Date());
					entity.setCreateUserId(user.getUserID());
					entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
					getHessianSystemLogService().insertLog(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 删除广告
	@AfterReturning(returning = "rvt", pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.PushAdInfoToolServiceImpl.deleteById(..))")
	public void deleteAdInfo(JoinPoint jp, Object rvt) {
		try {
			Integer resultFlag = (Integer)rvt;
			if(resultFlag > 0){
				Object[] objects = jp.getArgs();
				if (objects.length > 0) {
					HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
					SysRegisterUser user= (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
					String content = "删除广告:ID:" + objects[0];
					SystemLogEntity entity = new SystemLogEntity();
					entity.setOperationId(Long.parseLong(objects[0].toString()));
					entity.setChennel("1");
					entity.setContent(content);
					entity.setCreateTime(new Date());
					entity.setCreateUserId(user.getUserID());
					entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
					getHessianSystemLogService().insertLog(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 修改广告
	@AfterReturning(returning = "rvt", pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.PushAdInfoToolServiceImpl.updateDTO(..))")
	public void updateAdInfo(JoinPoint jp, Object rvt) {
		try {
			Integer resultFlag = (Integer)rvt;
			if(resultFlag > 0){
				Object[] objects = jp.getArgs();
				if (objects.length > 0) {
					PushAdInfoDTO obj = (PushAdInfoDTO) objects[0];
					HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
					SysRegisterUser user= (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
					String content = "修改广告:广告名称:" + obj.getAdName() + ",广告渠道:" + obj.getAdCanal() + 
							",广告类型:" + obj.getAdType() + ",市场ID:" + obj.getMarketId() + ",排序:" + obj.getSort();
					SystemLogEntity entity = new SystemLogEntity();
					entity.setOperationId(obj.getId());
					entity.setChennel("1");
					entity.setContent(content);
					entity.setCreateTime(new Date());
					entity.setCreateUserId(user.getUserID());
					entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
					getHessianSystemLogService().insertLog(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 添加公告
	@AfterReturning(returning = "rvt", pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.PushNoticeToolServiceImpl.insertEntity(..))")
	public void insertNotice(JoinPoint jp, Object rvt) {
		try {
			Long resultFlag = (Long)rvt;
			if(resultFlag > 0){
				Object[] objects = jp.getArgs();
				if (objects.length > 0) {
					PushNoticeEntity obj = (PushNoticeEntity) objects[0];
					HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
					SysRegisterUser user= (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
					String content = "添加广告:公告名称:" + obj.getTitle();
					SystemLogEntity entity = new SystemLogEntity();
					entity.setOperationId(obj.getId());
					entity.setChennel("1");
					entity.setContent(content);
					entity.setCreateTime(new Date());
					entity.setCreateUserId(user.getUserID());
					entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
					getHessianSystemLogService().insertLog(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 删除公告
	@AfterReturning(returning = "rvt", pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.PushNoticeToolServiceImpl.deleteById(..))")
	public void deleteNotice(JoinPoint jp, Object rvt) {
		try {
			Integer resultFlag = (Integer)rvt;
			if(resultFlag > 0){
				Object[] objects = jp.getArgs();
				if (objects.length > 0) {
					HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
					SysRegisterUser user= (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
					String content = "删除广告:ID:" + objects[0];
					SystemLogEntity entity = new SystemLogEntity();
					entity.setOperationId(Long.parseLong(objects[0].toString()));
					entity.setChennel("1");
					entity.setContent(content);
					entity.setCreateTime(new Date());
					entity.setCreateUserId(user.getUserID());
					entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
					getHessianSystemLogService().insertLog(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 修改公告
	@AfterReturning(returning = "rvt", pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.PushNoticeToolServiceImpl.updateDTO(..))")
	public void updateNotice(JoinPoint jp, Object rvt) {
		try {
			Integer resultFlag = (Integer)rvt;
			if(resultFlag > 0){
				Object[] objects = jp.getArgs();
				if (objects.length > 0) {
					PushNoticeDTO obj = (PushNoticeDTO) objects[0];
					HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
					SysRegisterUser user= (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
					String content = "修改广告:公告名称:" + obj.getTitle();
					SystemLogEntity entity = new SystemLogEntity();
					entity.setOperationId(obj.getId());
					entity.setChennel("1");
					entity.setContent(content);
					entity.setCreateTime(new Date());
					entity.setCreateUserId(user.getUserID());
					entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
					getHessianSystemLogService().insertLog(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
