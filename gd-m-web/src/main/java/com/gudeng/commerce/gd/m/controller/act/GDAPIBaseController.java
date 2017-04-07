package com.gudeng.commerce.gd.m.controller.act;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.m.dto.ProCommonPageDTO;
import com.gudeng.commerce.gd.m.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.m.util.Des3Request;
import com.gudeng.commerce.gd.m.util.Des3Response;
import com.gudeng.commerce.gd.m.util.GSONUtils;
import com.gudeng.commerce.gd.m.util.JSONUtils;
import com.gudeng.commerce.gd.m.util.ObjectResult;
import com.gudeng.framework.web.controller.BaseController;

public class GDAPIBaseController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(GDAPIBaseController.class);
	
	public static final String error_msg_key="message"; 
	
	/** 分页参数：起始页 */
	protected final String START_ROW = "startRow";
	/** 分页参数：结束页 */
	protected final String END_ROW = "endRow";
	
	@Autowired
	private MessageSource messageSource;
//	@Autowired
//	private RedisService redisService;

	
	/**
	 * 可以获取到当前登录用户的基本信息
	 * @return
	 */
	protected MemberBaseinfoDTO getUser(String sid){
		MemberBaseinfoDTO memberBaseinfoDTO=null;
//		try {
//			String object=redisService.get(sid);
//			memberBaseinfoDTO=JSON.parseObject(object, MemberBaseinfoDTO.class);
//		} catch (Exception e) {
//			logger.error("类型转换出错",e); 
//		}
		return memberBaseinfoDTO;
	}
	
	protected String getMessage(String key,Object[] params) {
		//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return messageSource.getMessage(key, params, Locale.CHINA);
		
	}
	
	protected String getMessage(String key,List<?> params) {
		//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		if(params==null){
			return messageSource.getMessage(key, null, Locale.CHINA);
		}else{
			return messageSource.getMessage(key, params.toArray(), Locale.CHINA);
		}
	}
	
	protected String getMessage(String key) {
		
		//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return messageSource.getMessage(key,null,Locale.CHINA);
		
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
//		renderJson(result, request, response, null);
		renderEncodeJson(result, request, response, null,false);
	}
	
	protected void renderJson(Object result, HttpServletRequest request,HttpServletResponse response,String format) {
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=utf-8");
//		try {
//			String json =null;
//
//			if(format==null ){
//				 json = JSONUtils.toJSONStringWithDateFormat(result,"yyyy-MM-dd");
//			}else{
//				 json = JSONUtils.toJSONStringWithDateFormat(result,format);
//			}
//			String jsoncallback = request.getParameter("jsoncallback");
//			if(StringUtils.trimToNull(jsoncallback)!=null){
//				json = jsoncallback+"("+json+")";
//			}			
//			response.getWriter().write(json);
//		} catch (Exception e) {
//			logger.error("返回Json结果错误 " + e.getMessage() ,e);
//		}
		renderEncodeJson(result, request, response, format, false);
	}
	/**
	 * 不传入dataFormat,不加密
	 * 
	 * */
	protected void setResult(ObjectResult result, ErrorCodeEnum statusCode, HttpServletRequest request, HttpServletResponse response){
		result.setStatusCode(statusCode.getStatusCode());
		result.setMsg(statusCode.getStatusMsg());
		renderEncodeJson(result, request, response,null,false);
	}
	
	protected void setResultFormat(ObjectResult result, ErrorCodeEnum statusCode, HttpServletRequest request, HttpServletResponse response){
		result.setStatusCode(statusCode.getStatusCode());
		result.setMsg(statusCode.getStatusMsg());
		renderEncodeJson(result, request, response,"yyyy-MM-dd hh:mm:ss",false);
	}
	
	/**
	 * 不传入dateFormat,格式化时间
	 * 
	 * */
	protected void setEncodeResult(ObjectResult result, ErrorCodeEnum statusCode, HttpServletRequest request, HttpServletResponse response){
		result.setStatusCode(statusCode.getStatusCode());
		result.setMsg(statusCode.getStatusMsg());
		renderEncodeJson(result, request, response, null, true);
	}
	
	/**
	 * 传入dateFormat,格式化时间
	 * 
	 * */
	protected void setEncodeResult(ObjectResult result, ErrorCodeEnum statusCode, HttpServletRequest request, HttpServletResponse response, String dateFormat){
		result.setStatusCode(statusCode.getStatusCode());
		result.setMsg(statusCode.getStatusMsg());
		renderEncodeJson(result, request, response, dateFormat, true);
	}
	
	protected void renderEncodeJson(Object result, HttpServletRequest request,HttpServletResponse response,String format,boolean isEncode) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
//			String json = JSONUtils.toJSONStringWithDateFormat(result,format);
			String json=null;
			if(format==null ){
				 json = JSONUtils.toJSONStringWithDateFormat(result,"yyyy-MM-dd");
			}else{
				 json = JSONUtils.toJSONStringWithDateFormat(result,format);
			}			
			String jsoncallback = request.getParameter("jsoncallback");
			if(StringUtils.trimToNull(jsoncallback)!=null){
				json = jsoncallback+"("+json+")";
			}
