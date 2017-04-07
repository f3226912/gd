package com.gudeng.commerce.gd.admin.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.gudeng.commerce.gd.admin.service.CarLineManageService;
import com.gudeng.commerce.gd.admin.service.CarsManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.QueryAreaService;
import com.gudeng.commerce.gd.admin.util.AreaUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.JsonConvertUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;


/**
 * 车辆专线管理
 * @author xiaodong
 */
@Controller
@RequestMapping("carLine")
public class CarLineManagerController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(CarLineManagerController.class);
	
	@Autowired
	public CarLineManageService carLineManageService;
	
	
	@Autowired
	public CarsManageService carsManageService;
	
	@Autowired
	public QueryAreaService queryAreaService;
	
	@Autowired
	public MemberBaseinfoToolService  memberBaseinfoToolService;
	
	@Autowired
	public AreaSettingToolService areaSettingToolService;
	
	@Autowired
	public GdProperties gdProperties;

	/**
	 * 列表页面
	 * @return
	 */
	@RequestMapping("")
	public String index(HttpServletRequest request) {
		try {
//			this.getUser(request)
			Map<String, Object> map = new HashMap<String, Object>();
			List<AreaSettingDTO> list = areaSettingToolService.getAllAreaName(map);
			request.setAttribute("areaNameList", list);
			return "carLine/carLineList";
		} catch (Exception e) {
			return null;
		}

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
			String id = request.getParameter("id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			// 设置查询参数
			// 记录数
			map.put("total", carLineManageService.getTotalForConsole(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<CarLineDTO> list = carLineManageService.getByName(map);
			convertPageList(list);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String queryStartDate = request.getParameter("queryStartDate");
		String queryEndDate = request.getParameter("queryEndDate");
		if (DateUtil.isDateIntervalOverFlow(queryStartDate, queryEndDate, 31)){
			resultMap.put("status", 0);
			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
			return JSONObject.toJSONString(resultMap);
		}
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("carType", request.getParameter("carType"));
			map.put("mobile", request.getParameter("mobile"));
			map.put("startDate", queryStartDate);
			map.put("endDate", queryEndDate);
			map.put("userType", request.getParameter("queryType"));
			map.put("areaName", request.getParameter("areaName"));
			//用来判断已进行软删除的的字段，导出已删除数据用
			map.put("isDel", request.getParameter("isDel"));
			
			int total = carLineManageService.getTotalForConsole(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("物流管理-线路管理导出异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	/**
	 * 根据检索条件，导出查询数据
	 */
	@RequestMapping("export")
	@ResponseBody
	public void export(String carType,String mobile, String queryStartDate, String queryEndDate, String queryType, String  areaName, String isDel, HttpServletResponse response)  {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
		map.put("carType", carType);
		map.put("mobile", mobile);
		map.put("startDate", queryStartDate);
		map.put("endDate", queryEndDate);
		map.put("userType", queryType);
		map.put("areaName", areaName);
		map.put("startRow", 0);
		map.put("endRow", 100000000);
		//用来判断已进行软删除的的字段，导出已删除数据用
		map.put("isDel", isDel);
		try {
			List<CarLineDTO> list = carLineManageService.getByName(map);
			if (list == null ) {
				list = Collections.emptyList();
			}	
			convertPageList(list);
			String fileName = new String("线路信息表".getBytes(), "ISO-8859-1")+queryStartDate+"_"+queryEndDate;
		    exportData(list, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void exportData(List<CarLineDTO> list, String exportFileName){
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			
			response.setHeader("Content-disposition", "attachment;filename=" + exportFileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据list
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("线路信息", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "车牌号码");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "车辆类型 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "载重(吨)");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "车长(米)");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "用户类型 ");// 
				Label label50 = new Label(5, 0, "企业名称");// 
				Label label60 = new Label(6, 0, "联系人");// 填充第一行第五个单元格的内容
				Label label70 = new Label(7, 0, "电话号码 ");//
				Label label80 = new Label(8, 0, "发布时间 ");//
				Label label90 = new Label(9, 0, "发车时间 ");//
				Label label101 = new Label(10, 0, "截止时间 ");//
				Label label102= new Label(11, 0, "发运方式 ");//
				Label label103 = new Label(12, 0, "价格");//
				Label label104 = new Label(13, 0, "出发地 ");//
				Label label105 = new Label(14, 0, "目的地");//
				Label label106 = new Label(15, 0, "所属区域");//
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
				sheet.addCell(label101);
				sheet.addCell(label102);
				sheet.addCell(label103);
				sheet.addCell(label104);
				sheet.addCell(label105);
				sheet.addCell(label106);
				/*** 循环添加数据到工作簿 ***/
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						CarLineDTO dto = list.get(i);
						if(StringUtils.isEmpty(dto.getCarNumber()))
						{
						   continue;
						}
						Label label0 = new Label(0, i + 1, dto.getCarNumber());
						Label label1 = new Label(1, i + 1, dto.getCarTypeString());
						Label label2 = new Label(2, i + 1, String.valueOf(dto.getMaxLoad()));
						Label label3 = new Label(3, i + 1, String.valueOf(dto.getCarLength()));
						Label label4 = new Label(4, i + 1, dto.getUserTypeName());
						Label label5 = new Label(5, i + 1, dto.getCompanyName());
						Label label6 = new Label(6, i + 1, dto.getNickName());
						Label label7 = new Label(7, i + 1, dto.getPhone());
						Label label8 = new Label(8, i + 1, dto.getCreateTimeString());
						
						Label label9 = new Label(9, i + 1, dto.getSendDateString());
						Label label110 = new Label(10, i + 1, dto.getEndDateString());
						Label label111 = new Label(11, i + 1, dto.getSendGoodsTypeString());
						Label label112 = new Label(12, i + 1, dto.getUnitTypeString());
						Label label113 = new Label(13, i + 1, dto.getStartPlace());
						Label label114 = new Label(14, i + 1, dto.getEndPlace());
						Label label115 = new Label(15, i + 1, dto.getAreaName());

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
						sheet.addCell(label110);// 
						sheet.addCell(label111);// 
						sheet.addCell(label112);
						sheet.addCell(label113);
						sheet.addCell(label114);
						sheet.addCell(label115);
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
	 * 根据name查询
	 * 
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="queryByParameters" )
	@ResponseBody
    public String queryByParameters(@RequestParam String carType, @RequestParam String mobile, @RequestParam String startDate,@RequestParam String endDate, @RequestParam String queryType,   @RequestParam String  areaName,  HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("carType", carType);
			map.put("mobile", mobile);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("userType", queryType);
			map.put("areaName", areaName);
			// 记录数
			map.put("total", carLineManageService.getTotalForConsole(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<CarLineDTO> list = carLineManageService.getByName(map);
			convertPageList(list);
			map.put("rows", list);// rows键 存放每页记录 list
			String returnStr = JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
           e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 根据ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="deletebyid" )
	@ResponseBody
    public String deleteById(@RequestParam String id, HttpServletRequest request){      
		try {
			logger.info("删除线路操作 id="+id);
			if(null==id || id.equals("")){
			return "faild";
			}			
			//carLineManageService.deleteById(id);
			//此处修改为updateCarLineByid 保留记录
			carLineManageService.updateCarLineByid(id);
			logger.info("删除线路操作 id="+id + " 成功！");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 增加页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("addDto")
	public String addDto(HttpServletRequest request){
		try {
			List<CarsDTO>  list =carsManageService.getAllByType(null);
			request.setAttribute("list", list);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return "carLine/addCarLine";
	}
	
	
	
	/**
	 * 	  增加修改同一个方法
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save")
	@ResponseBody
    public String saveOrUpdate( HttpServletRequest request , CarLineDTO carLineDTO){      
		try {
			 
			//始发地2,3不填，保存为1
			carLineDTO.setS_provinceId2(carLineDTO.getS_provinceId2()!=null?carLineDTO.getS_provinceId2():1);
			carLineDTO.setS_provinceId3(carLineDTO.getS_provinceId3()!=null?carLineDTO.getS_provinceId3():1);                            
			//目的地2,3,4,5不填，保存为1
			carLineDTO.setE_provinceId2(carLineDTO.getE_provinceId2() !=null ?carLineDTO.getE_provinceId2():1);                            
			carLineDTO.setE_provinceId3(carLineDTO.getE_provinceId3() !=null ?carLineDTO.getE_provinceId3():1);                            
			carLineDTO.setE_provinceId4(carLineDTO.getE_provinceId4() !=null ?carLineDTO.getE_provinceId4():1);                            
			carLineDTO.setE_provinceId5(carLineDTO.getE_provinceId5() !=null ?carLineDTO.getE_provinceId5():1);                            
			//选择全国，省市县areaId都保存为0，和APP端保持一致
			setAreaId(carLineDTO);
			
			carLineDTO.setSendDateString(StringUtils.isNotEmpty(request.getParameter("sendDate"))?request.getParameter("sendDate"):null);
			carLineDTO.setPrice(StringUtil.isNotEmpty(request.getParameter("price"))
					?Double.parseDouble(request.getParameter("price")):0d);
			carLineDTO.setEndDateString(StringUtils.isNotEmpty(request.getParameter("endDate"))?request.getParameter("endDate"):null);
			//发布时间判断
			String msg =checkTime(carLineDTO);
			if(StringUtils.isNotEmpty(msg))
			{
				return msg;
			}
			String id=request.getParameter("id");
			CarLineDTO carLine= carLineManageService.getById(id);
			
			CarsDTO  car = carsManageService.getById(String.valueOf(carLineDTO.getCarId()));
			if(car !=null)
			{
				carLineDTO.setMemberId(car.getUserId());
			}
			
			int i=0;
			//根据id判断是否存在，存在即为更新，不存在即为增加
			if(carLine != null){
				logger.info("更新线路操作 id="+carLine.getId());
				carLineDTO.setId(carLine.getId());
				carLineDTO.setUpdateUserId(getUser(request)!=null?getUser(request).getUserID():"1");                              
				carLineDTO.setUpdateTimeString(DateUtil.getSysDateTimeString());
				i=carLineManageService.updateCarLineDTO(carLineDTO);
				logger.info("更新线路操作 id="+carLine.getId()+ "成功！");
			}else{
				carLineDTO.setSource("0");
				carLineDTO.setCreateUserId(getUser(request)!=null?getUser(request).getUserID():"1");                              
				carLineDTO.setCreateTimeString(DateUtil.getSysDateTimeString());
				i=carLineManageService.addCarLineDTO(carLineDTO);
			}
			if(i>0){
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {
			logger.warn("保存线路操作 CarNumber="+carLineDTO.getCarNumber()+ "失败！");
			e.printStackTrace();
		}
		return null;
	}



	private void setAreaId(CarLineDTO carLineDTO) {
		if(carLineDTO.getS_provinceId() != null && carLineDTO.getS_provinceId() == 0)
		{
			carLineDTO.setS_cityId(0L);
			carLineDTO.setS_areaId(0L);
		}
		if(carLineDTO.getS_provinceId2() != null && carLineDTO.getS_provinceId2() ==  0)
		{
			carLineDTO.setS_cityId2(0L);
			carLineDTO.setS_areaId2(0L);
		}
		if(carLineDTO.getS_provinceId3() != null && carLineDTO.getS_provinceId3() ==  0)
		{
			carLineDTO.setS_cityId3(0L);
			carLineDTO.setS_areaId3(0L);
		}
		if(carLineDTO.getE_provinceId() != null && carLineDTO.getE_provinceId() ==  0)
		{
			carLineDTO.setE_cityId(0L);
			carLineDTO.setE_areaId(0L);
		}
		if(carLineDTO.getE_provinceId2() != null && carLineDTO.getE_provinceId2() ==  0)
		{
			carLineDTO.setE_cityId2(0L);
			carLineDTO.setE_areaId2(0L);
		}
		if(carLineDTO.getE_provinceId3() != null && carLineDTO.getE_provinceId3() ==  0)
		{
			carLineDTO.setE_cityId3(0L);
			carLineDTO.setE_areaId3(0L);
		}
		if(carLineDTO.getE_provinceId4() != null && carLineDTO.getE_provinceId4() ==  0)
		{
			carLineDTO.setE_cityId4(0L);
			carLineDTO.setE_areaId4(0L);
		}
		if(carLineDTO.getE_provinceId5() != null && carLineDTO.getE_provinceId5() ==  0)
		{
			carLineDTO.setE_cityId5(0L);
			carLineDTO.setE_areaId5(0L);
		}
	}
	
	
	
	/**
	 * 打开编辑页
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="edit" )
    public String edit( HttpServletRequest request) {     
		try {
			String id=request.getParameter("id");
			CarLineDTO dto=carLineManageService.getById(id);
		    if(dto !=null)
			{  
				CarsDTO car =carsManageService.getById(String.valueOf(dto.getCarId()));
		    	putModel("carId",dto.getCarId());
				putModel("carNumber",car.getCarNumber());
				putModel("id",dto.getId());
			    putModel("sendDate",DateUtil.toString(dto.getSentDate(), DateUtil.DATE_FORMAT_DATEONLY));
		        putModel("sentDateType",dto.getSentDateType());
				putModel("onLineHours",dto.getOnLineHours());
			    putModel("endDate",DateUtil.toString(dto.getEndDate(), DateUtil.DATE_FORMAT_DATEONLY));
				putModel("sendGoodsType",dto.getSendGoodsType());
				putModel("unitType" ,dto.getUnitType());
				if(dto.getPrice() !=null){
					DecimalFormat df = new DecimalFormat("0.00"); 
					putModel("price",df.format(dto.getPrice()));
				}
				putModel("start_provinceId",dto.getS_provinceId());
				putModel("start_cityId",dto.getS_cityId());
				putModel("start_areaId",dto.getS_areaId());
				
				putModel("start_provinceId2",dto.getS_provinceId2());
				putModel("start_cityId2",dto.getS_cityId2());
				putModel("start_areaId2",dto.getS_areaId2());
				
				putModel("start_provinceId3",dto.getS_provinceId3());
				putModel("start_cityId3",dto.getS_cityId3());
				putModel("start_areaId3",dto.getS_areaId3());
				
				putModel("end_provinceId",dto.getE_provinceId());
				putModel("end_cityId",dto.getE_cityId());
				putModel("end_areaId",dto.getE_areaId());
				
				putModel("end_provinceId2",dto.getE_provinceId2());
				putModel("end_cityId2",dto.getE_cityId2());
				putModel("end_areaId2",dto.getE_areaId2());
				
				putModel("end_provinceId3",dto.getE_provinceId3());
				putModel("end_cityId3",dto.getE_cityId3());
				putModel("end_areaId3",dto.getE_areaId3());
				
				putModel("end_provinceId4",dto.getE_provinceId4());
				putModel("end_cityId4",dto.getE_cityId4());
				putModel("end_areaId4",dto.getE_areaId4());
				
				
				putModel("end_provinceId5",dto.getE_provinceId5());
				putModel("end_cityId5",dto.getE_cityId5());
				putModel("end_areaId5",dto.getE_areaId5());

				putModel("remark", dto.getRemark());
			}    
		   
			return "carLine/editCarLine";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	//*******************区域查询************************//
	
	//查询全国行政区代码省
	@RequestMapping(value="queryProvince")
	@ResponseBody
	public String queryProvince(HttpServletRequest request , HttpServletResponse response) throws Exception{
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
	
	//查询全国行政区代码市
	@RequestMapping(value="queryCity" )
	@ResponseBody
	public String queryCity(HttpServletRequest request , HttpServletResponse response) throws Exception{
		List  <?> list=queryAreaService.findCity(request.getParameter("province"));
		this.jsonUtil(list,response);
	    return null;
	}
		
	//查询全国行政区代码县区
	@RequestMapping(value="queryCounty" )
	@ResponseBody
	public String queryCounty(HttpServletRequest request , HttpServletResponse response) throws Exception{
		String city = request.getParameter("city");
		List <?> list=queryAreaService.findCounty(city);
		this.jsonUtil(list,response);
	    return null;
	}
	

	private void jsonUtil(Object list ,HttpServletResponse response) throws Exception{
			logger.info("JSON格式：" + list.toString());
			String returnJson = JsonConvertUtil.returnJson(list);
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(returnJson);
	}
	


	private void convertPageList(List <CarLineDTO> list) throws Exception {
		
		if (list != null && list.size() > 0) {
			for (CarLineDTO dto : list) {
				StringBuffer startPlace =new StringBuffer();
				//始发地1
				if(dto.getS_provinceId() !=null && dto.getS_provinceId()>1){
			      AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId()));
				  if(dto.getS_cityId() != null && dto.getS_cityId()>0 && AreaUtil.isCity(city)){
				   startPlace.append(city != null ? city.getArea() : " ");
				  }
				  else{
					AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId()));
					startPlace.append(province != null?province.getArea():" ");
				 }
				}else if(dto.getS_provinceId() !=null && dto.getS_provinceId() ==0){
					startPlace.append("全国");
				}
				
				//始发地2
				String start2="";
				 if(dto.getS_provinceId2() !=null && dto.getS_provinceId2()>1){
					   AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId2()));
					 if(dto.getS_cityId2() != null && dto.getS_cityId2()>0  && AreaUtil.isCity(city)){
						 start2= "，"+city.getArea() ;
						
				 }else{
						AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId2()));
						start2 =province != null?"，"+province.getArea():" ";
						//startPlace.append(province != null?"，"+province.getArea():" ");
				  }
			     }else if(dto.getS_provinceId2() !=null && dto.getS_provinceId2() ==0){
						startPlace.append("全国");
				 }
				 //去重判断
				 if(!startPlace.toString().contains(start2.replace("，", "")))
				 {
					 startPlace.append(start2);
				 }
			
				 
				 //始发地3
				 String start3="";
				 if(dto.getS_provinceId3() !=null && dto.getS_provinceId3()>1){
					 AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId3()));
					 if(dto.getS_cityId3() != null && dto.getS_cityId3()>0 && AreaUtil.isCity(city) ){
						 start3="，"+city.getArea() ;
						// startPlace.append("，"+city.getArea() );
				 }else{
						AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId3()));
						start3 =province != null?"，"+province.getArea():" ";
						//startPlace.append(province != null?"，"+province.getArea():" ");
				  }
			     }else if(dto.getS_provinceId3() !=null && dto.getS_provinceId3() ==0){
						startPlace.append("全国");
				 }
				 
				 //去重判断
				 if(!startPlace.toString().contains(start3.replace("，", "")))
				 {
					 startPlace.append(start3);
				 }
				
				//目的地1
				StringBuffer endPlace =new StringBuffer();
				if(dto.getE_provinceId() !=null && dto.getE_provinceId()>1){
				AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto
							.getE_cityId()));
				if(dto.getE_cityId() !=null && dto.getE_cityId() >0 && AreaUtil.isCity(e_city)){
				endPlace.append(e_city != null ? " "+e_city.getArea() : "");
				}else{
					AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto
							.getE_provinceId()));
					endPlace.append(e_province != null ? e_province.getArea(): "");
				}
				/*AreaDTO e_area = queryAreaService.getArea(String.valueOf(dto
						.getE_areaId()));
				endPlace.append(e_area != null ? "--"+e_area.getArea() : "");*/
				}else if(dto.getE_provinceId() !=null && dto.getE_provinceId() ==0){
					endPlace.append("全国");
				}
				
				//目的地2
				String end2="";
				if(dto.getE_provinceId2() !=null && dto.getE_provinceId2()>1){
					AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto
							.getE_cityId2()));
					if(dto.getE_cityId2() !=null && dto.getE_cityId2() >0 && AreaUtil.isCity(e_city)){
						end2 =e_city != null ? "， "+e_city.getArea() : "";
						//endPlace.append(e_city != null ? "， "+e_city.getArea() : "");
					}else{
						AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto
								.getE_provinceId2()));
						end2=e_province != null ?  "， "+e_province.getArea(): "";
						//endPlace.append(e_province != null ?  "， "+e_province.getArea(): "");
					}
				   }else if(dto.getE_provinceId2() !=null && dto.getE_provinceId2() ==0){
						endPlace.append("全国");
				    }
				
				 //去重判断
				 if(!endPlace.toString().contains(end2.replace("， ", "")))
				 {
					 endPlace.append(end2);
				 }
				 
				 String end3="";
				//目的地3
				if(dto.getE_provinceId3() !=null && dto.getE_provinceId3()>1){
					AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto
							.getE_cityId3()));
					if(dto.getE_cityId3() !=null && dto.getE_cityId3() >0  && AreaUtil.isCity(e_city)){
						end3 =e_city != null ? "， "+e_city.getArea() : "";
						//endPlace.append(e_city != null ? "， "+e_city.getArea() : "");
					}else{
						AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto
								.getE_provinceId3()));
						end3 =e_province != null ?  "， "+e_province.getArea(): "";
						//endPlace.append(e_province != null ?  "， "+e_province.getArea(): "");
					}
				   }else if(dto.getE_provinceId3() !=null && dto.getE_provinceId3() ==0){
						endPlace.append("全国");
				   }
				
				 //去重判断
				 if(!endPlace.toString().contains(end3.replace("， ", "")))
				 {
					 endPlace.append(end3);
				 }
				 
				
				 String end4="";
				//目的地4
				if(dto.getE_provinceId4() !=null && dto.getE_provinceId4()>1){
					AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto
							.getE_cityId4()));
					if(dto.getE_cityId4() !=null && dto.getE_cityId4() >0  && AreaUtil.isCity(e_city)){
					end4 =e_city != null ? "， "+e_city.getArea() : "";
					//endPlace.append(e_city != null ? "， "+e_city.getArea() : "");
					}else{
						AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto
								.getE_provinceId4()));
						end4 =e_province != null ?  "， "+e_province.getArea(): "";
						//endPlace.append(e_province != null ?  "， "+e_province.getArea(): "");
					}
				    }else if(dto.getE_provinceId4() !=null && dto.getE_provinceId4() ==0){
						endPlace.append("全国");
				    }
				
				 //去重判断
				 if(!endPlace.toString().contains(end4.replace("， ", "")))
				 {
					 endPlace.append(end4);
				 }
				
				 String end5="";
				//目的地5
				if(dto.getE_provinceId5() !=null && dto.getE_provinceId5()>1){
					AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto
							.getE_cityId5()));
					if(dto.getE_cityId5() !=null && dto.getE_cityId5() >0  && AreaUtil.isCity(e_city)){
					end5=e_city != null ? "， "+e_city.getArea() : "";
					//endPlace.append(e_city != null ? "， "+e_city.getArea() : "");
					}else{
						AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto
								.getE_provinceId5()));
						end5=e_province != null ?  "， "+e_province.getArea(): "";
						//endPlace.append(e_province != null ?  "， "+e_province.getArea(): "");
					}
				   }else if(dto.getE_provinceId5() !=null && dto.getE_provinceId5() ==0){
						endPlace.append("全国");
					}
				  //去重判断
				 if(!endPlace.toString().contains(end5.replace("， ", "")))
				 {
					 endPlace.append(end5);
				 }
				
				//历史数据处理
				//始发地
				if(dto.getS_provinceId2() == null  && dto.getS_provinceId3() == null )
				{
					  if(dto.getS_provinceId() != null && dto.getS_provinceId()>0)	
					  dto.setStartPlace(startPlace.toString().replace("全国", ""));
					  else
					  dto.setStartPlace("全国");
				}
				else{
				 dto.setStartPlace(AreaUtil.isAllCity(startPlace.toString())?"全国" :startPlace.toString());
				}
				
				//目的地
				if (dto.getE_provinceId2() == null && dto.getE_provinceId3() == null) {
					if (dto.getE_provinceId() != null && dto.getE_provinceId() > 0)
						dto.setEndPlace(endPlace.toString().replace("全国", ""));
					else
						dto.setEndPlace("全国");
				}else{
				 dto.setEndPlace(AreaUtil.isAllCity(endPlace.toString())?"全国" :endPlace.toString());
				}
				if (StringUtils.isNotEmpty(dto.getCarType())) {
				switch (dto.getCarType()) { 
				case "0":                        
					dto.setCarTypeString("厢式货车");
					break;                         
				case "1":                        
					dto.setCarTypeString("敞车");    
					break;                         
				case "2":                        
					dto.setCarTypeString("冷藏车");  
					break;                         
				case "3":                        
					dto.setCarTypeString("保温车");  
					break;                         
				case "4":                        
					dto.setCarTypeString("其他");    
					break;                         
				case "5":                        
					dto.setCarTypeString("高栏车");  
					break;                         
				case "6":                        
					dto.setCarTypeString("平板车");  
					break;                         
				case "7":                        
					dto.setCarTypeString("活鲜水车");
					break;                         
				case "8":                        
					dto.setCarTypeString("集装箱");  
					break;                         
				default:                         
					dto.setCarTypeString("其他");    
					break;                         
				  }
				}else if (dto.getTransportCarType()!= null) {
					switch (dto.getTransportCarType()) {
					case 0:
						dto.setCarTypeString("小型面包");
						break;
					case 1:
						dto.setCarTypeString("金杯");
						break;
					case 2:
						dto.setCarTypeString("小型平板");
						break;
					case 3:
						dto.setCarTypeString("中型平板");
						break;
					case 4:
						dto.setCarTypeString("小型厢货");
						break;
					case 5:
						dto.setCarTypeString("大型厢货");
						break;
					}
				}
				if (StringUtils.isNotEmpty(dto.getSendGoodsType())) {
					switch (dto.getSendGoodsType()) {
					case "0":
						dto.setSendGoodsTypeString("零担");
						break;
					case "1":
						dto.setSendGoodsTypeString("整车");
						break;
					case "2":
						dto.setSendGoodsTypeString("不限");
						break;
					default:
						break;
					}
				}else{
					dto.setSendGoodsTypeString("不限");
				}
				
				NumberFormat nf = NumberFormat.getNumberInstance() ; 
				nf.setMaximumFractionDigits(2); 
				if (dto.getPrice() != null  && dto.getPrice()>0  && dto.getUnitType() != null) {
					
					switch (dto.getUnitType()) {
					case 0:
						dto.setUnitTypeString(nf.format(dto.getPrice()) + "元/吨");
						break;
					case 1:
						dto.setUnitTypeString(nf.format(dto.getPrice()) + "元/公斤");
						break;
					case 2:
						dto.setUnitTypeString(nf.format(dto.getPrice()) + "元/立方");
						break;
					case 3:
						dto.setUnitTypeString(nf.format(dto.getPrice()) + "元");
						break;
					default:
						break;
					}
			    if("1".equals(String.valueOf(dto.getSendGoodsType()))){
					dto.setUnitTypeString(nf.format(dto.getPrice()) + "元");
				 }
				}else if (dto.getPrice() != null  && dto.getPrice()>0  && dto.getUnitType() == null) {
					dto.setUnitTypeString(nf.format(dto.getPrice()) + "元");
				}else{
					dto.setUnitTypeString("面议");
				}

				dto.setSendDateString(DateUtil.toString(dto.getSentDate(),
						DateUtil.DATE_FORMAT_DATEONLY));
				dto.setCreateTimeString(DateUtil.toString(dto.getCreateTime(),
						DateUtil.DATE_FORMAT_DATEONLY));
				dto.setEndDateString(DateUtil.toString(dto.getEndDate(),
						DateUtil.DATE_FORMAT_DATEONLY));
				
				//企业用户查询
				if(StringUtil.isEmpty(dto.getNickName()) &&  StringUtil.isNotEmpty(dto.getPhone()))
				{
					MemberBaseinfoDTO  mem =memberBaseinfoToolService.getByMobile(dto.getPhone());
					dto.setNickName(mem !=null ? mem.getCompanyContact() :"");
				}
				if (dto.getUserType() != null) {
					if (1 == dto.getUserType()) {
						dto.setUserTypeName("个人");
						dto.setCompanyName("");
					} else if (2 == dto.getUserType()) {
						dto.setUserTypeName("企业");
					}
				}
			}
		}
		
	
	}

	
	/**
	 * 新增路线中查询车辆列表页面
	 * @return
	 */
	@RequestMapping("carList")
	public String carSelect() {
		return "carLine/carList";
	}
	
	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("queryCar")
	@ResponseBody
	public String queryCarList(@RequestParam String carNumber,HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("carNumber", carNumber);
			// 设置查询参数
			//记录数
			map.put("total", carsManageService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<CarsDTO> list = carsManageService.getByCondition(map);
			if(list !=null && list.size() >0)
			{
				for(CarsDTO dto : list)
				{
					setCarTypeName(dto);
				}
			}
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void setCarTypeName(CarsDTO dto) {
		if (dto.getCarType() != null) {
			switch (dto.getCarType()) {
			case "0":
				dto.setCarTypeName("厢式货车");
				break;
			case "1":
				dto.setCarTypeName("敞车");
				break;
			case "2":
				dto.setCarTypeName("冷藏车");
				break;
			case "3":
				dto.setCarTypeName("保温车");
				break;
			case "4":
				dto.setCarTypeName("其他");
				break;
			case "5":
				dto.setCarTypeName("高栏车");
				break;
			case "6":
				dto.setCarTypeName("平板车");
				break;
			case "7":
				dto.setCarTypeName("活鲜水车");
				break; 
			case "8":
				dto.setCarTypeName("集装箱");
				break; 
			default:
				dto.setCarTypeName("其他");
				break;
			}
		}else if (dto.getTransportCarType()!= null) {
			switch (dto.getTransportCarType()) {
			case 0:
				dto.setCarTypeName("小型面包");
				break;
			case 1:
				dto.setCarTypeName("金杯");
				break;
			case 2:
				dto.setCarTypeName("小型平板");
				break;
			case 3:
				dto.setCarTypeName("中型平板");
				break;
			case 4:
				dto.setCarTypeName("小型厢货");
				break;
			case 5:
				dto.setCarTypeName("大型厢货");
				break;
			}
		}
			
	}
	
	private String checkTime(CarLineDTO carLineDTO)
	{
		String message =null;
		java.util.Date nowdate=new java.util.Date(); 
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    String dateString = formatter.format(nowdate); 
	    nowdate=DateUtil.formateDate(dateString, formatter);
		if(StringUtils.isNotEmpty(carLineDTO.getSendDateString())){
			 carLineDTO.setSentDate(DateUtil.formateDate(carLineDTO.getSendDateString(), formatter));
				if(carLineDTO.getSentDateType()!=null&&Integer.parseInt(carLineDTO.getSentDateType())!=4&&carLineDTO.getSentDate().getTime()==nowdate.getTime()){
					int sentD=Integer.parseInt(carLineDTO.getSentDateType());
					Date d = new Date();
					int hour =d.getHours();
					if(sentD==0){
						if(hour>12){
							message ="prompt1";//"发车时间不能早于当前时间,上午时间段是6:00~12:00";
						}
					}
					else if(sentD==1){
						if(hour>14){
							message ="prompt2";//"发车时间不能早于当前时间,中午时间段是12:00~14:00";
						}
					}
					else if(sentD==2){
						if(hour>18){
							message ="prompt3";//"发车时间不能早于当前时间,下午时间段是14:00~18:00";
						}
					}
			}
		}
		return message;
	}
	
	
	
 /*	*//**
	 * 根据检索条件，导出查询数据
	 *//*
	@RequestMapping("export")
	@ResponseBody
	public void export(String queryCarType,String mobile, String queryStartDate, String queryEndDate,String isDel, HttpServletResponse response) throws Exception {
		//获取模板文件路径
		String templatePath = gdProperties.getProperties().getProperty("CARLINE_TEMPLETE");
//		String srcFilePath = this.getClass().getResource("/" + templatePath).getPath(); // 存在编译后文件乱码问题，移至根目录下
		String srcFilePath = getRequest().getSession().getServletContext().getRealPath("/WEB-INF/" + templatePath);
		logger.info("template file path：" + srcFilePath);
		//目标文件名
		String destFileName = RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xls";
		// 设置响应
		response.setHeader("Content-Disposition", "attachment;filename=" + destFileName);
		response.setContentType("application/vnd.ms-excel");
		// 查询订单数据
		Map<String, Object> beans = new HashMap<String, Object>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
		map.put("carType", queryCarType);
		map.put("mobile", mobile);
		map.put("startDate", queryStartDate);
		map.put("endDate", queryEndDate);
		
		//用来判断已进行软删除的的字段，导出已删除数据用
		map.put("isDel", isDel);
		
		// list
		List<CarLineDTO> list = carLineManageService.queryCarLineList(map);
		convertPageList(list);
		
		beans.put("list", list);
		//获取输出流
		OutputStream out = response.getOutputStream();
		JxlsExcelUtil.exportReportFromAbsolutePath(srcFilePath, beans, out);
	}*/
	
	
}
