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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AreaSettingToolService;
import com.gudeng.commerce.gd.admin.service.AreaToolService;
import com.gudeng.commerce.gd.admin.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.admin.util.AreaUtil;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.EGoodsType;
import com.gudeng.commerce.gd.admin.util.EHundredweightType;
import com.gudeng.commerce.gd.admin.util.EOrderStatusType;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderCountDTO;
import com.gudeng.commerce.gd.customer.enums.MemberCertificationStatusEnum;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 农速通统计Controller
 * @author xiaojun
 */
@Controller
@RequestMapping("orderCount")
public class NstOrderCountController extends AdminBaseController{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(NstOrderCountController.class);
	
	@Autowired
	private NstOrderBaseinfoToolService nstOrderBaseinfoToolService;
	@Autowired
	private AreaToolService areaToolService;
	@Autowired
	private AreaSettingToolService areaSettingToolService;
	/**
	 * 干线订单跳转订单统计页面
	 * @param request
	 * @return
	 */
	@RequestMapping("index")
	public String publish(HttpServletRequest request){
		request(request);
		return "count/nstOrderCountList";
	}
	
	/**
	 * 同城订单统计页面
	 * @param request
	 * @return
	 */
	@RequestMapping("same_city_index")
	public String sameCityIndex(HttpServletRequest request){
		request(request);
		return "count/nstSameCityOrderCountList";
	}
	
