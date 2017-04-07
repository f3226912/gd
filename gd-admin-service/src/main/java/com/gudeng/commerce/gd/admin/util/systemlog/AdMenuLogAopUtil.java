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
import com.gudeng.commerce.gd.customer.dto.AdMenuDTO;
import com.gudeng.commerce.gd.customer.entity.AdSpaceEntity;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@Aspect
@Component
public class AdMenuLogAopUtil {

	@Autowired
	public GdProperties gdProperties;

	private static SystemLogService systemLogService;

	/**
	 * 功能描述:日志服务
	 * 
	 * @param
	 * @return
	 */
	protected SystemLogService getHessianSystemLogService() throws MalformedURLException {
		String url = gdProperties.getSystemLogUrl();
		if (systemLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			systemLogService = (SystemLogService) factory.create(SystemLogService.class, url);
		}
		return systemLogService;
	}

	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.AdMenuToolServiceImpl.insert(..))")
	public void logAdSpaceInsert(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		AdMenuDTO dto = (AdMenuDTO) objects[0];

		String userID = null;
		String userName = null;
		if (getMember() != null) {
			userID = getMember().getUserID();
			userName = getMember().getUserName();
		}
		try {
			String content = "ID=" + userID + " 系统用户" + userName + "【新增】了菜单信息 ：菜单名称menuName=" + dto.getMenuName()
					+ ",父级Id fatherId=" + dto.getFatherId() + ",菜单标识menuSign=" + dto.getMenuSign();
			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(dto.getId());
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(userID);
			entity.setType("10");
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.AdMenuToolServiceImpl.update(..))")
	public void logAdSpaceUpdate(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		AdMenuDTO dto = (AdMenuDTO) objects[0];

		String userID = null;
		String userName = null;
		if (getMember() != null) {
			userID = getMember().getUserID();
			userName = getMember().getUserName();
		}
		try {
			String content = "ID=" + userID + " 系统用户" + userName + "【修改】了菜单信息 ：菜单id=" + dto.getId() + ",菜单名称menuName="
					+ dto.getMenuName() + ",父级Id fatherId=" + dto.getFatherId() + ",菜单标识menuSign=" + dto.getMenuSign();
			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(dto.getId());
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(userID);
			entity.setType("10");
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.AdMenuToolServiceImpl.delete(..))")
	public void logDeleteAdSpace(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		Map<String, Object> map = (Map<String, Object>) objects[0];
		Long id = Long.valueOf((String) map.get("id"));
		String userID = null;
		String userName = null;
		if (getMember() != null) {
			userID = getMember().getUserID();
			userName = getMember().getUserName();
		}
		try {
			String content = "ID=" + userID + " 系统用户" + userName + "【删除】了菜单信息（修改state=2） ：id=" + id;
			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(id);
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(userID);
			entity.setType("10");
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SysRegisterUser getMember() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Object user = request.getSession()
				.getAttribute(com.gudeng.commerce.gd.authority.sysmgr.util.Constant.SYSTEM_SMGRLOGINUSER);
		return (SysRegisterUser) user;
	}
}
