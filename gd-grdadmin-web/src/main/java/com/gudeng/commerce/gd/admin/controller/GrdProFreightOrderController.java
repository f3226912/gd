package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.GrdProFreightOrderToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.JxlsExcelUtil;
import com.gudeng.commerce.gd.admin.util.RandomIdGenerator;
import com.gudeng.commerce.gd.bi.dto.GrdProFreightOrderDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * @author liufan
 *
 */
@Controller
public class GrdProFreightOrderController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdProFreightOrderController.class);

	@Autowired
	private GrdProFreightOrderToolService grdProFreightOrderToolService;

	
	/**
	 * 配置文件
	 */
	@Autowired
	public GdProperties gdProperties;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@RequestMapping("grdProFreightOrder/main")
	public String list(HttpServletRequest request) {
		return "grdProFreightOrder/main";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@RequestMapping("grdProFreightOrder/query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			Map<String, Object> map =getSearchMapParams(request);
			// 设置查询参数
			// 记录数
			map.put("total", grdProFreightOrderToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GrdProFreightOrderDTO> list = grdProFreightOrderToolService.getListPage(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}

	/**
	 * 查询列表参数
	 * @param request
	 * @return
	 */
	private Map<String, Object> getSearchMapParams(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
		map.put("marketId", request.getParameter("marketId"));
		//map.put("teamId", request.getParameter("teamId"));
		map.put("grdUserName", request.getParameter("grdUserName"));
		map.put("grdMobile", request.getParameter("grdMobile"));
		map.put("orderStatus", request.getParameter("orderStatus"));
		map.put("payStatus", request.getParameter("payStatus"));
		map.put("startGenerateTime", request.getParameter("startGenerateTime"));
		map.put("endGenerateTime", request.getParameter("endGenerateTime"));
		
		/*if(StringUtils.isNotBlank(startDate)){
			map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
		}
		*/
		return map;
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@ResponseBody
	@RequestMapping(value = "grdProFreightOrder/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map =getSearchMapParams(request);
			int total = grdProFreightOrderToolService.getTotal(map);
			if (total > 10000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}else if (total < 1) {
				result.put("status", 0);
				result.put("message", "导出的结果集无任何数据，请重新修改查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		} catch (Exception e) {
			logger.info("product checkExportParams with ex : {} ", new Object[] { e });
		}
		return JSONObject.toJSONString(result);
	}

	/**
	 * 导出Excel文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author liufan
	 */
	@RequestMapping(value = "grdProFreightOrder/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		OutputStream out = null;
		try {
			Map<String, Object> map =getSearchMapParams(request);
			// 查询数据
			List<GrdProFreightOrderDTO> list = grdProFreightOrderToolService.getList(map);
	
			//获取模板文件路径
			String templatePath = gdProperties.getProperties().getProperty("GRDPROFREIGHTORDER_TEMPLETE");
			String srcFilePath = getRequest().getSession().getServletContext().getRealPath("/WEB-INF/" + templatePath);
	
			// 设置文件名和头信息
			String destFileName = new String("货运订单统计列表".getBytes(), "ISO8859-1") + RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xls";			//目标文件名
			response.setHeader("Content-Disposition", "attachment;filename=" + destFileName);	// 设置响应
			response.setContentType("application/vnd.ms-excel");
	
			// 设置列表数据
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("list", list);
	
			//获取输出流
			out = response.getOutputStream();
			JxlsExcelUtil.exportReportFromAbsolutePath(srcFilePath, beans, out);
		} catch (Exception e) {
			e.printStackTrace();
			logger.trace("数据导出失败", e);
		} finally {
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
