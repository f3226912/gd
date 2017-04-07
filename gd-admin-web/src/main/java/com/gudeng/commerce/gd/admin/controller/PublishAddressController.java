package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
import com.gudeng.commerce.gd.customer.dto.NstMemberAddressDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("publishAddress")
public class PublishAddressController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(PublishAddressController.class);
	
	
	@Autowired
	public MemberAddressManageService memberAddressManageService;
	
	@Autowired
	public AreaSettingToolService areaSettingToolService;
	
	@Autowired
	public QueryAreaService queryAreaService;
	
	/**
	 * 干线货源
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			List<AreaSettingDTO> list = areaSettingToolService.getAllAreaName(map);
			request.setAttribute("areaNameList", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "publishAddress/nst_memberAddress_list";
	}
	 
	
	/**
	 * 同城货源
	 * @return
	 */
	@RequestMapping("index_list")
	public String _index(HttpServletRequest request){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			List<AreaSettingDTO> list = areaSettingToolService.getAllAreaName(map);
			request.setAttribute("areaNameList", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "publishAddress/nst_samecity_address_list";
	}
	 
	
	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		try {
            
			Map<String, Object> map = new HashMap<String, Object>();
			String type= request.getParameter("transportType");
			List<NstMemberAddressDTO> list = null;
			boolean isSameCity=false;
			//干线
			if ("0".equals(type)) {
				map.put("total", memberAddressManageService.getDistributeAddressTotal(map));
				setCommParameters(request, map);
				list = memberAddressManageService.getDistributeAddressList(map);
			}
			//同城
			else if ("1".equals(type)) {
				map.put("total", memberAddressManageService.getDistributeSameCityAddressTotal(map));
				setCommParameters(request, map);
				list = memberAddressManageService.getDistributeSameCityAddressList(map);
				isSameCity=true;
			}
			//设定分页,排序
			
			//list
			if(list !=null && list.size()>0)
			{
				for(NstMemberAddressDTO dto : list)
				{
					setPageValue(dto,isSameCity);
				}
			}
			
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private void setPageValue(NstMemberAddressDTO dto ,boolean isSameCity) throws Exception {
		//如果是直发，代发人显示为空
		//如果是直发，代发人显示为空
		if("0".equals(dto.getDistributeType()))
		{
			dto.setPublishAccount("");
			dto.setPublishUserName("");
		}
		if(!isSameCity){
		setAddress(dto);
		dto.setWeightString(dto.getTotalWeight()+getWeight(dto.getHundredweight()));
		}
		NumberFormat weightFormat = NumberFormat.getNumberInstance();
		weightFormat.setMaximumFractionDigits(1);
		dto.setWeightString(dto.getTotalWeight()!=null && dto.getTotalWeight()>0 ? weightFormat.format(dto.getTotalWeight())+getWeight(dto.getHundredweight()) :"");		dto.setDistributeType(getRuleType(dto.getDistributeType()));
		dto.setClients(getPublishType(dto.getClients()));
		
		//常用城市
		AreaDTO areaDTO = queryAreaService.getArea(String.valueOf(dto.getcCityId()));
        dto.setCityName(areaDTO !=null? areaDTO.getArea():"");
		
	}

	
	
	
	/**
	 * 打开show页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="showbyid/{id}")
    public String showbyid(@PathVariable("id") String id, HttpServletRequest request){      
		return null;
	}
	
	
	
	/**
	 * 根据多个条件查询
	 * 
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="querybysearch" )
	@ResponseBody
    public String queryBysearch(NstMemberAddressDTO paramDto, HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("account", paramDto.getAccount());
			map.put("publishAccount", paramDto.getPublishAccount());
			map.put("companyName", paramDto.getCompanyName());
			map.put("addressStartDate", paramDto.getQueryStartDate());
			map.put("addressEndDate", paramDto.getQueryEndDate());
			map.put("distributeType", paramDto.getDistributeType());
			map.put("clients", paramDto.getClients());
			map.put("orderStatus", paramDto.getOrderStatus());
			//始发地目的地
			map.put("s_provinceId" ,request.getParameter("start_provinceId"));
			map.put("s_cityId" ,request.getParameter("start_cityId"));
			map.put("s_areaId" ,request.getParameter("start_areaId"));
			map.put("f_provinceId" ,request.getParameter("end_provinceId"));
			map.put("f_cityId" ,request.getParameter("end_cityId"));
			map.put("f_areaId" , request.getParameter("end_areaId"));
			
			if (StringUtils.isNotEmpty(request.getParameter("cityName"))) {
				AreaDTO area = queryAreaService.getAreaByName(request.getParameter("cityName"));
				// 设置查询参数
				if (area != null) {
					map.put("cCityId", area.getAreaID());
				} else {
					map.put("cCityId", "000000");
				}
			}
			
			//记录数
			List<NstMemberAddressDTO> list = null;
			//设定分页,排序
			setCommParameters(request, map);
			//list
			String type= request.getParameter("transportType");
			boolean isSameCity=false;
			//干线
			if ("0".equals(type)) {
				map.put("total", memberAddressManageService.getDistributeAddressTotal(map));
				list = memberAddressManageService.getDistributeAddressList(map);
			}
			//同城
			else if ("1".equals(type)) {
				map.put("total", memberAddressManageService.getDistributeSameCityAddressTotal(map));
				list = memberAddressManageService.getDistributeSameCityAddressList(map);
				isSameCity=true;
			}
			if(list !=null && list.size()>0)
			{
				for(NstMemberAddressDTO dto : list)
				{
					setPageValue(dto,isSameCity);
				}
			}
			map.put("rows", list);//rows键 存放每页记录 list  
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParam(HttpServletRequest request, NstMemberAddressDTO paramDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (DateUtil.isDateIntervalOverFlow(paramDto.getQueryStartDate(), paramDto.getQueryEndDate(), 31)){
			resultMap.put("status", 0);
			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
			return JSONObject.toJSONString(resultMap);
		}
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", paramDto.getAccount());
			map.put("publishAccount", paramDto.getPublishAccount());
			map.put("companyName", paramDto.getCompanyName());
			map.put("addressStartDate", paramDto.getQueryStartDate());
			map.put("addressEndDate", paramDto.getQueryEndDate());
			map.put("distributeType", paramDto.getDistributeType());
			map.put("clients", paramDto.getClients());
			map.put("orderStatus", paramDto.getOrderStatus());
			//始发地目的地
			map.put("s_provinceId" ,request.getParameter("start_provinceId"));
			map.put("s_cityId" ,request.getParameter("start_cityId"));
			map.put("s_areaId" ,request.getParameter("start_areaId"));
			map.put("f_provinceId" ,request.getParameter("end_provinceId"));
			map.put("f_cityId" ,request.getParameter("end_cityId"));
			map.put("f_areaId" , request.getParameter("end_areaId"));
			
			if (StringUtils.isNotEmpty(request.getParameter("cityName"))) {
				AreaDTO area = queryAreaService.getAreaByName(request.getParameter("cityName"));
				// 设置查询参数
				if (area != null) {
					map.put("cCityId", area.getAreaID());
				} else {
					map.put("cCityId", "000000");
				}
			}
			int total = memberAddressManageService.getDistributeAddressTotal(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("物流管理发货信息导出验证异常 ", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		List<NstMemberAddressDTO> list = null;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", request.getParameter("account"));
			map.put("publishAccount", request.getParameter("publishAccount"));
			map.put("companyName",  request.getParameter("companyName"));
			map.put("addressStartDate",  request.getParameter("queryStartDate"));
			map.put("addressEndDate", request.getParameter("queryEndDate"));
			map.put("distributeType", request.getParameter("distributeType"));
			map.put("clients", request.getParameter("clients"));
			map.put("orderStatus", request.getParameter("orderStatus"));
			//始发地目的地
			map.put("s_provinceId" ,request.getParameter("start_provinceId"));
			map.put("s_cityId" ,request.getParameter("start_cityId"));
			map.put("s_areaId" ,request.getParameter("start_areaId"));
			map.put("f_provinceId" ,request.getParameter("end_provinceId"));
			map.put("f_cityId" ,request.getParameter("end_cityId"));
			map.put("f_areaId" , request.getParameter("end_areaId"));
			map.put("startRow", 0);
			map.put("endRow", 99999999);
			if (StringUtils.isNotEmpty(request.getParameter("cityName"))) {
				AreaDTO area = queryAreaService.getAreaByName(request.getParameter("cityName"));
				// 设置查询参数
				if (area != null) {
					map.put("cCityId", area.getAreaID());
				} else {
					map.put("cCityId", "000000");
				}
			}
			map.put("total", memberAddressManageService.getDistributeAddressTotal(map));
			list = memberAddressManageService.getDistributeAddressList(map);
			if(list !=null && list.size()>0)
			{
				for(NstMemberAddressDTO dto : list)
				{
					setPageValue(dto,false);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = URLEncoder.encode("发货信息列表", "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");			
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("发货信息记录", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "信息发布人");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "发布人账号");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "发布时间");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "货物类型");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "货物名称");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "起运地");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "目的地");// 填充第一行第六个单元格的内容
				Label label70 = new Label(7, 0, "货物重量");// 填充第一行第七个单元格的内容
				Label label80 = new Label(8, 0, "货物体积");// 填充第一行第八个单元格的内容
				Label label90 = new Label(9, 0, "代发布人");// 填充第一行第9个单元格的内容
				Label label100 = new Label(10, 0, "公司名称");// 填充第一行第10个单元格的内容
				Label label101 = new Label(11, 0, "代发布人账号");// 填充第一行第10个单元格的内容
				Label label102 = new Label(12, 0, "订单状态");// 填充第一行第10个单元格的内容
				Label label103 = new Label(13, 0, "分配类型");// 填充第一行第10个单元格的内容
				Label label104 = new Label(14, 0, "发布来源");// 填充第一行第10个单元格的内容
				Label label105 = new Label(15, 0, "常用城市");// 填充第一行第10个单元格的内容
				
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
				sheet.addCell(label103);
				sheet.addCell(label104);
				sheet.addCell(label105);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						NstMemberAddressDTO dto = list.get(i);
						Label label0 =new Label(0, i + 1, dto.getUserName());
						Label label1 = new Label(1, i + 1, dto.getAccount());
						Label label2 = new Label(2, i + 1, dto.getCreateTime()==null?"":time.format(dto.getCreateTime()));
						Label label3 = new Label(3, i + 1, getGoodsType(dto.getGoodsType()));
						Label label4 = new Label(4, i + 1, dto.getGoodsName());
						Label label5 = new Label(5, i + 1, dto.getStartPlace());
						Label label6 = new Label(6, i + 1, dto.getEndPlace());
						Label label7 =new Label(7, i + 1, dto.getWeightString());
						Label label8 =new Label(8, i + 1, (dto.getTotalSize()!=null && dto.getTotalSize()>0)? dto.getTotalSize()+"立方米":"");
						//用户类型
						Label label9 =new Label(9, i + 1, dto.getPublishUserName());
						Label label21 =new Label(10, i + 1, dto.getPublishAccount());
						Label label22 =new Label(11, i + 1, dto.getCompanyName());
						Label label23 =new Label(12, i + 1, getStatus(dto.getOrderStatus()));
						Label label24 =new Label(13, i + 1, dto.getDistributeType());
						Label label25 =new Label(14, i + 1, dto.getClients());
						Label label26 =new Label(15, i + 1, dto.getCityName());

						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(label21);
						sheet.addCell(label22);
						sheet.addCell(label23);
						sheet.addCell(label24);
						sheet.addCell(label25);
						sheet.addCell(label26);
						
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
		return null;
	}
	
	@RequestMapping(value = "exportDataSameCity")
	public String exportDataSameCity(HttpServletRequest request, HttpServletResponse response) {
		List<NstMemberAddressDTO> list = null;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", request.getParameter("account"));
			map.put("publishAccount", request.getParameter("publishAccount"));
			map.put("companyName",  request.getParameter("companyName"));
			map.put("addressStartDate",  request.getParameter("queryStartDate"));
			map.put("addressEndDate", request.getParameter("queryEndDate"));
			map.put("distributeType", request.getParameter("distributeType"));
			map.put("clients", request.getParameter("clients"));
			map.put("orderStatus", request.getParameter("orderStatus"));
			//始发地目的地
			map.put("s_provinceId" ,request.getParameter("start_provinceId"));
			map.put("s_cityId" ,request.getParameter("start_cityId"));
			map.put("s_areaId" ,request.getParameter("start_areaId"));
			map.put("f_provinceId" ,request.getParameter("end_provinceId"));
			map.put("f_cityId" ,request.getParameter("end_cityId"));
			map.put("f_areaId" , request.getParameter("end_areaId"));
			map.put("startRow", 0);
			map.put("endRow", 99999999);
			if (StringUtils.isNotEmpty(request.getParameter("cityName"))) {
				AreaDTO area = queryAreaService.getAreaByName(request.getParameter("cityName"));
				// 设置查询参数
				if (area != null) {
					map.put("cCityId", area.getAreaID());
				} else {
					map.put("cCityId", "000000");
				}
			}
			map.put("total", memberAddressManageService.getDistributeSameCityAddressTotal(map));
			list = memberAddressManageService.getDistributeSameCityAddressList(map);
			if(list !=null && list.size()>0)
			{
				for(NstMemberAddressDTO dto : list)
				{
					setPageValue(dto,true);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = URLEncoder.encode("发货信息列表", "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");			
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("发货信息记录", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "信息发布人");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "发布人账号");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "发布时间");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "货物类型");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "货物名称");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "起运地");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "目的地");// 填充第一行第六个单元格的内容
				Label label70 = new Label(7, 0, "货物重量");// 填充第一行第七个单元格的内容
				Label label80 = new Label(8, 0, "货物体积");// 填充第一行第八个单元格的内容
				Label label90 = new Label(9, 0, "代发布人");// 填充第一行第9个单元格的内容
				Label label100 = new Label(10, 0, "公司名称");// 填充第一行第10个单元格的内容
				Label label101 = new Label(11, 0, "代发布人账号");// 填充第一行第10个单元格的内容
				Label label102 = new Label(12, 0, "订单状态");// 填充第一行第10个单元格的内容
				Label label103 = new Label(13, 0, "分配类型");// 填充第一行第10个单元格的内容
				Label label104 = new Label(14, 0, "发布来源");// 填充第一行第10个单元格的内容
				Label label105 = new Label(15, 0, "常用城市");// 填充第一行第10个单元格的内容
				
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
				sheet.addCell(label103);
				sheet.addCell(label104);
				sheet.addCell(label105);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						NstMemberAddressDTO dto = list.get(i);
						Label label0 =new Label(0, i + 1, dto.getUserName());
						Label label1 = new Label(1, i + 1, dto.getAccount());
						Label label2 = new Label(2, i + 1, dto.getCreateTime()==null?"":time.format(dto.getCreateTime()));
						Label label3 = new Label(3, i + 1, getGoodsType(dto.getGoodsType()));
						Label label4 = new Label(4, i + 1, dto.getGoodsName());
						Label label5 = new Label(5, i + 1, dto.getStartPlace());
						Label label6 = new Label(6, i + 1, dto.getEndPlace());
						Label label7 =new Label(7, i + 1, dto.getWeightString());
						Label label8 =new Label(8, i + 1, (dto.getTotalSize()!=null && dto.getTotalSize()>0)? dto.getTotalSize()+"立方米":"");
						//用户类型
						Label label9 =new Label(9, i + 1, dto.getPublishUserName());
						Label label21 =new Label(10, i + 1, dto.getPublishAccount());
						Label label22 =new Label(11, i + 1, dto.getCompanyName());
						Label label23 =new Label(12, i + 1, getStatus(dto.getOrderStatus()));
						Label label24 =new Label(13, i + 1, dto.getDistributeType());
						Label label25 =new Label(14, i + 1, dto.getClients());
						Label label26 =new Label(15, i + 1, dto.getCityName());

						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(label21);
						sheet.addCell(label22);
						sheet.addCell(label23);
						sheet.addCell(label24);
						sheet.addCell(label25);
						sheet.addCell(label26);
						
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
		return null;
	}
	

	private String getRuleType(String type) {
		String ruleType = null;
		if ("0".equals(type) || type==null) {
			ruleType = "直发";
		} else if ("1".equals(type)) {
			ruleType = "代发";
		}
		return ruleType;
	}
	private String getWeight(Integer type) {
		String weightType = null;
		if (type != null) {
			if (type == 0) {
				weightType = "吨";
			} else if (type == 1) {
				weightType = "公斤";
			}
		}else{
			weightType = "吨";
		}
		return weightType;
	}
	
	private String getPublishType(String client) {
		//'1谷登农批，2农速通，3农商友，4产地供应商，5农商友-农批商',
		String type = null;
		if ("1".equals(client)) {
			type = "谷登农批";
		} else if ("2".equals(client) || client==null ) {
			type = "农速通";
		} else if ("3".equals(client)) {
			type = "农商友";
		} else if ("4".equals(client)) {
			type = "产地供应商";
		}else if ("5".equals(client) || "6".equals(client)) {
			type = "农商友-农批商";
		}
		return type;
	}
	
	private String getStatus(String orderStatus) {
		String status = null;
		if ("1".equals(orderStatus)) {
			status = "待成交";
		} else if ("2".equals(orderStatus)) {
			status = "已成交";
		} else if ("3".equals(orderStatus)) {
			status = "已完成";
		} else if ("4".equals(orderStatus)) {
			status = "未完成";
		}else if ("5".equals(orderStatus)) {
			status = "已取消";
		}
		return status;
	}

	
	
	private String getGoodsType(Integer goodType) {
		String type = null;
		if (goodType != null) {
			switch (goodType) {
			case 0:
				type = "普货";
				break;
			case 1:
				type = "冷藏";
				break;
			case 2:
				type = "鲜活水产";
				break;
			case 3:
				type = "其他";
				break;
			case 4:
				type = "重货";
				break;
			case 5:
				type = "抛货";
				break;
			case 6:
				type = "蔬菜";
				break;
			case 7:
				type = "水果";
				break;
			case 8:
				type = "农副产品";
				break;
			case 9:
				type = "日用品";
				break;
			case 10:
				type = "纺织";
				break;
			case 11:
				type = "木材";
				break;
			default:
				break;
			}
		}
		return type;
	}
	
	private void setAddress(NstMemberAddressDTO dto) throws Exception {
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
	
}
