package com.gudeng.commerce.info.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gudeng.commerce.info.admin.service.SysLoginAdminService;
import com.gudeng.commerce.info.admin.service.SysUserRoleAdminService;
import com.gudeng.commerce.info.admin.util.CookieUtil;
import com.gudeng.commerce.info.admin.util.LoginUserUtil;
import com.gudeng.commerce.info.customer.dto.BaseMenu;
import com.gudeng.commerce.info.customer.entity.SysRegisterUser;
import com.gudeng.commerce.info.customer.util.CommonConstant;
import com.gudeng.commerce.info.customer.util.Constant;
import com.gudeng.commerce.info.customer.util.MessageUtil;
import com.gudeng.commerce.info.customer.util.PathUtil;

/**
 * 用户登录的action;
 * 
 * @author huyong;
 * @date 2012-06-11;
 * 
 */
@Controller
@RequestMapping("sys")
public class LoginController {

    /** 用户登录的Service */
    @Autowired
    private SysLoginAdminService sysLoginService;
    /** 用户角色 */
    @Autowired
    private SysUserRoleAdminService sysUserRoleService;

    /** 采购cookie */
    private static String xlc_cookie_key = "xlc_shop_cart_cookie_";

    /** 日志 */
    // private Logger logger = Logger.getLogger(LoginController.class);

    /**
     * 登录页面初始化
     * 
     * @author songhui
     * @date 创建时间：2015年7月29日 下午2:04:56
     * @param request
     * @return
     * 
     */
    @RequestMapping(Constant.PURCHASE_LOGIN_URL)
    public String loginInit(HttpServletRequest request) {

        return "redirect:" + PathUtil.getBasePath(request);
    }

    /**
     * 联采中心登录
     * 
     * @author songhui
     * @date 创建时间：2015年8月10日 下午3:27:45
     * @param request
     * @return
     * 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        if (LoginUserUtil.getLoginUserId(request) != null) {
            return "redirect:" + PathUtil.getBasePath(request) + "index";
        }
        String message = login(request, response, Constant.SYS_TYPE_PURCHASE);
        if (message == null) {
            // 登录成功
            return "redirect:" + PathUtil.getBasePath(request) + "index";
        }
        request.setAttribute(CommonConstant.COMMON_AJAX_ERROR, message);
        return "login";
    }

    /**
     * 学校登录页初始化
     * 
     * @author songhui
     * @date 创建时间：2015年8月10日 下午3:23:57
     * @param request
     * @return
     * 
     */
    @RequestMapping(Constant.SCHOOL_LOGIN_URL)
    public String schoolLogin(HttpServletRequest request) {

        return "schoollogin";
    }

    /**
     * 学校登录
     * 
     * @author songhui
     * @date 创建时间：2015年8月10日 下午3:27:26
     * @param request
     * @return
     * 
     */
    @RequestMapping("school/login")
    public String schoolLoginInit(HttpServletRequest request, HttpServletResponse response) {

        String message = login(request, response, Constant.SYS_TYPE_SCHOOL);
        if (message == null) {
            // 登录成功
            return "redirect:" + PathUtil.getBasePath(request) + "index";
        }
        request.setAttribute(CommonConstant.COMMON_AJAX_ERROR, message);
        return "schoollogin";
    }

    /**
     * 基地面页初始化
     * 
     * @author songhui
     * @date 创建时间：2015年8月10日 下午3:24:34
     * @param request
     * @return
     * 
     */
    @RequestMapping(Constant.BASE_LOGIN_URL)
    public String baseLoginInit(HttpServletRequest request) {

        return "baselogin";
    }

    /**
     * 基地登录
     * 
     * @author songhui
     * @date 创建时间：2015年8月10日 下午3:24:34
     * @param request
     * @return
     * 
     */
    @RequestMapping("base/login")
    public String baseLogin(HttpServletRequest request, HttpServletResponse response) {

        String message = login(request, response, Constant.SYS_TYPE_BASE);
        if (message == null) {
            // 登录成功
            return "redirect:" + PathUtil.getBasePath(request) + "index";
        }
        request.setAttribute(CommonConstant.COMMON_AJAX_ERROR, message);
        return "baselogin";
    }