//			response.getWriter().write(json);
			if(isEncode){
				response.getWriter().write(Des3Response.encode(json));
			}else{
				response.getWriter().write(json);
			}
		} catch (Exception e) {
			logger.error("返回Json结果错误 " + e.getMessage() ,e);
		}
	}
	
	/**
	 * 设置cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期  以秒为单位
	 */
	protected void addCookie(HttpServletResponse response,String name,String value,int maxAge){
		Cookie cookie = new Cookie(name,value);
		//cookie.setPath("/");
		if(maxAge>0){
			cookie.setMaxAge(maxAge*3600);
		}
		response.addCookie(cookie);
	}
	
	/**
	 * 删除cookie
	 * @param request
	 * @param response
	 * @param name  cookie名字
	 */
	protected void delCookie(HttpServletRequest request,HttpServletResponse response,String name){
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					Cookie cookie = new Cookie(name, "");// 这边得用"",不能用null
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}
	
	
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	protected Cookie getCookieByName(HttpServletRequest request,String name){
		Map<String,Cookie> cookieMap = ReadCookieMap(request);
		if(cookieMap.containsKey(name)){
			Cookie cookie = (Cookie)cookieMap.get(name);
			return cookie;
		}
		return null;
	}
	
	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	protected Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
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
	 * 根据总记录计算出 分页条件起始页 记录总页数 
	 * 
	 * @param request
	 * @param map
	 */
	protected ProCommonPageDTO getPageInfo(HttpServletRequest request, Map<String, Object> map){

		//当前第几页
		String page=request.getParameter("pageNum");
		//每页显示的记录数
		String rows=request.getParameter("pageSize"); 
		//当前页  
        int currentPage = Integer.parseInt((StringUtils.isBlank(page) || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((StringUtils.isBlank(rows) || rows == "0") ? "10":rows);
        
        ProCommonPageDTO pageDTO = new ProCommonPageDTO(currentPage, pageSize);
        map.put(START_ROW, pageDTO.getOffset());
		map.put(END_ROW, pageDTO.getPageSize());
		return pageDTO;
	}
	
	protected String getParamDecodeStr(HttpServletRequest request) throws Exception{
		
		String encryptParam = request.getParameter("param");
		//encryptParam="EBc9L94mvblz66s/g/PCeg+nfhnSV7zHI3HB2P70hdIga63QGKxNUw==";
		return StringUtils.isBlank(encryptParam) ? "{}" : Des3Request.decode(encryptParam);
	}
	
	/**
	 * 根据总记录计算出 分页条件起始页 记录总页数 
	 * 
	 * @param request
	 * @param map
	 */
	protected ProCommonPageDTO getPageInfoEncript(String jsonStr, Map<String, Object> map){

		//当前第几页
		String page = (String)GSONUtils.getJsonValueStr(jsonStr, "pageNum");
		//每页显示的记录数
		String rows = (String)GSONUtils.getJsonValueStr(jsonStr, "pageSize"); 
		//当前页  
        int currentPage = Integer.parseInt((StringUtils.isBlank(page) || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((StringUtils.isBlank(rows) || rows == "0") ? "10":rows);
        
        ProCommonPageDTO pageDTO = new ProCommonPageDTO(currentPage, pageSize);
        map.put(START_ROW, pageDTO.getOffset());
		map.put(END_ROW, pageDTO.getPageSize());
		return pageDTO;
	}	
	
	protected int checkPageParameters(String jsonStr){
		
		//当前第几页
		String page = (String)GSONUtils.getJsonValueStr(jsonStr, "pageNum");
		//每页显示的记录数
		String rows = (String)GSONUtils.getJsonValueStr(jsonStr, "pageSize"); 
		
		if(StringUtils.isEmpty(page)){
			return -1;
		}
		if(StringUtils.isEmpty(rows)){
			return -2;
		}
		return 0;
		
	}
	
	/**
	 * 获取解密后的对象
	 * @param request
	 * @param classOfT 类类型
	 * @return
	 */
	protected <T> Object getDecryptedObject(HttpServletRequest request, Class<T> classOfT){
		try {
			@SuppressWarnings("unchecked")
			T object = (T) GSONUtils.fromJsonToObject(getParamDecodeStr(request), classOfT);
			return object;
		} catch (Exception e) {
			logger.error("[ERROR]对象: "+classOfT.toString()+" 数据解密异常:", e);
		}
		return null;
	}
	
	/**
	 * 获取解密后某个key对应的值
	 * @param request
	 * @param key 
	 * @return
	 */
	protected String getDecryptedValue(HttpServletRequest request, String key){
		try {
			return GSONUtils.getJsonValueStr(getParamDecodeStr(request), key);
		} catch (Exception e) {
			logger.error("[ERROR]获取"+key+"数据解密异常:", e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(ErrorCodeEnum.SUCCESS);
	}
}
