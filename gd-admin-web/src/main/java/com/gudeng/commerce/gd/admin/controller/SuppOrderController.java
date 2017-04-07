package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gudeng.commerce.gd.admin.service.OrderProductDetailToolService;
import com.gudeng.commerce.gd.admin.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.admin.service.PreSaleDetailToolService;
import com.gudeng.commerce.gd.admin.service.PreSaleToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping("supporder")
public class SuppOrderController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(SuppOrderController.class);

	@Autowired
	public OrderBaseinfoToolService orderBaseinfoToolService;
	@Autowired
	public OrderProductDetailToolService orderProductDetailToolService;
	@Autowired
	public PaySerialnumberToolService paySerialnumberToolService;
	@Autowired
	public PreSaleDetailToolService preSaleDetailToolService;
	@Autowired
	public PreSaleToolService preSaleToolService;
	@Autowired
	public AuditLogToolService auditLogToolService;
	@Autowired
	public MarketManageService marketService;

	/**
	 * 订单列表页面
	 * @return
	 */
	@RequestMapping("orderList/{type}")
	public String orderList(@PathVariable("type") String type,HttpServletRequest request){
		putModel("type",type);
		try {
			//putModel("allMarket1", marketService.getAllByType("1"));
			putModel("allMarket2", marketService.getAllByType("2"));
			//putModel("allMarket3", marketService.getAllByType("3"));
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "order/supp_order_list";
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
			String orderAmount = request.getParameter("orderAmount");
			String orderStatus = request.getParameter("orderStatus");
			String payType = request.getParameter("payType");
			String account = request.getParameter("account");
			String mobile = request.getParameter("mobile");
			String shopName = request.getParameter("shopName");
//			String examineStatus = request.getParameter("examineStatus");
//			String upPayFlag = request.getParameter("upPayFlag");
			String marketId = request.getParameter("marketId");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String orderSource = request.getParameter("orderSource");
			String statementId = request.getParameter("statementId");
			String posNumber = request.getParameter("posNumber");
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (startDate != null && !"".equals(startDate)){
				map.put("startDate", startDate);
			}
			if (endDate != null && !"".equals(endDate)){
				map.put("endDate", endDate);
			}
			if (orderNo != null && !"".equals(orderNo)){
				map.put("orderNo", orderNo);
			}
			if (marketId != null && !"".equals(marketId)){
				map.put("marketId", marketId);
			}
			if (orderAmount != null && !"".equals(orderAmount)){
				map.put("orderAmount", orderAmount);
			}
			if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)){
				map.put("orderStatus", orderStatus);
			}
			if (payType != null && !"".equals(payType)){
				map.put("payType", payType);
			}
			if (account != null && !"".equals(account)){
				map.put("account", account);
			}
			if (mobile != null && !"".equals(mobile)){
				map.put("mobile", mobile);
			}
			if (shopName != null && !"".equals(shopName)){
				map.put("shopName", shopName);
			}
			if (StringUtils.isNotBlank(orderSource)){
				map.put("orderSource", orderSource);
			}
			if (StringUtils.isNotBlank(statementId)){
				map.put("statementId", statementId);
			}
			if (StringUtils.isNotBlank(posNumber)){
				map.put("posNumber", posNumber);
			}
			
			/*
			 * 设置为供应商订单
			 */
			map.put("orderType", 2);
