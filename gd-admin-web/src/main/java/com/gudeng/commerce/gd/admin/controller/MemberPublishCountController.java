package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AreaSettingToolService;
import com.gudeng.commerce.gd.admin.service.AreaToolService;
import com.gudeng.commerce.gd.admin.service.CarLineManageService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.ELevelTyle;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.dto.PublishCountDTO;
import com.gudeng.commerce.gd.customer.entity.Area;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 会员统计信息列表Controller
 * 
 * @author xiaojun
 */
@Controller
@RequestMapping("count")
public class MemberPublishCountController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(MemberPublishCountController.class);

	@Autowired
	private CarLineManageService carLineManageService;
	@Autowired
	private AreaSettingToolService areaSettingToolService;
	@Autowired
	private AreaToolService areaToolService;
	
	/**
	 * 跳转会员发布线路统计管理页面
	 * @param request
	 * @return
	 */
	@RequestMapping("publish")
	public String publish(HttpServletRequest request){
		request(request);
		return "count/memberPublishCarLineList";
	}
	
	/**
	 * 跳转会员发布线路统计管理页面(同城)
	 * @param request
	 * @return
	 */
	@RequestMapping("publishSameCity")
	public String publishSameCity(HttpServletRequest request){
		request(request);
		return "count/memberPublishCarLineListSameCity";
	}
	/**
	 * 跳转会员发布车辆统计管理页面
	 * @param request
	 * @return
	 */
	@RequestMapping("publishCar")
	public String publishCar(HttpServletRequest request){
		request(request);
		return "count/memberPublishCarList";
	}
	
	/**
	 * 跳转会员发布车辆统计管理页面(同城)
	 * @param request
	 * @return
	 */
	@RequestMapping("publishCarSameCity")
	public String publishCarSameCity(HttpServletRequest request){
		request(request);
		return "count/memberPublishCarListSameCity";
	}
	
	
	/**
	 * 获取区域名称和省份
	 * @param request
	 */
	private void request(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		List<AreaSettingDTO> list = null;
		List<AreaDTO> provinceList=null;
		try {
			list = areaSettingToolService.getAllAreaName(map);
			provinceList=areaToolService.findProvince();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("areaNameList", list);
		request.setAttribute("provinceList", provinceList);
	}
	/**
	 * 获取城市
	 * @param e_provinceId
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/getCity/{id}",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCity(@PathVariable("id") String e_provinceId,Map<String, Object> map){
		try {
			List<AreaDTO> cityList=areaToolService.findCity(e_provinceId);
			map.put("cityList", cityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(map);
	}
	/**
	 * 获取区域
	 * @param s_provinceId
	 * @param map
	 * @return
	 */
	@RequestMapping("/getArea/{id}")
	@ResponseBody
	public String getArea(@PathVariable("id") String e_cityId,Map<String, Object> map){
		try {
			List<AreaDTO> areaList=areaToolService.findCounty(e_cityId);
			map.put("areaList", areaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(map);
	}
	/**
	 * 会员发布线路信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("publishCarLineList")
	@ResponseBody
	public String publishCarLineList(HttpServletRequest request,PublishCountDTO publishCountDTO) {

		try {
			Map<String, Object> map = new HashMap<>();
			getMap(map,publishCountDTO);
			//设置总记录数
			map.put("total",carLineManageService.getMemberPublishCarLineCount(map));
			//设定分页,排序
			setCommParameters(request, map);
			List<PublishCountDTO> list = carLineManageService.memberPublishCarLine(map);
			matchAddress(list);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 会员发布线路信息列表(同城)
	 * @param request
	 * @return
	 */
	@RequestMapping("publishCarLineListSameCity")
	@ResponseBody
	public String publishCarLineListSameCity(HttpServletRequest request,PublishCountDTO publishCountDTO) {

		try {
			Map<String, Object> map = new HashMap<>();
			getMap(map,publishCountDTO);
			//设置总记录数
			map.put("total",carLineManageService.getMemberPublishCarLineCountSameCity(map));
			//设定分页,排序
			setCommParameters(request, map);
			List<PublishCountDTO> list = carLineManageService.memberPublishCarLineSameCity(map);
			matchAddress(list);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 拼装起始地和目的地
	 * @param list
	 */
	private void matchAddress(List<PublishCountDTO> list) throws Exception{
		StringBuffer sb=new StringBuffer();
		for (PublishCountDTO dto : list) {
			if ("0".equals(dto.getS_provinceId()) || "1".equals(dto.getS_provinceId())) {
				dto.setS_startAddress("全国");
				continue;
			}
			if(StringUtils.isNotEmpty(dto.getS_provinceId())){
				Area area=areaToolService.getArea(dto.getS_provinceId());
				sb.append(area.getArea());
			}
			if(StringUtils.isNotEmpty(dto.getS_cityId()) && !"0".equals(dto.getS_cityId()) && !"1".equals(dto.getS_cityId())){
				Area area=areaToolService.getArea(dto.getS_cityId());
				sb.append(area.getArea());
			}
			if(StringUtils.isNotEmpty(dto.getS_areaId()) && !"0".equals(dto.getS_areaId()) && !"1".equals(dto.getS_areaId())){
				Area area=areaToolService.getArea(dto.getS_areaId());
				sb.append(area.getArea());
			}
			dto.setS_startAddress(sb.toString());
			sb.setLength(0);
		}
		StringBuffer sb2=new StringBuffer();
		for (PublishCountDTO dto : list) {
			if ("0".equals(dto.getE_provinceId()) || "1".equals(dto.getE_provinceId())) {
				dto.setE_endAddress("全国");
				continue;
			}
			if(StringUtils.isNotEmpty(dto.getE_provinceId()) ){
				Area area=areaToolService.getArea(dto.getE_provinceId());
				sb2.append(area.getArea());
			}
			if(StringUtils.isNotEmpty(dto.getE_cityId()) && !"0".equals(dto.getE_cityId()) && !"1".equals(dto.getE_cityId())){
				Area area=areaToolService.getArea(dto.getE_cityId());
				sb2.append(area.getArea());
			}
			if(StringUtils.isNotEmpty(dto.getE_areaId()) && !"0".equals(dto.getE_areaId()) && !"1".equals(dto.getE_areaId())){
				Area area=areaToolService.getArea(dto.getE_areaId());
				sb2.append(area.getArea());
			}
			dto.setE_endAddress(sb2.toString());
			sb2.setLength(0);
		}
	}
	/**
	 * 会员发布车辆信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("publishCarList")
	@ResponseBody
	public String publishCarList(HttpServletRequest request,PublishCountDTO publishCountDTO) {

		try {
			Map<String, Object> map = new HashMap<>();
			getMap(map,publishCountDTO);
			//设置总记录数
			map.put("total",carLineManageService.getMemberPublishCarCount(map));
			//设定分页,排序
			setCommParameters(request, map);
			List<PublishCountDTO> list = carLineManageService.memberPublishCar(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 会员发布车辆信息列表(同城)
	 * @param request
	 * @return
	 */
	@RequestMapping("publishCarListSameCity")
	@ResponseBody
	public String publishCarListSameCity(HttpServletRequest request,PublishCountDTO publishCountDTO) {

		try {
			Map<String, Object> map = new HashMap<>();
			getMap(map,publishCountDTO);
			//设置总记录数
			map.put("total",carLineManageService.getMemberPublishCarCountSameCity(map));
			//设定分页,排序
			setCommParameters(request, map);
			List<PublishCountDTO> list = carLineManageService.memberPublishCarSameCity(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="checkExportParams", produces="application/json;charset=utf-8")
	@ResponseBody
	public String checkExportParams(HttpServletRequest request, PublishCountDTO publishCountDTO){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String memberCreateBeginTime = request.getParameter("memberCreateBeginTime");
		String memberCreateEndTime = request.getParameter("memberCreateEndTime");
		if(StringUtils.isNotBlank(memberCreateBeginTime) || StringUtils.isNotBlank(memberCreateEndTime)){
			if (DateUtil.isDateIntervalOverFlow(memberCreateBeginTime, memberCreateEndTime, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		String lineCreateBeginTime = request.getParameter("lineCreateBeginTime");
		String lineCreateEndTime = request.getParameter("lineCreateEndTime");
		if(StringUtils.isNotBlank(lineCreateBeginTime) || StringUtils.isNotBlank(lineCreateEndTime)){
			if (DateUtil.isDateIntervalOverFlow(lineCreateBeginTime, lineCreateEndTime, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		if(StringUtils.isBlank(memberCreateBeginTime) && StringUtils.isBlank(memberCreateEndTime) && StringUtils.isBlank(lineCreateBeginTime) && StringUtils.isBlank(lineCreateEndTime)){
			resultMap.put("status", 0);
			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
			return JSONObject.toJSONString(resultMap);
		}
		
		try{
			Map<String, Object> map = new HashMap<>();
			getMap(map,publishCountDTO);
			int total = carLineManageService.getMemberPublishCarLineCount(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("物流管理-会员发布线路统计导出参数检测异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request,HttpServletResponse response,PublishCountDTO publishCountDTO){
		Map<String, Object> map = new HashMap<>();
		getMap(map,publishCountDTO);
		map.put("startRow", 0);
		map.put("endRow", 99999999);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("会员发布线路统计表".getBytes(), "ISO-8859-1");
			String lineCreateBeginTime = request.getParameter("lineCreateBeginTime");
			String lineCreateEndTime = request.getParameter("lineCreateEndTime");
			String memberCreateBeginTime = request.getParameter("memberCreateBeginTime");
			String memberCreateEndTime = request.getParameter("memberCreateEndTime");
			if(StringUtils.isNotBlank(lineCreateBeginTime) && StringUtils.isNotBlank(lineCreateEndTime)){
				fileName = new String("会员发布线路统计表".getBytes(), "ISO-8859-1")+lineCreateBeginTime+"_"+lineCreateEndTime;
			}
			else if(StringUtils.isNotBlank(memberCreateBeginTime) && StringUtils.isNotBlank(memberCreateEndTime)){
				fileName = new String("会员发布线路统计表".getBytes(), "ISO-8859-1")+memberCreateBeginTime+"_"+memberCreateEndTime;
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			//查询数据
			List<PublishCountDTO> list = carLineManageService.memberPublishCarLine(map);
			matchAddress(list);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("会员发布线路统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "推荐人手机");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "推荐人姓名");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "被推荐人手机");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "被推荐人姓名");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "被推荐人角色");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "用户类型 ");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "区域名称 ");// 填充第一行第八个单元格的内容
				Label label70 = new Label(7, 0, "公司名称");// 填充第一行第九个单元格的内容
				Label label80 = new Label(8, 0, "发布线路信息时间 ");// 填充第一行第十个单元格的内容
				Label labe190 = new Label(9, 0, "起始地 ");// 
				Label labe200 = new Label(10, 0, "目的地 ");// 
				Label labe210 = new Label(11, 0, "认证状态 ");// 填充第一行第十个单元格的内容
				Label labe220 = new Label(12, 0, "被推荐人注册时间 ");// 填充第一行第十个单元格的内容
				Label labe230 = new Label(13, 0, "信息是否删除 ");// 填充第一行第十个单元格的内容
				sheet.addCell(label00);
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(labe190);
				sheet.addCell(labe200);
				sheet.addCell(labe210);
				sheet.addCell(labe220);
				sheet.addCell(labe230);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						PublishCountDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getMobile());
						Label label1 = new Label(1, i + 1, dto.getName());
						Label label2 = new Label(2, i + 1, dto.getMobile_ed());
						Label label3 = new Label(3, i + 1, dto.getName_ed());
						Label label4 = new Label(4, i + 1, ELevelTyle.getValueByCode(dto.getLevel()));
						Label label5 = new Label(5, i + 1, dto.getUserType());
						Label label6 = new Label(6, i + 1, dto.getAreaName());
						Label label7 = new Label(7, i + 1, dto.getCompanyName());
						Label label8 = new Label(8, i + 1, dto.getLineCreateTime());
						Label label9 = new Label(9, i + 1, dto.getS_startAddress());
						Label labe20 = new Label(10, i + 1, dto.getE_endAddress());
						Label labe21 = new Label(11, i + 1, dto.getNstStatus());
						Label labe22 = new Label(12, i + 1,dto.getMemberCreateTime());
						Label labe23 = new Label(13, i + 1,dto.getIsDeleted());
						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(labe20);
						sheet.addCell(labe21);
						sheet.addCell(labe22);
						sheet.addCell(labe23);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
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
	public String exportDataSameCity(HttpServletRequest request,HttpServletResponse response,PublishCountDTO publishCountDTO){
		Map<String, Object> map = new HashMap<>();
		getMap(map,publishCountDTO);
		map.put("startRow", 0);
		map.put("endRow", 99999999);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("会员发布线路统计表".getBytes(), "ISO-8859-1");
			String lineCreateBeginTime = request.getParameter("lineCreateBeginTime");
			String lineCreateEndTime = request.getParameter("lineCreateEndTime");
			String memberCreateBeginTime = request.getParameter("memberCreateBeginTime");
			String memberCreateEndTime = request.getParameter("memberCreateEndTime");
			if(StringUtils.isNotBlank(lineCreateBeginTime) && StringUtils.isNotBlank(lineCreateEndTime)){
				fileName = new String("会员发布线路统计表".getBytes(), "ISO-8859-1")+lineCreateBeginTime+"_"+lineCreateEndTime;
			}
			else if(StringUtils.isNotBlank(memberCreateBeginTime) && StringUtils.isNotBlank(memberCreateEndTime)){
				fileName = new String("会员发布线路统计表".getBytes(), "ISO-8859-1")+memberCreateBeginTime+"_"+memberCreateEndTime;
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			//查询数据
			List<PublishCountDTO> list = carLineManageService.memberPublishCarLineSameCity(map);
			matchAddress(list);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("会员发布线路统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "推荐人手机");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "推荐人姓名");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "被推荐人手机");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "被推荐人姓名");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "被推荐人角色");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "用户类型 ");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "区域名称 ");// 填充第一行第八个单元格的内容
				Label label70 = new Label(7, 0, "公司名称");// 填充第一行第九个单元格的内容
				Label label80 = new Label(8, 0, "发布线路信息时间 ");// 填充第一行第十个单元格的内容
				Label labe190 = new Label(9, 0, "起始地 ");// 
				Label labe200 = new Label(10, 0, "目的地 ");// 
				Label labe210 = new Label(11, 0, "认证状态 ");// 填充第一行第十个单元格的内容
				Label labe220 = new Label(12, 0, "被推荐人注册时间 ");// 填充第一行第十个单元格的内容
				Label labe230 = new Label(13, 0, "信息是否删除 ");// 填充第一行第十个单元格的内容
				sheet.addCell(label00);
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(labe190);
				sheet.addCell(labe200);
				sheet.addCell(labe210);
				sheet.addCell(labe220);
				sheet.addCell(labe230);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						PublishCountDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getMobile());
						Label label1 = new Label(1, i + 1, dto.getName());
						Label label2 = new Label(2, i + 1, dto.getMobile_ed());
						Label label3 = new Label(3, i + 1, dto.getName_ed());
						Label label4 = new Label(4, i + 1, ELevelTyle.getValueByCode(dto.getLevel()));
						Label label5 = new Label(5, i + 1, dto.getUserType());
						Label label6 = new Label(6, i + 1, dto.getAreaName());
						Label label7 = new Label(7, i + 1, dto.getCompanyName());
						Label label8 = new Label(8, i + 1, dto.getLineCreateTime());
						Label label9 = new Label(9, i + 1, dto.getS_startAddress());
						Label labe20 = new Label(10, i + 1, dto.getE_endAddress());
						Label labe21 = new Label(11, i + 1, dto.getNstStatus());
						Label labe22 = new Label(12, i + 1,dto.getMemberCreateTime());
						Label labe23 = new Label(13, i + 1,dto.getIsDeleted());
						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(labe20);
						sheet.addCell(labe21);
						sheet.addCell(labe22);
						sheet.addCell(labe23);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@RequestMapping(value="checkExportCarParams", produces="application/json;charset=utf-8")
	@ResponseBody
	public String checkExportCarParams(HttpServletRequest request, PublishCountDTO publishCountDTO){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String memberCreateBeginTime = request.getParameter("memberCreateBeginTime");
		String memberCreateEndTime = request.getParameter("memberCreateEndTime");
		if(StringUtils.isNotBlank(memberCreateBeginTime) || StringUtils.isNotBlank(memberCreateEndTime)){
			if (DateUtil.isDateIntervalOverFlow(memberCreateBeginTime, memberCreateEndTime, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		String carCreateBeginTime = request.getParameter("carCreateBeginTime");
		String carCreateEndTime = request.getParameter("carCreateEndTime");
		if(StringUtils.isNotBlank(carCreateBeginTime) || StringUtils.isNotBlank(carCreateEndTime)){
			if (DateUtil.isDateIntervalOverFlow(carCreateBeginTime, carCreateEndTime, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		if(StringUtils.isBlank(memberCreateBeginTime) && StringUtils.isBlank(memberCreateEndTime) && StringUtils.isBlank(carCreateBeginTime) && StringUtils.isBlank(carCreateEndTime)){
			resultMap.put("status", 0);
			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
			return JSONObject.toJSONString(resultMap);
		}
		try{
			Map<String, Object> map = new HashMap<>();
			getMap(map,publishCountDTO);
			int total = carLineManageService.getMemberPublishCarCount(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("物流管理-会员发布车辆统计导出参数检测异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "exportCarData")
	public String exportCarData(HttpServletRequest request,HttpServletResponse response,PublishCountDTO publishCountDTO){
		Map<String, Object> map = new HashMap<>();
		getMap(map,publishCountDTO);
		map.put("startRow", 0);
		map.put("endRow", 99999999);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("会员发布车辆统计表".getBytes(), "ISO-8859-1");
			String carCreateBeginTime = request.getParameter("carCreateBeginTime");
			String carCreateEndTime = request.getParameter("carCreateEndTime");
			String memberCreateBeginTime = request.getParameter("memberCreateBeginTime");
			String memberCreateEndTime = request.getParameter("memberCreateEndTime");
			if(StringUtils.isNotBlank(carCreateBeginTime) && StringUtils.isNotBlank(carCreateEndTime)){
				fileName = new String("会员发布车辆统计表".getBytes(), "ISO-8859-1")+carCreateBeginTime+"_"+carCreateEndTime;
			}
			else if(StringUtils.isNotBlank(memberCreateBeginTime) && StringUtils.isNotBlank(memberCreateEndTime)){
				fileName = new String("会员发布车辆统计表".getBytes(), "ISO-8859-1")+memberCreateBeginTime+"_"+memberCreateEndTime;
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			//查询数据
			List<PublishCountDTO> list = carLineManageService.memberPublishCar(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("会员发布车辆统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "推荐人手机");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "推荐人姓名");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "被推荐人手机");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "被推荐人姓名");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "被推荐人角色");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "用户类型 ");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "区域名称 ");// 填充第一行第八个单元格的内容
				Label label70 = new Label(7, 0, "公司名称");// 填充第一行第九个单元格的内容
				Label label80 = new Label(8, 0, "发布车辆信息时间 ");// 填充第一行第十个单元格的内容
				Label label90 = new Label(9, 0, "认证状态 ");// 填充第一行第十个单元格的内容
				Label labe200 = new Label(10, 0, "被推荐人注册时间 ");// 填充第一行第十个单元格的内容
				sheet.addCell(label00);
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(label90);
				sheet.addCell(labe200);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						PublishCountDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getMobile());
						Label label1 = new Label(1, i + 1, dto.getName());
						Label label2 = new Label(2, i + 1, dto.getMobile_ed());
						Label label3 = new Label(3, i + 1, dto.getName_ed());
						Label label4 = new Label(4, i + 1, ELevelTyle.getValueByCode(dto.getLevel()));
						Label label5 = new Label(5, i + 1, dto.getUserType());
						Label label6 = new Label(6, i + 1, dto.getAreaName());
						Label label7 = new Label(7, i + 1, dto.getCompanyName());
						Label label8 = new Label(8, i + 1, dto.getCarCreateTime());
						Label label9 = new Label(9, i + 1, dto.getNstStatus());
						Label labe20 = new Label(10, i + 1,dto.getMemberCreateTime());
						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(labe20);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@RequestMapping(value = "exportCarDataSameCity")
	public String exportCarDataSameCity(HttpServletRequest request,HttpServletResponse response,PublishCountDTO publishCountDTO){
		Map<String, Object> map = new HashMap<>();
		getMap(map,publishCountDTO);
		map.put("startRow", 0);
		map.put("endRow", 99999999);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("会员发布车辆统计表".getBytes(), "ISO-8859-1");
			String carCreateBeginTime = request.getParameter("carCreateBeginTime");
			String carCreateEndTime = request.getParameter("carCreateEndTime");
			String memberCreateBeginTime = request.getParameter("memberCreateBeginTime");
			String memberCreateEndTime = request.getParameter("memberCreateEndTime");
			if(StringUtils.isNotBlank(carCreateBeginTime) && StringUtils.isNotBlank(carCreateEndTime)){
				fileName = new String("会员发布车辆统计表".getBytes(), "ISO-8859-1")+carCreateBeginTime+"_"+carCreateEndTime;
			}
			else if(StringUtils.isNotBlank(memberCreateBeginTime) && StringUtils.isNotBlank(memberCreateEndTime)){
				fileName = new String("会员发布车辆统计表".getBytes(), "ISO-8859-1")+memberCreateBeginTime+"_"+memberCreateEndTime;
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			//查询数据
			List<PublishCountDTO> list = carLineManageService.memberPublishCarSameCity(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("会员发布车辆统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "推荐人手机");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "推荐人姓名");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "被推荐人手机");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "被推荐人姓名");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "被推荐人角色");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "用户类型 ");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "区域名称 ");// 填充第一行第八个单元格的内容
				Label label70 = new Label(7, 0, "公司名称");// 填充第一行第九个单元格的内容
				Label label80 = new Label(8, 0, "发布车辆信息时间 ");// 填充第一行第十个单元格的内容
				Label label90 = new Label(9, 0, "认证状态 ");// 填充第一行第十个单元格的内容
				Label labe200 = new Label(10, 0, "被推荐人注册时间 ");// 填充第一行第十个单元格的内容
				sheet.addCell(label00);
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(label90);
				sheet.addCell(labe200);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						PublishCountDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getMobile());
						Label label1 = new Label(1, i + 1, dto.getName());
						Label label2 = new Label(2, i + 1, dto.getMobile_ed());
						Label label3 = new Label(3, i + 1, dto.getName_ed());
						Label label4 = new Label(4, i + 1, ELevelTyle.getValueByCode(dto.getLevel()));
						Label label5 = new Label(5, i + 1, dto.getUserType());
						Label label6 = new Label(6, i + 1, dto.getAreaName());
						Label label7 = new Label(7, i + 1, dto.getCompanyName());
						Label label8 = new Label(8, i + 1, dto.getCarCreateTime());
						Label label9 = new Label(9, i + 1, dto.getNstStatus());
						Label labe20 = new Label(10, i + 1,dto.getMemberCreateTime());
						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(labe20);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 拼装Map
	 * @param map
	 * @param publishCountDTO
	 * @return
	 */
	private Map<String, Object> getMap(Map<String, Object> map,PublishCountDTO publishCountDTO){
		map.put("areaName", publishCountDTO.getAreaName());
		map.put("mobile", publishCountDTO.getMobile());
		map.put("mobile_ed", publishCountDTO.getMobile_ed());
		map.put("memberCreateBeginTime", publishCountDTO.getMemberCreateBeginTime());
		map.put("memberCreateEndTime", publishCountDTO.getMemberCreateEndTime());
		map.put("carCreateBeginTime", publishCountDTO.getCarCreateBeginTime());
		map.put("carCreateEndTime", publishCountDTO.getCarCreateEndTime());
		map.put("lineCreateBeginTime", publishCountDTO.getLineCreateBeginTime());
		map.put("lineCreateEndTime", publishCountDTO.getLineCreateEndTime());
		map.put("s_provinceId", publishCountDTO.getS_provinceId());
		map.put("s_cityId", publishCountDTO.getS_cityId());
		map.put("s_areaId", publishCountDTO.getS_areaId());
		map.put("e_provinceId", publishCountDTO.getE_provinceId());
		map.put("e_cityId", publishCountDTO.getE_cityId());
		map.put("e_areaId", publishCountDTO.getE_areaId());
		return map;
	}
}
