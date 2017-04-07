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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.QueryAreaService;
import com.gudeng.commerce.gd.admin.util.AreaUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.EOrderStatusType;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;

/**
 * 
 * 农速通订单
 * @author 
 *
 */
@Controller
@RequestMapping("nst_order")
public class NSTOrderController extends AdminBaseController
{
	@Autowired
	public NstOrderBaseinfoToolService nstOrderBaseinfoService;
	
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Autowired
	public QueryAreaService queryAreaService;
	/**
	 * 干线订单
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		return "nst_order/nst_order_list";
	}
	
	/**
	 * 同城订单
	 * @return
	 */
	@RequestMapping("index_list")
	public String _index(HttpServletRequest request){
		return "nst_order/nst_order_list_sameCity";
	}
	
	

	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping
	@ResponseBody
    public String updateStatus(@PathVariable("orderId") String orderId,HttpServletRequest request){      
		try {
			NstOrderBaseinfoDTO order=new NstOrderBaseinfoDTO();
			order.setOrderStatus(4);
			order.setId(Long.valueOf(orderId));
			int i=nstOrderBaseinfoService.updateNstOrderBaseinfoDTO(order);
			if(i>0){
				return "success";
			}else{
				return "failed";
			}
 		} catch (Exception e) {
			
		}
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
	@RequestMapping(value="queryByCondition" )
	@ResponseBody
    public String queryByCondition(HttpServletRequest request, HttpServletResponse response, String orderNo, String name1,  String mobile1,  String name2,  String mobile2, 
    		String isDeleted, String orderStatus,String startDate, String endDate, String transportType){      
		try {
			Map<String, Object> map = getCondition(orderNo, name1, mobile1,
					name2, mobile2, isDeleted ,orderStatus, startDate, endDate,transportType);
			
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<NstOrderBaseinfoDTO> list = null;//nstOrderBaseinfoService.getByCondition(map);
			
			//干线
			if ("0".equals(transportType)) {
				//设置总记录数
				map.put("total", nstOrderBaseinfoService.getTotal(map));
			    list = nstOrderBaseinfoService.getByCondition(map);
			}
			//同城
			else if ("1".equals(transportType)) {
				map.put("total", nstOrderBaseinfoService.getSameCityOrdersTotal(map));
				list = nstOrderBaseinfoService.getSameCityOrdersByCondition(map);
				if(list !=null && list.size()>0)
				{
					for (NstOrderBaseinfoDTO nstOrderBaseinfoDTO : list) {
						setAddress(nstOrderBaseinfoDTO);
					}
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


	private Map<String, Object> getCondition(String orderNo, String name1,
			String mobile1, String name2, String mobile2,String isDeleted , String orderStatus,
			String startDate, String endDate , String transportType) throws Exception {
		// 设置查询参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shipperName", name1);
		map.put("shipperMobile", mobile1);
		map.put("ownerName", name2);
		map.put("ownerMobile", mobile2);
		map.put("orderNo", orderNo);
		map.put("isDeleted", isDeleted);
		map.put("orderStatus", orderStatus);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		//干线或同城
		map.put("transportType", transportType);
		return map;
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
			String orderNo = request.getParameter("orderNo");
			String name1 = request.getParameter("name1");
			String mobile1 = request.getParameter("mobile1");
			String name2 = request.getParameter("name2");
			String mobile2 = request.getParameter("mobile2");
			String isDeleted = request.getParameter("isDeleted");
			String orderStatus = request.getParameter("orderStatus");
			String transportType=request.getParameter("transportType");
			Map<String, Object> map = getCondition(orderNo, name1, mobile1,
					name2, mobile2, isDeleted ,orderStatus, startDate, endDate,transportType);
			int total = nstOrderBaseinfoService.getTotal(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
//			logger.info("物流管理-订单导出参数检测异常", e);
			e.printStackTrace();
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param orderNo
	 * @param name1
	 * @param mobile1
	 * @param name2
	 * @param mobile2
	 * @param orderStatus
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String orderNo, String name1,  String mobile1,  String name2,  String mobile2,
			String isDeleted ,String orderStatus,String startDate, String endDate , String transportType) {
		List<NstOrderBaseinfoDTO> list =null;

		try{
			Map<String, Object> map = getCondition(orderNo, name1, mobile1,
					name2, mobile2, isDeleted ,orderStatus, startDate, endDate,transportType);
			map.put("startRow", 0);
			map.put("endRow", 99999999);
		    //list = nstOrderBaseinfoService.getByCondition(map);
			//干线
			if ("0".equals(transportType)) {
			    list = nstOrderBaseinfoService.getByCondition(map);
			}
			//同城
			else if ("1".equals(transportType)) {
				list = nstOrderBaseinfoService.getSameCityOrdersByCondition(map);
				if(list !=null && list.size()>0)
				{
					for (NstOrderBaseinfoDTO nstOrderBaseinfoDTO : list) {
						setAddress(nstOrderBaseinfoDTO);
					}
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
			String fileName  = new String("农速通订单列表".getBytes(), "ISO8859-1")+startDate+"_"+endDate;
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("农速通订单列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "运单号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "起运地 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "目的地 ");// 填充第一行第四个单元格的内容
				Label label30 = new Label(3, 0, "发运人");// 填充第一行第五个单元格的内容
				Label label40 = new Label(4, 0, "发运人手机号码");// 填充第一行第七个单元格的内容
				Label label50 = new Label(5, 0, "承运人");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "承运人手机号码 ");// 填充第一行第六个单元格的内容
				Label label70 = new Label(7, 0, "订单状态 ");//
				Label label80 = new Label(8, 0, "接单时间");//
				Label label90 = new Label(9, 0, "是否删除");//
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);// 将单元格加入表格
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(label90);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						NstOrderBaseinfoDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getOrderNo());
						Label label1 = new Label(1, i + 1, dto.getF_address_detail());
						Label label2 = new Label(2, i + 1, dto.getS_address_detail());
						Label label3 = new Label(3, i + 1,  dto.getShipperName());
						Label label4 = new Label(4, i + 1,  dto.getShipperMobile());
						Label label5 = new Label(5, i + 1,  dto.getOwnerName());
						Label label6 = new Label(6, i + 1,  dto.getOwnerMobile());
						//1.待成交，2.已成交，3.未完成，4.已完成
						Label label7 = new Label(7, i + 1,  EOrderStatusType.getValueByCode(dto.getOrderStatus()));
						Label label8 = new Label(8, i + 1,  DateUtil.toString(dto.getOrderTime(),
								DateUtil.DATE_FORMAT_DATETIME));
						Label label9 = new Label(9, i + 1,  "1".equals(dto.getIsDeleted())?"已删除":"未删除");
						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);// 将单元格加入表格
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
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
	
	
	private void setAddress(NstOrderBaseinfoDTO dto) throws Exception {
		StringBuilder startPlace = new StringBuilder();
		//发货地
		if(dto.getS_provinceId() != null && dto.getS_provinceId() >0){
		AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId()));
		startPlace.append(province != null ? province.getArea() : "");
		AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId()));
		//如果二级目录名称为市辖区，县，省直辖行政单位，不展示
		startPlace.append(AreaUtil.isCity(city)?" " +city.getArea() : "");
		AreaDTO area = queryAreaService.getArea(String.valueOf(dto.getS_areaId()));
		startPlace.append(area != null ? " " + area.getArea() : "");
		}
		//收货地
		StringBuilder endPlace = new StringBuilder();
		if(dto.getF_provinceId() != null && dto.getF_provinceId() >0){
		AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getF_provinceId()));
		endPlace.append(e_province != null ? e_province.getArea() : "");
		AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getF_cityId()));
		endPlace.append(AreaUtil.isCity(e_city)?" " +e_city.getArea() : "");
		AreaDTO e_area = queryAreaService.getArea(String.valueOf(dto.getF_areaId()));
		endPlace.append(e_area != null ? " " + e_area.getArea() : "");
		}
		// 发货地（全部）
		dto.setF_address_detail(startPlace.toString()+dto.getS_detail());
		// 收货地（全部）
		dto.setS_address_detail(endPlace.toString()+dto.getF_detail());
	}
	
}
