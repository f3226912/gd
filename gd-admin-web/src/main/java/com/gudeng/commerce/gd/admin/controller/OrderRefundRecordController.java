package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.OrderRefundRecordToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderRefundRecordDTO;
import com.gudeng.commerce.gd.order.enm.EOrderRefundStatus;
import com.gudeng.commerce.gd.order.entity.OrderRefundRecordEntity;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
public class OrderRefundRecordController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(OrderRefundRecordController.class);

	@Autowired
	private OrderRefundRecordToolService orderRefundRecordToolService;

	/**
	 * 市场服务类
	 */
	@Autowired
	private MarketManageService marketManageService;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("refundOrder/orderList")
	public String list(HttpServletRequest request) throws Exception {
		// 查询市场
		List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
		request.setAttribute("marketDTOs", marketDTOs);
		return "order/refund_list";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("orderRefundRecord/query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			String refundNo = request.getParameter("refundNo");
			if (StringUtils.isNotEmpty(refundNo)) {
				map.put("refundNo", refundNo);
			}

			String orderNo = request.getParameter("orderNo");
			if (StringUtils.isNotEmpty(orderNo)) {
				map.put("orderNo", orderNo);
			}

			String marketId = request.getParameter("marketId");
			if (StringUtils.isNotEmpty(marketId)) {
				map.put("marketId", marketId);
			}
			String status = request.getParameter("status");
			if (StringUtils.isNotEmpty(status)) {
				map.put("status", status);
			}

			String account = request.getParameter("account");
			if (StringUtils.isNotEmpty(account)) {
				map.put("account", account);
			}

			String mobile = request.getParameter("mobile");
			if (StringUtils.isNotEmpty(mobile)) {
				map.put("mobile", mobile);
			}

			String startCreateTime = request.getParameter("startDate");

			if (StringUtils.isNotEmpty(startCreateTime)) {
				map.put("startCreateTime", startCreateTime);
			}

			String endCreateTime = request.getParameter("endDate");
			if (StringUtils.isNotEmpty(endCreateTime)) {
				map.put("endCreateTime", endCreateTime);
			}
			// 记录数
			map.put("total", orderRefundRecordToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<OrderRefundRecordDTO> list = orderRefundRecordToolService.getListPage(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}

	/**
	 * 保存数据（新增、修改）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "orderRefundRecord/save" })
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, OrderRefundRecordEntity entity, OrderRefundRecordDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("createUserId");
			if (StringUtils.isNotEmpty(id)) {
				dto.setUpdateUserId(user.getUserID());
				dto.setUpdateTime(new Date());
				i = orderRefundRecordToolService.update(dto);
			} else {
				entity.setCreateUserId(user.getUserID());
				entity.setCreateTime(new Date());
				i = orderRefundRecordToolService.insert(entity);
			}
			if (i > 0) {
				map.put("msg", "success");
			} else {
				map.put("msg", "保存失败");
			}
		} catch (Exception e) {
			map.put("msg", "保存失败");
			logger.trace("新增保存错误", e);
		}
		return JSONObject.toJSONString(map);
	}

	/**
	 * 进入新增页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("orderRefundRecord/beforeSave")
	public String addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		return "orderRefundRecord/save";
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("orderRefundRecord/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			OrderRefundRecordDTO dto = orderRefundRecordToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("orderRefundRecord/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("orderRefundRecord/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			OrderRefundRecordDTO dto = orderRefundRecordToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("orderRefundRecord/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "orderRefundRecord/delete")
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isEmpty(ids)) {
				map.put("msg", "未选中删除数据");
			} else {
				List<String> list = Arrays.asList(ids.split(","));
				int i = orderRefundRecordToolService.deleteBatch(list);
				map.put("msg", "success");
			}
		} catch (Exception e) {
			map.put("msg", "服务器出错");
		}
		return JSONObject.toJSONString(map);
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "orderRefundRecord/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			String refundNo = request.getParameter("refundNo");
			if (StringUtils.isNotEmpty(refundNo)) {
				map.put("refundNo", refundNo);
			}

			String orderNo = request.getParameter("orderNo");
			if (StringUtils.isNotEmpty(orderNo)) {
				map.put("orderNo", orderNo);
			}

			String marketId = request.getParameter("marketId");
			if (StringUtils.isNotEmpty(marketId)) {
				map.put("marketId", marketId);
			}
			String status = request.getParameter("status");
			if (StringUtils.isNotEmpty(status)) {
				map.put("status", status);
			}

			String account = request.getParameter("account");
			if (StringUtils.isNotEmpty(account)) {
				map.put("account", account);
			}

			String mobile = request.getParameter("mobile");
			if (StringUtils.isNotEmpty(mobile)) {
				map.put("mobile", mobile);
			}

			String startCreateTime = request.getParameter("startDate");

			if (StringUtils.isNotEmpty(startCreateTime)) {
				map.put("startCreateTime", startCreateTime);
			}

			String endCreateTime = request.getParameter("endDate");
			if (StringUtils.isNotEmpty(endCreateTime)) {
				map.put("endCreateTime", endCreateTime);
			}
			int total = orderRefundRecordToolService.getTotal(map);
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
	 * 导出Excel文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "orderRefundRecord/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
		String refundNo = request.getParameter("refundNo");
		if (StringUtils.isNotEmpty(refundNo)) {
			map.put("refundNo", refundNo);
		}

		String orderNo = request.getParameter("orderNo");
		if (StringUtils.isNotEmpty(orderNo)) {
			map.put("orderNo", orderNo);
		}

		String marketId = request.getParameter("marketId");
		if (StringUtils.isNotEmpty(marketId)) {
			map.put("marketId", marketId);
		}
		String status = request.getParameter("status");
		if (StringUtils.isNotEmpty(status)) {
			map.put("status", status);
		}

		String account = request.getParameter("account");
		if (StringUtils.isNotEmpty(account)) {
			map.put("account", account);
		}

		String mobile = request.getParameter("mobile");
		if (StringUtils.isNotEmpty(mobile)) {
			map.put("mobile", mobile);
		}

		String startCreateTime = request.getParameter("startDate");

		if (StringUtils.isNotEmpty(startCreateTime)) {
			map.put("startCreateTime", startCreateTime);
		}

		String endCreateTime = request.getParameter("endDate");
		if (StringUtils.isNotEmpty(endCreateTime)) {
			map.put("endCreateTime", endCreateTime);
		}
		map.put("startRow", 0);
		map.put("endRow", 99999999);
		OutputStream ouputStream = null;
		WritableWorkbook wwb = null;
	
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("退款记录列表".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			List<OrderRefundRecordDTO> list = orderRefundRecordToolService.getListPage(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("退款记录列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页

				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "退款编号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "订单号");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "退款金额");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "用户账号");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "手机号码");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "创建时间");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "退款时间");// 填充第一行第六个单元格的内容
				Label label70 = new Label(7, 0, "退款状态");// 填充第一行第八个单元格的内容
				

				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);

				sheet.setColumnView(0, 20);
				sheet.setColumnView(1, 20);
				sheet.setColumnView(2, 15);
				sheet.setColumnView(3, 15);
				sheet.setColumnView(4, 15);
				sheet.setColumnView(5, 15);
				sheet.setColumnView(6, 15);
				sheet.setColumnView(7, 15);
				
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					DecimalFormat df = new DecimalFormat("#.00");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						OrderRefundRecordDTO item = list.get(i);
						Label label0 = new Label(0, i + 1, item.getRefundNo());
						Label label1 = new Label(1, i + 1, item.getOrderNo().toString());
						Label label2 = new Label(2, i + 1, item.getAmount()==null?"":df.format(item.getAmount()));
						Label label3 = new Label(3, i + 1, item.getAccount());		
						Label label4 = new Label(4, i + 1, item.getMobile());
						Label label5 = new Label(5, i + 1, time.format(item.getCreateTime()));
						Label label6 = new Label(6, i + 1,item.getRefund_time()==null?"":time.format(item.getRefund_time()));
						Label label7 = new Label(7, i + 1,EOrderRefundStatus.getEnumByCode(item.getStatus()).getValue());
						
						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						
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
