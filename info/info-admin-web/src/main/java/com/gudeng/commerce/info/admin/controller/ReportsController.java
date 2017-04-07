package com.gudeng.commerce.info.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.info.admin.report.ReportBaseCommand;
import com.gudeng.commerce.info.admin.service.ProBaiduToolService;
import com.gudeng.commerce.info.admin.service.ProBszbankToolService;
import com.gudeng.commerce.info.admin.service.ReportsToolService;
import com.gudeng.commerce.info.admin.service.SysMenuAdminService;
import com.gudeng.commerce.info.customer.dto.ProBszbankDTO;
import com.gudeng.commerce.info.customer.dto.ReportDataDTO;
import com.gudeng.commerce.info.customer.dto.ReportsDTO;
import com.gudeng.commerce.info.customer.entity.SysMenu;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 报表管理
 * @author wind
 *
 */
@RequestMapping("reports")
@Controller
public class ReportsController extends AdminBaseController implements ApplicationContextAware{
	
	private final GdLogger logger = GdLoggerFactory.getLogger(ReportsController.class);

	@Autowired
	private ReportsToolService reportsToolService;
	
	@Autowired
	private ProBszbankToolService proBszbankToolService;
	
	@Autowired
	private ProBaiduToolService proBaiduToolService;
	
	 @Autowired
	 private SysMenuAdminService sysMenuService;
	
	private ApplicationContext ctx;
	
	private static Map<Long, String> commandMap;
	static{
		commandMap = new HashMap<Long, String>();
		commandMap.put(1L, "transactionAmountReportCommand");
		commandMap.put(2L, "orderNumberReportCommand");
		commandMap.put(3L, "orderAmountReportCommand");
		commandMap.put(4L, "transactionUserReportCommand");
		commandMap.put(5L, "userReportCommand");
		commandMap.put(6L, "userAmountReportCommand");
		commandMap.put(7L, "dailyOrderReportCommand");
		commandMap.put(8L, "userOrderReportCommand");
		commandMap.put(9L, "userDealReportCommand");
		commandMap.put(10L, "userVisitBaiduReportCommand");
		commandMap.put(11L, "newUserVisitBaiduReportCommand");
		commandMap.put(12L, "ipVisitBaiduReportCommand");
		commandMap.put(13L, "comRegCountReportCommand");
		commandMap.put(14L, "nsyUserRegReportCommand");
		commandMap.put(15L, "webRegCountReportCommand");
	}
	
