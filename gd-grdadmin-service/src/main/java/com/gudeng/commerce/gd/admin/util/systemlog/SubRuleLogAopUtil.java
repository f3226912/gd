package com.gudeng.commerce.gd.admin.util.systemlog;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleParamDTO;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

/**
 * 
 * @author Semon
 *
 */

@Aspect
public class SubRuleLogAopUtil {
	
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
	
	private SysRegisterUser getMember() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Object user= request.getSession().getAttribute(com.gudeng.commerce.gd.authority.sysmgr.util.Constant.SYSTEM_SMGRLOGINUSER);
		return (SysRegisterUser)user;
	}	
	
	/**
	 * 活动规则操作日志
	 * @param jp
	 */
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.SubRuleToolServiceImpl.*(..))")
	public void subRuleLog(JoinPoint jp) {
		try {
		Object[] objects = jp.getArgs();
		//获取方法名称
		String method = jp.getSignature().getName();
		if(method.startsWith("get"))return;
		String content ="" ;
		String createUserId = getMember().getUserID();
		long operationId = 0;
		if(method.equals("addSubRule")){
			SubPayRuleParamDTO param = (SubPayRuleParamDTO)objects[0];
			 content ="id为" + createUserId+ "的系统用户对账户新增了一条活动规则";
		}else if(method.equals("updateRuleStatus")){
			Map<String, Object> map=(Map<String, Object>) objects[0];
			createUserId=(String) map.get("createUserId");
			content ="id为" + createUserId+ "的系统用户对账户修改了活动规则"+ map.get("ruleId").toString()+"的状态";
			operationId = Long.parseLong(map.get("ruleId").toString());
		}else if(method.equals("modifyRule")){
			SubPayRuleParamDTO param = (SubPayRuleParamDTO)objects[0];
			content ="id为" + createUserId+ "的系统用户对账户修改了活动规则"+ param.getSubPayRule().getRuleId();
			operationId = param.getSubPayRule().getRuleId();
		}else{
			return ;
		}
		SystemLogEntity entity = new SystemLogEntity();
		entity.setChennel("1");
		entity.setOperationId(operationId);
		entity.setContent(content);
		entity.setCreateTime(new Date());
		entity.setCreateUserId(createUserId);
		entity.setType(SystemLogTypeEnum.SUBSIDY.getKey());
		Long l=getHessianSystemLogService().insertLog(entity);
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 系统规则操作日志
	 * @param jp
	 */
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.SubLimitRuleToolServiceImpl.*(..))")
	public void limitRuleLog(JoinPoint jp) {
		try {
		Object[] objects = jp.getArgs();
		//获取方法名称
		String method = jp.getSignature().getName();
		if(method.startsWith("get"))return;
		String content ="" ;
		String createUserId = getMember().getUserID();
		long operationId = 0;
		if(method.equals("saveOrUpdateSubLimitRule")){
			SubLimitRuleDTO param = (SubLimitRuleDTO)objects[0];
			if(null==param.getRuleId()){
				 content ="id为" + createUserId+ "的系统用户对账户新增了一条系统规则";
			}else{
				content ="id为" + createUserId+ "的系统用户对账户修改了一条系统规则";
			}
		}else if(method.equals("delWhiteList")){
			Map<String, Object> map=(Map<String, Object>) objects[0];
			content ="id为" + createUserId+ "删除了系统规则的白名单"+ map.get("memberId").toString();
			operationId = Long.parseLong(map.get("memberId").toString());
		}else if(method.equals("updateSubLimitRuleStatus")){
			Map<String, Object> map=(Map<String, Object>) objects[0];
			content ="id为" + createUserId+ "终止了系统规则"+map.get("ruleId").toString();
			operationId = Long.parseLong(map.get("ruleId").toString());
		}else{
			return ;
		}
		SystemLogEntity entity = new SystemLogEntity();
		entity.setOperationId(operationId);
		entity.setChennel("1");
		entity.setContent(content);
		entity.setCreateTime(new Date());
		entity.setCreateUserId(createUserId);
		entity.setType(SystemLogTypeEnum.SUBSIDY.getKey());
		Long l=getHessianSystemLogService().insertLog(entity);
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
