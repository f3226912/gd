package com.gudeng.commerce.gd.home.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.home.Constant;
import com.gudeng.commerce.gd.home.dto.PageDTO;
import com.gudeng.commerce.gd.home.util.JSONUtils;
import com.gudeng.commerce.gd.home.util.PageUtil;
import com.gudeng.framework.web.controller.BaseController;

/**
 * 公共controller类
 * @author ailen
 *
 */
public class HomeBaseController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(MsgController.class);

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}
	
	public void putModel(String key, Object result) {
		HttpServletRequest request = this.getRequest();
		request.setAttribute(key, result);
	}

	/**
	 * 获得前台参数 返回字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return this.getRequest().getParameter(key);
	}

	/**
	 * 获得前台参数 返回整形
	 * 
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key) {
		return Integer.valueOf(this.getRequest().getParameter(key));

	}

	/**
	 * 获得前台参数 返回Long
	 * 
	 * @param key
	 * @return
	 */
	public Long getLong(String key) {
		return Long.valueOf(this.getRequest().getParameter(key));

	}

	/**
	 * @Description getUser 获取登录用户的信息
	 * @param request
	 * @return
	 * @CreationDate 2015年10月20日 下午4:55:44
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public MemberBaseinfoDTO getUser(HttpServletRequest request) {
		return (MemberBaseinfoDTO) request.getSession().getAttribute(Constant.SYSTEM_LOGINUSER);
	}

	/**
	 * 根据总记录计算出 分页条件起始页 记录总页数
	 * 
	 * @param request
	 * @param map
	 */
	protected <T> void setCommParameters(PageDTO<T> pageDTO,
			Map<String, Object> map) {
		// 排序字段名称。
		String sort = StringUtils.trimToNull(this.getString("sort"));
		// 排序顺序
		String sortOrder = StringUtils.trimToNull(this.getString("order"));

		Integer page = null;
		Integer pageSize = null;

		try {
			// 当前第几页
			page = this.getInteger("page");
			// 每页显示的记录数
			pageSize = this.getInteger("pageSize");
		} catch (NumberFormatException nfe) {
			System.out.print("没有提供页面数据，按默认值显示");
		}
		/*
		 * 数据为空不存储，代表第一次打开 默认显示第一页数据
		 */
		if (page != null) {
			pageDTO.setCurrentPage(page);
		}
		if (pageSize != null) {
			pageDTO.setPageSize(pageSize);
		}
		/*
		 * 计算查询前的数据
		 */
		PageUtil.preCatchPagedataCalculate(pageDTO);

		/*
		 * 添加参数到查询条件中
		 */
		map.put(com.gudeng.commerce.gd.home.Constant.START_ROW,
				pageDTO.getIndex());
		map.put(com.gudeng.commerce.gd.home.Constant.ROW_SIZE,
				pageDTO.getPageSize());

		/*
		 * 排序条件添加
		 */
		map.put("sortName", sort);
		map.put("sortOrder", sortOrder);

	}

	/**
	 * 添加数据到Model
	 * 
	 * @param pageDto
	 */
	protected <T> void putPagedata(PageDTO<T> pageDto) {

		PageUtil.preShowPageCalculate(pageDto);
		putModel("page", pageDto);
	}
	
	/**
	 * 获取url
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected String getUrl() {
		HttpServletRequest req = getRequest();
		Enumeration<String> en = req.getParameterNames();
		StringBuffer buffer = new StringBuffer();
		while (en.hasMoreElements()) {
			String elem = en.nextElement();
			buffer.append(elem + "=" + req.getParameter(elem) + "&");
		}
		return req.getRequestURL() + "?" + buffer.toString();
	}

	/**
	 * 
	 * @param strIp
	 * @return
	 */
	public static long ipToLong(String strIp) {
		long[] ip = new long[4];
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	/**
	 * 获取IP
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 设置cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期  以秒为单位
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath("/");
	    if(maxAge>0)  cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}
	
	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
	
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	
	/**
	 * 根据名字获取cookie市场ID
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static String getCookieByMarketId(HttpServletRequest request){
	    Cookie cookie = getCookieByName(request, "marketId");
	    if(null != cookie){
	        return cookie.getValue();
	    }else{
	        return "1";
	    }   
	}
	
	/**
	 * 判断是否登录
	 * @param request
	 * @return
	 */
	public static boolean checkLogin(HttpServletRequest request){
		MemberBaseinfoDTO mbdto = (MemberBaseinfoDTO) request.getSession().getAttribute(Constant.SYSTEM_LOGINUSER);
	    if(null != mbdto){
	        return true;
	    }else{
	        return false;
	    }   
	}
	protected void renderText(String result, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(result);
		} catch (Exception e) {
			logger.error("返回Json结果错误 " + e.getMessage() ,e);
		}
	}
	
	protected void renderJson(Object result, HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			String json = JSONUtils.toJSONStringWithDateFormat(result,"yyyy-MM-dd");
			String jsoncallback = request.getParameter("jsoncallback");
			if(StringUtils.trimToNull(jsoncallback)!=null){
				json = jsoncallback+"("+json+")";
			}			
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("返回Json结果错误 " + e.getMessage() ,e);
		}
	}
	
	protected void renderJson(Object result, HttpServletRequest request,HttpServletResponse response,String format) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			String json = JSONUtils.toJSONStringWithDateFormat(result,format);
			String jsoncallback = request.getParameter("jsoncallback");
			if(StringUtils.trimToNull(jsoncallback)!=null){
				json = jsoncallback+"("+json+")";
			}			
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("返回Json结果错误 " + e.getMessage() ,e);
		}
	}
	

}
