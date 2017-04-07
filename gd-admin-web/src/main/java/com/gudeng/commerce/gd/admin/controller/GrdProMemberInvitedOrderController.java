package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.GrdGiftDetailToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MarketBerthDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.util.ExcelUtil;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 促成订单列表contorller
 * @author caixu
 *
 */
@Controller
public class GrdProMemberInvitedOrderController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdProMemberInvitedOrderController.class);

	@Autowired
	private GrdGiftDetailToolService grdGiftDetailService;

	/**
	 * 配置文件
	 */
	@Autowired
	public GdProperties gdProperties;
	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProMemberInvitedOrder/grd_order")
	public String list(HttpServletRequest request) {
		return "grdProMemberInvitedOrder/grd_order";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdProMemberInvitedOrder/query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("market", request.getParameter("marketId"));
			map.put("name", request.getParameter("name"));
			map.put("shopsName", request.getParameter("shopsName"));
			map.put("mobile", request.getParameter("mobile"));
			map.put("orderNo", request.getParameter("orderNo"));
			map.put("buyerMobile", request.getParameter("buyerMobile"));
			String startDate = request.getParameter("startDate");
			if(startDate != null && !"".equals(startDate)){
				startDate = startDate+" 00:00:00";
			}
			map.put("startDate", startDate);
			String endDate = request.getParameter("endDate");
			if(endDate != null && !"".equals(endDate)){
				endDate = endDate+" 23:59:59";
			}
			map.put("endDate", endDate);
			// 记录数
			map.put("total", grdGiftDetailService.getGrdOrderTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GrdGiftDetailDTO> list = grdGiftDetailService.getGrdOrderList(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}
	
	
	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@ResponseBody
	@RequestMapping(value = "grdProMemberInvitedOrder/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("market", "-1".equals(request.getParameter("marketId").toString()) ? null : request.getParameter("marketId"));
			map.put("name", request.getParameter("name"));
			map.put("shopsName", request.getParameter("shopsName"));
			map.put("mobile", request.getParameter("mobile"));
			map.put("orderNo", request.getParameter("orderNo"));
			map.put("buyerMobile", request.getParameter("buyerMobile"));
			String startDate = request.getParameter("startDate");
			if(startDate != null && !"".equals(startDate)){
				startDate = startDate+" 00:00:00";
			}
			map.put("startDate", startDate);
			String endDate = request.getParameter("endDate");
			if(endDate != null && !"".equals(endDate)){
				endDate = endDate+" 23:59:59";
			}
			map.put("endDate", endDate);
			int total = grdGiftDetailService.getGrdOrderTotal(map);
			if (total > 50000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>50000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		} catch (Exception e) {
			logger.info("product checkExportParams with ex : {} ", new Object[] { e });
		}
		return JSONObject.toJSONString(result);
	}
	
	
    /**
	 * 导出产品查询结果 注意 : 如果修改查询条件, 需要同步修改product/checkExportParams接口
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "grdProMemberInvitedOrder/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
//		map.put("market", "-1".equals(request.getParameter("marketId").toString()) ? null : request.getParameter("marketId"));
		map.put("market", request.getParameter("marketId"));
		map.put("name", request.getParameter("name"));
		map.put("shopsName", request.getParameter("shopsName"));
		map.put("mobile", request.getParameter("mobile"));
		map.put("orderNo", request.getParameter("orderNo"));
		map.put("buyerMobile", request.getParameter("buyerMobile"));
		String startDate = request.getParameter("startDate");
		if(startDate != null && !"".equals(startDate)){
			startDate = startDate+" 00:00:00";
		}
		map.put("startDate", startDate);
		String endDate = request.getParameter("endDate");
		if(endDate != null && !"".equals(endDate)){
			endDate = endDate+" 23:59:59";
		}
		map.put("endDate", endDate);
        map.put("startRow", 0);
        map.put("endRow", 50000);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			List<GrdGiftDetailDTO> list = grdGiftDetailService.getGrdOrderList(map);
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("促成订单统计".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("促成订单列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				Label label00 = new Label(0, 0, "所属市场");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "地推姓名 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "地推手机");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "订单号");// 填充第一行第三个单元格的内容
				Label label40 = new Label(4, 0, "交易时间");// 填充第一行第四个单元格的内容
				Label label50 = new Label(5, 0, "买家手机 ");// 
				Label label60 = new Label(6, 0, "买家姓名");// 
				Label label70 = new Label(7, 0, "商铺");// 填充第一行第五个单元格的内容
				Label label80 = new Label(8, 0, "实付金额");// 填充第一行第五个单元格的内容
			
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
						GrdGiftDetailDTO dto= list.get(i);
						//Label label0 = new Label(0, i + 1, String.valueOf(dto.getId()));
						Label label0 = new Label(0, i + 1, String.valueOf(dto.getMarket() == null ? "" : dto.getMarket()));
						Label label1 = new Label(1, i + 1, String.valueOf(dto.getName()) == null ? "" : dto.getName());
						Label label2 = new Label(2, i + 1, String.valueOf(dto.getMobile()) == null ? "" : dto.getMobile());
						Label label3 = new Label(3, i + 1, String.valueOf(dto.getOrderNo()) == null ? "" : dto.getOrderNo());
						Label label4 = new Label(4, i + 1, String.valueOf(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(dto.getOrderTime())));
						Label label5 = new Label(5, i + 1, String.valueOf(dto.getBuyerMobile()) == null ? "" : dto.getBuyerMobile() );
						Label label6 = new Label(6, i + 1, String.valueOf(dto.getBuyerName()) == null ? "" : dto.getBuyerName());
						Label label7 = new Label(7, i + 1, String.valueOf(dto.getShopsName()) == null ? "" : dto.getShopsName());
						Label label8 = new Label(8, i + 1, String.valueOf(dto.getOrderPrice()));
						
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
