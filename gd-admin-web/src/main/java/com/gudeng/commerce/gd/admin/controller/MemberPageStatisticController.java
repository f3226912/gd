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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MemberPageStatisticToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.customer.dto.PageStatisMemberDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("memberPageStatistic")
public class MemberPageStatisticController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(MemberPageStatisticController.class);
	
	@Autowired
	public MemberPageStatisticToolService memberPageStatisticToolService;

	/**
	 *
	 */
	@RequestMapping("")
	public String memberPageStatistic(HttpServletRequest request){
		return "member/memberPageStatistic_list";
	}

	/**
	 * 根据多个条件查询
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="querybysearch" )
	@ResponseBody
    public String querybysearch(HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", request.getParameter("mobile"));
			map.put("account", request.getParameter("account"));
			map.put("shopsName", request.getParameter("shopsName"));
			map.put("memberGrade", request.getParameter("memberGrade"));
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(StringUtils.isNotBlank(startDate)){
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
			}
			if(StringUtils.isNotBlank(endDate)){
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(endDate)));
			}
			map.put("total", memberPageStatisticToolService.getTotal(map));

			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<PageStatisMemberDTO> list = memberPageStatisticToolService.getBySearch(map);
			map.put("rows", list);//rows键 存放每页记录 list
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
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
	public String checkExportParams(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			/*String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}*/
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", request.getParameter("mobile"));
			map.put("account", request.getParameter("account"));
			map.put("shopsName", request.getParameter("shopsName"));
			map.put("memberGrade", request.getParameter("memberGrade"));
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(StringUtils.isNotBlank(startDate)){
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
			}
			if(StringUtils.isNotBlank(endDate)){
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(endDate)));
			}
			int total = memberPageStatisticToolService.getTotal(map);
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
			String name = "金牌会员页面访问统计_" + DateFormatUtils.format(new Date(), "yyyyMMdd");
			String fileName  = new String(name.getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", request.getParameter("mobile"));
			map.put("account", request.getParameter("account"));
			map.put("shopsName", request.getParameter("shopsName"));
			map.put("memberGrade", request.getParameter("memberGrade"));
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(StringUtils.isNotBlank(startDate)){
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
			}
			if(StringUtils.isNotBlank(endDate)){
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(endDate)));
			}
			map.put("startRow", 0);
			map.put("endRow", 99999999);

			List<PageStatisMemberDTO> list = memberPageStatisticToolService.getBySearch(map);
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet(name + "列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				String[] titles={"商铺名称","用户账号","手机号码","注册时间","主营分类","会员等级","访问类型", "统计时间","帐号状态"};
				for (int i = 0; i < titles.length; i++) {
					sheet.addCell(new Label(i, 0, titles[i]));
				}
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						PageStatisMemberDTO item = list.get(i);
						sheet.addCell(new Label(0, i + 1, item.getShopsName()));
						sheet.addCell(new Label(1, i + 1, item.getAccount()));
						sheet.addCell(new Label(2, i + 1, item.getMobile()));
						sheet.addCell(new Label(3, i + 1, item.getCreateTime()==null? "" : DateFormatUtils.format(item.getCreateTime(), "yyyy-MM-dd HH:mm:ss")));
						sheet.addCell(new Label(4, i + 1, item.getCateName()));
						String memberGradeName = "";
						if(item.getMemberGrade() != null){
							if(item.getMemberGrade() == 0) {
								memberGradeName = "普通会员";
							} else if(item.getMemberGrade() == 1){
								memberGradeName = "金牌供应商";
							}
						}
						sheet.addCell(new Label(5, i + 1, memberGradeName));
						String pageTypeName = "";
						if("1".equals(item.getPageType())){
							pageTypeName = "金牌会员访问会员说明页面";
						} else if("2".equals(item.getPageType())){
							pageTypeName = "金牌会员点击立即支付";
						}
						sheet.addCell(new Label(6, i + 1, pageTypeName));
						sheet.addCell(new Label(7, i + 1, item.getStatisTime()==null? "" : DateFormatUtils.format(item.getStatisTime(), "yyyy-MM-dd HH:mm:ss")));
						String statusName = "";
						if("0".equals(item.getStatus())) {
							statusName = "禁用";
						} else if("1".equals(item.getStatus())) {
							statusName = "启用";
						}
						sheet.addCell(new Label(8, i + 1, statusName));
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