//			if (examineStatus != null && !"".equals(examineStatus)){
//				map.put("examineStatus", examineStatus);
//			}
//			if (upPayFlag != null && !"".equals(upPayFlag)){
//				map.put("upPayFlag", upPayFlag);
//			}

			//获取条件/记录总数
			//map.put("total", orderBaseinfoToolService.getTotal(map));
			map.put("total", orderBaseinfoToolService.getOrderTotal(map));
			//设置分页参数
			setCommParameters(request, map);
			//数据集合
			//List<OrderBaseinfoDTO> list = orderBaseinfoToolService.getListByConditionPage(map);
			List<OrderBaseinfoDTO> list = orderBaseinfoToolService.getSuppOrderListByConditionPage(map);
			//设置默认农商友用户
			if (CollectionUtils.isNotEmpty(list)) {
				for (OrderBaseinfoDTO orderBase : list) {
					orderBase.setRealName(StringUtils.isBlank(orderBase.getRealName()) ? "农商友用户": orderBase.getRealName());
				}
			}

			//rows键 存放每页记录 list
			map.put("rows", list);
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 	 初始化 查看详细信息
	 * application/json
	 * @param request
	 * @return
	 */
	@RequestMapping("orderDetailById/{persaleId}")
	public String orderDetailById(@PathVariable("persaleId") String persaleId, ModelMap map){
		try {
			OrderBaseinfoDTO orderBaseinfoDTO = orderBaseinfoToolService.getById(Long.valueOf(persaleId));
			if(null != orderBaseinfoDTO){
				//翻译订单状态
//				if("1".equals(orderBaseinfoDTO.getOrderStatus())){
//					orderBaseinfoDTO.setOrderStatus("付款未完成");
//				}else if("2".equals(orderBaseinfoDTO.getOrderStatus())){
//					orderBaseinfoDTO.setOrderStatus("部分付款");
//				}else if("3".equals(orderBaseinfoDTO.getOrderStatus())){
//					orderBaseinfoDTO.setOrderStatus("已付款");
//				}else if("4".equals(orderBaseinfoDTO.getOrderStatus())){
//					orderBaseinfoDTO.setOrderStatus("已出场");
//				}else if("8".equals(orderBaseinfoDTO.getOrderStatus())){
//					orderBaseinfoDTO.setOrderStatus("已取消");
//				}else if("9".equals(orderBaseinfoDTO.getOrderStatus())){
//					orderBaseinfoDTO.setOrderStatus("已过期");
//				}else if("10".equals(orderBaseinfoDTO.getOrderStatus())){
//					orderBaseinfoDTO.setOrderStatus("已完成");
//				}
				//翻译审核状态
//				if("0".equals(orderBaseinfoDTO.getExamineStatus())){
//					orderBaseinfoDTO.setExamineStatus("待审核");
//				}else if("1".equals(orderBaseinfoDTO.getExamineStatus())){
//					orderBaseinfoDTO.setExamineStatus("审核通过");
//				}else if("2".equals(orderBaseinfoDTO.getExamineStatus())){
//					orderBaseinfoDTO.setExamineStatus("审核驳回");
//				}
				//翻译付款方式
//				if ("1".equals(orderBaseinfoDTO.getPayType())) {
//					orderBaseinfoDTO.setPayType("账户余额");
//				} else if ("2".equals(orderBaseinfoDTO.getPayType())) {
//					orderBaseinfoDTO.setPayType("POS刷卡");
//				} else if ("3".equals(orderBaseinfoDTO.getPayType())) {
//					orderBaseinfoDTO.setPayType("现金");
//				} else if ("12".equals(orderBaseinfoDTO.getPayType())) {
//					orderBaseinfoDTO.setPayType("账户余额+POS刷卡");
//				} else if ("13".equals(orderBaseinfoDTO.getPayType())) {
//					orderBaseinfoDTO.setPayType("账户余额+现金");
//				}

				Map<String, Object> detailmap = new HashMap<String, Object>();
				detailmap.put("orderNo", orderBaseinfoDTO.getOrderNo());
				orderBaseinfoDTO.setDetailList(orderProductDetailToolService.getListByOrderNo(detailmap));

				PaySerialnumberDTO paySerialnumberDTO = paySerialnumberToolService.getByOrderNoAndPayType(orderBaseinfoDTO.getOrderNo());
				if(null != paySerialnumberDTO)orderBaseinfoDTO.setPaySerialnumberDTO(paySerialnumberDTO);

				List<PaySerialnumberDTO> payment = paySerialnumberToolService.getListByCondition(detailmap);
				orderBaseinfoDTO.setPayments(payment);

				orderBaseinfoDTO.setAuditLogList(auditLogToolService.getListByOrderNo(orderBaseinfoDTO.getOrderNo()));
			}
			map.put("dto", orderBaseinfoDTO);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "order/supp_order_detail";
	}

	/**
	 * 	  提交更新数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="orderSaveEdit" )
	@ResponseBody
	public String orderSaveEdit(PaySerialnumberDTO dto, HttpServletRequest request) {
		try {
			SysRegisterUser user = this.getUser(request);
			int i = 0;
			Integer payId = dto.getPayId();
			Long orderNo = dto.getOrderNo();
			String type = request.getParameter("type");
			String description = dto.getDescription();
			String statementId = dto.getStatementId();
			if(null != statementId && !"".equals(statementId))statementId = statementId.replace("，", ",");
			if(null != description && !"".equals(description))description = description.replace("，", ",");
			String updateUserId = user.getUserID();
			String userName = user.getUserName();
			i = orderBaseinfoToolService.orderExamine(payId, orderNo, type, description, statementId, updateUserId, userName);
			if (i > 0) {
				return "success";
			}else if(i == -1){
				return "failedbylsh";
			}else if(i == -2){
				return "failedbysh";
			}else {
				return "failed";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "failed";
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			String orderNo = request.getParameter("orderNo");
			String orderAmount = request.getParameter("orderAmount");
			String orderStatus = request.getParameter("orderStatus");
			String payType = request.getParameter("payType");
			String account = request.getParameter("account");
			String shopName = request.getParameter("shopName");
			String marketId = request.getParameter("marketId");
//			String examineStatus = request.getParameter("examineStatus");
//			String upPayFlag = request.getParameter("upPayFlag");
			String orderSource = request.getParameter("orderSource");
			String statementId = request.getParameter("statementId");
			String posNumber = request.getParameter("posNumber");
			String mobile=request.getParameter("mobile");
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (startDate != null && !"".equals(startDate)){
				map.put("startDate", startDate);
			}
			if (endDate != null && !"".equals(endDate)){
				map.put("endDate", endDate);
			}
			if (orderNo != null && !"".equals(orderNo)){
				map.put("orderNo", orderNo);
			}
			if (marketId != null && !"".equals(marketId)){
				map.put("marketId", marketId);
			}
			if (orderAmount != null && !"".equals(orderAmount)){
				map.put("orderAmount", orderAmount);
			}
			if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)){
				map.put("orderStatus", orderStatus);
			}
			if (payType != null && !"".equals(payType)){
				map.put("payType", payType);
			}
			if (account != null && !"".equals(account)){
				map.put("account", account);
			}
			if (mobile != null && !"".equals(mobile)){
				map.put("mobile", mobile);
			}
			if (shopName != null && !"".equals(shopName)){
				map.put("shopName", shopName);
			}
			if (StringUtils.isNotBlank(orderSource)){
				map.put("orderSource", orderSource);
			}
			if (StringUtils.isNotBlank(statementId)){
				map.put("statementId", statementId);
			}
			if (StringUtils.isNotBlank(posNumber)){
				map.put("posNumber", posNumber);
			}
//			if (examineStatus != null && !"".equals(examineStatus)){
//				map.put("examineStatus", examineStatus);
//			}
//			if (upPayFlag != null && !"".equals(upPayFlag)){
//				map.put("upPayFlag", upPayFlag);
//			}

			map.put("orderType", 2);
			//int total = orderBaseinfoToolService.getTotal(map);
			int total = orderBaseinfoToolService.getSuppOrderTotal(map);
			if (total > 10000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("product checkExportParams with ex : {} ", new Object[]{e});
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
			String orderNo = request.getParameter("orderNo");
			String orderAmount = request.getParameter("orderAmount");
			String orderStatus = request.getParameter("orderStatus");
			String payType = request.getParameter("payType");
			String account = request.getParameter("account");
			String marketId = request.getParameter("marketId");
//			String examineStatus = request.getParameter("examineStatus");
			String shopName = request.getParameter("shopName");
			//if(null != shopName && !"".equals(shopName))shopName = java.net.URLDecoder.decode(shopName,"UTF-8");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String orderSource = request.getParameter("orderSource");
			String statementId = request.getParameter("statementId");
			String posNumber = request.getParameter("posNumber");
			String mobile = request.getParameter("mobile");
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (startDate != null && !"".equals(startDate)){
				map.put("startDate", startDate);
			}
			if (CommonUtil.isEmpty(endDate)){
				endDate = DateUtil.getSysDateTimeString() ;
			}
			map.put("endDate", endDate);
			if (orderNo != null && !"".equals(orderNo)){
				map.put("orderNo", orderNo);
			}
			if (orderAmount != null && !"".equals(orderAmount)){
				map.put("orderAmount", orderAmount);
			}
			if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)){
				map.put("orderStatus", orderStatus);
			}
			if (payType != null && !"".equals(payType)){
				map.put("payType", payType);
			}
			if (marketId != null && !"".equals(marketId)){
				map.put("marketId", marketId);
			}
			if (account != null && !"".equals(account)){
				map.put("account", account);
			}
			if (mobile != null && !"".equals(mobile)){
				map.put("mobile", mobile);
			}
			if (shopName != null && !"".equals(shopName)){
				map.put("shopName", shopName);
			}
			if (StringUtils.isNotBlank(orderSource)){
				map.put("orderSource", orderSource);
			}
			if (StringUtils.isNotBlank(statementId)){
				map.put("statementId", statementId);
			}
			if (StringUtils.isNotBlank(posNumber)){
				map.put("posNumber", posNumber);
			}
//			if (examineStatus != null && !"".equals(examineStatus)){
//				map.put("examineStatus", examineStatus);
//			}
			map.put("orderType", 2);
			
			map.put("startRow", 0);
			map.put("endRow", 99999999);
			WritableWorkbook wwb = null;


			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String fileName = null;
			String agent = request.getHeader("USER-AGENT");
            if (agent != null && -1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")) {// ie
                fileName = URLEncoder.encode("农批商采购订单列表", "UTF-8");
            } else if (agent != null && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
            	fileName = new String("农批商采购订单列表".getBytes("UTF-8"), "ISO-8859-1");
            }


            //根据checkExportParams接口中对日期的限制, startDate必须不能为空, 若endDate为空则endDate自动被设置为当前日期
            String suffix =  String.format("%tF", DateUtil.sdfDateTime.parse(startDate)).replace("-", "")
            		+ "_" + String.format("%tF", DateUtil.sdfDateTime.parse(endDate)).replace("-", "");

            response.setHeader("Content-disposition", "attachment;filename=" + fileName +suffix + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			//List<OrderBaseinfoDTO> list = orderBaseinfoToolService.getListByConditionPage(map);
			List<OrderBaseinfoDTO> list = orderBaseinfoToolService.getSuppOrderListByConditionPage(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("订单信息", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				int col = 0;
				Label label00 = new Label(col++, 0, "订单编号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(col++, 0, "订单金额");// 填充第一行第四个单元格的内容
				Label label101 = new Label(col++, 0, "预付款");// 填充第一行第四个单元格的内容
//				Label label20 = new Label(col++, 0, "抵扣金额");// 填充第一行第五个单元格的内容
//				Label label30 = new Label(col++, 0, "应付金额");// 填充第一行第七个单元格的内容
				Label label301 = new Label(col++, 0, "实付款");
				Label label40 = new Label(col++, 0, "支付方式");// 填充第一行第二个单元格的内容
				//Label label401 = new Label(col++, 0, "订单来源");// 填充第一行第二个单元格的内容
				Label label402 = new Label(col++, 0, "终端号");// 填充第一行第二个单元格的内容
				Label label403 = new Label(col++, 0, "银行卡");// 填充第一行第二个单元格的内容
				Label label404 = new Label(col++, 0, "流水号");// 填充第一行第二个单元格的内容
				Label label50 = new Label(col++, 0, "用户账号");// 填充第一行第六个单元格的内容
				Label label51 = new Label(col++, 0, "手机号码");// 填充第一行第六个单元格的内容
				Label label60 = new Label(col++, 0, "买家姓名");// 填充第一行第八个单元格的内容
//				Label label70 = new Label(col++, 0, "订单时间");// 填充第一行第三个单元格的内容
				Label label80 = new Label(col++, 0, "商铺名称");// 填充第一行第三个单元格的内容
				Label label802 = new Label(col++, 0, "主营分类");// 填充第一行第三个单元格的内容
				Label label803 = new Label(col++, 0, "其他分类");// 填充第一行第三个单元格的内容
				Label label801 = new Label(col++, 0, "创建时间");// 填充第一行第三个单元格的内容
				Label label90 = new Label(col++, 0, "订单状态");// 填充第一行第三个单元格的内容
				Label label901 = new Label(col++, 0, "状态词");// 填充第一行第三个单元格的内容
//				Label label100 = new Label(row++, 0, "审核状态");// 填充第一行第三个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label101);// 将单元格加入表格
//				sheet.addCell(label20);
//				sheet.addCell(label30);
				sheet.addCell(label301);
				sheet.addCell(label40);
				//sheet.addCell(label401);
				sheet.addCell(label402);
				sheet.addCell(label403);
				sheet.addCell(label404);
				sheet.addCell(label50);
				sheet.addCell(label51);
				sheet.addCell(label60);
//				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(label802);
				sheet.addCell(label803);
				sheet.addCell(label801);
				sheet.addCell(label90);
				sheet.addCell(label901);// 将单元格加入表格
//				sheet.addCell(label100);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						OrderBaseinfoDTO orderBaseinfoDTO = list.get(i);
						col = 0;
						Label label0 = new Label(col++, i + 1, orderBaseinfoDTO.getOrderNo().toString());
						Label label1 = new Label(col++, i + 1, orderBaseinfoDTO.getOrderAmount().toString());
						Label label11 = new Label(col++, i + 1, orderBaseinfoDTO.getPrepayAmt()==null ? "0.00" : orderBaseinfoDTO.getPrepayAmt().toString());
//						Label label2 = new Label(col++, i + 1, orderBaseinfoDTO.getDiscountAmount().toString());
//						Label label3 = new Label(col++, i + 1, orderBaseinfoDTO.getReceAmount().toString());
						Label label31 = new Label(col++, i + 1, orderBaseinfoDTO.getPayAmount().toString());
						Label label4 = new Label(col++, i + 1, orderBaseinfoDTO.getPayTypeView());
//						if ("1".equals(orderBaseinfoDTO.getPayType())) {
//							label4 = new Label(col++, i + 1, "账户余额".toString());
//						} else if ("2".equals(orderBaseinfoDTO.getPayType())) {
//							label4 = new Label(col++, i + 1, "POS刷卡".toString());
//						} else if ("3".equals(orderBaseinfoDTO.getPayType())) {
//							label4 = new Label(col++, i + 1, "现金".toString());
//						} else if ("12".equals(orderBaseinfoDTO.getPayType())) {
//							label4 = new Label(col++, i + 1, "账户余额+POS刷卡".toString());
//						} else if ("13".equals(orderBaseinfoDTO.getPayType())) {
//							label4 = new Label(col++, i + 1, "账户余额+现金".toString());
//						}else{
//							label4 = new Label(col++, i + 1, "".toString());
//						}
						//Label label41 = new Label(col++, i + 1, orderBaseinfoDTO.getOrderSourceView());
						Label label42 = new Label(col++, i + 1, orderBaseinfoDTO.getPosNumber());
						Label label43 = new Label(col++, i + 1, orderBaseinfoDTO.getPaymentAcc());
						Label label44 = new Label(col++, i + 1, orderBaseinfoDTO.getStatementId());
						Label label5 = new Label(col++, i + 1, StringUtils.isEmpty(orderBaseinfoDTO.getAccount()) ? "" : orderBaseinfoDTO.getAccount());
						Label label5a = new Label(col++, i + 1, StringUtils.isEmpty(orderBaseinfoDTO.getBuyerMobile()) ? "" : orderBaseinfoDTO.getBuyerMobile());

						Label label6 = new Label(col++, i + 1, StringUtils.isEmpty(orderBaseinfoDTO.getRealName()) ? "" : orderBaseinfoDTO.getRealName());
//						Label label7 = new Label(col++, i + 1, time.format(orderBaseinfoDTO.getOrderTime()));
						Label label8 = new Label(col++, i + 1, StringUtils.isEmpty(orderBaseinfoDTO.getShopName()) ? "" : orderBaseinfoDTO.getShopName());
						Label label82 = new Label(col++, i + 1, StringUtils.isEmpty(orderBaseinfoDTO.getCateNames()) ? "" : orderBaseinfoDTO.getCateNames());
						Label label83 = new Label(col++, i + 1, StringUtils.isEmpty(orderBaseinfoDTO.getOtherCateNames()) ? "" : orderBaseinfoDTO.getOtherCateNames());
						Label label81 = new Label(col++, i + 1, DateUtil.toString(orderBaseinfoDTO.getOrderTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label9 = new Label(col++, i + 1, orderBaseinfoDTO.getOrderStatusView());
						Label label91 = new Label(col++, i + 1, orderBaseinfoDTO.getStateComment());
//						Label label101 = null;
//						if("1".equals(orderBaseinfoDTO.getOrderStatus())){
//							label9 = new Label(col++, i + 1, "付款未完成".toString());
//						}else if("2".equals(orderBaseinfoDTO.getOrderStatus())){
//							label9 = new Label(col++, i + 1, "部分付款".toString());
//						}else if("3".equals(orderBaseinfoDTO.getOrderStatus())){
//							label9 = new Label(col++, i + 1, "已付款".toString());
//						}else if("4".equals(orderBaseinfoDTO.getOrderStatus())){
//							label9 = new Label(col++, i + 1, "已出场".toString());
//						}else if("8".equals(orderBaseinfoDTO.getOrderStatus())){
//							label9 = new Label(col++, i + 1, "已取消".toString());
//						}else if("9".equals(orderBaseinfoDTO.getOrderStatus())){
//							label9 = new Label(col++, i + 1, "已作废".toString());
//						}else if("10".equals(orderBaseinfoDTO.getOrderStatus())){
//							label9 = new Label(col++, i + 1, "已完成".toString());
//						}else{
//							label9 = new Label(col++, i + 1, "".toString());
//						}

//						if ("0".equals(orderBaseinfoDTO.getExamineStatus())) {
//							label101 = new Label(10, i + 1, "待审核".toString());
//						} else if ("1".equals(orderBaseinfoDTO.getExamineStatus())) {
//							label101 = new Label(10, i + 1, "审核通过".toString());
//						} else if ("2".equals(orderBaseinfoDTO.getExamineStatus())) {
//							label101 = new Label(10, i + 1, "审核驳回".toString());
//						}else{
//							label101 = new Label(10, i + 1, "".toString());
//						}
						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label11);// 将单元格加入表格
//						sheet.addCell(label2);
//						sheet.addCell(label3);
						sheet.addCell(label31);
						sheet.addCell(label4);
						//sheet.addCell(label41);
						sheet.addCell(label42);
						sheet.addCell(label43);
						sheet.addCell(label44);
						sheet.addCell(label5);
						sheet.addCell(label5a);
						sheet.addCell(label6);
//						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label82);
						sheet.addCell(label83);
						sheet.addCell(label81);
						sheet.addCell(label9);
						sheet.addCell(label91);
//						sheet.addCell(label101);
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
