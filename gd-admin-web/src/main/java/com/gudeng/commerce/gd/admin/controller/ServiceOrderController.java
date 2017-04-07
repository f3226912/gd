package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
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
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AuditLogToolService;
import com.gudeng.commerce.gd.admin.service.InvoiceInfoToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.OrderBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.OrderDeliveryAddressToolService;
import com.gudeng.commerce.gd.admin.service.OrderProductDetailToolService;
import com.gudeng.commerce.gd.admin.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.admin.service.PreSaleDetailToolService;
import com.gudeng.commerce.gd.admin.service.PreSaleToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.InvoiceInfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderDeliveryAddressDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("serviceOrder")
public class ServiceOrderController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(ServiceOrderController.class);

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
	@Autowired
	private OrderDeliveryAddressToolService orderDeliveryAddressToolService;
	@Autowired
	private InvoiceInfoToolService invoiceInfoToolService;

	/**
	 * 订单列表页面
	 * @return
	 */
	@RequestMapping("orderList")
	public String orderList(HttpServletRequest request){
		return "order/service_order_list";
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
			String thirdStatementId = request.getParameter("thirdStatementId");
			String account = request.getParameter("account");
			String orderStatus = request.getParameter("orderStatus");
			String mobile = request.getParameter("mobile");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (orderNo != null && !"".equals(orderNo)){
				map.put("orderNo", orderNo);
			}
			if (thirdStatementId != null && !"".equals(thirdStatementId)){
				map.put("thirdStatementId", thirdStatementId);
			}
			if (account != null && !"".equals(account)){
				map.put("account", account);
			}
			if (mobile != null && !"".equals(mobile)){
				map.put("mobile", mobile);
			}
			if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)){
				map.put("orderStatus", orderStatus);
			}
			if (startDate != null && !"".equals(startDate)){
				map.put("startDate", startDate);
			}
			if (endDate != null && !"".equals(endDate)){
				map.put("endDate", endDate);
			}
			//获取条件/记录总数
			map.put("total", orderBaseinfoToolService.getServiceOrderTotal(map));
			//设置分页参数
			setCommParameters(request, map);
			//数据集合
			//List<OrderBaseinfoDTO> list = orderBaseinfoToolService.getListByConditionPage(map);
			List<OrderBaseinfoDTO> list = orderBaseinfoToolService.getServiceOrderListByConditionPage(map);
			//设置默认农商友用户
			/*if (CollectionUtils.isNotEmpty(list)) {
				for (OrderBaseinfoDTO orderBase : list) {
					orderBase.setRealName(StringUtils.isBlank(orderBase.getRealName()) ? "农商友用户": orderBase.getRealName());
				}
			}*/

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
	 * 查询订单的相似流水，待付款和已关闭的订单使用
	 *
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("queryOrderSameStatement")
	@ResponseBody
	public String queryOrderSameStatement(String persaleId) {
		Map<String,String> map = new HashMap<>();
		map.put("res", "success");
		try {
			String statementId = orderBaseinfoToolService.queryOderSameStatement(persaleId);
			map.put("statementId", statementId);
		} catch (Exception e) {
			logger.trace("查询订单相似流水失败,"+e.getMessage(), e);
			//e.printStackTrace();
			map.put("res", "error");
		}
		return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 保存补单信息
	 * @param persaleId
	 * @return
	 */
	@RequestMapping("saveSupplementOrder")
	@ResponseBody
	public String saveSupplementOrder(HttpServletRequest request, String persaleId,String statementId) {
		Map<String,String> map = new HashMap<>();
		map.put("res", "success");
		try {
			SysRegisterUser user = getUser(request);
			orderBaseinfoToolService.saveSupplementOrder(user.getUserID(), persaleId, statementId);
		} 
		catch(RuntimeException e){
			map.put("res", "error");
			map.put("msg", e.getCause().getMessage());//事物引起的
			logger.trace("保存补单失败,"+e.getMessage(), e);
		}
		catch (Exception e) {
			logger.trace("保存补单失败,"+e.getMessage(), e);
			//e.printStackTrace();
			map.put("res", "error");
			map.put("res", "系统错误，请联系管理员");
		}
		return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
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
				Map<String, Object> detailmap = new HashMap<String, Object>();
				detailmap.put("orderNo", orderBaseinfoDTO.getOrderNo());
				orderBaseinfoDTO.setDetailList(orderProductDetailToolService.getListByOrderNo(detailmap));

				PaySerialnumberDTO paySerialnumberDTO = paySerialnumberToolService.getByOrderNo(orderBaseinfoDTO.getOrderNo());
				if(null != paySerialnumberDTO){
					orderBaseinfoDTO.setPaySerialnumberDTO(paySerialnumberDTO);
				}
				
				InvoiceInfoDTO invoiceInfoDTO = invoiceInfoToolService.getBySearch(detailmap);
				if(null != invoiceInfoDTO){
					orderBaseinfoDTO.setInvoiceInfoDTO(invoiceInfoDTO);
				}

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
		return "order/service_order_detail";
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
			String thirdStatementId = request.getParameter("thirdStatementId");
			String account = request.getParameter("account");
			String orderStatus = request.getParameter("orderStatus");
			String mobile = request.getParameter("mobile");
			Map<String, Object> map = new HashMap<String, Object>();
			if (orderNo != null && !"".equals(orderNo)){
				map.put("orderNo", orderNo);
			}
			if (thirdStatementId != null && !"".equals(thirdStatementId)){
				map.put("thirdStatementId", thirdStatementId);
			}
			if (account != null && !"".equals(account)){
				map.put("account", account);
			}
			if (mobile != null && !"".equals(mobile)){
				map.put("mobile", mobile);
			}
			if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)){
				map.put("orderStatus", orderStatus);
			}
			if (startDate != null && !"".equals(startDate)){
				map.put("startDate", startDate);
			}
			if (endDate != null && !"".equals(endDate)){
				map.put("endDate", endDate);
			}
			int total = orderBaseinfoToolService.getServiceOrderTotal(map);
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

	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String name = "服务订单_" + DateFormatUtils.format(new Date(), "yyyyMMdd");
			String fileName  = new String(name.getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String orderNo = request.getParameter("orderNo");
			String thirdStatementId = request.getParameter("thirdStatementId");
			String account = request.getParameter("account");
			String mobile = request.getParameter("mobile");
			String orderStatus = request.getParameter("orderStatus");
			Map<String, Object> map = new HashMap<String, Object>();
			if (orderNo != null && !"".equals(orderNo)){
				map.put("orderNo", orderNo);
			}
			if (thirdStatementId != null && !"".equals(thirdStatementId)){
				map.put("thirdStatementId", thirdStatementId);
			}
			if (account != null && !"".equals(account)){
				map.put("account", account);
			}
			if (mobile != null && !"".equals(mobile)){
				map.put("mobile", mobile);
			}
			if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)){
				map.put("orderStatus", orderStatus);
			}
			if (startDate != null && !"".equals(startDate)){
				map.put("startDate", startDate);
			}
			if (endDate != null && !"".equals(endDate)){
				map.put("endDate", endDate);
			}
			map.put("startRow", 0);
			map.put("endRow", 99999999);

			List<OrderBaseinfoDTO> list = orderBaseinfoToolService.getServiceOrderListByConditionPage(map);
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet(name + "列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				String[] titles={"订单编号","服务名称","商品总价","实付款","支付方式","平台流水号","第三方流水号",
						"用户账号","手机号码","买家姓名","创建时间","成交时间","订单状态","发票状态","发票号码"};
				for (int i = 0; i < titles.length; i++) {
					//sheet.setColumnView(i, 18);
					sheet.addCell(new Label(i, 0, titles[i]));
				}
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						OrderBaseinfoDTO item = list.get(i);
						sheet.addCell(new Label(0, i + 1, String.valueOf(item.getOrderNo())));
						sheet.addCell(new Label(1, i + 1, item.getServiceName()));
						sheet.addCell(new Label(2, i + 1, String.valueOf(item.getOrderAmount())));
						sheet.addCell(new Label(3, i + 1, String.valueOf(item.getPayAmount())));
						sheet.addCell(new Label(4, i + 1, item.getPayTypeView()));
						sheet.addCell(new Label(5, i + 1, item.getStatementId()));
						sheet.addCell(new Label(6, i + 1, item.getThirdStatementId()));
						sheet.addCell(new Label(7, i + 1, item.getAccount()));
						sheet.addCell(new Label(8, i + 1, item.getBuyerMobile()));
						sheet.addCell(new Label(9, i + 1, item.getRealName()));
						sheet.addCell(new Label(10, i + 1, item.getOrderTime()==null? "" : DateFormatUtils.format(item.getOrderTime(), "yyyy-MM-dd HH:mm:ss")));
						sheet.addCell(new Label(11, i + 1, item.getFinishedTime()==null? "" : DateFormatUtils.format(item.getFinishedTime(), "yyyy-MM-dd HH:mm:ss")));
						sheet.addCell(new Label(12, i + 1, item.getOrderStatusView()));
						sheet.addCell(new Label(13, i + 1, item.getInvoice()));
						sheet.addCell(new Label(14, i + 1, item.getInvoiceContent()));
						
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
