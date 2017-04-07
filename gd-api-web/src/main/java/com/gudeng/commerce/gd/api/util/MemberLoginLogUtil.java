package com.gudeng.commerce.gd.api.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.gudeng.commerce.gd.api.service.MemberLoginLogToolService;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberLoginLogEntity;


public class MemberLoginLogUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		MemberLoginLogUtil.applicationContext = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	public static void insertLoginLog(MemberBaseinfoDTO mbdto,String loginType,HttpServletRequest request) {
		MemberLoginLogToolService service = (MemberLoginLogToolService) MemberLoginLogUtil
				.getBean("memberLoginLogToolService");
		try {
			String ip=request.getRemoteAddr();
			ip=ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
			MemberLoginLogEntity entity=new MemberLoginLogEntity();
			entity.setMemberId(mbdto.getMemberId().intValue());
			entity.setAccount(mbdto.getAccount());
			entity.setMobile(mbdto.getMobile());
			entity.setRealName(mbdto.getRealName());
			entity.setIp(ip);
			entity.setLoginType(loginType);
			entity.setDescription("app登录");
			entity.setCreateTime(new Date());
			service.insert(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}