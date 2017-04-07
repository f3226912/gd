package com.gudeng.commerce.gd.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.gudeng.commerce.gd.api.util.SensitiveWordUtil;
import com.gudeng.commerce.gd.api.util.SpecialCharacterUtil;

public class MHttpServletRequest extends HttpServletRequestWrapper {

    public MHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        // 返回值之前 先进行过滤
//        return SpecialCharacterUtil.stripXss(super.getParameter(name));
        return SensitiveWordUtil.stripXss(SpecialCharacterUtil.stripXss(super.getParameter(name)));
    }

    @Override
    public String[] getParameterValues(String name) {
        // 返回值之前 先进行过滤
        String[] values = super.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
//                values[i] = SpecialCharacterUtil.stripXss(values[i]);
                values[i] = SensitiveWordUtil.stripXss(SpecialCharacterUtil.stripXss(values[i]));

            }
        }
        return values;
    }
}