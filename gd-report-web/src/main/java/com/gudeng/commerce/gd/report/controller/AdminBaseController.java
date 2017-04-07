package com.gudeng.commerce.gd.report.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gudeng.framework.web.controller.BaseController;

public class AdminBaseController  extends BaseController {

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

	public AdminBaseController() {

	}
	
	public HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	public void putModel(String key,Object result){
		HttpServletRequest request = this.getRequest();
		request.setAttribute(key, result);
	}
	
	/**
	 * 获取sessian中用户信息
	 * @param request
	 * @return
	 * @date 2015年4月16日下午7:16:17
	 *
	 */
//	public UserSummary getUserInfo(HttpServletRequest request){
//		return (UserSummary) request.getSession().getAttribute("userSummaryForAuthority");
//	}
	
	/**
	 * 根据总记录计算出 分页条件起始页 记录总页数 
	 * 
	 * @param request
	 * @param map
	 */
	protected void setCommParameters(HttpServletRequest request, Map<String, Object> map){

		//排序字段名称。
		String sort=StringUtils.trimToNull(request.getParameter("sort"));
		//排序顺序
		String sortOrder=StringUtils.trimToNull(request.getParameter("order"));
		//当前第几页
		String page=request.getParameter("page");
		//每页显示的记录数
		String rows=request.getParameter("rows"); 
		//当前页  
        int currentPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int pageSize = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number +1   
        int startRow = (currentPage-1)*pageSize;  
        map.put(START_ROW, startRow);
		map.put(END_ROW, pageSize);
		map.put("sortName", sort);
		map.put("sortOrder", sortOrder);
	}
	

}
