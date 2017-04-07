package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.ActActivityBaseinfoToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 活动统计
 * @author dengjianfeng
 *
 */
@RequestMapping("activityStatistic")
@Controller
public class ActActivityStatisticController extends AdminBaseController{

	private final GdLogger logger = GdLoggerFactory.getLogger(ActActivityStatisticController.class);

	@Resource
	private ActActivityBaseinfoToolService activityBaseinfoToolService;
	
	@RequestMapping("list")
	public String initList(){
		return "actActivity/actActivity_statistic";
	}
	
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String, Object> map = getSearchMapParams(request);
			Integer total = activityBaseinfoToolService.getActivityStatisticTotalCount(map);
			resultMap.put("total", total);
			
			setCommParameters(request, map);
			List<ActActivityBaseinfoDTO> list = activityBaseinfoToolService.queryActivityStatisticPage(map);
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.info("活动统计查询异常", e);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}
	
	@RequestMapping(value="checkExportParams",produces="application/json;charset=utf-8")
	@ResponseBody
	public String checkExportParams(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
			resultMap.put("status", 0);
			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
			return JSONObject.toJSONString(resultMap);
		}
		try{
			Map<String, Object> map = getSearchMapParams(request);
			Integer total = activityBaseinfoToolService.getActivityStatisticTotalCount(map); 
			if (total != null && total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("活动导出参数验证异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "exportData")
	public void exportData(HttpServletRequest request, HttpServletResponse response){
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<ActActivityBaseinfoDTO> list = null;
		try{
			Map<String, Object> map = getSearchMapParams(request);
			list = activityBaseinfoToolService.queryActStatisticListByCondition(map);
			
		}catch(Exception e){
			logger.info("导出活动统计数据异常", e);
		}
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = new String("活动统计列表".getBytes(), "ISO8859-1")+startDate+"_"+endDate;
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("活动统计列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "活动编号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "活动名称");// 填充第一行第一个单元格的内容
				Label label20 = new Label(2, 0, "总浏览数 ");// 填充第一行第二个单元格的内容
				Label label30 = new Label(3, 0, "总参与数 ");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "总分享数");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "活动开始时间");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "活动结束时间");// 填充第一行第七个单元格的内容
				Label label70 = new Label(7, 0, "活动状态");// 填充第一行第七个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);// 将单元格加入表格
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, len = list.size(); i < len; i++) {
						ActActivityBaseinfoDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getId()+"");
						Label label1 = new Label(1, i + 1, dto.getName());
						Label label2 = new Label(2, i + 1, dto.getPv()+"");
						Label label3 = new Label(3, i + 1, dto.getJoinCount()+"");
						Label label4 = new Label(4, i + 1, dto.getShareCount()+"");
						Label label5 = new Label(5, i + 1, DateUtil.getDate(dto.getEffectiveStartTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label6 = new Label(6, i + 1, DateUtil.getDate(dto.getEffectiveEndTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label7 = new Label(7, i + 1, getLaunchStr(dto.getLaunch()));
					
						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);// 将单元格加入表格
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 查询列表参数
	 * @param request
	 * @return
	 */
	private Map<String, Object> getSearchMapParams(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", request.getParameter("name"));
		map.put("launch", request.getParameter("launch"));

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(startDate)){
			map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
		}
		if(StringUtils.isNotBlank(endDate)){
			map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(endDate)));
		}
		return map;
	}
	
	private String getLaunchStr(String launch){
		if(launch == null){
			return "";
		}
		String result = "";
		switch (launch) {
		case "0":
			result = "结束";
			break;
		case "1":
			result = "开启";
			break;
		default:
			break;
		}
		return result;
	}
}
