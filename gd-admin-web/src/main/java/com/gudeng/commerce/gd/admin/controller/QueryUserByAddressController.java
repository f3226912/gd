package com.gudeng.commerce.gd.admin.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AreaSettingToolService;
import com.gudeng.commerce.gd.admin.service.MemberAddressManageService;
import com.gudeng.commerce.gd.admin.service.QueryAreaService;
import com.gudeng.commerce.gd.admin.util.AreaUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.JsonConvertUtil;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.dto.RecommendedUserDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("queryUserByAddress")
public class QueryUserByAddressController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(QueryUserByAddressController.class);
	
	@Autowired
	public MemberAddressManageService memberAddressManageService;
	
	@Autowired
	public AreaSettingToolService areaSettingToolService;

	
	@Autowired
	public QueryAreaService queryAreaService;
	
	/**
	 * 列表页面
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		/*	String mobile =this.getUser(request).getMobile();
			map.put("mobile", mobile);*/
			List<AreaSettingDTO> list = areaSettingToolService.getAllAreaName(map);
			request.setAttribute("areaNameList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryUserByAddress/userList";
	}
	
	
	
	/**
	 * 同城列表页面
	 * @return
	 */
	@RequestMapping("indexSameCity")
	public String indexSameCity(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		/*	String mobile =this.getUser(request).getMobile();
			map.put("mobile", mobile);*/
			List<AreaSettingDTO> list = areaSettingToolService.getAllAreaName(map);
			request.setAttribute("areaNameList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryUserByAddress/userListSameCity";
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
			String  queryAddressStartDate =request.getParameter("queryAddressStartDate");
			String  queryAddressEndDate =request.getParameter("queryAddressEndDate");

			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("mobile",request.getParameter("mobile"));
			map.put("recommendedMobile",request.getParameter("recommendedMobile"));
			map.put("areaName",request.getParameter("areaName"));
			map.put("startDate", request.getParameter("startDate"));
			map.put("endDate", request.getParameter("endDate"));
			map.put("addressStartDate", queryAddressStartDate  );
			map.put("addressEndDate", queryAddressEndDate );
			
			map.put("s_provinceId" ,request.getParameter("start_provinceId"));
			map.put("s_cityId" ,request.getParameter("start_cityId"));
			map.put("s_areaId" ,request.getParameter("start_areaId"));
			map.put("f_provinceId" ,request.getParameter("end_provinceId"));
			map.put("f_cityId" ,request.getParameter("end_cityId"));
			map.put("f_areaId" , request.getParameter("end_areaId"));
			map.put("isReferees" , request.getParameter("isReferees"));
			//记录数
			map.put("total", memberAddressManageService.getTotalByAddress(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<RecommendedUserDTO> list = memberAddressManageService.getListByAddress(map);
			if(list !=null && list.size()>0)
			{
				for(RecommendedUserDTO dto : list)
				{
					dto.setNstStatus(getNstStatusString(dto)); 
					dto.setIsAddressDeleted(("1".equals(dto.getIsAddressDeleted())?"已删除":"未删除"));
					if ("个人".equals(dto.getUserType())) {
						dto.setCompanyName("");
					}
					setAddress(dto);
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
	
	/**
	 * 默认查询和id条件查询结合(同城)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("querySameCity")
	@ResponseBody
	public String querySameCity(HttpServletRequest request){
		try {
			String  queryAddressStartDate =request.getParameter("queryAddressStartDate");
			String  queryAddressEndDate =request.getParameter("queryAddressEndDate");

			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("mobile",request.getParameter("mobile"));
			map.put("recommendedMobile",request.getParameter("recommendedMobile"));
			map.put("areaName",request.getParameter("areaName"));
			map.put("startDate", request.getParameter("startDate"));
			map.put("endDate", request.getParameter("endDate"));
			map.put("addressStartDate", queryAddressStartDate  );
			map.put("addressEndDate", queryAddressEndDate );
			
			map.put("s_provinceId" ,request.getParameter("start_provinceId"));
			map.put("s_cityId" ,request.getParameter("start_cityId"));
			map.put("s_areaId" ,request.getParameter("start_areaId"));
			map.put("f_provinceId" ,request.getParameter("end_provinceId"));
			map.put("f_cityId" ,request.getParameter("end_cityId"));
			map.put("f_areaId" , request.getParameter("end_areaId"));
			map.put("isReferees" , request.getParameter("isReferees"));
			//记录数
			map.put("total", memberAddressManageService.getTotalByAddressSameCity(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<RecommendedUserDTO> list = memberAddressManageService.getListByAddressSameCity(map);
			if(list !=null && list.size()>0)
			{
				for(RecommendedUserDTO dto : list)
				{
					dto.setNstStatus(getNstStatusString(dto)); 
					dto.setIsAddressDeleted(("1".equals(dto.getIsAddressDeleted())?"已删除":"未删除"));
					if ("个人".equals(dto.getUserType())) {
						dto.setCompanyName("");
					}
					setAddress(dto);
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

	@RequestMapping(value="checkExportParams/{type}", produces="application/json;charset=utf-8")
	@ResponseBody
	public String checkExportParams(HttpServletRequest request, @PathVariable int type){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int total = 0;
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)){
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		String queryAddressStartDate = request.getParameter("queryAddressStartDate");
		String queryAddressEndDate = request.getParameter("queryAddressEndDate");
		if(StringUtils.isNotBlank(queryAddressStartDate) || StringUtils.isNotBlank(queryAddressEndDate)){
			if (DateUtil.isDateIntervalOverFlow(queryAddressStartDate, queryAddressEndDate, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		if(StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate) && StringUtils.isBlank(queryAddressStartDate) && StringUtils.isBlank(queryAddressEndDate)){
			resultMap.put("status", 0);
			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
			return JSONObject.toJSONString(resultMap);
		}
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
	        //推荐人 
			map.put("mobile",request.getParameter("mobile"));
			//被推荐人 
			map.put("recommendedMobile", request.getParameter("recommendedMobile"));
			map.put("areaName", request.getParameter("areaName"));
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("addressStartDate", queryAddressStartDate);
			map.put("addressEndDate", queryAddressEndDate);
			map.put("s_provinceId" ,request.getParameter("start_provinceId"));
			map.put("s_cityId" ,request.getParameter("start_cityId"));
			map.put("s_areaId" ,request.getParameter("start_areaId"));
			map.put("f_provinceId" ,request.getParameter("end_provinceId"));
			map.put("f_cityId" ,request.getParameter("end_cityId"));
			map.put("f_areaId" , request.getParameter("end_areaId"));
			if(type==1){
				total = memberAddressManageService.getTotalByAddressSameCity(map);
			}else if(type==2){
				total = memberAddressManageService.getTotalByAddress(map);
			}
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("物流管理-会员发布货源统计导出参数检测异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	

	
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, @RequestParam String mobile , @RequestParam String recommendedMobile , @RequestParam String areaName ,
			@RequestParam String startDate,@RequestParam String endDate ,@RequestParam String queryAddressStartDate,@RequestParam String queryAddressEndDate ,
			@RequestParam String start_provinceId, @RequestParam String start_cityId, @RequestParam String start_areaId,
			@RequestParam String end_provinceId, @RequestParam String end_cityId,@RequestParam String end_areaId,@RequestParam String isReferees
			) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
        //推荐人 
		map.put("mobile",mobile);
		//被推荐人 
		map.put("recommendedMobile",recommendedMobile);
		map.put("areaName",areaName);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("addressStartDate", queryAddressStartDate  );
		map.put("addressEndDate", queryAddressEndDate );
		map.put("s_provinceId" ,request.getParameter("start_provinceId"));
		map.put("s_cityId" ,request.getParameter("start_cityId"));
		map.put("s_areaId" ,request.getParameter("start_areaId"));
		map.put("f_provinceId" ,request.getParameter("end_provinceId"));
		map.put("f_cityId" ,request.getParameter("end_cityId"));
		map.put("f_areaId" , request.getParameter("end_areaId"));
		map.put("isReferees" , request.getParameter("isReferees"));
		map.put("startRow", 0);
		map.put("endRow", 100000000);
	
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			
			String fileName = new String("会员发布货源统计表".getBytes(), "ISO-8859-1");
			if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
				fileName = new String("会员发布货源统计表".getBytes(), "ISO-8859-1")+startDate+"_"+endDate;
			}
			else if(StringUtils.isNotBlank(queryAddressStartDate) && StringUtils.isNotBlank(queryAddressEndDate)){
				fileName = new String("会员发布货源统计表".getBytes(), "ISO-8859-1")+queryAddressStartDate+"_"+queryAddressEndDate;
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据list
			List<RecommendedUserDTO> list = memberAddressManageService.getListByAddress(map);

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
				Label label70 = new Label(7, 0, "被推荐人注册时间");//
				Label label80 = new Label(8, 0, "货源发布时间");//
				Label label90 = new Label(9, 0, "货源状态");//
				Label label100 = new Label(10, 0, "认证状态");//
			    Label label110 = new Label(11, 0, "所属区域");// 
			    Label label111 = new Label(12, 0, "被推荐人角色 ");//
			    Label label112 = new Label(13, 0, "始发地");// 
			    Label label113= new Label(14, 0, "目的地");//
			    Label label114= new Label(15, 0, "是否被推荐");//

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
				sheet.addCell(label110);
				sheet.addCell(label111);
				sheet.addCell(label112);
				sheet.addCell(label113);
				sheet.addCell(label114);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						
						RecommendedUserDTO dto = list.get(i);
						setAddress(dto);
						Label label0 = new Label(0, i + 1, dto.getMobile());
						Label label1 = new Label(1, i + 1, dto.getUserName());
						Label label2 = new Label(2, i + 1, dto.getRecommendedMobile());
						Label label3 = new Label(3, i + 1, dto.getRecommendedUserName());
						Label label4 = new Label(4, i + 1, dto.getUserType());
						Label label5 = new Label(5, i + 1, "个人".equals(dto.getUserType())? "":dto.getCompanyName());
						Label label6 = new Label(6, i + 1, dto.getLinkMan());
						Label label7 = new Label(7, i + 1, DateUtil.toString(dto.getCreateUserTime(),DateUtil.DATE_FORMAT_DATETIME));
						Label label8 = new Label(8, i + 1, DateUtil.toString(dto.getCreateAddressTime(),DateUtil.DATE_FORMAT_DATETIME));
						Label label9 = new Label(9, i + 1, "1".equals(dto.getIsAddressDeleted())?"已删除":"未删除");
						Label label101 = new Label(10, i + 1, getNstStatusString(dto));
						Label label102 = new Label(11, i + 1, dto.getAreaName());
						Label label103 = new Label(12, i + 1, dto.getLevelType());
						Label label104 = new Label(13, i + 1, dto.getStartPlace());
						Label label105 = new Label(14, i + 1, dto.getEndPlace());
						Label label106 = new Label(15, i + 1, (dto.getIsReferees()!=null)?"是":"否");

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
						sheet.addCell(label101);
						sheet.addCell(label102);
						sheet.addCell(label103);
						sheet.addCell(label104);
						sheet.addCell(label105);
						sheet.addCell(label106);
						
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
	
	@RequestMapping(value = "exportDataSameCity")
	public String exportDataSameCity(HttpServletRequest request, HttpServletResponse response, @RequestParam String mobile , @RequestParam String recommendedMobile , @RequestParam String areaName ,
			@RequestParam String startDate,@RequestParam String endDate ,@RequestParam String queryAddressStartDate,@RequestParam String queryAddressEndDate ,
			@RequestParam String start_provinceId, @RequestParam String start_cityId, @RequestParam String start_areaId,
			@RequestParam String end_provinceId, @RequestParam String end_cityId,@RequestParam String end_areaId
			) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
        //推荐人 
		map.put("mobile",mobile);
		//被推荐人 
		map.put("recommendedMobile",recommendedMobile);
		map.put("areaName",areaName);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("addressStartDate", queryAddressStartDate  );
		map.put("addressEndDate", queryAddressEndDate );
		map.put("s_provinceId" ,request.getParameter("start_provinceId"));
		map.put("s_cityId" ,request.getParameter("start_cityId"));
		map.put("s_areaId" ,request.getParameter("start_areaId"));
		map.put("f_provinceId" ,request.getParameter("end_provinceId"));
		map.put("f_cityId" ,request.getParameter("end_cityId"));
		map.put("f_areaId" , request.getParameter("end_areaId"));
		map.put("isReferees" , request.getParameter("isReferees"));
		map.put("startRow", 0);
		map.put("endRow", 100000000);
	
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			
			String fileName = new String("会员发布货源统计表".getBytes(), "ISO-8859-1");
			if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
				fileName = new String("会员发布货源统计表".getBytes(), "ISO-8859-1")+startDate+"_"+endDate;
			}
			else if(StringUtils.isNotBlank(queryAddressStartDate) && StringUtils.isNotBlank(queryAddressEndDate)){
				fileName = new String("会员发布货源统计表".getBytes(), "ISO-8859-1")+queryAddressStartDate+"_"+queryAddressEndDate;
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据list
			List<RecommendedUserDTO> list = memberAddressManageService.getListByAddressSameCity(map);

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
				Label label70 = new Label(7, 0, "被推荐人注册时间");//
				Label label80 = new Label(8, 0, "货源发布时间");//
				Label label90 = new Label(9, 0, "货源状态");//
				Label label100 = new Label(10, 0, "认证状态");//
			    Label label110 = new Label(11, 0, "所属区域");// 
			    Label label111 = new Label(12, 0, "被推荐人角色 ");//
			    Label label112 = new Label(13, 0, "始发地");// 
			    Label label113= new Label(14, 0, "目的地");//
			    Label label114= new Label(15, 0, "是否被推荐");//

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
				sheet.addCell(label110);
				sheet.addCell(label111);
				sheet.addCell(label112);
				sheet.addCell(label113);
				sheet.addCell(label114);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						
						RecommendedUserDTO dto = list.get(i);
						setAddress(dto);
						Label label0 = new Label(0, i + 1, dto.getMobile());
						Label label1 = new Label(1, i + 1, dto.getUserName());
						Label label2 = new Label(2, i + 1, dto.getRecommendedMobile());
						Label label3 = new Label(3, i + 1, dto.getRecommendedUserName());
						Label label4 = new Label(4, i + 1, dto.getUserType());
						Label label5 = new Label(5, i + 1, "个人".equals(dto.getUserType())? "":dto.getCompanyName());
						Label label6 = new Label(6, i + 1, dto.getLinkMan());
						Label label7 = new Label(7, i + 1, DateUtil.toString(dto.getCreateUserTime(),DateUtil.DATE_FORMAT_DATETIME));
						Label label8 = new Label(8, i + 1, DateUtil.toString(dto.getCreateAddressTime(),DateUtil.DATE_FORMAT_DATETIME));
						Label label9 = new Label(9, i + 1, "1".equals(dto.getIsAddressDeleted())?"已删除":"未删除");
						Label label101 = new Label(10, i + 1, getNstStatusString(dto));
						Label label102 = new Label(11, i + 1, dto.getAreaName());
						Label label103 = new Label(12, i + 1, dto.getLevelType());
						Label label104 = new Label(13, i + 1, dto.getStartPlace());
						Label label105 = new Label(14, i + 1, dto.getEndPlace());
						Label label106 = new Label(15, i + 1, (dto.getIsReferees()!=null)?"是":"否");

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
						sheet.addCell(label101);
						sheet.addCell(label102);
						sheet.addCell(label103);
						sheet.addCell(label104);
						sheet.addCell(label105);
						sheet.addCell(label106);
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
	
	
	
	
	// 查询全国行政区代码省
		@RequestMapping(value = "queryProvince")
		@ResponseBody

		public String queryProvince(HttpServletRequest request, HttpServletResponse response) throws Exception {

			String type =request.getParameter("type");
			List<AreaDTO> list = queryAreaService.findProvince();
			if("start".equals(type)){
			 this.jsonUtil(list, response);	
			}else{
			 List<AreaDTO>  newList= new ArrayList<AreaDTO>(); 
			 AreaDTO area =new AreaDTO();
			 area.setAreaID("0");
			 area.setArea("全国");
			 newList.add(area);
			 newList.addAll(list);
			this.jsonUtil(newList, response);
			}
			return null;
		}

		// 查询全国行政区代码市
		@RequestMapping(value = "queryCity")
		@ResponseBody
		public String queryCity(HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<?> list = queryAreaService.findCity(request.getParameter("province"));
			this.jsonUtil(list, response);
			return null;
		}

		// 查询全国行政区代码县区
		@RequestMapping(value = "queryCounty")
		@ResponseBody
		public String queryCounty(HttpServletRequest request, HttpServletResponse response) throws Exception {

			String city = request.getParameter("city");
			List<?> list = queryAreaService.findCounty(city);
			this.jsonUtil(list, response);
			return null;
		}
		
		private void jsonUtil(Object list, HttpServletResponse response) throws Exception {
			logger.info("JSON格式：" + list.toString());
			String returnJson = JsonConvertUtil.returnJson(list);
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(returnJson);
		}

		
		private void setAddress(RecommendedUserDTO dto) throws Exception {
			StringBuilder startPlace = new StringBuilder();
			if(dto.getS_provinceId() != null && dto.getS_provinceId() >1){
			AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId()));
			startPlace.append(province != null ? province.getArea() : "");
			AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId()));
			//如果二级目录名称为市辖区，县，省直辖行政单位，不展示
			startPlace.append(AreaUtil.isCity(city)?" " +city.getArea() : "");
			AreaDTO area = queryAreaService.getArea(String.valueOf(dto.getS_areaId()));
			startPlace.append(area != null ? " " + area.getArea() : "");
			}else{
				startPlace.append("全国");
			}

			StringBuilder endPlace = new StringBuilder();
			if(dto.getF_provinceId() != null && dto.getF_provinceId() >1){
			AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getF_provinceId()));
			endPlace.append(e_province != null ? e_province.getArea() : "");
			AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getF_cityId()));
			endPlace.append(AreaUtil.isCity(e_city)?" " +e_city.getArea() : "");
			AreaDTO e_area = queryAreaService.getArea(String.valueOf(dto.getF_areaId()));
			endPlace.append(e_area != null ? " " + e_area.getArea() : "");
			}else{
				endPlace.append("全国");
			}
			dto.setStartPlace(startPlace.toString());
			dto.setEndPlace(endPlace.toString());
		}
	
}
