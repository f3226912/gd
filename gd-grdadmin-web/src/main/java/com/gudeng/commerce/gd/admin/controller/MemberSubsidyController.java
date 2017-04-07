package com.gudeng.commerce.gd.admin.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MemberSubsidyToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.GdJxlLabel;
import com.gudeng.commerce.gd.admin.util.RandomIdGenerator;
import com.gudeng.commerce.gd.order.dto.MemberSubsidyDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @Description 统计信息，会员补贴信息控制器
 * @Project gd-admin-web
 * @ClassName MemberSubsidy.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月9日 上午10:15:49
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Controller
@RequestMapping("memberSubsidy")
public class MemberSubsidyController extends AdminBaseController {
	/** 记录日志 */
	 private static final GdLogger logger = GdLoggerFactory.getLogger(MemberSubsidyController.class);
	// 会员补贴
	@Autowired
	private MemberSubsidyToolService memberSubsidyToolService;

	/**
	 * @Description list 会员补贴信息统计列表
	 * @param request
	 * @param mv
	 * @return
	 * @CreationDate 2015年12月9日 上午10:20:58
	 * @Author lidong(dli@gdeng.cn)
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request, ModelAndView mv) {
		// 市场列表
		mv.setViewName("memberSubsidy/index");
		return mv;
	}

	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request, MemberSubsidyDTO memberSubsidyDTO) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", memberSubsidyDTO.getAccount() == null ? null : memberSubsidyDTO.getAccount().trim());
			map.put("mobile", memberSubsidyDTO.getMobile() == null ? null : memberSubsidyDTO.getMobile().trim());
			if (memberSubsidyDTO.getLevel() != null && memberSubsidyDTO.getLevel() > 0) {
				map.put("level", memberSubsidyDTO.getLevel());
			}
			map.put("startDate", request.getParameter("startDate"));
			map.put("endDate", request.getParameter("endDate"));
			// 设置查询参数
			// 记录数
			map.put("total", memberSubsidyToolService.queryTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<MemberSubsidyDTO> list = memberSubsidyToolService.queryList(map);

			MemberSubsidyDTO mDto = memberSubsidyToolService.queryStatistic(map);
			list.add(mDto);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {

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
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, MemberSubsidyDTO memberSubsidyDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
//			String startDate = CommonUtil.getStartOfDay(DateUtil.formateDate(params.getStartDate()));
//			String endDate = CommonUtil.getEndOfDay(DateUtil.formateDate(params.getEndDate()));
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", memberSubsidyDTO.getAccount() == null ? null : memberSubsidyDTO.getAccount().trim());
			map.put("mobile", memberSubsidyDTO.getMobile() == null ? null : memberSubsidyDTO.getMobile().trim());
			if (memberSubsidyDTO.getLevel() != null && memberSubsidyDTO.getLevel() > 0) {
				map.put("level", memberSubsidyDTO.getLevel());
			}
			map.put("startDate", request.getParameter("startDate"));
			map.put("endDate", request.getParameter("endDate"));

			int total = memberSubsidyToolService.queryTotal(map);
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
	 * @CreationDate 2015年11月5日 下午7:20:28
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String account, String mobile, Integer level, String startDate,
			String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("mobile", mobile);
		map.put("productName", account);
		if (level > 0) {
			map.put("level", level);
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("startRow", 0);
		map.put("endRow", 99999999);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			String destFileName = RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xls"; // 目标文件名
			response.setHeader("Content-Disposition", "attachment;filename=" + destFileName); // 设置响应
			response.setContentType("application/vnd.ms-excel");
			ouputStream = response.getOutputStream();
			// 查询数据
			List<MemberSubsidyDTO> list = memberSubsidyToolService.queryList(map);
			MemberSubsidyDTO mDto = memberSubsidyToolService.queryStatistic(map);
			list.add(mDto);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("会员补贴统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				GdJxlLabel label00 = new GdJxlLabel(0, 0, "帐号");// 填充第一行第一个单元格的内容
				GdJxlLabel label10 = new GdJxlLabel(1, 0, "手机");// 填充第一行第四个单元格的内容
				GdJxlLabel label20 = new GdJxlLabel(2, 0, "姓名");// 填充第一行第五个单元格的内容
				GdJxlLabel label30 = new GdJxlLabel(3, 0, "注册时间");// 填充第一行第七个单元格的内容
				GdJxlLabel label40 = new GdJxlLabel(4, 0, "所属市场");// 填充第一行第二个单元格的内容
				GdJxlLabel label50 = new GdJxlLabel(5, 0, "注册来源");// 填充第一行第六个单元格的内容
				GdJxlLabel label60 = new GdJxlLabel(6, 0, "系统驳回次数");// 填充第一行第八个单元格的内容
				GdJxlLabel label70 = new GdJxlLabel(7, 0, "采购订单总数");// 填充第一行第三个单元格的内容
				GdJxlLabel label80 = new GdJxlLabel(8, 0, "采购产品总数");// 填充第一行第三个单元格的内容
				GdJxlLabel label90 = new GdJxlLabel(9, 0, "交易总额");// 填充第一行第三个单元格的内容
				GdJxlLabel label100 = new GdJxlLabel(10, 0, "补贴累计金额");// 填充第一行第三个单元格的内容
				GdJxlLabel label110 = new GdJxlLabel(11, 0, "已抵扣金额");// 填充第一行第三个单元格的内容
				GdJxlLabel label120 = new GdJxlLabel(12, 0, "已提现金额");// 填充第一行第三个单元格的内容
				GdJxlLabel label130 = new GdJxlLabel(13, 0, "钱包现有余额");// 填充第一行第三个单元格的内容
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
				sheet.addCell(label100);
				sheet.addCell(label110);
				sheet.addCell(label120);
				sheet.addCell(label130);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						MemberSubsidyDTO memberSubsidyDTO = list.get(i);
						GdJxlLabel label0 = new GdJxlLabel(0, i + 1, memberSubsidyDTO.getAccount());
						GdJxlLabel label1 = new GdJxlLabel(1, i + 1, memberSubsidyDTO.getMobile());
						GdJxlLabel label2 = new GdJxlLabel(2, i + 1, memberSubsidyDTO.getRealName());
						GdJxlLabel label3 = new GdJxlLabel(3, i + 1, memberSubsidyDTO.getCreateTime() == null ? null : time.format(memberSubsidyDTO.getCreateTime()));
						GdJxlLabel label4 = new GdJxlLabel(4, i + 1, memberSubsidyDTO.getMarketIdStr());
						GdJxlLabel label5 = new GdJxlLabel(5, i + 1, memberSubsidyDTO.getLevelStr());
						GdJxlLabel label6 = new GdJxlLabel(6, i + 1, memberSubsidyDTO.getRejectCount());
						GdJxlLabel label7 = new GdJxlLabel(7, i + 1, memberSubsidyDTO.getOrderCount());
						GdJxlLabel label8 = new GdJxlLabel(8, i + 1, memberSubsidyDTO.getProductCount());
						GdJxlLabel label9 = new GdJxlLabel(9, i + 1, memberSubsidyDTO.getOrderAmount());
						GdJxlLabel label_10 = new GdJxlLabel(10, i + 1, memberSubsidyDTO.getSubAmount());
						GdJxlLabel label_11 = new GdJxlLabel(11, i + 1, memberSubsidyDTO.getDiscountAmount());
						GdJxlLabel label_12 = new GdJxlLabel(12, i + 1, memberSubsidyDTO.getCashAmount());
						GdJxlLabel label_13 = new GdJxlLabel(13, i + 1, memberSubsidyDTO.getBalAvailable());
						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(label_10);
						sheet.addCell(label_11);
						sheet.addCell(label_12);
						sheet.addCell(label_13);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				wwb.close();// 关闭
				ouputStream.flush();
				ouputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
