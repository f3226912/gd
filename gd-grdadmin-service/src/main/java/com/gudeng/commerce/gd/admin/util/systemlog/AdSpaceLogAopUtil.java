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
import com.gudeng.commerce.gd.customer.entity.AdSpaceEntity;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@Aspect
@Component
public class AdSpaceLogAopUtil {

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
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.AdSpaceToolServiceImpl.insert(com.gudeng.commerce.gd.customer.entity.AdSpaceEntity))")
	public void logAdSpaceInsert(JoinPoint jp){
		Object[] objects = jp.getArgs();
		AdSpaceEntity adSpaceEntity = (AdSpaceEntity) objects[0];
		
		String userID = null;
		String userName = null;
		if(getMember() != null){
			userID = getMember().getUserID();
			userName = getMember().getUserName();
		}
		try {
			String content = "ID=" + userID + " 系统用户" + userName
					+ "【新增】了广告位信息 ：广告位标识spaceSign=" +adSpaceEntity.getSpaceSign()
					+ ",广告位名称adName=" + adSpaceEntity.getAdName()
					+ ",所属广告菜单ID menuId=" + adSpaceEntity.getMenuId()
					+ ",广告类型adType=" + adSpaceEntity.getAdType()
					+ ",广告位规格adSize=" + adSpaceEntity.getAdSize()
			        + ",广告位价格adPrice=" + adSpaceEntity.getAdPrice()
			        + ",广告位展示图片showImg=" + adSpaceEntity.getShowImg()
			        + ",广告位过期默认图片replaceImg=" + adSpaceEntity.getReplaceImg();
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
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.AdSpaceToolServiceImpl.update(com.gudeng.commerce.gd.customer.entity.AdSpaceEntity))")
	public void logAdSpaceUpdate(JoinPoint jp){
		Object[] objects = jp.getArgs();
		AdSpaceEntity adSpaceEntity = (AdSpaceEntity) objects[0];
		
		String userID = null;
		String userName = null;
		if(getMember() != null){
			userID = getMember().getUserID();
			userName = getMember().getUserName();
		}
		try {
			String content = "ID=" + userID + " 系统用户" + userName
					+ "【修改】了广告位信息 ：id=" + adSpaceEntity.getId()
					+ ",广告位标识spaceSign=" +adSpaceEntity.getSpaceSign()
					+ ",广告位名称adName=" + adSpaceEntity.getAdName()
					+ ",所属广告菜单ID menuId=" + adSpaceEntity.getMenuId()
					+ ",广告类型adType=" + adSpaceEntity.getAdType()
					+ ",广告位规格adSize=" + adSpaceEntity.getAdSize()
			        + ",广告位价格adPrice=" + adSpaceEntity.getAdPrice()
			        + ",广告位展示图片showImg=" + adSpaceEntity.getShowImg()
			        + ",广告位过期默认图片replaceImg=" + adSpaceEntity.getReplaceImg();
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
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.AdSpaceToolServiceImpl.deleteAdSpace(..))")
	public void logDeleteAdSpace(JoinPoint jp){
		Object[] objects = jp.getArgs();
		Long id = (Long) objects[0];
		
		String userID = null;
		String userName = null;
		if(getMember() != null){
			userID = getMember().getUserID();
			userName = getMember().getUserName();
		}
		try {
			String content = "ID=" + userID + " 系统用户" + userName
					+ "【删除】了广告位信息（修改state=2） ：id=" + id;
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
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Object user= request.getSession().getAttribute(com.gudeng.commerce.gd.authority.sysmgr.util.Constant.SYSTEM_SMGRLOGINUSER);
		return (SysRegisterUser)user;
	}
}
