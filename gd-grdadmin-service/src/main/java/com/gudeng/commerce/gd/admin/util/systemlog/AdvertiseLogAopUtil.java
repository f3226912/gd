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
import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;
import com.gudeng.commerce.gd.customer.entity.AdAdvertise;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@Aspect
@Component
public class AdvertiseLogAopUtil {

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
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.AdAdvertiseToolServiceImpl.persit(..))")
	public void logAdSpaceInsert(JoinPoint jp){
		Object[] objects = jp.getArgs();
		AdAdvertise adSpaceEntity = (AdAdvertise) objects[0];
		String userID = null;
		String userName = null;
		if(getMember() != null){
			userID = getMember().getUserID();
			userName = getMember().getUserName();
		}
		try {
			String content = "ID=" + userID + " 系统用户" + userName
					+ "【新增】了广告信息 ：广告名称adName=" + adSpaceEntity.getAdName()
					+ ",所属广告位ID adSpaceId=" + adSpaceEntity.getAdSpaceId()
					+ ",跳转类型jumpType=" + adSpaceEntity.getJumpType()
					+ ",跳转地址jumpUrl=" + adSpaceEntity.getJumpUrl()
			        + ",广告文字adWord=" + adSpaceEntity.getAdWord()
			        + ",广告图片adUrl=" + adSpaceEntity.getAdUrl()
			        + ",开始时间startTime=" + adSpaceEntity.getStartTime()
			        + ",结束时间endTime=" + adSpaceEntity.getEndTime()
			        + ",所属市场ID marketId=" + adSpaceEntity.getMarketId()
			        + ",推送产品分类id categoryId=" + adSpaceEntity.getCategoryId()
			        + ",推送产品id productId=" + adSpaceEntity.getProductId()
			        + ",商品标签productSign=" + adSpaceEntity.getProductSign();
			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(adSpaceEntity.getId());
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
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.AdAdvertiseToolServiceImpl.update(..))")
	public void logAdSpaceUpdate(JoinPoint jp){
		Object[] objects = jp.getArgs();
		AdAdvertiseDTO adSpaceEntity = (AdAdvertiseDTO) objects[0];
		
		String userID = null;
		String userName = null;
		if(getMember() != null){
			userID = getMember().getUserID();
			userName = getMember().getUserName();
		}
		try {
			 String content = "ID=" + userID + " 系统用户" + userName
	                    + "【修改】了广告信息 ：id=" + adSpaceEntity.getId()
	                    + "广告名称adName=" + adSpaceEntity.getAdName()
	                    + ",所属广告位ID adSpaceId=" + adSpaceEntity.getAdSpaceId()
	                    + ",跳转类型jumpType=" + adSpaceEntity.getJumpType()
	                    + ",跳转地址jumpUrl=" + adSpaceEntity.getJumpUrl()
	                    + ",广告文字adWord=" + adSpaceEntity.getAdWord()
	                    + ",广告图片adUrl=" + adSpaceEntity.getAdUrl()
	                    + ",开始时间startTime=" + adSpaceEntity.getStartTimeStr()
	                    + ",结束时间endTime=" + adSpaceEntity.getEndTimeStr()
	                    + ",所属市场ID marketId=" + adSpaceEntity.getMarketId()
	                    + ",推送产品分类id categoryId=" + adSpaceEntity.getCategoryId()
	                    + ",推送产品id productId=" + adSpaceEntity.getProductId()
	                    + ",商品标签productSign=" + adSpaceEntity.getProductSign();
			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(adSpaceEntity.getId());
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
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.AdAdvertiseToolServiceImpl.updateState(..))")
	public void logDeleteAdSpace(JoinPoint jp){
		Object[] objects = jp.getArgs();
		AdAdvertiseDTO dto = (AdAdvertiseDTO) objects[0];
		
		String userID = null;
		String userName = null;
		if(getMember() != null){
			userID = getMember().getUserID();
			userName = getMember().getUserName();
		}
		try {
			String content = "ID=" + userID + " 系统用户" + userName
					+ "【删除】了广告信息（修改state=4） ：id=" + dto.getId();
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
	
	private SysRegisterUser getMember() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Object user= request.getSession().getAttribute(com.gudeng.commerce.gd.authority.sysmgr.util.Constant.SYSTEM_SMGRLOGINUSER);
		return (SysRegisterUser)user;
	}
}
