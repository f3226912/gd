package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.EnPostLogManageService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.order.entity.EnPostLogEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("enPostLog")
public class EnPostLogController extends AdminBaseController {
    /** 记录日志 */
    @SuppressWarnings("unused")
	private static final GdLogger logger = GdLoggerFactory.getLogger(EnPostLogController.class);

	@Autowired
	private EnPostLogManageService enPostLogManageService;
	

	/**
	 * 干线列表页面
	 * @return
	 */
	@RequestMapping("")
	public String index(HttpServletRequest request){
		return "enPostLog/enPostLogList";
	}
	
	
	
	/**
	 * 查询提现信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String enPostLogRequestList(HttpServletRequest request,EnPostLogEntity enPostLog) {
		try{
	    Map<String, Object> map = new HashMap<>();
	    map.put("transseqno", request.getParameter("transseqno"));
	    map.put("startDate", request.getParameter("startDate"));
	    map.put("endDate", request.getParameter("endDate"));
		map.put("state",request.getParameter("state"));
		map.put("total", enPostLogManageService.getTotal(map));
		//设定分页,排序
		setCommParameters(request, map);
		List<EnPostLogEntity> list = enPostLogManageService.getByCondition(map);
		map.put("rows", list);//rows键 存放每页记录 list  
		return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.info("查询订单日志出错： "+e.getMessage());
		}
		return null;
	}
	
	
	
	/**
     * 查询提现信息列表
     * @param request
     * @return
     */
    @RequestMapping("enPostLogDetail/{enPostLogId}")
    public String enPostLogDetail(@PathVariable("enPostLogId") String persaleId, ModelMap map) {
        try{
          Map<String, Object> newMap = new HashMap<String, Object>();
          newMap.put("ID", persaleId);
          EnPostLogEntity enPostLogEntity= enPostLogManageService.getById(newMap);
          map.put("enPostLogEntity", enPostLogEntity);
        } catch (Exception e) {
            logger.info("查询订单日志出错： "+e.getMessage());
        }
        return "enPostLog/enPostLog_detail";
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
			   map.put("transseqno", request.getParameter("transseqno"));
			    map.put("startDate", request.getParameter("startDate"));
			    map.put("endDate", request.getParameter("endDate"));
			    map.put("state",request.getParameter("state"));
			int total = enPostLogManageService.getTotal(map);
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
	public String exportData(HttpServletRequest request,EnPostLogEntity enPostLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
	    map.put("transseqno", request.getParameter("transseqno"));
	    map.put("startDate", request.getParameter("startDate"));
	    map.put("endDate", request.getParameter("endDate"));
	    map.put("state",request.getParameter("state"));
		map.put("startRow", 0);
		map.put("endRow", 100000000);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("订单日志信息".getBytes(), "ISO-8859-1")+request.getParameter("endDate")+"_"+request.getParameter("endDate");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据list
			List<EnPostLogEntity> list = enPostLogManageService.getByCondition(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("订单日志信息", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "ID");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "订单号 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "交易类型");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "POS终端号");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "支付金额 ");// 
				Label label50 = new Label(5, 0, "付款卡号");// 
				Label label60 = new Label(6, 0, "交易日期");// 填充第一行第五个单元格的内容
				Label label70 = new Label(7, 0, "交易时间 ");//
				Label label80 = new Label(8, 0, "交易流水号 ");//
				Label label90 = new Label(9, 0, "状态 ");//
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
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						EnPostLogEntity enPostLogV= list.get(i);
						Label label0 = new Label(0, i + 1, String.valueOf(enPostLogV.getID()));
						Label label1 = new Label(1, i + 1, String.valueOf(enPostLogV.getOrderno()));
						String Transype="";
						if("0".equals(enPostLogV.getTransype())){
							Transype= "订单支付";
						}else if("1".equals(enPostLogV.getTransype())){
							Transype= "刷卡消费";
						}
						Label label2 = new Label(2, i + 1, Transype);
						Label label3 = new Label(3, i + 1, String.valueOf(enPostLogV.getMachinenum()));
						Label label4 = new Label(4, i + 1, String.valueOf(enPostLogV.getPayfee()));
						Label label5 = new Label(5, i + 1, String.valueOf(enPostLogV.getPaycardno()));
						Label label6 = new Label(6, i + 1, DateUtil.toString(enPostLogV.getTransdate(),
								DateUtil.DATE_FORMAT_DATEONLYED));
						Label label7 = new Label(7, i + 1,  DateUtil.toString(enPostLogV.getTranstime(),
								DateUtil.DATE_FORMAT_DATETIME));
						Label label8 = new Label(8, i + 1, String.valueOf(enPostLogV.getTransseqno()));
						String StateName="";
						if(enPostLogV.getState()==1){
						  StateName= "请求原文";
						}else if(enPostLogV.getState()==2){
						  StateName= "请求成功";
						}else if(enPostLogV.getState()==3){
						  StateName= "请求失败(无对应数据)";
						}
						Label label9 = new Label(9, i + 1, StateName);
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


