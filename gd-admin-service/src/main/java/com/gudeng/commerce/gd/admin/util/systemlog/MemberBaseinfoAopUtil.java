package com.gudeng.commerce.gd.admin.util.systemlog;

import java.net.MalformedURLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@Aspect
@Component
public class  MemberBaseinfoAopUtil {

	@Autowired
	public GdProperties gdProperties;

//	private static MemberBaseinfoService memberBaseinfoService;

	private static SystemLogService systemLogService;

	/**
	 * 功能描述: memberBaseinfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
//	protected MemberBaseinfoService getHessianMemberBaseinfoService() throws MalformedURLException {
//		String url = gdProperties.getMemberBaseinfoUrl();
//		if (memberBaseinfoService == null) {
//			HessianProxyFactory factory = new HessianProxyFactory();
//			factory.setOverloadEnabled(true);
//			memberBaseinfoService = (MemberBaseinfoService) factory.create(MemberBaseinfoService.class, url);
//		}
//		return memberBaseinfoService;
//	}

	
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
	
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.MemberBaseinfoToolServiceImpl.addMemberBaseinfoEnt(com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity))",returning="id")
	public void addMember(JoinPoint jp,Object id) {
		Object[] objects = jp.getArgs();
		Long memberId =(Long)id;
		MemberBaseinfoEntity memberBaseinfoEntity = (MemberBaseinfoEntity) objects[0];
		try {
			String content = "id为" + getMember().getUserID()
					+ "的系统用户在管理后台，添加了账号为："+
					memberBaseinfoEntity.getAccount()+"，手机号为："+memberBaseinfoEntity.getMobile()+"的用户";
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.MEMBER.getKey());
			entity.setOperationId(memberId);
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 

	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.MemberBaseinfoToolServiceImpl.updateMemberBaseinfoDTO(com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO))")
	public void updateMemberBaseinfoDTO(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		MemberBaseinfoDTO memberBaseinfoDTO = (MemberBaseinfoDTO) objects[0];
		try {
			String content = "id为" + getMember().getUserID()
					+ "的系统用户在管理后台，修改了id为"+memberBaseinfoDTO.getMemberId()+"的账号，修改后的密码为："+
					memberBaseinfoDTO.getPassword()+"，手机号为："+memberBaseinfoDTO.getMobile()+"";
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.MEMBER.getKey());
			entity.setOperationId(memberBaseinfoDTO.getMemberId());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description 删除
	 * @CreationDate 2015年12月30日 上午11:17:40
	 * @Author lidong(dli@gdeng.cn)
	 */
	@Pointcut("execution(* com.gudeng.commerce.gd.admin.service.impl.MemberBaseinfoToolServiceImpl.updateStatus(..))")
	private void updateStatus() {
	}
	
	@AfterReturning("updateStatus()")
	public void updateStatus(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		MemberBaseinfoDTO memberBaseinfoDTO = (MemberBaseinfoDTO) objects[0];
		try {
			String content = "id为" + getMember().getUserID()
					+ "的系统用户在管理后台，修改了id为"+memberBaseinfoDTO.getMemberId()+"的状态，修改后的状态为"+memberBaseinfoDTO.getStatus();
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.MEMBER.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
							
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.MemberBaseinfoToolServiceImpl.updateSubShow(..))")
	public void updateSubShow(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		MemberBaseinfoDTO memberBaseinfoDTO = (MemberBaseinfoDTO) objects[0];
		try {
			String content = "id为" + getMember().getUserID()
					+ "的系统用户在管理后台，修改了id为"+memberBaseinfoDTO.getMemberId()+"的是否显示补贴状态，修改后的补贴状态为"+memberBaseinfoDTO.getSubShow();
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.MEMBER.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.MemberBaseinfoToolServiceImpl.updateUserType(com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO))")
	public void updateUserType(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		MemberBaseinfoDTO memberBaseinfoDTO = (MemberBaseinfoDTO) objects[0];
		try {
			String content = "id为" + getMember().getUserID()
					+ "的系统用户在管理后台，修改了id为"+memberBaseinfoDTO.getMemberId()+"的userType，修改后的userType为"+memberBaseinfoDTO.getUserType();
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.MEMBER.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
