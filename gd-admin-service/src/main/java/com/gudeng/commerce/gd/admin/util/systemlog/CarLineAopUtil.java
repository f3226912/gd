package com.gudeng.commerce.gd.admin.util.systemlog;

import java.net.MalformedURLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@Aspect
@Component
public class CarLineAopUtil {
	
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
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.CarLineManageServiceImpl.addCarLineDTO(com.gudeng.commerce.gd.customer.dto.CarLineDTO))")
	public void logCarLinAdd(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		CarLineDTO dto = (CarLineDTO) objects[0];
		try {
			String content = "ID=" + getMember().getUserID() + " 用户" + getMember().getUserName() 
					+ "新增了线路信息 ：  始发地S_provinceId= " + dto.getS_provinceId()
					+ ",目的地E_provinceId="+ dto.getE_provinceId() 
			        + ",发运方式SendGoodsType="+ dto.getSendGoodsType() 
			        + ",价格price="+ dto.getPrice() ;
			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(dto.getId());
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.TRANSPORT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.CarLineManageServiceImpl.updateCarLineDTO(com.gudeng.commerce.gd.customer.dto.CarLineDTO))")
	public void logCarLineEdit(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		CarLineDTO dto = (CarLineDTO) objects[0];
		try {
			String content = "ID=" + getMember().getUserID() + " 用户" + getMember().getUserName() 
					+ "修改了线路信息 ：  始发地S_provinceId= " + dto.getS_provinceId()
					+ ",目的地E_provinceId="+ dto.getE_provinceId() 
			        + ",发运方式SendGoodsType="+ dto.getSendGoodsType() 
			        + ",价格price="+ dto.getPrice() ;

			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(dto.getId());
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.TRANSPORT.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.CarLineManageServiceImpl.updateCarLineByid(..))")
	public void logCarLineDelete(JoinPoint jp) {

		Object[] objects = jp.getArgs();
		String strs=(String)objects[0];
		String[] list =strs.split(",");
		if (list == null || list.length < 1) {
			return;
		}
		SysRegisterUser user = getMember();
		try {
			for (int i = 0; i < list.length; i++) {
				String id = list[i];
				String content = "ID=" + user.getUserID() + " 用户" + user.getUserName() + "【删除】线路信息:ID=" + id;
				SystemLogEntity entity = new SystemLogEntity();
				entity.setOperationId(StringUtils.isNotEmpty(id)?Long.parseLong(id):0L);
				entity.setChennel("1");
				entity.setContent(content);
				entity.setCreateTime(new Date());
				entity.setCreateUserId(user.getUserID());
				entity.setType(SystemLogTypeEnum.TRANSPORT.getKey());
				getHessianSystemLogService().insertLog(entity);
			}
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
