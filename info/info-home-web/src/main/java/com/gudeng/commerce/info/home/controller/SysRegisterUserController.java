package com.gudeng.commerce.info.home.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.info.customer.entity.SysRegisterUser;
import com.gudeng.commerce.info.customer.util.StringUtil;
import com.gudeng.commerce.info.home.service.SysRegisterUserHomeService;
import com.gudeng.commerce.info.home.util.LoginUserUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("sysRegisterUser")
public class SysRegisterUserController extends AdminBaseController {
    private static final GdLogger logger = GdLoggerFactory.getLogger(SysRegisterUserController.class);

    /** 用户Serivce */
    @Autowired
    private SysRegisterUserHomeService sysRegisterUserHomeService;

    /**
     * 查询账户信息
     * 
     * @author liufan
     * @param request
     * @return String
     */
    @RequestMapping("getSysRegisterUser")
    public String getSysRegisterUser(HttpServletRequest request) throws Exception {
        try {
            String userID = request.getParameter("userID");
            if (StringUtils.isEmpty(userID)) {
                userID = LoginUserUtil.getLoginUserId(request);
            }
            // 查询用户信息
            SysRegisterUser user = sysRegisterUserHomeService.get(userID);
            putModel("user", user);
        } catch (Exception e) {
            logger.trace(e.getMessage());
        }
        return "H5/account/my-account";
    }

    /**
     * 修改用户密码
     * 
     * @author liufan
     * @param request
     * @return String
     */
    @RequestMapping(value = "updateUserPwd")
    @ResponseBody
    public String updateUserPwd(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 获取旧密码并进行加密
            String userPassWord = StringUtil.stringEncryptMD5(request.getParameter("oldPWD"));
            map.put("userPassWord", userPassWord);
            // 获取新密码并进行加密
            String userNewPassWord = StringUtil.stringEncryptMD5(request.getParameter("newPWD"));
            map.put("userNewPassWord", userNewPassWord);
            String userID = request.getParameter("userID");
            map.put("userID", userID);
            return sysRegisterUserHomeService.updateUserPwd(map);
            // 执行修改密码处理
        } catch (Exception e) {
            return getExceptionMessage(e, logger);
        }

    }

    /**
     * 修改用户密码初始化页面;
     * 
     * @author liufan
     * @param request
     * @return
     */
    @RequestMapping(value = "updateUserPWDInit")
    public String updateUserPWDInit(HttpServletRequest request) throws Exception {
        return "H5/account/modifyPassword";
    }

    /**
     * 我的账户初始化页面;
     * 
     * @author liufan
     * @param request
     * @return
     */
    @RequestMapping(value = "accountInit")
    public String accountInit(HttpServletRequest request) throws Exception {
        return "H5/account/main";
    }

}
