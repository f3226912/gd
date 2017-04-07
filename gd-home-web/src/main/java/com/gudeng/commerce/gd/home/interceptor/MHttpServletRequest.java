package com.gudeng.commerce.gd.home.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.gudeng.commerce.gd.home.util.SensitiveWordUtil;
import com.gudeng.commerce.gd.home.util.SpecialCharacterUtil;
import com.gudeng.commerce.gd.home.util.XssShieldUtil;

/**   
 * @Description 预防XSS攻击，对请求进行脚本等安全过滤，
 * @Project gd-home-web
 * @ClassName MHttpServletRequest.java
 * @Author lidong(dli@gdeng.cn)    
 * @CreationDate 2016年3月29日 下午12:45:14
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public class MHttpServletRequest extends HttpServletRequestWrapper {

    public MHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        // 返回值之前 先进行过滤
        return SensitiveWordUtil.stripXss(SpecialCharacterUtil.stripXss(XssShieldUtil.stripXss(super.getParameter(XssShieldUtil.stripXss(name)))));
    }

    @Override
    public String[] getParameterValues(String name) {
        // 返回值之前 先进行过滤
        String[] values = super.getParameterValues(XssShieldUtil.stripXss(name));
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                values[i] = SensitiveWordUtil.stripXss(SpecialCharacterUtil.stripXss(XssShieldUtil.stripXss(values[i])));
            }
        }
        return values;
    }

}