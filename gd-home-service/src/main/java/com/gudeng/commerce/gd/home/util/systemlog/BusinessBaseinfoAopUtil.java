package com.gudeng.commerce.gd.home.util.systemlog;

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
import com.gudeng.commerce.gd.home.Constant;
import com.gudeng.commerce.gd.home.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@Aspect
@Component
public class  BusinessBaseinfoAopUtil {

	@Autowired
	public GdProperties gdProperties;


	private static SystemLogService systemLogService;


	private MemberBaseinfoDTO getMember() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Object user= request.getSession().getAttribute(com.gudeng.commerce.gd.authority.sysmgr.util.Constant.SYSTEM_SMGRLOGINUSER);
		return (MemberBaseinfoDTO) request.getSession().getAttribute(Constant.SYSTEM_LOGINUSER);

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
	
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.home.service.impl.BusinessBaseinfoToolServiceImpl.addBusinessBaseinfoEnt(com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity))",returning="id")
	public void addBusiness(JoinPoint jp,Object id) {
		Long businessId =(Long)id;
		Object[] objects = jp.getArgs();
		BusinessBaseinfoEntity businessBaseinfoEntity = (BusinessBaseinfoEntity) objects[0];
		try {
			String content = "id为" + getMember().getMemberId()
					+ "的用户，在web前台，添加了商铺名为："+businessBaseinfoEntity.getShopsName()+"      的商铺";
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("2");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(String.valueOf( getMember().getMemberId()));
			entity.setType(SystemLogTypeEnum.SHOP.getKey());
			entity.setOperationId(businessId);
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 

	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.home.service.impl.BusinessBaseinfoToolServiceImpl.updateBusinessBaseinfoDTO(com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO))")
	public void updateBusinessBaseinfoDTO(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		BusinessBaseinfoDTO businessBaseinfoDTO = (BusinessBaseinfoDTO) objects[0];
		try {
			String content = "id为" +  getMember().getMemberId()
					+ "的用户,在web前台，修改了id为"+businessBaseinfoDTO.getBusinessId()+"的商铺，修改后的商铺名称为："+
					businessBaseinfoDTO.getShopsName()+"，商铺描述为："+businessBaseinfoDTO.getShopsDesc()+
					",经营模式为："+businessBaseinfoDTO.getBusinessModel()+""
							+ "";
			SystemLogEntity entity = new SystemLogEntity();
			entity.setChennel("2");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(String.valueOf( getMember().getMemberId()));
			entity.setType(SystemLogTypeEnum.SHOP.getKey());
			entity.setOperationId(businessBaseinfoDTO.getBusinessId());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
							
	

}
