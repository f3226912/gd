package com.gudeng.commerce.gd.admin.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gudeng.commerce.gd.admin.service.sysmgr.SysMenuAdminService;
import com.gudeng.commerce.gd.admin.util.LoginUserUtil;
import com.gudeng.commerce.gd.authority.sysmgr.base.dto.BaseMenu;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysMenu;
import com.gudeng.commerce.gd.authority.sysmgr.util.PathUtil;

/**
 * @Description 对用户访问的菜单路径进行过滤，防止浏览器直接输入地址访问无权限的菜单
 * @Project gd-admin-web
 * @ClassName MenuInterceptor.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年10月20日 下午1:59:26
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public class MenuInterceptor extends HandlerInterceptorAdapter {
    // private static Logger logger = LoggerFactory.getLogger(MenuInterceptor.class);
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

    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = PathUtil.urlFormat(request.getRequestURL().toString().replace(PathUtil.getBasePath(request), ""));
        // 如果不要进行检查的url，直接跳过;登录，首页等。。。。
        if (doNotCheckUrl != null) {
            String str = "";
            for (int i = 0; i < doNotCheckUrl.size(); i++) {
                str = doNotCheckUrl.get(i);
                if (url.indexOf(str) >= 0) {
                    return super.preHandle(request, response, handler);
                }
            }
        }

        /**
         * ===========未登录处理============
         */
        if (LoginUserUtil.getLoginUserId(request) == null) {
            // 重写URL返回登录页
            // response.sendRedirect(PathUtil.getBasePath(request));
            retMessage(request, response);
            return super.preHandle(request, response, handler);
        }
        /**
         * ===========已登录处理============
         */

        /**
         * Ajax请求，或者页面js中加载请求===== 如果请求的路径不存在于系统菜单中，则不进行拦截
         */
        if (!checkUrl(url)) {
            return super.preHandle(request, response, handler);
        }

        /**
         * 菜单URL请求
         */
        // 用户拥有的合法URL
        List<BaseMenu> menuList = (List<BaseMenu>) request.getSession().getAttribute("menuList");
        if (menuList != null && menuList.size() > 0) {
            for (BaseMenu baseMenu : menuList) {
                if (StringUtils.isEmpty(baseMenu.getActionUrl())) {
                    continue;
                } else {
                    if (url.equals(baseMenu.getActionUrl())) {
                        return super.preHandle(request, response, handler);
                    }
                }
            }
            // response.sendRedirect(PathUtil.getBasePath(request));
            retMessage(request, response);
        } else {
            // response.sendRedirect(PathUtil.getBasePath(request));
            retMessage(request, response);
        }
        return super.preHandle(request, response, handler);
    }

    private void retMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String backUrl = PathUtil.getBasePath(request);
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
        if (allMenus == null || allMenus.size() == 0) {
            return true;
        }
        for (SysMenu sysMenu : allMenus) {
            String menuUrl = sysMenu.getActionUrl();
            if (StringUtils.isEmpty(menuUrl)) {
                continue;
            } else {
                if (url.equals(menuUrl)) {
                    return true;
                }
            }
        }
        return false;
    }
}
