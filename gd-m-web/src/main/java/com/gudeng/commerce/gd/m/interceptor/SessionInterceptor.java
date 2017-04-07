package com.gudeng.commerce.gd.m.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gudeng.commerce.gd.m.entity.UserSummary;
import com.gudeng.framework.security.core.mgr.SysMenuManager;
import com.gudeng.framework.security.core.mgr.SysRegisterUserManager;
import com.gudeng.framework.security.core.model.SysmenuDTO;
import com.gudeng.framework.security.core.model.SysregisteruserEntity;


public class SessionInterceptor extends HandlerInterceptorAdapter {
	  private static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    /**
     * 系统编码
     */
    private String systemCode;
    /**
     * CAS URL
     */
    private String casUrl;
    public void setCasUrl(String casUrl) {
        this.casUrl = casUrl;
    }

    /**
     * 用户菜单
     */
    private SysMenuManager sysMenuManager;

    /**
     * 用户注册
     */
    private SysRegisterUserManager sysRegisterUserManager;

    public boolean preHandle(HttpServletRequest request,
	    HttpServletResponse response, Object handler) throws Exception {

	HttpSession session = request.getSession(true);
//	if (session.getAttribute(UserSummary.SESSION_USER_KEY) == null) {
//	    // 单点登录获取系统信息 
//	    String userId = null;
//	    if (SecurityContextHolder.getContext().getAuthentication() != null) {
//			UserDetails userDetails = (UserDetails) SecurityContextHolder
//				.getContext().getAuthentication().getPrincipal();
//			userId = userDetails.getUsername();
//			logger.info("preHandle Security load cas userid:" + casUrl);
//		    } else {
//			logger.info("preHandle session time out,sendRedirect("+casUrl+")");
//			response.sendRedirect(casUrl);
//		    }
//
//	    if (userId != null) {
//		// 获取菜单信息
//		List<SysmenuDTO> menuList = sysMenuManager.getSysMenuByUserId(
//			userId, systemCode);
//		// 获取用户信息
//		SysregisteruserEntity registeruser = sysRegisterUserManager
//			.getUserByUserId(userId);
//		// 构造用户信息
//		UserSummary u = new UserSummary();
//		u.setUserid(registeruser.getId());
//		u.setName(registeruser.getUserName());
//		logger.info("preHandle cas load cas userId:" + registeruser.getId() + " userName:" + registeruser.getUserName());
//
//		session.setAttribute("menuList", menuList);
//		session.setAttribute(UserSummary.SESSION_USER_KEY, u);
//	    }
//
//	}

	return super.preHandle(request, response, handler);
    }

    public void setSystemCode(String systemCode) {
	this.systemCode = systemCode;
    }

    public void setSysMenuManager(SysMenuManager sysMenuManager) {
	this.sysMenuManager = sysMenuManager;
    }

    public void setSysRegisterUserManager(
	    SysRegisterUserManager sysRegisterUserManager) {
	this.sysRegisterUserManager = sysRegisterUserManager;
    }
}
