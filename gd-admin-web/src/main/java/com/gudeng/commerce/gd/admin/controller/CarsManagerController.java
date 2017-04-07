package com.gudeng.commerce.gd.admin.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.CarsManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.QueryAreaService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.JsonConvertUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;


/**
 * 车辆管理
 * @author xiaodong
 */
@Controller
@RequestMapping("cars")
public class CarsManagerController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(CarsManagerController.class);
	
	@Autowired
	public CarsManageService carsManageService;
	
	@Autowired
	public QueryAreaService queryAreaService;
	
	@Autowired
	public MemberBaseinfoToolService  memberBaseinfoToolService;
	

	/**
	 * 干线列表页面
	 * @return
	 */
	@RequestMapping("")
	public String index(HttpServletRequest request){
		return "cars/carsList";
	}
	
	

	/**
	 * 同城列表页面
	 * @return
	 */
	@RequestMapping("index_list")
	public String index_list(HttpServletRequest request){
		return "cars/same_city_cars_list";
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
			String id=request.getParameter("id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("transportType", request.getParameter("transportType"));
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
					setUserTypeName(dto);
					dto.setCreateTimeString(DateUtil.toString(dto.getCreateTime(),
							DateUtil.DATE_FORMAT_DATETIME));

				}
			}
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private void setUserTypeName(CarsDTO dto) throws Exception {
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



    /**
     * @param carType
     * @param carNumber
     * @param phone
     * @param request
     * @return
     */
	@RequestMapping(value="queryByParameters" )
	@ResponseBody
    public String queryByParameters(@RequestParam String carType,@RequestParam String carNumber,@RequestParam String phone, @RequestParam String queryType,
    		@RequestParam String startDate,@RequestParam String endDate, @RequestParam String transportType, HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("carType", request.getParameter("carType"));
			map.put("carNumber", request.getParameter("carNumber"));
			map.put("phone", request.getParameter("phone"));
			map.put("userType", queryType);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			//干线或同城
			map.put("transportType", transportType);

			//记录数
			map.put("total", carsManageService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<CarsDTO> list = carsManageService.queryByParameters(map);
			if(list !=null && list.size() >0)
			{
				for(CarsDTO dto : list)
				{
					setCarTypeName(dto);
					setUserTypeName(dto);
					dto.setCreateTimeString(DateUtil.toString(dto.getCreateTime(),
							DateUtil.DATE_FORMAT_DATETIME));
				}
			}
			map.put("rows", list);//rows键 存放每页记录 list  
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
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
			logger.info("删除车辆操作 id="+id);
			if(null==id || id.equals("")){
			return "faild";
			}
			carsManageService.deleteById(id);
			logger.info("删除车辆操作 id="+id + " 成功！");
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
	public String addDto(HttpServletRequest request, Model model){
		String transportType = request.getParameter("transportType");
		model.addAttribute("transportType", transportType);
		return "cars/addCar";
	}
	
	
	
	/**
	 * 	  增加修改同一个方法
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save" )
	@ResponseBody
    public String saveOrUpdate( HttpServletRequest request){      
		try {
			
			String carNumber=request.getParameter("number");
			CarsDTO dto=new CarsDTO();
			dto.setCarNumber(carNumber);
			String transportType = request.getParameter("transportType");
			dto.setTransportType(Integer.parseInt((transportType==null?"0":transportType)));
			if(dto.getTransportType()==0){
				dto.setCarType(request.getParameter("carType"));
			}else{
				dto.setTransportCarType(Integer.parseInt(request.getParameter("carType")));
			}
			dto.setMaxLoad(StringUtil.isNotEmpty(request.getParameter("maxLoad"))
					?Double.parseDouble(request.getParameter("maxLoad")):0d);
			dto.setCarLength(StringUtil.isNotEmpty(request.getParameter("carLength"))?Double.parseDouble(request.getParameter("carLength")):0.0);
			String mobile =request.getParameter("phone");
			MemberBaseinfoDTO member =memberBaseinfoToolService.getByMobile(mobile);
			if(member !=null)
			{
				dto.setUserId(member.getMemberId());
			}else
			{
			  return "not exist";
			}
			dto.setPhone(mobile);
//			String entUserId =request.getParameter("memberId");
//			dto.setEntUserId(StringUtil.isNotEmpty(entUserId)?Long.parseLong(entUserId) :0);
			String id=request.getParameter("id");
			CarsDTO car= carsManageService.getById(id);
			int i=0;
			//根据id判断是否存在，存在即为更新，不存在即为增加
			if(car != null){
				dto.setId(car.getId());
				dto.setUpdateTimeString(DateUtil.getSysDateTimeString());
				i=carsManageService.updateCarsDTO(dto);
			}else{
				CarsDTO carsDTO=carsManageService.selectCarByCarNumber(carNumber);
				if (carsDTO!=null) {
					return "carNumberExist";
				}
				i=carsManageService.addCarsDTO(dto);
			}
			if(i>0){
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
			CarsDTO dto=carsManageService.getById(id);
			if (dto !=null) {
				MemberBaseinfoDTO member =memberBaseinfoToolService.getByMobile(dto.getPhone());
				putModel("id",dto.getId());
				putModel("carNumber",dto.getCarNumber());
				putModel("carType",dto.getCarType());
				putModel("carLength",dto.getCarLength());
				putModel("maxLoad",dto.getMaxLoad());
				putModel("phone",dto.getPhone());
				putModel("userName",member!=null ?member.getRealName() :"");
				putModel("transportType",dto.getTransportType());
				putModel("transportCarType",dto.getTransportCarType());
				return "cars/addCar";
			}else{
				return null;
			}
			
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
		List <?> list=queryAreaService.findProvince();
		this.jsonUtil(list ,response);
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
	
	
	// 查询会员信息
		@RequestMapping(value = "queryMemberByMobile")
		@ResponseBody
		public MemberBaseinfoDTO queryMemberByMobile(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String mobile = request.getParameter("userMobile");
			MemberBaseinfoDTO member  = memberBaseinfoToolService.getByMobile(mobile);
			return member;
		}

	

	/**
	 * @Description entSelect 进入选择企业页面
	 * @return
	 * @CreationDate 2015年11月11日 下午8:10:49
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@RequestMapping("entList")
	public String entSelect() {
		return "cars/entList";
	}

	/**
	 * @Description queryEntList 查询企业列表
	 * @param request
	 * @return
	 * @CreationDate 2015年11月11日 下午8:12:41
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@RequestMapping("queryEnt")
	@ResponseBody
	public String queryEntList(HttpServletRequest request) {
		try {
			String companyName = getRequest().getParameter("companyName");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("companyName", companyName);
			// 设置查询参数
			// 记录数
			map.put("total", memberBaseinfoToolService.getCompanyByConditionTotal(map));
			// //设定分页,排序
			setCommParameters(request, map);
			// list
			List<MemberBaseinfoDTO> list = memberBaseinfoToolService.getCompanyByCondition(map);
			if (list != null && list.size() > 0) {
				Iterator<MemberBaseinfoDTO> iterator = list.iterator();
				while (iterator.hasNext()) {
					MemberBaseinfoDTO dto = iterator.next();
					if (StringUtils.isEmpty(dto.getCompanyName())) {
						list.remove(dto);
					}
				}
			}
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 新增路线中查询车辆列表页面
	 * @return
	 */
	@RequestMapping("carList")
	public String carSelect() {
		return "cars/memberList";
	}
	
	
	@ResponseBody
	@RequestMapping("queryMemberList")
	public String query(HttpServletRequest request, @RequestParam String  status,  @RequestParam String account){
		try {
			String id = request.getParameter("id");
            
			Map<String, Object> map = new HashMap<String, Object>();
			if(null==id || id.equals("")){
			}else{
				map.put("id", id);
			}
			// 设置查询参数
			map.put("status", status);
			map.put("account", account);
			//记录数
			map.put("total", memberBaseinfoToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<MemberBaseinfoDTO> list = memberBaseinfoToolService.getBySearch(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			
		}
		return null;
	}
	

	private void jsonUtil(Object list ,HttpServletResponse response) throws Exception{
			logger.info("JSON格式：" + list.toString());
			String returnJson = JsonConvertUtil.returnJson(list);
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(returnJson);
	}
	
	private void setCarTypeName(CarsDTO dto) {
//		  <!-- 数据库值 0:厢式车;1:敞车;2:冷藏车;3:保温车;4:其他;5:高栏车6:平板车;7:活鲜水车;8:集装箱 -->
		if (null == dto.getTransportType() || 0 == dto.getTransportType()) {
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
			}
		} else {
			if (dto.getTransportCarType() != null) {
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
	}
	
	@RequestMapping(value="checkExportParams", produces="application/json;charset=utf-8")
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
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("carType", request.getParameter("carType"));
			map.put("carNumber", request.getParameter("carNumber"));
			map.put("phone", request.getParameter("phone"));
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("transportType", request.getParameter("transportType"));
			int total = memberBaseinfoToolService.getCompanyByConditionTotal(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("物流管理-车辆信息导出参数检测异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, @RequestParam String carType,@RequestParam String carNumber,@RequestParam String phone ,
			@RequestParam String startDate,@RequestParam String endDate, @RequestParam String transportType) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
		map.put("carType", request.getParameter("carType"));
		map.put("carNumber", request.getParameter("carNumber"));
		map.put("phone", request.getParameter("phone"));
		map.put("userType", request.getParameter("queryType"));
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("transportType", transportType);
		map.put("startRow", 0);
		map.put("endRow", 100000000);
	
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("车辆信息表".getBytes(), "ISO-8859-1")+startDate+"_"+endDate;
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据list
			List<CarsDTO> list = carsManageService.queryByParameters(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("路線信息", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						CarsDTO carDTO = list.get(i);
						setCarTypeName(carDTO);
						setUserTypeName(carDTO);
						carDTO.setCreateTimeString(DateUtil.toString(carDTO.getCreateTime(),
								DateUtil.DATE_FORMAT_DATETIME));
						
						Label label0 = new Label(0, i + 1, carDTO.getCarNumber());
						Label label1 = new Label(1, i + 1, carDTO.getCarTypeName());
						Label label2 = new Label(2, i + 1, String.valueOf(carDTO.getMaxLoad()));
						Label label3 = new Label(3, i + 1, String.valueOf(carDTO.getCarLength()));
						Label label4 = new Label(4, i + 1, carDTO.getUserTypeName());
						Label label5 = new Label(5, i + 1, carDTO.getCompanyName());
						Label label6 = new Label(6, i + 1, carDTO.getNickName());
						Label label7 = new Label(7, i + 1, carDTO.getPhone());
						Label label8 = new Label(8, i + 1, carDTO.getCreateTimeString());
						sheet.addCell(label0);//将单元格加入表格
						sheet.addCell(label1);// 
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
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
	
	
}
