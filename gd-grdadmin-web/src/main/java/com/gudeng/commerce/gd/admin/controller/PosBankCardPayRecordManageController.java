package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
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
import com.gudeng.commerce.gd.admin.dto.PosBankCardPayRecordQueryDTO;
import com.gudeng.commerce.gd.admin.service.OrderBillToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.order.dto.PosBankCardPayRecordDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("posPay")
public class PosBankCardPayRecordManageController extends AdminBaseController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(PosBankCardPayRecordManageController.class);

	@Autowired
	private OrderBillToolService orderBillToolService;
	
	/**
	 * POS刷卡银行流水列表页面
	 * @return
	 */
	@RequestMapping("/recordList/{type}")
	public String orderList(@PathVariable("type") Integer type,HttpServletRequest request){
		putModel("type", type == null ? 0 : type);
		return "orderBill/record_list";
	}
	
	/**
	 * 查询POS刷卡银行流水列表详情
	 * @param request
	 * @param orderBillDTO
	 * @return
	 * @author TerryZhang
	 */
	@RequestMapping("/query")
	@ResponseBody
	public String orderBillList(HttpServletRequest request, PosBankCardPayRecordQueryDTO queryDTO) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("type", queryDTO.getType() == null ? 0:queryDTO.getType());
			map.put("OrderNo",queryDTO.getOrderNo());
			map.put("clientNo",queryDTO.getClientNo());
			map.put("shopName",queryDTO.getShopName());
			map.put("sysRefeNo", queryDTO.getSysRefeNo());
			map.put("billBeginTime",queryDTO.getBillBeginTime());
			map.put("billEndTime", queryDTO.getBillEndTime());
			map.put("tradeMoney",queryDTO.getTradeMoney());
			//设定分页,排序
			setCommParameters(request, map);
			PageQueryResultDTO<PosBankCardPayRecordDTO> pageQueryResult = orderBillToolService.getPageQueryResultByCondition(map);
			//设置总记录数
			map.put("total", pageQueryResult.getTotalCount());
			map.put("rows", pageQueryResult.getDataList());//rows键 存放每页记录 list 
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("[ERROR]查询POS刷卡银行流水异常", e);
		}
		return null;
	}
	
	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, HttpServletResponse response, 
			PosBankCardPayRecordQueryDTO queryDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String startDate = queryDTO.getBillBeginTime();
			String endDate = queryDTO.getBillEndTime();
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", queryDTO.getType() == null ? 0:queryDTO.getType());
			map.put("OrderNo",queryDTO.getOrderNo());
			map.put("clientNo",queryDTO.getClientNo());
			map.put("shopName",queryDTO.getShopName());
			map.put("sysRefeNo", queryDTO.getSysRefeNo());
			map.put("billBeginTime",queryDTO.getBillBeginTime());
			map.put("billEndTime", queryDTO.getBillEndTime());
			map.put("tradeMoney",queryDTO.getTradeMoney());

			PageQueryResultDTO<PosBankCardPayRecordDTO> pageQueryResult = orderBillToolService.getPageQueryResultByCondition(map);
			int total = pageQueryResult.getTotalCount();
			if (total > 10000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		}catch(Exception e){
			e.printStackTrace();
			logger.warn("Order Bill checkExportParams Exception : ", e);
		}
		return JSONObject.toJSONString(result);
	}

	/**
	 * @Description exportData 数据导出
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response,
			PosBankCardPayRecordQueryDTO queryDTO) {
		OutputStream ouputStream = null;
		try {
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", queryDTO.getType() == null ? 0:queryDTO.getType());
			map.put("OrderNo",queryDTO.getOrderNo());
			map.put("clientNo",queryDTO.getClientNo());
			map.put("shopName",queryDTO.getShopName());
			map.put("sysRefeNo", queryDTO.getSysRefeNo());
			map.put("billBeginTime",queryDTO.getBillBeginTime());
			map.put("billEndTime", queryDTO.getBillEndTime());
			map.put("tradeMoney",queryDTO.getTradeMoney());
			
			WritableWorkbook wwb = null;
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String fileName = null;
			String agent = request.getHeader("USER-AGENT");
            if (agent != null && -1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")) {// ie
                fileName = URLEncoder.encode("POS刷卡银行流水记录", "UTF-8");
            } else if (agent != null && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
            	fileName = new String("POS刷卡银行流水记录".getBytes("UTF-8"), "ISO-8859-1");
            }

			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			PageQueryResultDTO<PosBankCardPayRecordDTO> pageQueryResult = orderBillToolService.getPageQueryResultByCondition(map);
			List<PosBankCardPayRecordDTO> list = pageQueryResult.getDataList();
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("POS刷卡银行流水信息", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				int col = 0;
				String[] header = {"系统参考号", "POS终端流水号", "商铺终端号", "买家账号", "交易卡号", 
						"流水交易金额", "订单实付款", "商铺名称", "订单编号", "银行交易时间", 
						"订单成交时间", "类型"};
				for(int i=0, len = header.length; i<len;i++){
					Label label = new Label(col++, 0, header[i]);
					sheet.addCell(label);
				}
				
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						PosBankCardPayRecordDTO dto = list.get(i);
						col = 0;
						Label label1 = new Label(col++, i + 1, StringUtils.isEmpty(dto.getSysRefeNo()) ? "" : dto.getSysRefeNo());
						Label label2 = new Label(col++, i + 1, StringUtils.isEmpty(dto.getClientNo()) ? "" : dto.getClientNo());
						Label label3 = new Label(col++, i + 1, StringUtils.isEmpty(dto.getShopPosNumber()) ? "" : dto.getShopPosNumber());
						Label label4 = new Label(col++, i + 1, StringUtils.isEmpty(dto.getAccount()) ? "" : dto.getAccount());
						Label label5 = new Label(col++, i + 1, StringUtils.isEmpty(dto.getCardNo()) ? "" : dto.getCardNo());
						Label label6 = new Label(col++, i + 1, dto.getTradeMoney() == null ? "" : dto.getTradeMoney().toString());
						Label label7 = new Label(col++, i + 1, dto.getTradeAmount() == null ? "" : dto.getTradeAmount().toString());
						Label label8 = new Label(col++, i + 1, dto.getShopName());
						Label label9 = new Label(col++, i + 1, dto.getOrderNo() == null ? "" : dto.getOrderNo().toString());
						Label label10 = new Label(col++, i + 1, dto.getBankTradeTime() == null ? "" : DateUtil.getDate(dto.getBankTradeTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label11 = new Label(col++, i + 1, dto.getOrderTradeTime() == null ? "" : DateUtil.getDate(dto.getOrderTradeTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label12 = new Label(col++, i + 1, getMatchType(dto.getMatchType()));
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(label10);
						sheet.addCell(label11);
						sheet.addCell(label12);
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
	
	private String getMatchType(String type) {
		String sType = "未知类型";
		switch(type){
		case "1": sType = "完全匹配"; break;
		case "2": sType = "有流水无订单(未升级pos)"; break;
		case "3": sType = "有流水无订单(已升级pos)"; break;
		case "4": sType = "有订单无流水(延时)"; break;
		default: break;
		}
		return sType;
	}
}
