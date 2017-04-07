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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.admin.dto.SuppTelStatQueryBean;
import com.gudeng.commerce.gd.admin.service.CallstatiSticsToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.ECallSourceType;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 供应商电话记录统计
 * @author Ailen
 *
 */
@Controller
@RequestMapping("supptelstat")
public class SuppTelStatController extends AdminBaseController
{		
	@Autowired
	public CallstatiSticsToolService callstatiSticsService;
	
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(SuppTelStatController.class);
	
	/**
	 * demo
	 * @return
	 */
	@RequestMapping("")
	public String logitelstat(HttpServletRequest request){
		return "supptelstat/suppTelStat_list";
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
    public String queryByName(SuppTelStatQueryBean stsqb, HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(stsqb!=null) {
				map.put("e_Mobile", stsqb.getE_Mobile());
				map.put("shopName", stsqb.getShopName());
				if (StringUtil.isNotEmpty(stsqb.getStartDate())) {
					map.put("startDate", CommonUtil.getStartOfDay(DateUtil
							.formateDate(stsqb.getStartDate())));
				}
				if (StringUtil.isNotEmpty(stsqb.getEndDate())) {
					map.put("endDate", CommonUtil.getEndOfDay(DateUtil
							.formateDate(stsqb.getEndDate())));
				}
			}
			
			map.put("total", callstatiSticsService.getTotal3(map));

			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<CallstatiSticsDTO> list = callstatiSticsService.getBySearchForSupplier(map);
			for( CallstatiSticsDTO cas: list){
				cas.setSource(ECallSourceType.getValueByCode(cas.getSource()==null?"":cas.getSource()));
			}
			map.put("rows", list);//rows键 存放每页记录 list  
			String returnStr=JSONObject.toJSONString(map);
			return returnStr;
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return null;
	}
	
	@ResponseBody
    @RequestMapping(value = "checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)) {
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
                return JSONObject.toJSONString(resultMap);
            }
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("e_Mobile", request.getParameter("e_Mobile"));
			if (StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil
						.formateDate(startDate)));
			}
			if (StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil
						.formateDate(endDate)));
			}
			int total = callstatiSticsService.getTotal3(map);
            if (total > 10000) {
                resultMap.put("status", 0);
                resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
                return JSONObject.toJSONString(resultMap);
            }
            resultMap.put("status", 1);
            resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("供应商电话数据统计导出参数检测异常", e);
			resultMap.put("status", 0);
	        resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	/**
	 * @Description exportData 数据导出
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String e_Mobile) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("e_Mobile", e_Mobile);
		
		if (StringUtil.isNotEmpty(startDate)) {
			map.put("startDate", CommonUtil.getStartOfDay(DateUtil
					.formateDate(startDate)));
		}
		if (StringUtil.isNotEmpty(endDate)) {
			map.put("endDate", CommonUtil.getEndOfDay(DateUtil
					.formateDate(endDate)));
		}
		
		map.put("startRow", 0);
		map.put("endRow", 99999999);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			//设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + StringUtil.GenerateRandomStr() + ".xls");
			ouputStream = response.getOutputStream();
			
			//查询数据
			List<CallstatiSticsDTO> list = callstatiSticsService.getBySearchForSupplier(map);
			
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("农批电话数据统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "拨打来源");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "被叫号码");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "被叫客户类型");// 填充第一行第二个单元格的内容
				Label label30 = new Label(3, 0, "被叫号码姓名");// 填充第一行第三个单元格的内容
				Label label40 = new Label(4, 0, "主叫号码");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "主叫客户类型");// 填充第一行第二个单元格的内容
				Label label60 = new Label(6, 0, "主叫号码姓名");// 填充第一行第七个单元格的内容
				Label label70 = new Label(7, 0, "拨打时间");// 填充第一行第七个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				/***循环添加数据到工作簿***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						CallstatiSticsDTO callstatiSticsDTO = list.get(i);
						Label label0 = new Label(0, i + 1, ECallSourceType.getValueByCode(callstatiSticsDTO.getSource()==null?"":callstatiSticsDTO.getSource()));
						Label label1 = new Label(1, i + 1, callstatiSticsDTO.getS_Mobile());
//						Label label2 = new Label(2, i + 1, callstatiSticsDTO.getB_levelStr());//供应商拨打农批商的电话，被叫客户端，固定为‘农批商’
						Label label2 = new Label(2, i + 1, "农批商");
						Label label3 = new Label(3, i + 1, callstatiSticsDTO.getS_Name());
						Label label4 = new Label(4, i + 1, callstatiSticsDTO.getE_Mobile());
						Label label5 = new Label(5, i + 1, callstatiSticsDTO.getLevelStr());
						Label label6 = new Label(6, i + 1, callstatiSticsDTO.getE_Name());
						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						if(callstatiSticsDTO.getCreateTime()!=null)
						{
							Label label7 = new Label(7, i + 1, time.format(callstatiSticsDTO.getCreateTime()));
							sheet.addCell(label7);
						}
					}
				}
				wwb.write();//将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e1) {
			logger.trace(e1.getMessage());
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				logger.trace(e.getMessage());
			}
		}
		return null;
	}
}
