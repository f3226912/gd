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
import com.gudeng.commerce.gd.admin.service.FinanceCreditToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.customer.entity.FinanceCreditEntity;
import com.gudeng.commerce.gd.order.entity.EnPostLogEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
/**
 * 贷款信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping("financeCredit")
public class FinanceCreditController extends AdminBaseController {
    /** 记录日志 */
    @SuppressWarnings("unused")
	private static final GdLogger logger = GdLoggerFactory.getLogger(FinanceCreditController.class);

	@Autowired
	private FinanceCreditToolService financeCreditToolService;
	

	/**
	 * 干线列表页面
	 * @return
	 */
	@RequestMapping("")
	public String index(HttpServletRequest request){
		return "financeCredit/financeCreditList";
	}
	
	
	
	/**
	 * 查询贷款信息表列表
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String financeCreditRequestList(HttpServletRequest request,FinanceCreditEntity financeCreditEntity) {
		try{
	    Map<String, Object> map = new HashMap<>();
	    map.put("marketId", request.getParameter("marketId"));
	    map.put("memberAccount", request.getParameter("memberAccount"));
	    map.put("startOrderAmount", request.getParameter("startOrderAmount"));
	    map.put("endOrderAmount", request.getParameter("endOrderAmount"));
	    map.put("startDate", request.getParameter("startDate"));
	    map.put("endDate", request.getParameter("endDate"));
		map.put("userStar",request.getParameter("userStar"));
		map.put("creditQuotaRange",request.getParameter("creditQuotaRange"));
		map.put("total", financeCreditToolService.getTotal(map));
		//设定分页,排序
		setCommParameters(request, map);
		List<FinanceCreditEntity> list = financeCreditToolService.getListByConditionPage(map);
		map.put("rows", list);//rows键 存放每页记录 list  
		return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.info("查询贷款信息表出错： "+e.getMessage());
		}
		return null;
	}
	
	
	
	/**
     * 查询贷款信息表列表
     * @param request
     * @return
     */
    @RequestMapping("financeCreditDetail/{financeCreditId}")
    public String financeCreditDetail(@PathVariable("financeCreditId") String persaleId, ModelMap map) {
        try{
          Map<String, Object> newMap = new HashMap<String, Object>();
          newMap.put("id", persaleId);
          FinanceCreditEntity financeCreditEntity= financeCreditToolService.getById(newMap);
         
          map.put("CreateTime",  DateUtil.toString(financeCreditEntity.getCreateTime(),
					DateUtil.DATE_FORMAT_DATETIME));
          map.put("financeCreditEntity", financeCreditEntity);
        } catch (Exception e) {
            logger.info("查询贷款信息表出错： "+e.getMessage());
        }
        return "financeCredit/financeCreditDetail_detail";
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
			    map.put("marketId", request.getParameter("marketId"));
			    map.put("memberAccount", request.getParameter("memberAccount"));
			    map.put("startOrderAmount", request.getParameter("startOrderAmount"));
			    map.put("endOrderAmount", request.getParameter("endOrderAmount"));
			    map.put("startDate", request.getParameter("startDate"));
			    map.put("endDate", request.getParameter("endDate"));
				map.put("state",request.getParameter("state"));
				map.put("creditQuotaRange",request.getParameter("creditQuotaRange"));
			int total = financeCreditToolService.getTotal(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("贷款信息表导出参数检测异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	

	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request,EnPostLogEntity enPostLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
	    map.put("marketId", request.getParameter("marketId"));
	    map.put("memberAccount", request.getParameter("memberAccount"));
	    map.put("startOrderAmount", request.getParameter("startOrderAmount"));
	    map.put("endOrderAmount", request.getParameter("endOrderAmount"));
	    map.put("startDate", request.getParameter("startDate"));
	    map.put("endDate", request.getParameter("endDate"));
		map.put("state",request.getParameter("state"));
		map.put("creditQuotaRange",request.getParameter("creditQuotaRange"));
		map.put("startRow", 0);
		map.put("endRow", 100000000);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("贷款信息".getBytes(), "ISO-8859-1")+request.getParameter("endDate")+"_"+request.getParameter("endDate");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据list
			List<FinanceCreditEntity> list = financeCreditToolService.getListByConditionPage(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("贷款信息", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "ID");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "用户账号 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "所属市场");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "订单总交易额");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "用户星级");// 
				Label label50 = new Label(5, 0, "贷款额度");// 
				Label label60 = new Label(6, 0, "申请日期");// 填充第一行第五个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				//*** 循环添加数据到工作簿 ***//*
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						FinanceCreditEntity financeCreditEntity= list.get(i);
						/*
						memberAccount  marketName  orderAmount  userStar creditQuotaRange  createTime 
						*/
						Label label0 = new Label(0, i + 1, String.valueOf(financeCreditEntity.getId()));
						Label label1 = new Label(1, i + 1, String.valueOf(financeCreditEntity.getMemberAccount()));
						Label label2 = new Label(2, i + 1, String.valueOf(financeCreditEntity.getMarketName()));
						Label label3 = new Label(3, i + 1, String.valueOf(financeCreditEntity.getOrderAmount()));
						String UserStar=null;
						if("1".equals(financeCreditEntity.getUserStar())){
							UserStar="一星";
						}
						if("2".equals(financeCreditEntity.getUserStar())){
							UserStar="二星";
						}
						if("3".equals(financeCreditEntity.getUserStar())){
							UserStar="三星";
						}
						if("4".equals(financeCreditEntity.getUserStar())){
							UserStar="四星";
						}
						if("5".equals(financeCreditEntity.getUserStar())){
							UserStar="五星";
						}
						Label label4 = new Label(4, i + 1, String.valueOf(UserStar));
						String CreditQuotaRange=null;
						if("1".equals(financeCreditEntity.getCreditQuotaRange())){
							CreditQuotaRange="1——5万";
						}
						if("2".equals(financeCreditEntity.getCreditQuotaRange())){
							CreditQuotaRange="10——50万";
						}
						if("3".equals(financeCreditEntity.getCreditQuotaRange())){
							CreditQuotaRange="50——100万";
						}
						if("4".equals(financeCreditEntity.getCreditQuotaRange())){
							CreditQuotaRange="100——300万";
						}
						Label label5 = new Label(5, i + 1, String.valueOf(CreditQuotaRange));
						
						Label label6 = new Label(6, i + 1, DateUtil.toString(financeCreditEntity.getCreateTime(),
								DateUtil.DATE_FORMAT_DATETIME));
						sheet.addCell(label0);//将单元格加入表格
						sheet.addCell(label1);// 
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
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