	@RequestMapping("")
	public String reports(HttpServletRequest request,ModelMap map){
		try {
			Map<String, Object> map2 = new HashMap<String, Object>();
			// 条件参数
			map2.put("level", 1);
			map2.put("attribute", 3);
			map2.put("startRow", 0);
			map2.put("endRow", 999);
			List<SysMenu> list = sysMenuService.getByCondition(map2);
			map.put("menuList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "reports/reports-list";
	}
	
	@RequestMapping(value="query", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String query(HttpServletRequest request, HttpServletResponse response, ReportsDTO reportsDTO){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String, Object> paramMap = reportsDTOToMap(reportsDTO);
			Integer total = reportsToolService.getTotalByCondition(paramMap);
			setCommParameters(request, paramMap);
			List<ReportsDTO> rows = reportsToolService.getPageByCondition(paramMap);
			
			resultMap.put("total", total);
			resultMap.put("rows", rows);
		}
		catch(Exception e){
			logger.warn("查询报表列表异常", e);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 修改状态
	 * @param request
	 * @param response
	 * @param reportsDTO
	 * @return
	 */
	@RequestMapping("updateState")
	@ResponseBody
	public String updateState(HttpServletRequest request, HttpServletResponse response, ReportsDTO reportsDTO){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String, Object> paramMap = reportsDTOToMap(reportsDTO);
			reportsToolService.updateState(paramMap);
			resultMap.put("resultCode", 0);
		}
		catch(Exception e){
			logger.warn("修改报表状态异常", e);
			resultMap.put("resultCode", -1);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 进入按天统计报表详情页面
	 * @param request
	 * @param response
	 * @param proBszbankDTO
	 * @return
	 */
	@RequestMapping(value="detail")
	public String detail(HttpServletRequest request, HttpServletResponse response, ProBszbankDTO proBszbankDTO){
		try{
			ReportsDTO reportsDTO = reportsToolService.getById(proBszbankDTO.getReportsID());
			request.setAttribute("reports", reportsDTO);
			
			//默认设置时间为最近七天
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			Calendar calendar = Calendar.getInstance(); 
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_MONTH, -7); 
			String startDate =dft.format(calendar.getTime());
			calendar.clear();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_MONTH, -1); 
			String endDate =dft.format(calendar.getTime());
			
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("queryStartDate",startDate);
			queryMap.put("queryEndDate",endDate);
			request.setAttribute("queryMap", queryMap);
		}
		catch(Exception e){
			logger.warn("查看报表详情异常", e);
		}
		return "reports/detail";
	}
	
	/**
	 * 获取报表数据
	 * @param request
	 * @param response
	 * @param proBszbankDTO
	 * @return
	 */
	@RequestMapping(value="getReportData",produces="application/json; charset=utf-8")
	@ResponseBody
	public String getReportData(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Long reportsId = StringUtils.isBlank(request.getParameter("reportsId"))? 0L : Long.parseLong(request.getParameter("reportsId"));
		if(reportsId == null){
			resultMap.put("resultCode", -1);
			return JSONObject.toJSONString(resultMap);
		}
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("reportsId", reportsId);
			
			String marketId = request.getParameter("market");
			if(StringUtils.isNotBlank(marketId)){
				paramMap.put("marketId", Long.parseLong(marketId));
			}
			String queryStartDate =request.getParameter("startTime");//开始时间
			String queryEndDate =request.getParameter("endTime");//结束时间
			
			if(StringUtils.isNotBlank(queryStartDate) && StringUtils.isNotBlank(queryEndDate)){
				paramMap.put("queryStartDate",queryStartDate);
				paramMap.put("queryEndDate",queryEndDate);
			}else{
				//默认设置时间为最近七天
				SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				Calendar calendar = Calendar.getInstance(); 
				calendar.setTime(currentDate);
				calendar.add(Calendar.DAY_OF_MONTH, -7); 
				String startDate =dft.format(calendar.getTime());
				calendar.clear();
				calendar.setTime(currentDate);
				calendar.add(Calendar.DAY_OF_MONTH, -1); 
				String endDate =dft.format(calendar.getTime());
				paramMap.put("queryStartDate",startDate);
				paramMap.put("queryEndDate",endDate);
			}
			
			ReportsDTO reportsDTO = reportsToolService.getById(reportsId);
			if(reportsDTO == null){
				resultMap.put("resultCode", -1);
				return JSONObject.toJSONString(resultMap);
			}

		
			Map<String, Object> rowsMap = new HashMap<String, Object>();
			//报表信息
			rowsMap.put("id", reportsDTO.getId());
			rowsMap.put("menuId", reportsDTO.getMenuId());
			rowsMap.put("name", reportsDTO.getName());
			rowsMap.put("type", reportsDTO.getType());
			rowsMap.put("where", reportsDTO.getShow());
			rowsMap.put("show", reportsDTO.getShow());
			rowsMap.put("parameter1", reportsDTO.getParameter1());
			rowsMap.put("parameter2", reportsDTO.getParameter2());
			rowsMap.put("parameter3", reportsDTO.getParameter3());
			rowsMap.put("parameter4", reportsDTO.getParameter3());
			
			//报表数据
			ReportDataDTO reportDataDTO = null;
			String commandName = commandMap.get(reportsId);
			if(commandName != null){
				ReportBaseCommand reportCommand = (ReportBaseCommand) ctx.getBean(commandName);
				reportDataDTO = reportCommand.generateReportData(paramMap);
			}
			if(reportDataDTO != null){
				rowsMap.put("data", reportDataDTO);
				rowsMap.put("sum", reportDataDTO.getSum());
				rowsMap.put("sumDate", reportDataDTO.getParms().size());
			}else{
				rowsMap.put("sum", 0);
				rowsMap.put("sumDate", 0);
			}
			
			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			rows.add(rowsMap);
			resultMap.put("rows", rows);
			resultMap.put("size", 1);
			resultMap.put("resultCode", 0);
		}
		catch(Exception e){
			logger.warn("查看报表详情异常", e);
			resultMap.put("resultCode", -1);
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	private Map<String, Object> reportsDTOToMap(ReportsDTO reportsDTO){
		Map<String, Object> map = new HashMap<String, Object>();
		if(reportsDTO.getId() != null){
			map.put("id", reportsDTO.getId());
		}
		if(StringUtils.isNotBlank(reportsDTO.getName())){
			map.put("name", reportsDTO.getName());
		}
		if(StringUtils.isNotBlank(reportsDTO.getType())){
			map.put("type", reportsDTO.getType());
		}
		if(StringUtils.isNotBlank(reportsDTO.getState())){
			map.put("state", reportsDTO.getState());
		}
		if(reportsDTO.getDataSourceId() != null){
			map.put("dataSourceId", reportsDTO.getDataSourceId());
		}
		if(StringUtils.isNotBlank(reportsDTO.getMenuId())){
			map.put("menuId", reportsDTO.getMenuId());
		}
		return map;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ctx = applicationContext;
	}
}
