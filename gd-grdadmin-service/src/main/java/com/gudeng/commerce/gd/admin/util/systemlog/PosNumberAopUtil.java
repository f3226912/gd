package com.gudeng.commerce.gd.admin.util.systemlog;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessPosDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.ReBusinessPosEntity;
import com.gudeng.commerce.gd.customer.service.ReBusinessPosService;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@Aspect
@Component
public class  PosNumberAopUtil {

	@Autowired
	public GdProperties gdProperties;

//	private static MemberBaseinfoService memberBaseinfoService;

	private static SystemLogService systemLogService;

	private static ReBusinessPosService reBusinessPosService;

	
	private SysRegisterUser getMember() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Object user= request.getSession().getAttribute(com.gudeng.commerce.gd.authority.sysmgr.util.Constant.SYSTEM_SMGRLOGINUSER);
		return (SysRegisterUser)user;
	}

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
	
	protected ReBusinessPosService getHessianReBusinessPosService() throws MalformedURLException {
		String url = gdProperties.getReBusinessPosUrl();
		if (reBusinessPosService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reBusinessPosService = (ReBusinessPosService) factory.create(ReBusinessPosService.class, url);
		}
		return reBusinessPosService;
	}
	
	
	@Before("execution(* com.gudeng.commerce.gd.admin.service.impl.ReBusinessPosToolServiceImpl.deleteByBusinessId(java.lang.Long))")
//	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ReBusinessPosToolServiceImpl.deleteByBusinessId(java.lang.Long))")
	public void deleteByBusinessId(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		Long businessId =(Long)objects[0];
		try {
			SysRegisterUser sysUser=getMember();
			List<ReBusinessPosDTO> list=getHessianReBusinessPosService().getReBusinessPosByBusinessId(businessId);
			
			StringBuffer sbf=new StringBuffer();
			sbf.append("id为").append(sysUser.getUserID()).append("的系统用户在管理后台，删除了商铺ID为").append(businessId).append("的pos号：");
			for(ReBusinessPosDTO rbpdto:list){
				sbf.append(rbpdto.getPosNumber()).append(",");
			}
			
			String content =  sbf.toString();
			
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(sysUser.getUserID());
			entity.setType(SystemLogTypeEnum.SHOP.getKey());
			entity.setOperationId(businessId);
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ReBusinessPosToolServiceImpl.addReBusinessPosEntity(com.gudeng.commerce.gd.customer.entity.ReBusinessPosEntity))")
	public void addReBusinessPosEntity(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		ReBusinessPosEntity reBusinessPosEntity =(ReBusinessPosEntity)objects[0];
		try {
			SysRegisterUser sysUser=getMember();
//			List<ReBusinessPosDTO> list=reBusinessPosService.getReBusinessPosByBusinessId(businessId);
			
			StringBuffer sbf=new StringBuffer();
			sbf.append("id为").append(sysUser.getUserID()).append("的系统用户在管理后台，为商铺 ").append(reBusinessPosEntity.getBusinessId()).append("增加了pos号：");
			sbf.append(reBusinessPosEntity.getPosNumber());
			String content =  sbf.toString();
			
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(sysUser.getUserID());
			entity.setType(SystemLogTypeEnum.SHOP.getKey());
			entity.setOperationId(reBusinessPosEntity.getBusinessId());
			getHessianSystemLogService().insertLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
