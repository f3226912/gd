package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.gudeng.commerce.gd.admin.service.ActActivityBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.ActGiftBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.ReActivityGiftToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActGiftBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActReActivitityGiftDto;
import com.gudeng.commerce.gd.promotion.entity.ActReActivitityGiftEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("actActivity")
public class ActActivityBaseinfoController extends AdminBaseController{

	private final GdLogger logger = GdLoggerFactory.getLogger(ActActivityBaseinfoController.class);
	
	@Resource
	private ActActivityBaseinfoToolService actActivityBaseinfoToolService;
	
	@Resource
	private ActGiftBaseinfoToolService actGiftBaseinfoToolService;
	
	@Resource
	private ReActivityGiftToolService reActivityGiftToolService;
	
	@Resource
	private SysRegisterUserAdminService sysRegisterUserAdminService;
	
	@RequestMapping("list")
	public String initList(){
		return "actActivity/actActivity_list";
	}
	
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String, Object> map = getSearchMapParams(request);
			Integer total = actActivityBaseinfoToolService.getTotalCountByCondition(map);
			resultMap.put("total", total);
			
			setCommParameters(request, map);
			List<ActActivityBaseinfoDTO> list = actActivityBaseinfoToolService.queryPageByCondition(map);
			
			for(ActActivityBaseinfoDTO dto : list){
				SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getCreateUserId());
				dto.setCreateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
			}
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.info("查询活动列表异常",e);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}
	
	@RequestMapping("add")
	public String add(){
		return "actActivity/actActivity_add";
	}
	
	@RequestMapping("saveAdd")
	@ResponseBody
	public String saveAdd(ActActivityBaseinfoDTO dto, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String[] giftIds = request.getParameterValues("giftId");
			String[] costs = request.getParameterValues("cost");
			String[] exchangeScores = request.getParameterValues("exchangeScore");
			
			if(giftIds == null || giftIds.length == 0){
				resultMap.put("status", -1);
				resultMap.put("msg", "至少需要选择1项礼品！");
				return JSONObject.toJSONString(resultMap);
			}
			
			//礼品-活动关联实体
			List<ActReActivitityGiftDto> reActivityGiftList = new ArrayList<ActReActivitityGiftDto>();
			for(int i = 0, len = giftIds.length; i < len; i++){
				ActReActivitityGiftDto reActivityGift = new ActReActivitityGiftDto();
				reActivityGift.setGiftId(Integer.parseInt(giftIds[i]));
				reActivityGift.setCost(Integer.parseInt(costs[i]));
				reActivityGift.setExchangeScore(Integer.parseInt(exchangeScores[i]));
				reActivityGiftList.add(reActivityGift);
			}
			dto.setReActivityGiftList(reActivityGiftList);
			
			//验证礼品可用库存是否足够
			for(ActReActivitityGiftEntity entity : reActivityGiftList){
				String validateAvailableStockResult = stockAvailableValidate(entity.getGiftId(), entity.getCost());
				if(validateAvailableStockResult != null){
					resultMap.put("status", -1);
					resultMap.put("msg", validateAvailableStockResult);
					return JSONObject.toJSONString(resultMap);
				}
			}
			
			dto.setTimes(1);//默认值
			SysRegisterUser sysRegisterUser = getUser(request);//当前登录用户
			dto.setCreateUserId(sysRegisterUser == null ? null : sysRegisterUser.getUserID());
			actActivityBaseinfoToolService.addActivity(dto);
			
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("保存活动信息异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "保存活动信息异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	/**
	 * 礼品库存是否足够
	 * @return
	 * @throws Exception 
	 */
	private String stockAvailableValidate(Integer giftId, Integer cost) throws Exception{
		Map<String, Object> giftAvailableMap = new HashMap<String, Object>();
		giftAvailableMap.put("id", giftId);
		ActGiftBaseinfoDTO giftDTO = actGiftBaseinfoToolService.getById(giftId);
		if(giftDTO != null){
			int stockAvailable = giftDTO.getStockAvailable() == null ? 0 : giftDTO.getStockAvailable();
			if(stockAvailable < cost){
				return "礼品：‘"+giftDTO.getName()+"’当前可用库存为"+stockAvailable+",活动预算不能大于当前可用库存";
			}
		}
		return null;
	}
	
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable("id") Integer id){
		try {
			ActActivityBaseinfoDTO dto = actActivityBaseinfoToolService.getById(id);
			if(dto != null){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("activityId", dto.getId());
				List<ActReActivitityGiftDto> reActivityGiftList = reActivityGiftToolService.getActivityGiftList(params);
				dto.setReActivityGiftList(reActivityGiftList);
			}
			putModel("dto", dto);
		} catch (Exception e) {
			logger.info("编辑活动时，查询活动异常", e);
		}
		return "actActivity/actActivity_edit";
	}
	
	@RequestMapping("giftSelect")
	public String giftSelect(HttpServletRequest request){
		String giftRowId = request.getParameter("giftRowId");
		putModel("giftRowId", giftRowId);
		return "actActivity/gift_select";
		
	}
	
	@RequestMapping(value="delete/{id}", produces="application/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("id")Integer id, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			ActActivityBaseinfoDTO dto = new ActActivityBaseinfoDTO();
			dto.setId(id);
			SysRegisterUser sysRegisterUser = getUser(request);
			dto.setUpdateUserId(sysRegisterUser == null ? null : sysRegisterUser.getUserID());
			actActivityBaseinfoToolService.delete(dto);
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("删除活动异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "删除活动失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping("saveEdit")
	@ResponseBody
	public String saveEdit(ActActivityBaseinfoDTO dto, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String startDate = DateUtil.getDate(dto.getEffectiveStartTime(), DateUtil.DATE_FORMAT_DATETIME);
			String endDate=DateUtil.getDate(dto.getEffectiveEndTime(), DateUtil.DATE_FORMAT_DATETIME);
			dto.setEffectiveStartTimeStr(startDate);
			dto.setEffectiveEndTimeStr(endDate);
			
			String[] reActivityGiftIds = request.getParameterValues("reActivityGiftId");
			String[] giftIds = request.getParameterValues("giftId");
			String[] costs = request.getParameterValues("cost");
			String[] exchangeScores = request.getParameterValues("exchangeScore");
			if(reActivityGiftIds != null){
				//礼品-活动关联实体
				List<ActReActivitityGiftDto> reActivityGiftList = new ArrayList<ActReActivitityGiftDto>();
				for(int i = 0, len = reActivityGiftIds.length; i < len; i++){
					ActReActivitityGiftDto reActivityGift = new ActReActivitityGiftDto();
					reActivityGift.setId(Integer.parseInt(reActivityGiftIds[i]));
					reActivityGift.setGiftId(Integer.parseInt(giftIds[i]));
					reActivityGift.setCost(Integer.parseInt(costs[i]));
					reActivityGift.setExchangeScore(Integer.parseInt(exchangeScores[i]));
					reActivityGiftList.add(reActivityGift);
				}
				dto.setReActivityGiftList(reActivityGiftList);
					
				for(ActReActivitityGiftDto reActivityGiftDTO : reActivityGiftList){
					//计算当前礼品活动预算修改前后的相差值
					int curCost = reActivityGiftToolService.getCostById(reActivityGiftDTO.getId());
					int diffCost = reActivityGiftDTO.getCost() - curCost;
					//如果预算增加，验证礼品可用库存是否足够
					if(diffCost > 0){
						String validateAvailableStockResult = stockAvailableValidate(reActivityGiftDTO.getGiftId(), diffCost);
						if(validateAvailableStockResult != null){
							resultMap.put("status", -1);
							resultMap.put("msg", validateAvailableStockResult);
							return JSONObject.toJSONString(resultMap);
						}
					}
					reActivityGiftDTO.setDiffCost(diffCost);
				}
			}
			SysRegisterUser sysRegisterUser = getUser(request);//当前登录用户
			dto.setCreateUserId(sysRegisterUser == null ? null : sysRegisterUser.getUserID());
			actActivityBaseinfoToolService.updateActivity(dto);
			
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("编辑活动异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "编辑活动失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value="updateStatus/{id}",produces="application/json;charset=utf-8")
	@ResponseBody
	public String updateStatus(@PathVariable("id")Integer id, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			ActActivityBaseinfoDTO dto = actActivityBaseinfoToolService.getById(id);
			if(dto == null){
				resultMap.put("status", -1);
				resultMap.put("msg", "活动信息不存在！");
				return JSONObject.toJSONString(resultMap);
			}
			//当积分兑换截止时间小于当前时间时，提示活动开启失败，请修改时间
			Date effectiveEndTime = dto.getEffectiveEndTime();
			String fmt = "yyyyMMdd";
			double daysBetween = DateUtil.getBetweenDays(DateUtil.getDate(new Date(), fmt), DateUtil.getDate(effectiveEndTime, fmt), fmt);
			if(daysBetween <= 0){
				resultMap.put("status", -1);
				resultMap.put("msg", "活动开启失败，积分兑换截止时间小于当前时间,请修改时间！");
				return JSONObject.toJSONString(resultMap);
			}
			
			dto.setLaunch(request.getParameter("launch"));
			SysRegisterUser sysRegisterUser = getUser(request);
			dto.setUpdateUserId(sysRegisterUser == null ? null : sysRegisterUser.getUserID());
			actActivityBaseinfoToolService.updateLaunch(dto);
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("操作活动异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "操作失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping("view/{id}")
	public String view(@PathVariable("id") Integer id){
		try {
			ActActivityBaseinfoDTO dto = actActivityBaseinfoToolService.getById(id);
			if(dto != null){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("activityId", dto.getId());
				List<ActReActivitityGiftDto> reActivityGiftList = reActivityGiftToolService.getActivityGiftList(params);
				dto.setReActivityGiftList(reActivityGiftList);
			}
			putModel("dto", dto);
		} catch (Exception e) {
			logger.info("查询活动异常", e);
		}
		return "actActivity/actActivity_view";
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
			Map<String, Object> map = getSearchMapParams(request);
			Integer total = actActivityBaseinfoToolService.getTotalCountByCondition(map); 
			if (total != null && total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("活动导出参数验证异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "exportData")
	public void exportData(HttpServletRequest request, HttpServletResponse response){
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<ActActivityBaseinfoDTO> list = null;
		try{
			Map<String, Object> map = getSearchMapParams(request);
			list = actActivityBaseinfoToolService.queryListByCondition(map);
			if(list != null){
				for(ActActivityBaseinfoDTO dto : list){
					SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getCreateUserId());
					dto.setCreateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
				}
			}
		}catch(Exception e){
			logger.info("导出活动数据异常", e);
		}
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = new String("活动列表".getBytes(), "ISO8859-1")+startDate+"_"+endDate;
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("活动列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "活动编号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "活动名称");// 填充第一行第一个单元格的内容
				Label label20 = new Label(2, 0, "活动类型 ");// 填充第一行第二个单元格的内容
				Label label30 = new Label(3, 0, "活动渠道 ");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "参与用户群");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "创建时间");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "活动状态");// 填充第一行第七个单元格的内容
				Label label70 = new Label(7, 0, "活动创建人");// 填充第一行第七个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);// 将单元格加入表格
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, len = list.size(); i < len; i++) {
						ActActivityBaseinfoDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getId()+"");
						Label label1 = new Label(1, i + 1, dto.getName());
						Label label2 = new Label(2, i + 1, getTypeStr(dto.getType()));
						Label label3 = new Label(3, i + 1, getChannelStr(dto.getChannel()));
						Label label4 = new Label(4, i + 1, getUerGroupStr(dto.getUserGroup()));
						Label label5 = new Label(5, i + 1, DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label6 = new Label(6, i + 1, getLaunchStr(dto.getLaunch()));
						Label label7 = new Label(7, i + 1, dto.getCreateUserName());
					
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
	 * 查询列表参数
	 * @param request
	 * @return
	 */
	private Map<String, Object> getSearchMapParams(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", request.getParameter("name"));
		map.put("type", request.getParameter("type"));
		map.put("channel", request.getParameter("channel"));
		map.put("userGroup", request.getParameter("userGroup"));
		
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
	
	private String getTypeStr(Integer type){
		if(type == null){
			return "";
		}
		String result = "";
		switch (type) {
		case 1:
			result = "刮刮卡";
			break;
		case 2:
			result = "幸运大转盘";
			break;
		case 3:
			result = "摇一摇";
			break;
		case 4:
			result = "疯狂抢红包";
			break;
		case 5:
			result = "砸金蛋";
			break;
		default:
			break;
		}
		return result;
	}
	
	private String getChannelStr(Integer channel){
		if(channel == null){
			return "";
		}
		String result = "";
		switch (channel) {
		case 1:
			result = "H5-农商友";
			break;
		default:
			break;
		}
		return result;
	}
	
	private String getUerGroupStr(Integer userGroup){
		if(userGroup == null){
			return "";
		}
		String result = "";
		switch (userGroup) {
		case 1:
			result = "微信绑定用户";
			break;
		default:
			break;
		}
		return result;
	}
	
	private String getLaunchStr(String launch){
		if(launch == null){
			return "";
		}
		String result = "";
		switch (launch) {
		case "0":
			result = "结束";
			break;
		case "1":
			result = "启用";
			break;
		case "2":
			result = "等待";
			break;
		default:
			break;
		}
		return result;
	}
}
