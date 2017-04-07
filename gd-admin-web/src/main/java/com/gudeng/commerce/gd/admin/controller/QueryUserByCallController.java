package com.gudeng.commerce.gd.admin.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AreaSettingToolService;
import com.gudeng.commerce.gd.admin.service.CarsManageService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.dto.RecommendedUserDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("queryUserByCall")
public class QueryUserByCallController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(QueryUserByCallController.class);
	
	@Autowired
	public CarsManageService carsManageService;
	
	@Autowired
	public AreaSettingToolService areaSettingToolService;

	

	/**
	 * 列表页面
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
//			String mobile =this.getUser(request).getMobile();
//			map.put("mobile", mobile);
			List<AreaSettingDTO> list = areaSettingToolService.getAllAreaName(map);
			request.setAttribute("areaNameList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryUserByCall/userList";
	}
	
	
	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		try {
			//String mobile =this.getUser(request).getMobile();
			String  callStartDate =request.getParameter("queryCallStartDate");
			String  callEndDate =request.getParameter("queryCallEndDate");
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("mobile",request.getParameter("mobile"));
			map.put("recommendedMobile",request.getParameter("recommendedMobile"));
			map.put("areaName",request.getParameter("areaName"));
			map.put("startDate", request.getParameter("startDate"));
			map.put("endDate", request.getParameter("endDate"));
			map.put("callStartDate", callStartDate);
			map.put("callEndDate", callEndDate);
			map.put("TCType", request.getParameter("TCType"));
			//记录数
			map.put("total", carsManageService.getTotalByCallTime(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<RecommendedUserDTO> list = carsManageService.getRecommendedUserListByCallTime(map);
		   	if(list !=null && list.size()>0)
			{
		   		for(RecommendedUserDTO dto : list)
				{
					dto.setNstStatus(getNstStatusString(dto)); 
					if ("个人".equals(dto.getUserType())) {
						dto.setCompanyName("");
					}
				}	
			}
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.debug("Exception is :"+e);
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value="checkExportParams", produces="application/json;charset=utf-8")
	@ResponseBody
	public String checkExportPrams(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)){
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		String queryCallStartDate = request.getParameter("queryCallStartDate");
		String queryCallEndDate = request.getParameter("queryCallEndDate");
		if(StringUtils.isNotBlank(queryCallStartDate) || StringUtils.isNotBlank(queryCallEndDate)){
			if (DateUtil.isDateIntervalOverFlow(queryCallStartDate, queryCallEndDate, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		if(StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate) && StringUtils.isBlank(queryCallStartDate) && StringUtils.isBlank(queryCallEndDate)){
			resultMap.put("status", 0);
			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
			return JSONObject.toJSONString(resultMap);
		}
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
	        //推荐人 
			map.put("mobile", request.getParameter("mobile"));
			//被推荐人 
			map.put("recommendedMobile", request.getParameter("recommendedMobile"));
			map.put("areaName", request.getParameter("areaName"));
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("callStartDate", queryCallStartDate);
			map.put("callEndDate", queryCallEndDate);
			int total = carsManageService.getTotalByCallTime(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("物流管理-会员拨打电话统计导出参数检测异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	

	
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, @RequestParam String mobile , @RequestParam String recommendedMobile , @RequestParam String areaName ,
			@RequestParam String startDate,@RequestParam String endDate ,@RequestParam String queryCallStartDate,@RequestParam String queryCallEndDate,@RequestParam String TCType) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
        //推荐人 
		map.put("mobile",mobile);
		//被推荐人 
		map.put("recommendedMobile",recommendedMobile);
		map.put("areaName",areaName);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("callStartDate", queryCallStartDate);
		map.put("callEndDate", queryCallEndDate);
		map.put("TCType", TCType);
		
		map.put("startRow", 0);
		map.put("endRow", 100000000);
	
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("会员拨打电话统计表".getBytes(), "ISO-8859-1");
			if(StringUtils.isNotBlank(queryCallStartDate) && StringUtils.isNotBlank(queryCallEndDate)){
				fileName = new String("会员拨打电话统计表".getBytes(), "ISO-8859-1")+queryCallStartDate+"_"+queryCallEndDate;
			}
			else if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
				fileName = new String("会员拨打电话统计表".getBytes(), "ISO-8859-1")+startDate+"_"+endDate;
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据list
			List<RecommendedUserDTO> list = carsManageService.getRecommendedUserListByCallTime(map);

			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("被推荐人统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "推荐人手机号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "推荐人姓名");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "被推荐人手机号");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "被推荐人姓名");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "用户类型");// 填充第一行第四个单元格的内容
				Label label50 = new Label(5, 0, "公司名称");// 
				Label label60 = new Label(6, 0, "联系人");// 
				Label label70 = new Label(7, 0, "拨号类型");//
				Label label80= new Label(8, 0, "被推荐人 注册时间");//
				Label label90 = new Label(9, 0, "被推荐人拨号时间");//
				Label label100 = new Label(10, 0, "被推荐人认证状态");//
			    Label label101 = new Label(11, 0, "所属区域");//
			    Label label102 = new Label(12, 0, "用户角色");//


				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(label90);
				sheet.addCell(label100);
				sheet.addCell(label101);
				sheet.addCell(label102);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						
						RecommendedUserDTO dto = list.get(i);
						String source = dto.getSource();
						if("TCWYZH".equals(source) || "TCWYZC".equals(source) || "TCNSTORDER".equals(source)){
							source = "同城";
						}else{
							source = "干线";
						}
						Label label0 = new Label(0, i + 1, dto.getMobile());
						Label label1 = new Label(1, i + 1, dto.getUserName());
						Label label2 = new Label(2, i + 1, dto.getRecommendedMobile());
						Label label3 = new Label(3, i + 1, dto.getRecommendedUserName());
						Label label4 = new Label(4, i + 1, dto.getUserType());
						Label label5 = new Label(5, i + 1, "个人".equals(dto.getUserType())? "":dto.getCompanyName());
						Label label6 = new Label(6, i + 1, dto.getLinkMan());
						Label label7 = new Label(7, i + 1, source);
						Label label8 = new Label(8, i + 1, DateUtil.toString(dto.getCreateUserTime(),DateUtil.DATE_FORMAT_DATETIME));
						Label label9 = new Label(9, i + 1, DateUtil.toString(dto.getCreateCallTime(),DateUtil.DATE_FORMAT_DATETIME));
						Label label1001 = new Label(10, i + 1, getNstStatusString(dto));
						Label label1002= new Label(11, i + 1, dto.getAreaName());
						Label label1003= new Label(12, i + 1, dto.getLevelType());
						
						sheet.addCell(label0);//将单元格加入表格
						sheet.addCell(label1);// 
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(label1001);
						sheet.addCell(label1002);
						sheet.addCell(label1003);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e1) {
			logger.debug("Exception is :"+e1);
			e1.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	private String getNstStatusString(RecommendedUserDTO dto) {
		String status=null;
		if(dto.getNstStatus()==null || "".equals(dto.getNstStatus())){
			status ="未提交认证";
		}else if(dto.getNstStatus().equals("1")){
			status ="已认证";
		}else if(dto.getNstStatus().equals("2")){
			status ="认证不通过";
		}else if(dto.getNstStatus().equals("0")){
			status ="已提交待认证";
		}
		return status;
	}

	
	
	
	
}
