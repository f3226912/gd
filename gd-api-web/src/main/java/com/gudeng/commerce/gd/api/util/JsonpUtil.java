package com.gudeng.commerce.gd.api.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;

public final class JsonpUtil {

    public static final String JSONP_CALLBACK_METHODNAME_PARAMNAME = "callback";

    public static final String JSONP_CALLBACK_METHODNAME_DEFAULT = "callback";

    private JsonpUtil() {

    }

    /**
     * 处理JSONP跨域调用。
     * 
     * @param req
     *            请求对象
     * @param respStr
     *            JSON数据(JSON字符串)
     * @return　jsonp调用
     */
    public static String jsonpCallback(HttpServletRequest req, String jsonData) {
        String respStr = null;
        String callbackMethodName = req.getParameter(JSONP_CALLBACK_METHODNAME_PARAMNAME);
        if (StringUtils.isEmpty(callbackMethodName)) {
            // callback(jsonData); ==> callback({a:1,b:2,c:3});
        	callbackMethodName = req.getParameter("jsonpcallback");
        }
        if(!StringUtils.isEmpty(callbackMethodName)){
        	respStr = callbackMethodName + "(" + jsonData + ");";
        }else {
            respStr = jsonData;
        }
        return respStr;
    }

    /**
     * 处理JSONP跨域调用。
     * 
     * @param req
     *            请求对象
     * @param respStr
     *            JSON数据(JSON字符串)
     * @return　jsonp调用
     */
    public static JSONPObject jsonpCallback2(HttpServletRequest req, String jsonData) {
        JSONPObject jsonp = null;
        String callbackMethodName = req.getParameter(JSONP_CALLBACK_METHODNAME_PARAMNAME);
        if (callbackMethodName != null && callbackMethodName.trim().length() > 0) {
            jsonp = new JSONPObject(callbackMethodName, jsonData);
        }
        else {
            jsonp = new JSONPObject(JSONP_CALLBACK_METHODNAME_DEFAULT, jsonData);
        }
        return jsonp;
    }

}
