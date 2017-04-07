package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AppshareToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.AppshareDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
/**
 * App分享记录
 * @author liufan
 *
 */
@RequestMapping("appshare")
@Controller
public class AppshareController extends AdminBaseController{
	private final GdLogger logger = GdLoggerFactory.getLogger(AppshareController.class);
	
	@Resource
	private AppshareToolService appshareToolService;
	
	@Resource
	private SysRegisterUserAdminService sysRegisterUserAdminService;
	
	@RequestMapping("list")
	public String initList(){
		return "appshare/appshare_list";
	}
	
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String, Object> map = getSearchParams(request);
			int total = appshareToolService.getTotalCountByCondtion(map);
			resultMap.put("total", total);
			
			setCommParameters(request, map);
			List<AppshareDTO> list = appshareToolService.queryPageByCondition(map);
			for(AppshareDTO dto : list){
				if(StringUtils.isNotBlank(dto.getUpdateUserId())){
					SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getUpdateUserId());
					dto.setUpdateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
				}
			}
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.info("查询App分享记录异常", e);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}
	@RequestMapping(value="updateStatus/{shareId}",produces="application/json;charset=utf-8")
	@ResponseBody
	public String updateStatus(@PathVariable("shareId")Integer shareId, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String status = request.getParameter("giftStatu");
			AppshareDTO dto = new AppshareDTO();
			dto.setGiftStatu(status);
			dto.setUpdateTime(new Date());
			dto.setUpdateUserId(getUser(request) == null ? null : getUser(request).getUserID());
			dto.setShareId(shareId);
		
			appshareToolService.updateStatus(dto);
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("修改礼品发放状态异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "操作失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping("view/{memberId}")
	public String view(@PathVariable("memberId")Integer memberId){
		putModel("memberId", memberId);
		return "appshare/appshare_detail";
	}
	@RequestMapping("queryDetail")
	@ResponseBody
	public String queryDetail(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId",  request.getParameter("memberId"));
			int total = appshareToolService.getDetailTotalCountByCondtion(map);
			resultMap.put("total", total);
			
			setCommParameters(request, map);
			List<AppshareDTO> list = appshareToolService.queryDetailPageByCondition(map);
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.info("查询App分享详情异常", e);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}
	
	@RequestMapping(value="checkExportParams",produces="application/json;charset=utf-8")
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
			Map<String, Object> map = getSearchParams(request);
			Integer total = appshareToolService.getTotalCountByCondtion(map);
			if (total != null && total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("礼品导出参数验证异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "exportData")
	public void exportData(HttpServletRequest request, HttpServletResponse response){
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<AppshareDTO> list = null;
		try{
			Map<String, Object> map = getSearchParams(request);
			list = appshareToolService.queryListByCondition(map);
			if(list != null){
				for(AppshareDTO dto : list){
					if(StringUtils.isNotBlank(dto.getUpdateUserId())){
						SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getUpdateUserId());
						dto.setUpdateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
					}
				}
			}
		}catch(Exception e){
			logger.info("导出礼品兑换获取记录异常", e);
		}
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = new String("App分享记录".getBytes(), "ISO8859-1")+startDate+"_"+endDate;
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("App分享列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "用户账号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "真实姓名 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "手机号码 ");// 填充第一行第四个单元格的内容
				Label label30 = new Label(3, 0, "分享时间 ");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "推广链接所属市场");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "访问次数");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "礼品发放状态 ");// 填充第一行第四个单元格的内容
				Label label70 = new Label(7, 0, "操作员");// 填充第一行第五个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);// 将单元格加入表格
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);// 将单元格加入表格
				sheet.addCell(label60);
				sheet.addCell(label70);
				
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, len = list.size(); i < len; i++) {
						AppshareDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getAccount());
						Label label1 = new Label(1, i + 1, dto.getRealName());
						Label label2 = new Label(2, i + 1, dto.getMobile());
						Label label3 = new Label(3, i + 1, DateUtil.getDate(dto.getShareDate(), DateUtil.DATE_FORMAT_DATETIME));
						Label label4 = new Label(4, i + 1, dto.getMarketName());
						Label label5 = new Label(5, i + 1, dto.getViewCount()+"");
						Label label6 = new Label(6, i + 1, getStatusStr(dto.getGiftStatu()));
						Label label7 = new Label(7, i + 1, dto.getUpdateUserName());
						
						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);// 将单元格加入表格
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
	}
	
	/**
	 * 查询参数
	 * @param request
	 * @return
	 */
	private Map<String, Object> getSearchParams(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", request.getParameter("account"));
		map.put("mobile", request.getParameter("mobile"));
		map.put("realName", request.getParameter("realName"));
		map.put("marketId", request.getParameter("marketId"));
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(startDate)){
			map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
		}
		if(StringUtils.isNotBlank(endDate)){
			map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(endDate)));
		}
		return map;
	}
	
	private String getStatusStr(String status){
		if(StringUtils.isBlank(status)){
			return "";
		}
		String result = "";
		switch (status) {
		case "0":
			result = "未发";
			break;
			
		case "1":
			result = "已发";
			break;

		default:
			break;
		}
		return result;
	}
}