    /**
     * 登录操作
     * 
     * @author songhui
     * @date 创建时间：2015年8月10日 下午3:28:19
     * @param request
     * @param response
     * @param sysType
     *            系统类型
     * @return
     * 
     */
    public String login(HttpServletRequest request, HttpServletResponse response, String sysType) {

        try {
            // 校验用户与密码;
            String userCode = StringUtils.trimToNull(request.getParameter("userCode"));
            String userPassword = StringUtils.trimToNull(request.getParameter("userPassword"));
            String message = null;
            // 校验界面输入的数据;
            if (StringUtils.isBlank(userCode)) {
                // 用户没有输入;
                message = MessageUtil.getMessage("login.noUserCode");
            } else if (StringUtils.isBlank(userPassword)) {
                // 密码没有输入;
                message = MessageUtil.getMessage("login.noUserPassword");
            }
            // 依据code取得用户的信息;
            SysRegisterUser regUser = sysLoginService.getLoginUserInfo(userCode);
            // 根据登录用户判断
            if (regUser == null) {
                // 用户不存在;
                message = MessageUtil.getMessage("login.user.noExist");
            } else {
                // 验证用户是否登录正确系统
                if (!checkUserSys(regUser.getType(), sysType)) {
                    message = MessageUtil.getMessage("login.user.noExist");
                } else {
                    // 登录处理
                    regUser.setPassword(userPassword);
                    message = sysLoginService.processLogin(regUser);
                }
                regUser.setUserIP(LoginUserUtil.getIpAddr(request));// 记录用户登录IP
            }
            // 如果有错误信息，返回错误信息
            if (StringUtils.isNotBlank(message)) {
                return message;
            }
            // 设置系统信息
            setSysInfoSession(sysType, request);
            // 设定用户session按钮
            setUserSessionButton(regUser.getUserID(), request);
            // 设定用户session菜单;
            setUserSessionMenu(regUser.getUserID(), request);
            // 设定用户的session 按钮;
            // setUserSessionButton(regUser.getUserID(), request);
            // 设定全局的用户ID;
            request.getSession().setAttribute(Constant.SYSTEM_USERID, regUser.getUserID());
            // 设定全局的用户名称;
            request.getSession().setAttribute(Constant.SYSTEM_USERNAME, regUser.getUserName());
            // 设定全局的用户账号;
            request.getSession().setAttribute(Constant.SYSTEM_USERCODE, regUser.getUserCode());
            // 设定全局用户
            request.getSession().setAttribute(Constant.SYSTEM_SMGRLOGINUSER, regUser);
            // 根据用户类型获取用户登录页面信息
            String backUrl = getLoginPageInfoByType(regUser.getType());
            // 设置登录cookie
            CookieUtil.addCookie(response, null, Constant.SYS_TYPE_COOKIE, backUrl, -1);
            return null;
        } catch (Exception e) {
            // e.printStackTrace();
            // logger.info(e.getMessage());
            return "登录异常";
        }
    }

    /***
     * 根据用户类型查询系统相关信息
     * 
     * @author songhui
     * @date 创建时间：2015年8月14日 上午9:11:08
     * @param sysType
     *            系统类型:后台管理（purchase）,学校管理（school）,基地管理（base）
     * @param request
     * @return
     *
     */
    private void setSysInfoSession(String sysType, HttpServletRequest request) {

        HttpSession session = request.getSession();
        // 设置系统类型到session中
        session.setAttribute(Constant.SYS_TYPE, sysType);
        // 系统名称
        if (Constant.SYS_TYPE_PURCHASE.equals(sysType)) {
            // 联采中心
            session.setAttribute(Constant.SYS_NAME, Constant.SYS_NAME_PURCHASE);
            session.setAttribute(Constant.SYS_FULL_NAME, Constant.SYS_FULL_NAME_PURCHASE);
        } else if (Constant.SYS_TYPE_SCHOOL.equals(sysType)) {
            // 学校
            session.setAttribute(Constant.SYS_NAME, Constant.SYS_NAME_SCHOOL);
            session.setAttribute(Constant.SYS_FULL_NAME, Constant.SYS_FULL_NAME_SCHOOL);
        } else if (Constant.SYS_TYPE_BASE.equals(sysType)) {
            // 基地
            session.setAttribute(Constant.SYS_NAME, Constant.SYS_NAME_BASE);
            session.setAttribute(Constant.SYS_FULL_NAME, Constant.SYS_FULL_NAME_BASE);
        }
    }

