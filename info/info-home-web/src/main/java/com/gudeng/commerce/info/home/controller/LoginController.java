package com.gudeng.commerce.info.home.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.info.customer.dto.BaseMenu;
import com.gudeng.commerce.info.customer.entity.SysRegisterUser;
import com.gudeng.commerce.info.customer.util.CommonConstant;
import com.gudeng.commerce.info.customer.util.Constant;
import com.gudeng.commerce.info.customer.util.MessageUtil;
import com.gudeng.commerce.info.customer.util.PathUtil;
import com.gudeng.commerce.info.home.service.SysLoginAdminService;
import com.gudeng.commerce.info.home.service.SysUserRoleAdminService;
import com.gudeng.commerce.info.home.util.CookieUtil;
import com.gudeng.commerce.info.home.util.LoginUserUtil;

/**
 * 用户登录的action;
 * 
 * @author huyong;
 * @date 2012-06-11;
 * 
 */
@Controller
@RequestMapping("H5/sys")
public class LoginController {

    /** 用户登录的Service */
    @Autowired
    private SysLoginAdminService sysLoginService;
    /** 用户角色 */
    @Autowired
    private SysUserRoleAdminService sysUserRoleService;

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
    @RequestMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        // if (LoginUserUtil.getLoginUserId(request) != null) {
        // return "redirect:" + PathUtil.getBasePath(request);
        // }
        String message = login(request, response, Constant.SYS_TYPE_PURCHASE);
        if (message == null) {
            // 登录成功
            return "redirect:" + PathUtil.getBasePath(request) + "H5/workbench/main";
        }
        request.setAttribute(CommonConstant.COMMON_AJAX_ERROR, message);
        return "redirect:" + PathUtil.getBasePath(request);
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
                // 登录处理
                regUser.setPassword(userPassword);
                message = sysLoginService.processLogin(regUser);
                regUser.setUserIP(LoginUserUtil.getIpAddr(request));// 记录用户登录IP
            }
            // 如果有错误信息，返回错误信息
            if (StringUtils.isNotBlank(message)) {
                return message;
            }
            // 设定用户session菜单;
            setUserSessionMenu(regUser.getUserID(), request);
            // 设定全局的用户ID;
            request.getSession().setAttribute(Constant.SYSTEM_USERID, regUser.getUserID());
            // 设定全局的用户名称;
            request.getSession().setAttribute(Constant.SYSTEM_USERNAME, regUser.getUserName());
            // 设定全局的用户账号;
            request.getSession().setAttribute(Constant.SYSTEM_USERCODE, regUser.getUserCode());
            // 设定全局用户
            request.getSession().setAttribute(Constant.SYSTEM_SMGRLOGINUSER, regUser);
            return null;
        } catch (Exception e) {
            return "帐号或密码错误";
        }
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

        // 将所有的数据类型加入集合
        List<BaseMenu> dataMenu = new ArrayList<>();
        for (BaseMenu baseMenu : menuList) {
            if (baseMenu != null && "3".equals(baseMenu.getAttribute())) {
                dataMenu.add(baseMenu);
            }
        }
        // 一级菜单
        request.getSession().setAttribute(Constant.SYSTEM_ALLMENU, menuList);
        // 菜单code
        request.getSession().setAttribute(Constant.SYSTEM_MENUCODE, userMenuMap);
        // 二级菜单
        request.getSession().setAttribute(Constant.SYSTEM_SENCONDMENU, subMenuList);
        // 三级菜单
        request.getSession().setAttribute(Constant.SYSTEM_THIRDMENU, trdMenuList);
        // 用户合法数据类型
        request.getSession().setAttribute(Constant.USER_DATAMENU, dataMenu);
    }

    /**
     * @Description 登陆验证
     * @param request
     * @param response
     * @return
     * @CreationDate 2016年3月14日 下午2:21:25
     * @Author lidong(dli@gdeng.cn)
     */
    @RequestMapping(value="validate", produces="application/json;charset=UTF-8")
    @ResponseBody
    public String validate(HttpServletRequest request, HttpServletResponse response) {
        String message = login(request, response, null);
        return JSONObject.toJSONString(message, SerializerFeature.WriteDateUseDateFormat);
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
        // 设置session失效
        request.getSession().invalidate();
        mv.setViewName("redirect:/");
        return mv;
    }

}
