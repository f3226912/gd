package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AuditLogToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.OrderBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.OrderDeliveryAddressToolService;
import com.gudeng.commerce.gd.admin.service.OrderProductDetailToolService;
import com.gudeng.commerce.gd.admin.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoSendnowDTO;
import com.gudeng.commerce.gd.order.dto.OrderDeliveryAddressDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("sendnoworder")
public class SendNowOrderController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(SendNowOrderController.class);

	@Autowired
	public OrderProductDetailToolService orderProductDetailToolService;
	@Autowired
	private OrderBaseinfoToolService orderBaseinfoToolService;
	@Autowired
	private MarketManageService marketService;
	@Autowired
	private GdProperties gdProperties;
	@Autowired
	public PaySerialnumberToolService paySerialnumberToolService;
	@Autowired
	public AuditLogToolService auditLogToolService;
	@Autowired
	private OrderDeliveryAddressToolService orderDeliveryAddressToolService;

	/**
	 * 订单列表页面
	 * 
	 * @return
	 */
	@RequestMapping("orderList")
	public String orderList(HttpServletRequest request) {
		return "order/sendnow_order_list";
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");

			String orderNo = request.getParameter("orderNo");
			String mobile = request.getParameter("mobile");
			String realName = request.getParameter("realName");
			String orderStatus = request.getParameter("orderStatus");

			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)) {
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			// 设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (startDate != null && !"".equals(startDate)) {
				map.put("startDate", startDate);
			}
			if (endDate != null && !"".equals(endDate)) {
				map.put("endDate", endDate);
			}

			if (orderNo != null && !"".equals(orderNo)) {
				map.put("orderNo", orderNo);
			}

			if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)) {
				map.put("orderStatus", orderStatus);
			}
			if (!StringUtils.isBlank(mobile)) {
				map.put("mobile", mobile);
			}
			if (!StringUtils.isBlank(realName)) {
				map.put("realName", realName);
			}

			map.put("businessId", gdProperties.getProperties().getProperty("GD_SENDNOW_BUSINESS_ID"));

			int total = orderBaseinfoToolService.getSuppOrderTotal(map);
			if (total > 10000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
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
	 * @Description exportData 数据导出
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		OutputStream ouputStream = null;
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");

			String orderNo = request.getParameter("orderNo");
			String mobile = request.getParameter("mobile");
			String realName = request.getParameter("realName");
			String orderStatus = request.getParameter("orderStatus");
			// 设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (startDate != null && !"".equals(startDate)) {
				map.put("startDate", startDate);
			}
			if (CommonUtil.isEmpty(endDate)) {
				endDate = DateUtil.getSysDateTimeString();
			}

			map.put("businessId", gdProperties.getProperties().getProperty("GD_SENDNOW_BUSINESS_ID"));
			map.put("endDate", endDate);

			if (orderNo != null && !"".equals(orderNo)) {
				map.put("orderNo", orderNo);
			}

			if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)) {
				map.put("orderStatus", orderStatus);
			}
			if (!StringUtils.isBlank(mobile)) {
				map.put("mobile", mobile);
			}
			if (!StringUtils.isBlank(realName)) {
				map.put("realName", realName);
			}

			map.put("startRow", 0);
			map.put("endRow", 99999999);
			WritableWorkbook wwb = null;

			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String fileName = null;
			String agent = request.getHeader("USER-AGENT");
			if (agent != null && -1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")) {// ie
				fileName = URLEncoder.encode("鲜农订单列表", "UTF-8");
			} else if (agent != null && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
				fileName = new String("鲜农订单列表".getBytes("UTF-8"), "ISO-8859-1");
			}

			// 根据checkExportParams接口中对日期的限制, startDate必须不能为空,
			// 若endDate为空则endDate自动被设置为当前日期
			String suffix = String.format("%tF", DateUtil.sdfDateTime.parse(startDate)).replace("-", "") + "_"
					+ String.format("%tF", DateUtil.sdfDateTime.parse(endDate)).replace("-", "");

			response.setHeader("Content-disposition", "attachment;filename=" + fileName + suffix + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			// List<OrderBaseinfoDTO> list =
			// orderBaseinfoToolService.getListByConditionPage(map);
			List<OrderBaseinfoSendnowDTO> list = orderBaseinfoToolService.getSendnowOrderListByConditionPage(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("订单信息", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				int col = 0;
				Label label0 = new Label(col++, 0, "订单编号");// 填充第一行第一个单元格的内容
				Label label1 = new Label(col++, 0, "用户账号");// 填充第一行第四个单元格的内容
				Label label1a = new Label(col++, 0, "手机号码");// 填充第一行第四个单元格的内容
				Label label2 = new Label(col++, 0, "买家姓名");// 填充第一行第四个单元格的内容
				
				Label label17 = new Label(col++, 0, "配送方式");// 配送方式
				Label label3 = new Label(col++, 0, "商品名称");
				Label label4 = new Label(col++, 0, "采购量");// 填充第一行第二个单元格的内容
				Label label5 = new Label(col++, 0, "单价");// 填充第一行第二个单元格的内容
				Label label6 = new Label(col++, 0, "小计");// 填充第一行第二个单元格的内容
				Label label10 = new Label(col++, 0, "收货地");// 填充第一行第三个单元格的内容
				Label label9 = new Label(col++, 0, "收货人");// 填充第一行第八个单元格的内容

				Label label11 = new Label(col++, 0, "联系电话");// 填充第一行第三个单元格的内容
				Label label13 = new Label(col++, 0, "创建时间");// 填充第一行第三个单元格的内容
				Label label12 = new Label(col++, 0, "买家留言");// 填充第一行第三个单元格的内容
				Label label7 = new Label(col++, 0, "订单金额");// 填充第一行第二个单元格的内容
				Label label8 = new Label(col++, 0, "实付款");// 填充第一行第六个单元格的内容

				Label label14 = new Label(col++, 0, "成交时间");// 填充第一行第三个单元格的内容
				Label label15 = new Label(col++, 0, "关闭时间");// 填充第一行第三个单元格的内容
				Label label16 = new Label(col++, 0, "订单状态");// 填充第一行第三个单元格的内容
				sheet.addCell(label0);
				sheet.addCell(label1);
				sheet.addCell(label1a);
				sheet.addCell(label2);
				sheet.addCell(label17);
				sheet.addCell(label3);
				sheet.addCell(label4);
				sheet.addCell(label5);
				sheet.addCell(label6);
				sheet.addCell(label10);
				sheet.addCell(label9);

				sheet.addCell(label11);
				sheet.addCell(label13);
				sheet.addCell(label12);
				sheet.addCell(label7);
				sheet.addCell(label8);

				sheet.addCell(label14);
				sheet.addCell(label15);
				sheet.addCell(label16);

				DecimalFormat df = new DecimalFormat("#.00");
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						OrderBaseinfoSendnowDTO orderBaseinfoDTO = list.get(i);
						col = 0;
						label0 = new Label(col++, i + 1, orderBaseinfoDTO.getOrderNo().toString());
						label1 = new Label(col++, i + 1, orderBaseinfoDTO.getAccount());
						label1a = new Label(col++, i + 1, orderBaseinfoDTO.getBuyerMobile());
						label2 = new Label(col++, i + 1, orderBaseinfoDTO.getRealName());
						label17 = new Label(col++, i + 1, orderBaseinfoDTO.getDistributeMode() == null ? ""
								: (orderBaseinfoDTO.getDistributeMode().equals("0") ? "自提" : "平台配送"));
						label3 = new Label(col++, i + 1, orderBaseinfoDTO.getProductName());
						label4 = new Label(col++, i + 1, orderBaseinfoDTO.getPurQuantity() == null ? ""
								: orderBaseinfoDTO.getPurQuantity().toString());
						label5 = new Label(col++, i + 1,
								orderBaseinfoDTO.getPrice() == null ? "" : df.format(orderBaseinfoDTO.getPrice()));
						label6 = new Label(col++, i + 1, orderBaseinfoDTO.getTradingPrice() == null ? ""
								: df.format(orderBaseinfoDTO.getTradingPrice()));
						label10 = new Label(col++, i + 1, orderBaseinfoDTO.getDeliveryDetail());
						label9 = new Label(col++, i + 1, orderBaseinfoDTO.getDeliveryLinkman());

						label11 = new Label(col++, i + 1, orderBaseinfoDTO.getDeliveryMobile());
						label13 = new Label(col++, i + 1,
								DateUtil.getDate(orderBaseinfoDTO.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
						label12 = new Label(col++, i + 1, orderBaseinfoDTO.getMessage());
						label7 = new Label(col++, i + 1, orderBaseinfoDTO.getOrderAmount() == null ? ""
								: df.format(orderBaseinfoDTO.getOrderAmount()));
						label8 = new Label(col++, i + 1, orderBaseinfoDTO.getPayAmount() == null ? ""
								: df.format(orderBaseinfoDTO.getPayAmount()));
						label14 = new Label(col++, i + 1,
								DateUtil.getDate(orderBaseinfoDTO.getPayTime(), DateUtil.DATE_FORMAT_DATETIME));
						label15 = new Label(col++, i + 1,
								DateUtil.getDate(orderBaseinfoDTO.getCloseTime(), DateUtil.DATE_FORMAT_DATETIME));
						label16 = new Label(col++, i + 1, orderBaseinfoDTO.getOrderStatusView());
						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label1a);// 将单元格加入表格
						sheet.addCell(label2);
						sheet.addCell(label17);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label10);
						sheet.addCell(label9);

						sheet.addCell(label11);
						sheet.addCell(label13);
						sheet.addCell(label12);
						sheet.addCell(label7);
						sheet.addCell(label8);

						sheet.addCell(label14);
						sheet.addCell(label15);
						sheet.addCell(label16);
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

	/**
	 * 订单管理列表数据查询
	 *
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("orderQuery")
	@ResponseBody
	public String orderQuery(HttpServletRequest request) {
		try {
			String orderNo = request.getParameter("orderNo");
			String mobile = request.getParameter("mobile");
			String realName = request.getParameter("realName");
			String orderStatus = request.getParameter("orderStatus");

			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");

			// 设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (startDate != null && !"".equals(startDate)) {
				map.put("startDate", startDate);
			}

			if (endDate != null && !"".equals(endDate)) {
				map.put("endDate", endDate);
			}
			if (orderNo != null && !"".equals(orderNo)) {
				map.put("orderNo", orderNo);
			}

			if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)) {
				map.put("orderStatus", orderStatus);
			}
			if (!StringUtils.isBlank(mobile)) {
				map.put("mobile", mobile);
			}
			if (!StringUtils.isBlank(realName)) {
				map.put("realName", realName);
			}

			map.put("businessId", gdProperties.getProperties().getProperty("GD_SENDNOW_BUSINESS_ID"));

			// 获取条件/记录总数
			map.put("total", orderBaseinfoToolService.getOrderTotal(map));
			// 设置分页参数
			setCommParameters(request, map);
			// 数据集合
			// List<OrderBaseinfoDTO> list =
			// orderBaseinfoToolService.getListByConditionPage(map);
			List<OrderBaseinfoDTO> list = orderBaseinfoToolService.getOrderListByConditionPage(map);
			// 设置默认农商友用户
			if (CollectionUtils.isNotEmpty(list)) {
				for (OrderBaseinfoDTO orderBase : list) {
					orderBase.setRealName(
							StringUtils.isBlank(orderBase.getRealName()) ? "农商友用户" : orderBase.getRealName());
				}
			}

			// rows键 存放每页记录 list
			map.put("rows", list);
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初始化 查看详细信息 application/json
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("orderDetailById/{persaleId}")
	public String orderDetailById(@PathVariable("persaleId") String persaleId, ModelMap map) {
		try {
			OrderBaseinfoDTO orderBaseinfoDTO = orderBaseinfoToolService.getById(Long.valueOf(persaleId));
			if (null != orderBaseinfoDTO) {

				Map<String, Object> detailmap = new HashMap<String, Object>();
				detailmap.put("orderNo", orderBaseinfoDTO.getOrderNo());
				orderBaseinfoDTO.setDetailList(orderProductDetailToolService.getListByOrderNo(detailmap));

				PaySerialnumberDTO paySerialnumberDTO = paySerialnumberToolService
						.getByOrderNoAndPayType(orderBaseinfoDTO.getOrderNo());
				if (null != paySerialnumberDTO)
					orderBaseinfoDTO.setPaySerialnumberDTO(paySerialnumberDTO);

				List<PaySerialnumberDTO> payment = paySerialnumberToolService.getListByCondition(detailmap);
				orderBaseinfoDTO.setPayments(payment);

				orderBaseinfoDTO.setAuditLogList(auditLogToolService.getListByOrderNo(orderBaseinfoDTO.getOrderNo()));

				List<OrderDeliveryAddressDTO> orderDeliverys = orderDeliveryAddressToolService.getList(detailmap);
				if (CollectionUtils.isNotEmpty(orderDeliverys)) {
					orderBaseinfoDTO.setOrderDelivery(orderDeliverys.get(0));
				}
			}
			map.put("dto", orderBaseinfoDTO);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "order/order_detail";
	}
}
