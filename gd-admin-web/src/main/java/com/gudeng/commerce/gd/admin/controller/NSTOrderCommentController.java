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
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderCommentDTO;

/**
 * 
 * 农速通订单评论
 * @author 
 *
 */
@Controller
@RequestMapping("nst_order_comment")
public class NSTOrderCommentController extends AdminBaseController
{
	@Autowired
	public NstOrderBaseinfoToolService nstOrderBaseinfoService;
	
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		return "nst_order/order_comment_list";
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
    public String queryByCondition(HttpServletRequest request, HttpServletResponse response, String orderNo, String name1,  String mobile1,  String name2,  String mobile2, String orderStatus , String evaluateType , String startDate, String endDate){      
		try {
			Map<String, Object> map = getCondition(orderNo, name1, mobile1,
					name2, mobile2, orderStatus, evaluateType, startDate, endDate);
			map.put("total", nstOrderBaseinfoService.getCommentTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<NstOrderBaseinfoDTO> list = nstOrderBaseinfoService.getCommentListByCondition(map);
			if (list != null && list.size() > 0) {
				for (NstOrderBaseinfoDTO dto : list) {
					   dto.setEvaluateType(getEvaluateType(dto.getEvaluateType()));
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
			String mobile1, String name2, String mobile2, String orderStatus, String evaluateType,
			String startDate, String endDate) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("shipperName", name1);
		map.put("shipperMobile", mobile1);
		map.put("ownerName", name2);
		map.put("ownerMobile", mobile2);
		
		map.put("orderNo", orderNo);
		map.put("orderStatus", orderStatus);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("evaluateType", evaluateType);
		return map;
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
			String orderNo = request.getParameter("orderNo");
			String name1 = request.getParameter("name1");
			String mobile1 = request.getParameter("mobile1");
			String name2 = request.getParameter("name2");
			String mobile2 = request.getParameter("mobile2");
			String orderStatus = request.getParameter("orderStatus");
			String evaluateType = request.getParameter("evaluateType");
			Map<String, Object> map = getCondition(orderNo, name1, mobile1,
					name2, mobile2, orderStatus, evaluateType, startDate, endDate);
			int total = nstOrderBaseinfoService.getCommentTotal(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
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
	public String exportData(HttpServletRequest request, HttpServletResponse response, String orderNo, String name1,  String mobile1,  String name2,  String mobile2, String orderStatus, String evaluateType , String startDate, String endDate) {
		List<NstOrderBaseinfoDTO> list =null;

		try{
			Map<String, Object> map = getCondition(orderNo, name1, mobile1,
					name2, mobile2, orderStatus, evaluateType, startDate, endDate);
			map.put("startRow", 0);
			map.put("endRow", 99999999);
		    list = nstOrderBaseinfoService.getCommentListByCondition(map);
 			
		}catch(Exception e){
			e.printStackTrace();
		}
	
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = new String("农速通订单评价列表".getBytes(), "ISO8859-1")+startDate+"_"+endDate;
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("农速通订单评价列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
				Label label90 = new Label(9, 0, "评价");//
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
						Label label7 = new Label(7, i + 1,  getStatus(String.valueOf(dto.getOrderStatus())));
						Label label8 = new Label(8, i + 1,  DateUtil.toString(dto.getOrderTime(),
								DateUtil.DATE_FORMAT_DATETIME));
						Label label9 = new Label(9, i + 1,  getEvaluateType(dto.getEvaluateType()));

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
	
	
	@RequestMapping(value="showById/{id}")
    public String showById(@PathVariable("id") String id, HttpServletRequest request){      
		try {
			NstOrderCommentDTO dto=nstOrderBaseinfoService.getById(id);
			dto.setCreateTimeString(DateUtil.toString(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
		    dto.setComment(dto.getComment()!=null?dto.getComment().replaceFirst(";", " "):"");
			putModel("dto",dto); 
			return "nst_order/view_order_comment";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	private String getEvaluateType(String evaluateType) {
		String type = null;
		if ("1".equals(evaluateType)) {
			type = "好评";
		} else if ("2".equals(evaluateType)) {
			type = "差评";
		} else{
			type = "未评价";
		}
		return type;
	}

}