	/**
	 * 获取区域和省份
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
	 * @param s_provinceId
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/getCity/{id}",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCity(@PathVariable("id") String s_provinceId,Map<String, Object> map){
		try {
			List<AreaDTO> cityList=areaToolService.findCity(s_provinceId);
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
	public String getArea(@PathVariable("id") String s_cityId,Map<String, Object> map){
		try {
			List<AreaDTO> areaList=areaToolService.findCounty(s_cityId);
			map.put("areaList", areaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(map);
	}
	/**
	 * 订单统计页面列表
	 * @param request
	 * @param nstOrderCountDTO
	 * @return
	 */
	@RequestMapping("nstOrderCountList")
	@ResponseBody
	public String nstOrderCountList(HttpServletRequest request,NstOrderCountDTO nstOrderCountDTO) {

		try {
			String type= request.getParameter("transportType");
		
			Map<String, Object> map = new HashMap<>();
			getMap(map,nstOrderCountDTO);
			//设定分页,排序
			setCommParameters(request, map);
			List<NstOrderCountDTO> list =null;
			//干线
			if ("0".equals(type)) {
				//设置总记录数
				map.put("total",nstOrderBaseinfoToolService.getOrderCountListTotal(map));	
			    list = nstOrderBaseinfoToolService.getOrderCountList(map);
			}
			//同城
			else if ("1".equals(type)) {
				map.put("total", nstOrderBaseinfoToolService.getSameCityOrderTotal(map));
				list = nstOrderBaseinfoToolService.getSameCityOrderList(map);
				if(list !=null && list.size()>0)
				{
					for (NstOrderCountDTO dto : list) {
						setAddress(dto);
					}
				}	
			}
			 map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("Exception :"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 展示订单详情
	 * @param orderNO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="showOrderDetail",produces={"application/json;charset=UTF-8"})
	public String showOrderDetail(@RequestParam("orderNo") String orderNo, @RequestParam("transportType") String transportType, Model model){
		try {
			NstOrderCountDTO orderDTO = null;
			if ("0".equals(transportType)) {
				 orderDTO=nstOrderBaseinfoToolService.getOrderCountDetailByOrderNo(orderNo);
			}
			//同城
			else if ("1".equals(transportType)) {
				orderDTO= nstOrderBaseinfoToolService.getSameCityOrderDetailByOrderNo(orderNo);
				setAddress(orderDTO);
			}
			orderDTO.setReceiveNstStatus(MemberCertificationStatusEnum.getValue(orderDTO.getReceiveNstStatus()));
			orderDTO.setReleaseNstStatus(MemberCertificationStatusEnum.getValue(orderDTO.getReleaseNstStatus()));
			model.addAttribute("orderDTO", orderDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "count/orderDetail";
	}
	/**
	 * 组装map
	 * @param map
	 * @param nstOrderCountDTO
	 */
	private void getMap(Map<String, Object> map,NstOrderCountDTO nstOrderCountDTO){
		map.put("orderNo", nstOrderCountDTO.getOrderNo());
		map.put("s_provinceId", nstOrderCountDTO.getS_provinceId());
		map.put("s_cityId", nstOrderCountDTO.getS_cityId());
		map.put("s_areaId", nstOrderCountDTO.getS_areaId());
		map.put("f_provinceId", nstOrderCountDTO.getF_provinceId());
		map.put("f_cityId", nstOrderCountDTO.getF_cityId());
		map.put("f_areaId", nstOrderCountDTO.getF_areaId());
		map.put("orderStatus", nstOrderCountDTO.getOrderStatus());
		map.put("orderBeginTime", nstOrderCountDTO.getOrderBeginTime());
		map.put("orderEndTime", nstOrderCountDTO.getOrderEndTime());
		map.put("order_completeBeginTime", nstOrderCountDTO.getOrder_completeBeginTime());
		map.put("order_completeEndTime", nstOrderCountDTO.getOrder_completeEndTime());
		map.put("receiveMobile", nstOrderCountDTO.getReceiveMobile());
		map.put("releaseMobile", nstOrderCountDTO.getReleaseMobile());
		map.put("releaseMobile_ed", nstOrderCountDTO.getReleaseMobile_ed());
		map.put("releaseAreaName", nstOrderCountDTO.getReleaseAreaName());
		map.put("receiveMobile_ed", nstOrderCountDTO.getReceiveMobile_ed());
		map.put("receiveAreaName", nstOrderCountDTO.getReceiveAreaName());
		
		if(StringUtils.isNotBlank(nstOrderCountDTO.getOrder_confirmBeginTime())){
			map.put("order_confirmBeginTime", CommonUtil.getStartOfDay(DateUtil.formateDate(nstOrderCountDTO.getOrder_confirmBeginTime())));
		}
		if(StringUtils.isNotBlank(nstOrderCountDTO.getOrder_confirmEndTime())){
			map.put("order_confirmEndTime", CommonUtil.getEndOfDay(DateUtil.formateDate(nstOrderCountDTO.getOrder_confirmEndTime())));
		}
	}
	
	@RequestMapping(value="checkExportParams", produces="application/json;charset=utf-8")
	@ResponseBody
	public String checkExportParams(HttpServletRequest request, NstOrderCountDTO nstOrderCountDTO){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String orderBeginTime = nstOrderCountDTO.getOrderBeginTime();
		String orderEndTime = nstOrderCountDTO.getOrderEndTime();
		if(StringUtils.isNotBlank(orderBeginTime) || StringUtils.isNotBlank(orderEndTime)){
			if (DateUtil.isDateIntervalOverFlow(orderBeginTime, orderEndTime, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		String order_completeBeginTime = nstOrderCountDTO.getOrder_completeBeginTime();
		String order_completeEndTime = nstOrderCountDTO.getOrder_completeEndTime();
		if(StringUtils.isNotBlank(order_completeBeginTime) || StringUtils.isNotBlank(order_completeEndTime)){
			if (DateUtil.isDateIntervalOverFlow(order_completeBeginTime, order_completeEndTime, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		String order_confirmBeginTime = nstOrderCountDTO.getOrder_confirmBeginTime();
		String order_confirmEndTime = nstOrderCountDTO.getOrder_confirmEndTime();
		if(StringUtils.isNotBlank(order_confirmBeginTime) || StringUtils.isNotBlank(order_confirmEndTime)){
			if (DateUtil.isDateIntervalOverFlow(order_confirmBeginTime, order_confirmEndTime, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		if(StringUtils.isBlank(orderBeginTime) && StringUtils.isBlank(orderEndTime)
				&& StringUtils.isBlank(order_completeBeginTime) && StringUtils.isBlank(order_completeEndTime)
				&& StringUtils.isBlank(order_confirmBeginTime) && StringUtils.isBlank(order_confirmEndTime)){
			resultMap.put("status", 0);
			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
			return JSONObject.toJSONString(resultMap);
		}
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			getMap(map, nstOrderCountDTO);
			String type= request.getParameter("transportType");
			if ("0".equals(type)) {
				long total = nstOrderBaseinfoToolService.getOrderCountListTotal(map);
				if (total > 10000){
					resultMap.put("status", 0);
					resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
					return JSONObject.toJSONString(resultMap);
				}
			}else if ("1".equals(type)){
				long total=nstOrderBaseinfoToolService.getSameCityOrdersTotal(map);
				if (total > 10000){
					resultMap.put("status", 0);
					resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
					return JSONObject.toJSONString(resultMap);
				}
			}
			
			
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("物流管理-订单统计导出参数检测异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request,HttpServletResponse response,NstOrderCountDTO nstOrderCountDTO){
		Map<String, Object> map = new HashMap<>();
		getMap(map,nstOrderCountDTO);
		map.put("startRow", 0);
		map.put("endRow", 99999999);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			String type= request.getParameter("transportType");
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("农速通订单统计表".getBytes(), "ISO-8859-1");
			String orderBeginTime = nstOrderCountDTO.getOrderBeginTime();
			String orderEndTime = nstOrderCountDTO.getOrderEndTime();
			String order_completeBeginTime = nstOrderCountDTO.getOrder_completeBeginTime();
			String order_completeEndTime = nstOrderCountDTO.getOrder_completeEndTime();
			if(StringUtils.isNotBlank(orderBeginTime) && StringUtils.isNotBlank(orderEndTime)){
				fileName = new String("农速通订单统计表".getBytes(), "ISO-8859-1")
					+DateUtil.formatStrDate(orderBeginTime, DateUtil.DATE_FORMAT_DATEONLYNOSP)+"_"
					+DateUtil.formatStrDate(orderEndTime, DateUtil.DATE_FORMAT_DATEONLYNOSP);
			}
			else if(StringUtils.isNotBlank(order_completeBeginTime) && StringUtils.isNotBlank(order_completeEndTime)){
				fileName = new String("农速通订单统计表".getBytes(), "ISO-8859-1")
					+DateUtil.formatStrDate(order_completeBeginTime, DateUtil.DATE_FORMAT_DATEONLYNOSP)+"_"
					+DateUtil.formatStrDate(order_completeEndTime, DateUtil.DATE_FORMAT_DATEONLYNOSP);
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			//查询数据
			List<NstOrderCountDTO> list = null;//nstOrderBaseinfoToolService.getOrderCountList(map);
			//干线
			if ("0".equals(type)) {
			    list = nstOrderBaseinfoToolService.getOrderCountList(map);
			}
			//同城
			else if ("1".equals(type)) {
				list = nstOrderBaseinfoToolService.getSameCityOrderList(map);
				if(list !=null && list.size()>0)
				{
					for (NstOrderCountDTO dto : list) {
						setAddress(dto);
					}
				}	
			}
			
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("订单统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "订单号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "起始地");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "目的地");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "订单类型");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "货物类型");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "重量 ");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "体积 ");// 填充第一行第七个单元格的内容
				Label label70 = new Label(7, 0, "车牌号");// 填充第一行第八个单元格的内容
				Label label80 = new Label(8, 0, "订单状态 ");// 填充第一行第九个单元格的内容
				Label label90 = new Label(9, 0, "接单时间 ");// 填充第一行第十个单元格的内容
				Label labe200 = new Label(10, 0, "订单确认时间");// 填充第一行第十一个单元格的内容
				Label labe210 = new Label(11, 0, "订单完成时间 ");// 填充第一行第十二个单元格的内容
				Label labe220 = new Label(12, 0, "发布者用户类型 ");// 填充第一行第十三个单元格的内容
				Label labe230 = new Label(13, 0, "接单者姓名");// 填充第一行第十四个单元格的内容
				Label labe240 = new Label(14, 0, "接单者电话");// 填充第一行第十五个单元格的内容
				Label labe250 = new Label(15, 0, "发布者姓名");// 填充第一行第十六个单元格的内容
				Label labe260 = new Label(16, 0, "发布者电话");// 填充第一行第十七个单元格的内容
				Label labe270 = new Label(17, 0, "发布者推荐姓名");// 填充第一行第十八个单元格的内容
				Label labe280 = new Label(18, 0, "发布者推荐人电话");// 填充第一行第十九个单元格的内容
				Label labe290 = new Label(19, 0, "发布者推荐人所属区域");// 填充第一行第二十个单元格的内容
				Label labe300 = new Label(20, 0, "接单者推荐人姓名");// 填充第一行第二十一个单元格的内容
				Label labe310 = new Label(21, 0, "接单者推荐人电话");// 填充第一行第二十二个单元格的内容
				Label labe320 = new Label(22, 0, "接单者推荐人所属区域");// 填充第一行第二十三个单元格的内容
				Label labe330 = new Label(23, 0, "接单者认证状态");// 填充第一行第二十二个单元格的内容
				Label labe340 = new Label(24, 0, "发布者认证状态");// 填充第一行第二十三个单元格的内容
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
				sheet.addCell(labe210);
				sheet.addCell(labe220);
				sheet.addCell(labe230);
				sheet.addCell(labe240);
				sheet.addCell(labe250);
				sheet.addCell(labe260);
				sheet.addCell(labe270);
				sheet.addCell(labe280);
				sheet.addCell(labe290);
				sheet.addCell(labe300);
				sheet.addCell(labe310);
				sheet.addCell(labe320);
				sheet.addCell(labe330);
				sheet.addCell(labe340);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						NstOrderCountDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getOrderNo());
						Label label1 = new Label(1, i + 1, dto.getF_address_detail());
						Label label2 = new Label(2, i + 1, dto.getS_address_detail());
						Label label3 = new Label(3, i + 1, dto.getOrderType());
						Label label4 = new Label(4, i + 1, dto.getGoodsType()!=null ?EGoodsType.getValueByCode(dto.getGoodsType()):"不限");
						Label label5 = new Label(5, i + 1, 
								dto.getTotalWeight()==null?
										"":
											dto.getTotalWeight()+EHundredweightType.getValueByCode(dto.getHundredweight()==null?0l:dto.getHundredweight()));
						Label label6 = new Label(6, i + 1, dto.getTotalSize()==null?"":dto.getTotalSize().toString());
						Label label7 = new Label(7, i + 1, dto.getCarNumber());
						Label label8 = new Label(8, i + 1, EOrderStatusType.getValueByCode(dto.getOrderStatus()));
						Label label9 = new Label(9, i + 1, dto.getOrderTime());
						Label labe20 = new Label(10, i + 1,dto.getOrder_confirmTime());
						Label labe21 = new Label(11, i + 1,dto.getOrder_completeTime());
						Label labe22 = new Label(12, i + 1,dto.getReleaseUserType());
						Label labe23 = new Label(13, i + 1,dto.getReceiveName());
						Label labe24 = new Label(14, i + 1,dto.getReceiveMobile());
						Label labe25 = new Label(15, i + 1,dto.getReleaseName());
						Label labe26 = new Label(16, i + 1,dto.getReleaseMobile());
						Label labe27 = new Label(17, i + 1,dto.getReleaseName_ed());
						Label labe28 = new Label(18, i + 1,dto.getReleaseMobile_ed());
						Label labe29 = new Label(19, i + 1,dto.getReleaseAreaName());
						Label labe30 = new Label(20, i + 1,dto.getReceiveName_ed());
						Label labe31 = new Label(21, i + 1,dto.getReceiveMobile_ed());
						Label labe32 = new Label(22, i + 1,dto.getReceiveAreaName());
						Label labe33 = new Label(23, i + 1,MemberCertificationStatusEnum.getValue(dto.getReceiveNstStatus()));
						Label labe34 = new Label(24, i + 1,MemberCertificationStatusEnum.getValue(dto.getReleaseNstStatus()));
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
						sheet.addCell(labe24);
						sheet.addCell(labe25);
						sheet.addCell(labe26);
						sheet.addCell(labe27);
						sheet.addCell(labe28);
						sheet.addCell(labe29);
						sheet.addCell(labe30);
						sheet.addCell(labe31);
						sheet.addCell(labe32);
						sheet.addCell(labe33);
						sheet.addCell(labe34);
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
	
	
	private void setAddress(NstOrderCountDTO dto) throws Exception {
		StringBuilder startPlace = new StringBuilder();
		//发货地
		if(StringUtils.isNotEmpty(dto.getS_provinceId())){
		AreaDTO province = areaToolService.getArea(dto.getS_provinceId());
		startPlace.append(province != null ? province.getArea() : "");
		AreaDTO city = areaToolService.getArea(dto.getS_cityId());
		//如果二级目录名称为市辖区，县，省直辖行政单位，不展示
		startPlace.append(AreaUtil.isCity(city)?" " +city.getArea() : "");
		AreaDTO area = areaToolService.getArea(dto.getS_areaId());
		startPlace.append(area != null ? " " + area.getArea() : "");
		}
		StringBuilder endPlace = new StringBuilder();
		//收货地
		if(StringUtils.isNotEmpty(dto.getF_provinceId())){
		AreaDTO e_province = areaToolService.getArea(dto.getF_provinceId());
		endPlace.append(e_province != null ? e_province.getArea() : "");
		AreaDTO e_city = areaToolService.getArea(dto.getF_cityId());
		endPlace.append(AreaUtil.isCity(e_city)?" " +e_city.getArea() : "");
		AreaDTO e_area = areaToolService.getArea(String.valueOf(dto.getF_areaId()));
		endPlace.append(e_area != null ? " " + e_area.getArea() : "");
		}
		
		//发货地（全部）
		dto.setF_address_detail(startPlace.toString()+dto.getF_address_detail());
		//收货地（全部）
		dto.setS_address_detail(endPlace.toString()+dto.getS_address_detail());
	}
	
	
}
