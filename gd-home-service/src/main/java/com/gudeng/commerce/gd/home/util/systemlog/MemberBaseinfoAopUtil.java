package com.gudeng.commerce.gd.home.util.systemlog;

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
import com.gudeng.commerce.gd.home.util.GdProperties;
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
	
 
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.home.service.impl.MemberBaseinfoToolServiceImpl.updateMemberBaseinfoDTO(com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO))")
	public void updateMemberBaseinfoDTO(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		MemberBaseinfoDTO memberBaseinfoDTO = (MemberBaseinfoDTO) objects[0];
		try {
			String content = "";
			if(null == memberBaseinfoDTO.getPassword() ){
				content= "id为" + memberBaseinfoDTO.getMemberId()
						+ "的用户在web端修改了个人信息，修改后，姓名为:"+
						memberBaseinfoDTO.getRealName()+"，手机号为："+memberBaseinfoDTO.getMobile();
			}else{
				content= "id为" + memberBaseinfoDTO.getMemberId()
						+ "的用户在web端修改了密码，修改后，密码为:"+memberBaseinfoDTO.getPassword();
			}
			
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("2");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(String.valueOf(memberBaseinfoDTO.getMemberId()));
			entity.setType(SystemLogTypeEnum.MEMBER.getKey());
			entity.setOperationId(memberBaseinfoDTO.getMemberId());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