    /**
     * 根据登录人类型获取登录页面URL
     * 
     * @author songhui
     * @date 创建时间：2015年8月14日 上午9:44:29
     * @param loginType
     *            登录用户类型
     * @return
     *
     */
    private String getLoginPageInfoByType(int loginType) {

        // 登录URL
        String loginUrl = Constant.PURCHASE_LOGIN_URL;
        // 0系统，1联采中心，2配送站，3学校，4食堂，5基地
        switch (loginType) {
        case 0:
            loginUrl = Constant.PURCHASE_LOGIN_URL;
            break;
        case 1:
            loginUrl = Constant.PURCHASE_LOGIN_URL;
            break;
        case 2:
            loginUrl = Constant.PURCHASE_LOGIN_URL;
            break;
        case 3:
            loginUrl = Constant.SCHOOL_LOGIN_URL;
            break;
        case 4:
            loginUrl = Constant.SCHOOL_LOGIN_URL;
            break;
        case 5:
            loginUrl = Constant.BASE_LOGIN_URL;
            break;
        default:
            loginUrl = Constant.PURCHASE_LOGIN_URL;
            break;
        }
        return "sys/" + loginUrl;
    }

    /**
     * 根据用户类型判断用户是否登录正确的系统
     * 
     * @author songhui
     * @date 创建时间：2015年8月14日 上午9:52:28
     * @param loginType
     * @param sysType
     * @return
     *
     */
    private boolean checkUserSys(int loginType, String sysType) {

        // 0系统，1联采中心，2配送站，3学校，4食堂，5基地
        if (Constant.SYS_TYPE_PURCHASE.equals(sysType)) {
            // 后台管理
            if (loginType == 0 || loginType == 1 || loginType == 2) {
                return true;
            }
        } else if (Constant.SYS_TYPE_SCHOOL.equals(sysType)) {
            // 学校管理
            if (loginType == 3 || loginType == 4) {
                return true;
            }
        } else if (Constant.SYS_TYPE_BASE.equals(sysType)) {
            // 基
            if (loginType == 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设定session菜单;
     * 
     * @param userID
     *            用户ID;
     * @param request
     *            请求;
     */
    @SuppressWarnings("unchecked")
    private void setUserSessionMenu(String userID, HttpServletRequest request) throws Exception {

        Object[] obj = sysLoginService.getUserAllMenu(userID);
        // 菜单code
        Map<String, String> userMenuMap = (Map<String, String>) obj[1];
        // 一级菜单
        List<BaseMenu> menuList = (List<BaseMenu>) obj[0];
        // 二级菜单
        List<BaseMenu> subMenuList = (List<BaseMenu>) obj[2];
        // 三级菜单
        List<BaseMenu> trdMenuList = (List<BaseMenu>) obj[3];
        // 一级菜单
        request.getSession().setAttribute(Constant.SYSTEM_ALLMENU, menuList);
        // 菜单code
        request.getSession().setAttribute(Constant.SYSTEM_MENUCODE, userMenuMap);
        // 二级菜单
        request.getSession().setAttribute(Constant.SYSTEM_SENCONDMENU, subMenuList);
        // 三级菜单
        request.getSession().setAttribute(Constant.SYSTEM_THIRDMENU, trdMenuList);
    }

    /**
     * 设定session Button;
     * 
     * @param userID
     *            用户id;
     * @param request
     *            请求;
     */
    private void setUserSessionButton(String userID, HttpServletRequest request) {

        Map<String, String> btnMap = null;
        try {
            btnMap = sysLoginService.getUserAllMenuButton(userID);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.getSession().setAttribute(Constant.SYSTEM_BUTTONCODE, btnMap);
    }

    /**
     * 退出登陆
     * 
     * @param request
     * @param response
     * @author tanjun
     * @date 2012-12-03
     * @return
     */
    @RequestMapping("loginOut")
    public ModelAndView loginOut(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        String userId = LoginUserUtil.getLoginUserId(request);
        CookieUtil.deleteCookie(response, xlc_cookie_key + userId);

        // 清除登录cookie
        CookieUtil.deleteCookie(response, Constant.SYS_TYPE_COOKIE);
        String backUrl = PathUtil.getBasePath(request) + LoginUserUtil.getLoginPageUrl(request);
        // 设置session失效
        request.getSession().invalidate();
        // return "redirect:" + backUrl;
        mv.addObject("logout", "logout");
        mv.setViewName("redirect:/");
        return mv;
    }

}
