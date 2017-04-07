package com.gudeng.commerce.gd.m.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gudeng.commerce.gd.promotion.dto.WechatMemberDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.m.Constant;
import com.gudeng.commerce.gd.m.dto.PageDTO;
import com.gudeng.commerce.gd.m.dto.ResponseDTO;
import com.gudeng.commerce.gd.m.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.m.enums.ResponseCodeEnum;
import com.gudeng.commerce.gd.m.util.Des3Request;
import com.gudeng.commerce.gd.m.util.Des3Response;
import com.gudeng.commerce.gd.m.util.JSONUtils;
import com.gudeng.commerce.gd.m.util.ObjectResult;
import com.gudeng.commerce.gd.m.util.PageUtil;
import com.gudeng.framework.web.controller.BaseController;

import cn.gdeng.weixin.base.bean.api.UserInfo;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

/**
 * @Description TODO
 * @Project gd-m-web
 * @ClassName AdminBaseController.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月26日 上午8:54:10
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public class MBaseController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(MBaseController.class);
	/** 分页参数：起始页 */
	protected final String START_ROW = "startRow";
	/** 分页参数：结束页 */
	protected final String END_ROW = "endRow";
	/** 总记录数 */
	protected final String ALL_RECORDERS = "allRecorders";
	/** 总页数 */
	protected final String PAGE_COUNT = "pageCount";
	/** 当前页 */
	protected final String PAGE_NO = "pageNo";
	/** 每页数据条数 */
	protected final String PAGE_SIZE = "pageSize";

	protected final int CODE_STATE_OK = 0;
	protected final int CODE_STATE_TIMEOUT = 1;
	protected final int CODE_STATE_ERROR = 2;

	public MBaseController() {

	}

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 获取不带端口号(即默认80)的项目根访问路径
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	public String get80BasePath(HttpServletRequest request) {
		String basePath = request.getScheme() + "://" + request.getServerName() + "/" ;
		return basePath.replace("http://", "https://");
	}

	/**
	 * 获取带端口号的项目根访问路径
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	public String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		return basePath.replace("http://", "https://");
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

	public void putModel(String key, Object result) {
		HttpServletRequest request = this.getRequest();
		request.setAttribute(key, result);
	}

	/**
	 * @Description getUser 获取登录用户的信息
	 * @param request
	 * @return
	 * @CreationDate 2015年10月20日 下午4:55:44
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public WechatMemberDTO getUser(HttpServletRequest request) {
		return (WechatMemberDTO) request.getSession().getAttribute(Constant.SYSTEM_LOGINUSER);
	}

	/**
	 * 获取当前微信用户信息
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	public UserInfo getWeixinUser(HttpServletRequest request) {
		return (UserInfo) request.getSession().getAttribute(Constant.SYSTEM_WEIXINUSER);
	}

	/**
	 * 判断是否登录
	 * 
	 * @param request
	 * @return
	 */
	public static boolean checkLogin(HttpServletRequest request) {
		MemberBaseinfoDTO mbdto = (MemberBaseinfoDTO) request.getSession().getAttribute(Constant.SYSTEM_LOGINUSER);
		if (null != mbdto) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检验验证码是否正确
	 *
	 * @param code
	 * @return CODE_STATE_OK = 0
	 *         <p>
	 *         CODE_STATE_TIMEOUT = 1
	 *         <p>
	 *         CODE_STATE_ERROR = 2
	 *         <p>
	 * @author lidong
	 */
	protected int checkCode(String code) {
		HttpSession session = getRequest().getSession();
		String scode = (String) session.getAttribute(Constant.MOBILE_CHECK);
		// 数据判断 semon 20151031
		if (StringUtils.isBlank(code) || StringUtils.isBlank(scode)) {
			return CODE_STATE_TIMEOUT;
		}
		if (code.equals(scode)) {
			return CODE_STATE_OK;
		}
		return CODE_STATE_ERROR;
	}

	/**
	 * 产生随机的六位数
	 *
	 * @return
	 */
	protected String getSixCode() {
		Random rad = new Random();
		String result = rad.nextInt(1000000) + "";
		if (result.length() != 6) {
			return getSixCode();
		}
		return result;
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
	 * 
	 * @param response
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * 根据总记录计算出 分页条件起始页 记录总页数
	 * 
	 * @param map
	 */
	protected <T> void setCommParameters(PageDTO<T> pageDTO, Map<String, Object> map) {
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
			logger.info("没有提供页面数据，按默认值显示");
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
		map.put(Constant.START_ROW, pageDTO.getIndex());
		map.put(Constant.ROW_SIZE, pageDTO.getPageSize());

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

	protected String getParamDecodeStr(HttpServletRequest request) throws Exception {
		return Des3Request.decode(request.getParameter("param"));
	}

	/**
	 * 不传入dateFormat,格式化时间
	 * 
	 */
	protected void setEncodeResult(ObjectResult result, ErrorCodeEnum statusCode, HttpServletRequest request, HttpServletResponse response) {
		result.setStatusCode(statusCode.getStatusCode());
		result.setMsg(statusCode.getStatusMsg());
		renderEncodeJson(result, request, response, null, true);
	}

	/**
	 * 传入dateFormat,格式化时间
	 * 
	 * 不需要格式化时间的，dateFormat 传入null
	 * 
	 * 加密
	 * 
	 */
	protected void setEncodeResult(ObjectResult result, ErrorCodeEnum statusCode, HttpServletRequest request, HttpServletResponse response, String format) {
		result.setStatusCode(statusCode.getStatusCode());
		result.setMsg(statusCode.getStatusMsg());
		renderEncodeJson(result, request, response, format, true);
	}

	protected void renderEncodeJson(Object result, HttpServletRequest request, HttpServletResponse response, String format, boolean isEncode) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			// String json = JSONUtils.toJSONStringWithDateFormat(result,format);
			String json = null;
			if (format == null) {
				json = JSONUtils.toJSONStringWithDateFormat(result, "yyyy-MM-dd");
			} else {
				json = JSONUtils.toJSONStringWithDateFormat(result, format);
			}
			String jsoncallback = request.getParameter("jsoncallback");
			if (StringUtils.trimToNull(jsoncallback) != null) {
				json = jsoncallback + "(" + json + ")";
			}
			// response.getWriter().write(json);
			if (isEncode) {
				response.getWriter().write(Des3Response.encode(json));
			} else {
				response.getWriter().write(json);
			}
		} catch (Exception e) {
			logger.error("返回Json结果错误 " + e.getMessage(), e);
		}
	}

	/**
	 * 返回JSON封装方法
	 * 
	 * @param result
	 * @param request
	 * @param response
	 */
	protected void renderJson(Object result, HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			String json = JSONUtils.toJSONStringWithDateFormat(result, "yyyy-MM-dd");
			String jsoncallback = request.getParameter("jsoncallback");
			if (StringUtils.trimToNull(jsoncallback) != null) {
				json = jsoncallback + "(" + json + ")";
			}
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("返回Json结果错误 " + e.getMessage(), e);
		}
	}

	/**
	 * 返回json结果
	 * 
	 */
	public static String getResponseJson(Object obj) {
		ResponseDTO responseDTO = new ResponseDTO();
		if (obj instanceof ResponseCodeEnum) {
			responseDTO.setResultcode(((ResponseCodeEnum) obj).getResultCode());
			responseDTO.setResultmsg(((ResponseCodeEnum) obj).getResultMsg());
		} else {
			responseDTO.setResultcode(ResponseCodeEnum.OPERATION_SUCCESS.getResultCode());
			responseDTO.setResultmsg(ResponseCodeEnum.OPERATION_SUCCESS.getResultMsg());
			responseDTO.setDatajson(obj);
		}
		String responseJson = JSONObject.toJSONString(responseDTO, SerializerFeature.WriteDateUseDateFormat);
		logger.info("响应结果：" + responseJson);
		return responseJson;
	}
}
