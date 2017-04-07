package com.gudeng.commerce.gd.admin.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gudeng.commerce.gd.admin.service.sysmgr.SysMenuAdminService;
import com.gudeng.commerce.gd.admin.util.LoginUserUtil;
import com.gudeng.commerce.gd.authority.sysmgr.base.dto.BaseMenu;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysMenu;
import com.gudeng.commerce.gd.authority.sysmgr.util.PathUtil;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
	private static List<SysMenu> allMenus = new ArrayList<>();
	/** 不用检查的checkUrl */
	private List<String> doNotCheckUrl;

	public List<String> getDoNotCheckUrl() {
		return doNotCheckUrl;
	}

	public void setDoNotCheckUrl(List<String> doNotCheckUrl) {
		this.doNotCheckUrl = doNotCheckUrl;
	}

	@Autowired
	private SysMenuAdminService sysMenuService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 如果用户的ID为null，则直接到登录页面;
		String url = request.getRequestURI();
		String debug = request.getParameter("debug");
		// 如果请求的路径不存在于系统菜单中，则不进行拦截
		if (!checkUrl(url)) {
			return super.preHandle(request, response, handler);
		}
		if (LoginUserUtil.getLoginUserId(request) == null) {
			// 如果不要进行检查的url，直接跳过;
			if (doNotCheckUrl != null) {
				String str = "";
				for (int i = 0; i < doNotCheckUrl.size(); i++) {
					str = doNotCheckUrl.get(i);
					if (url.indexOf(str) >= 0) {
						return super.preHandle(request, response, handler);
					}
				}
			}
			retMessage(request, response);
			return false;
		} else {
			// 如果不要进行检查的url，直接跳过;
			if (doNotCheckUrl != null) {
				String str = "";
				for (int i = 0; i < doNotCheckUrl.size(); i++) {
					str = doNotCheckUrl.get(i);
					if (url.indexOf(str) >= 0) {
						return super.preHandle(request, response, handler);
					}
				}
			}

			List<BaseMenu> menuList = (List<BaseMenu>) request.getSession().getAttribute("menuList");
			if (menuList != null && menuList.size() > 0) {
				for (BaseMenu baseMenu : menuList) {
					if (StringUtils.isEmpty(baseMenu.getActionUrl())) {
						continue;
					} else {
						if (url.indexOf(baseMenu.getActionUrl()) >= 0) {
							return super.preHandle(request, response, handler);
						}
					}
				}
				retMessage(request, response);
				return false;
			} else {
				retMessage(request, response);
				return false;
			}
		}
//		if ("debug".equals(debug)) {
//			return super.preHandle(request, response, handler);
//		}
	}

	private void retMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String backUrl = PathUtil.getBasePath(request);
		// response.sendRedirect(backUrl);
		PrintWriter pw = response.getWriter();
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<meta charset=utf-8>");
		sb.append("<link href='" + backUrl + "images/favicon.ico' type='image/x-icon' rel='icon' />");
		sb.append("<title>非法请求</title>");
		sb.append("<body style='text-align:center;'>");
		sb.append("<p>非法请求</p>");
		sb.append("<a href='" + backUrl + "'>返回</a>");
		sb.append("</body>");
		sb.append("</html>");
		pw.println(sb.toString());
		pw.flush();
		pw.close();

		return;
	}

	/**
	 * @Description checkUrl
	 * @param url
	 * @return 不存在为false，存在系统菜单中，为true
	 * @throws Exception
	 * @CreationDate 2015年11月26日 下午3:29:01
	 * @Author lidong(dli@gdeng.cn)
	 */
	private boolean checkUrl(String url) throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (allMenus != null && allMenus.size() > 0) {

		} else {
			allMenus = sysMenuService.getAll(map);
		}
		for (SysMenu sysMenu : allMenus) {
			String menuUrl = sysMenu.getActionUrl();
			if (StringUtils.isEmpty(menuUrl)) {
				continue;
			} else {
				if (menuUrl.indexOf("/") < 0) {
					if (url.indexOf("/" + menuUrl + "/") >= 0) {
						return false;
					}
				}
				if (url.indexOf(menuUrl) >= 0) {
					return true;
				}
			}
		}
		return false;
	}
}
