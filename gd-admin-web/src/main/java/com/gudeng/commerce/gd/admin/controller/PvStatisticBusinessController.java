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

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.PvStatisticBusinessToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.BusinessPvStatisDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.PageStatisMemberDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("pvStatisticBusiness")
public class PvStatisticBusinessController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(PvStatisticBusinessController.class);

	@Autowired
	private  MarketManageService marketManageService;
	
	@Autowired
	public PvStatisticBusinessToolService pvStatisticBusinessToolService;

	/**
	 *
	 */
	@RequestMapping(value="pv")
	public String pvStatisticBusiness(HttpServletRequest request){
		//市场列表
		try{
			List<MarketDTO> list = marketManageService.getAllByType("2");
			request.setAttribute("marketList", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "member/pvStatisticBusiness_list";
	}

	/**
	 * 根据多个条件查询
	 *
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="querybysearch" )
	@ResponseBody
    public String querybysearch(HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();

//			String level=request.getParameter("level");
//			String isAuth=request.getParameter("isAuth");

			map.put("mobile", request.getParameter("mobile"));
			map.put("account", request.getParameter("account"));
			map.put("shopsName", request.getParameter("shopsName"));
			map.put("marketId", request.getParameter("marketId"));
			map.put("total", pvStatisticBusinessToolService.getTotal(map));

			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<BusinessPvStatisDTO> list = pvStatisticBusinessToolService.getBySearch(map);
			map.put("rows", list);//rows键 存放每页记录 list
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {

		}
		return null;
	}
	
	/**
	 *
	 */
	@RequestMapping(value="amount")
	public String amountStatisticBusiness(HttpServletRequest request){
		//市场列表
		try{
			List<MarketDTO> list = marketManageService.getAllByType("2");
			request.setAttribute("marketList", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "member/amountStatisticBusiness_list";
	}
	
	/**
	 * 根据多个条件查询
	 *
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="queryAmountbysearch" )
	@ResponseBody
    public String queryAmountbysearch(HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();

//			String level=request.getParameter("level");
//			String isAuth=request.getParameter("isAuth");

			map.put("mobile", request.getParameter("mobile"));
			map.put("account", request.getParameter("account"));
			map.put("shopsName", request.getParameter("shopsName"));
			map.put("marketId", request.getParameter("marketId"));
			map.put("startdate", request.getParameter("startDate"));
			map.put("enddate", request.getParameter("endDate"));
			map.put("total", pvStatisticBusinessToolService.getAmountTotal(map));

			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<BusinessPvStatisDTO> list = pvStatisticBusinessToolService.getAmountBySearch(map);
			map.put("rows", list);//rows键 存放每页记录 list
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {

		}
		return null;
	}
	
	/**
	 * 保存信息
	 * @return
	 */
	@RequestMapping("updatePv")
	@ResponseBody
	public String updatePv(HttpServletRequest request, String id, String shopPv) {
		Map<String,String> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			map.put("id", id);
			map.put("pv", shopPv);
			map.put("updateUserId", user.getUserID());
			pvStatisticBusinessToolService.updatePv(map);
			map.put("res", "success");
		} catch (Exception e) {
			logger.trace("保存失败,"+e.getMessage(), e);
			//e.printStackTrace();
			map.put("res", "error");
			map.put("msg", "系统错误，请联系管理员");
		}
		return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportAmountParams", produces="application/json; charset=utf-8")
	public String checkExportAmountParams(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", request.getParameter("mobile"));
			map.put("account", request.getParameter("account"));
			map.put("shopsName", request.getParameter("shopsName"));
			map.put("marketId", request.getParameter("marketId"));
			map.put("startdate", startDate);
			map.put("enddate", endDate);
			int total = pvStatisticBusinessToolService.getAmountTotal(map);
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

	@RequestMapping(value = "exportAmountData")
	public String exportAmountData(HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String name = "商铺日交易额统计_" + DateFormatUtils.format(new Date(), "yyyyMMdd");
			String fileName  = new String(name.getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", request.getParameter("mobile"));
			map.put("account", request.getParameter("account"));
			map.put("shopsName", request.getParameter("shopsName"));
			map.put("marketId", request.getParameter("marketId"));
			map.put("startdate", request.getParameter("startDate"));
			map.put("enddate", request.getParameter("endDate"));
			map.put("startRow", 0);
			map.put("endRow", 99999999);

			List<BusinessPvStatisDTO> list = pvStatisticBusinessToolService.getAmountBySearch(map);
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet(name + "列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				String[] titles={"商铺名称","用户账号","手机号码","注册时间","商铺所属市场","日交易金额", "统计时间","帐号状态"};
				for (int i = 0; i < titles.length; i++) {
					sheet.addCell(new Label(i, 0, titles[i]));
				}
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						BusinessPvStatisDTO item = list.get(i);
						sheet.addCell(new Label(0, i + 1, item.getShopsName()));
						sheet.addCell(new Label(1, i + 1, item.getAccount()));
						sheet.addCell(new Label(2, i + 1, item.getMobile()));
						sheet.addCell(new Label(3, i + 1, item.getCreateTime()==null? "" : DateFormatUtils.format(item.getCreateTime(), "yyyy-MM-dd HH:mm:ss")));
						sheet.addCell(new Label(4, i + 1, item.getMarketName()));
						sheet.addCell(new Label(5, i + 1, item.getTradeMoney()));
						sheet.addCell(new Label(6, i + 1, item.getTradeDay()==null? "" : DateFormatUtils.format(item.getTradeDay(), "yyyy-MM-dd")));
						String statusName = "";
						if(item.getStatus() != null && item.getStatus() == 0) {
							statusName = "禁用";
						} else if(item.getStatus() != null && item.getStatus() == 1) {
							statusName = "启用";
						}
						sheet.addCell(new Label(7, i + 1, statusName));
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
