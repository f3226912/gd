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
import com.gudeng.commerce.gd.admin.service.ActActivityBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.ActGiftExchangeToolService;
import com.gudeng.commerce.gd.admin.service.ReActivityGiftToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActGiftExchangeApplyDto;
import com.gudeng.commerce.gd.promotion.dto.ActReActivitityGiftDto;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.entity.ActActivityBaseinfoEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 礼品兑换记录
 * @author dengjianfeng
 *
 */
@RequestMapping("actGiftExchange")
@Controller
public class ActGiftExchangeController extends AdminBaseController{

	private final GdLogger logger = GdLoggerFactory.getLogger(ActGiftExchangeController.class);
	
	@Resource
	private ActGiftExchangeToolService actGiftExhangeToolService;
	
	@Resource
	private SysRegisterUserAdminService sysRegisterUserAdminService;
	
	@Resource
	private ReActivityGiftToolService reActivityGiftToolService;
	
	@Resource
	private ActActivityBaseinfoToolService actActivityBaseinfoToolService;
	
	@RequestMapping("list")
	public String initList(){
		return "actGiftExchange/actGiftExchange_list";
	}
	
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String, Object> map = getSearchParams(request);
			int total = actGiftExhangeToolService.getTotalCountByCondtion(map);
			resultMap.put("total", total);
			
			setCommParameters(request, map);
			List<ActGiftExchangeApplyDto> list = actGiftExhangeToolService.queryPageByCondition(map);
			for(ActGiftExchangeApplyDto dto : list){
				if(StringUtils.isNotBlank(dto.getCreateUserId())){
					SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getCreateUserId());
					dto.setCreateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
				}
			}
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.info("查询礼品兑换记录异常", e);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}
	
	@RequestMapping("add")
	public String add(){
		return "actGiftExchange/actGiftExchange_add";
	}
	
	@RequestMapping("saveAdd")
	@ResponseBody
	public String saveAdd(ActGiftExchangeApplyDto dto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			//检测活动兑换礼品次数，
			ActActivityBaseinfoDTO activityDTO = actActivityBaseinfoToolService.getById(dto.getActivity_id());
			if(activityDTO != null){
				//如果兑换次数有限制，判断用户是否兑换过礼品次数
				int exchangeTime = activityDTO.getExchangeTime() == null ? ActActivityBaseinfoEntity.EXCHANGE_TIME_NO_LIMIT : activityDTO.getExchangeTime();
				if(exchangeTime != ActActivityBaseinfoEntity.EXCHANGE_TIME_NO_LIMIT){
					int hasExchangeCount = actGiftExhangeToolService.hasExchangeGiftCount(dto.getActivity_id(), dto.getUserid());
					if(hasExchangeCount >= exchangeTime){
						resultMap.put("status", -1);
						resultMap.put("msg", "该用户兑换礼品次数已经超过限制了！");
						return JSONObject.toJSONString(resultMap);
					}
				}
			}
			
			//检测奖品余量
			ActReActivitityGiftDto reActivitityGiftDto = reActivityGiftToolService.getActivityGift(dto.getActivity_id(), dto.getGift_id());
			if(reActivitityGiftDto == null){
				resultMap.put("status", -1);
				resultMap.put("msg", "兑换的礼品不存在！");
				return JSONObject.toJSONString(resultMap);
			}
			Integer cost = reActivitityGiftDto.getCost();
			if(cost == null || cost <= 0){
				resultMap.put("status", -1);
				resultMap.put("msg", "礼品库存不足！");
				return JSONObject.toJSONString(resultMap);
			}
			
			//检测用户积分是否足够
			ActReUserActivityDto reUserActivityDto = actActivityBaseinfoToolService.getActivityUser(dto.getActivity_id()+"", dto.getUserid()+"");
			if(reUserActivityDto == null){
				resultMap.put("status", -1);
				resultMap.put("msg", "用户没有参与活动！");
				return JSONObject.toJSONString(resultMap);
			}
			int scoreNeeded = reActivitityGiftDto.getExchangeScore() == null ? 0 : reActivitityGiftDto.getExchangeScore();
			int userScore = reUserActivityDto.getScore() == null ? 0 : reUserActivityDto.getScore();
			int scoreLeft = userScore - scoreNeeded;
			if(scoreLeft < 0){
				resultMap.put("status", -1);
				resultMap.put("msg", "用户积分不足！");
				return JSONObject.toJSONString(resultMap);
			}
			dto.setScoreLeft(scoreLeft);//用户剩余积分
			dto.setCostLeft(cost-1);//礼品剩余库存
			dto.setCreateUserId(getUser(request) == null ? null : getUser(request).getUserID());
			actGiftExhangeToolService.addGiftExchangeRecord(dto);
			
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("保存礼品兑换记录异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "新增礼品兑换记录失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable("id")Integer id){
		try{
			ActGiftExchangeApplyDto dto = actGiftExhangeToolService.getById(id);
			putModel("dto", dto);
		}catch(Exception e){
			logger.info("查询礼品兑换记录详情异常", e);
		}
		return "actGiftExchange/actGiftExchange_add";
	}
	
	@RequestMapping("saveEdit")
	@ResponseBody
	public String saveEdit(ActGiftExchangeApplyDto dto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			ActGiftExchangeApplyDto giftExchangeApplyDto = actGiftExhangeToolService.getById(dto.getId());
			if(giftExchangeApplyDto == null){
				resultMap.put("status", -1);
				resultMap.put("msg", "编辑礼品兑换记录失败！");
				return JSONObject.toJSONString(resultMap);
			}
			
			//活动和礼品没修改，不需要走下面的流程
			if(giftExchangeApplyDto.getActivity_id().toString().equals(dto.getActivity_id().toString())
					&& giftExchangeApplyDto.getGift_id().toString().equals(dto.getGift_id().toString())){
				resultMap.put("status", 0);
				resultMap.put("msg", "success");
				return JSONObject.toJSONString(resultMap);
			}
			
			//检测奖品余量
			ActReActivitityGiftDto reActivitityGiftDto = reActivityGiftToolService.getActivityGift(dto.getActivity_id(), dto.getGift_id());
			if(reActivitityGiftDto == null){
				resultMap.put("status", -1);
				resultMap.put("msg", "兑换的礼品不存在！");
				return JSONObject.toJSONString(resultMap);
			}
			Integer cost = reActivitityGiftDto.getCost();
			if(cost == null || cost <= 0){
				resultMap.put("status", -1);
				resultMap.put("msg", "礼品库存不足！");
				return JSONObject.toJSONString(resultMap);
			}
			
			//检测用户积分是否足够
			ActReUserActivityDto reUserActivityDto = actActivityBaseinfoToolService.getActivityUser(dto.getActivity_id()+"", dto.getUserid()+"");
			if(reUserActivityDto == null){
				resultMap.put("status", -1);
				resultMap.put("msg", "用户没有参与活动！");
				return JSONObject.toJSONString(resultMap);
			}
			int scoreNeeded = reActivitityGiftDto.getExchangeScore() == null ? 0 : reActivitityGiftDto.getExchangeScore();
			int userScore = reUserActivityDto.getScore() == null ? 0 : reUserActivityDto.getScore();
			//把修改前的兑换礼品积分加回到用户积分
			ActReActivitityGiftDto oldReActivitityGiftDto = reActivityGiftToolService.getActivityGift(giftExchangeApplyDto.getActivity_id(), giftExchangeApplyDto.getGift_id());
			if(oldReActivitityGiftDto != null){
				int exchangeScore = oldReActivitityGiftDto.getExchangeScore() == null ? 0 : oldReActivitityGiftDto.getExchangeScore();
				userScore += exchangeScore;
			}
			int scoreLeft = userScore - scoreNeeded;
			if(scoreLeft < 0){
				resultMap.put("status", -1);
				resultMap.put("msg", "用户积分不足！");
				return JSONObject.toJSONString(resultMap);
			}
			
			dto.setScoreLeft(scoreLeft);//用户剩余积分
			
			//修改后的活动礼品库存减1
			dto.setCostLeft(cost-1);
			
			//修改前的活动礼品库存加1
			int oldCostLeft = oldReActivitityGiftDto.getCost() == null ? 0 : oldReActivitityGiftDto.getCost();
			dto.setOldCostLeft(oldCostLeft+1);
			dto.setOldActivityId(giftExchangeApplyDto.getActivity_id());
			dto.setOldGiftId(giftExchangeApplyDto.getGift_id());
			dto.setUpdateUserId(getUser(request) == null ? null : getUser(request).getUserID());
			actGiftExhangeToolService.updateGiftExchangeRecord(dto);
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("编辑礼品兑换记录异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "编辑礼品兑换记录失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value="updateStatus/{id}",produces="application/json;charset=utf-8")
	@ResponseBody
	public String updateStatus(@PathVariable("id")Integer id, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String status = request.getParameter("status");
			ActGiftExchangeApplyDto dto = actGiftExhangeToolService.getById(id);
			if(dto == null){
				resultMap.put("status", -1);
				resultMap.put("msg", "礼品兑换记录不存在！");
				return JSONObject.toJSONString(resultMap);
			}
			dto.setStatus(status);
			dto.setUpdateTime(new Date());
			dto.setUpdateUserId(getUser(request) == null ? null : getUser(request).getUserID());
		
			actGiftExhangeToolService.updateStatus(dto);
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("修改礼品兑换记录状态异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "操作失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping("view/{id}")
	public String view(@PathVariable("id")Integer id){
		try{
			ActGiftExchangeApplyDto dto = actGiftExhangeToolService.getById(id);
			if(dto != null){
				if(StringUtils.isNotBlank(dto.getCreateUserId())){
					SysRegisterUser createUser = sysRegisterUserAdminService.get(dto.getCreateUserId());
					dto.setCreateUserName(createUser == null ? null : createUser.getUserName());
				}
				
				if(StringUtils.isNotBlank(dto.getUpdateUserId())){
					SysRegisterUser updateUser = sysRegisterUserAdminService.get(dto.getUpdateUserId());
					dto.setUpdateUserName(updateUser == null ? null : updateUser.getUserName());
				}
			}
			putModel("dto", dto);
		}catch(Exception e){
			logger.info("查询礼品兑换记录详情异常", e);
		}
		return "actGiftExchange/actGiftExchange_view";
	}
	
	@RequestMapping("/memberSelect")
	public String memberSelect(HttpServletRequest request){
		return "actGiftExchange/memberSelect";
	}
	
	@RequestMapping("/giftSelect")
	public String giftSelect(HttpServletRequest request){
		putModel("activityId", request.getParameter("activityId"));
		return "actGiftExchange/giftSelect";
	}
	
	@RequestMapping("queryGiftList")
	@ResponseBody
	public String queryGiftList(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("activityId", request.getParameter("activityId"));
			map.put("giftName", request.getParameter("giftName"));
			//总记录数
			int total = reActivityGiftToolService.getActivityGiftTotal(map);
			resultMap.put("total", total);
			//查询列表数据
			setCommParameters(request, map);
			List<ActReActivitityGiftDto> list = reActivityGiftToolService.queryActivityGiftPage(map);
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.info("查询礼品列表异常", e);
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
			Integer total = actGiftExhangeToolService.getTotalCountByCondtion(map);
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
		List<ActGiftExchangeApplyDto> list = null;
		try{
			Map<String, Object> map = getSearchParams(request);
			list = actGiftExhangeToolService.queryListByCondition(map);
			if(list != null){
				for(ActGiftExchangeApplyDto dto : list){
					if(StringUtils.isNotBlank(dto.getCreateUserId())){
						SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getCreateUserId());
						dto.setCreateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
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
			String fileName  = new String("礼品兑换记录列表".getBytes(), "ISO8859-1")+startDate+"_"+endDate;
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("礼品兑换记录列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "会员账号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "手机号码 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "活动编号 ");// 填充第一行第四个单元格的内容
				Label label30 = new Label(3, 0, "活动名称 ");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "礼品名称");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "礼品发放状态");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "礼品发放时间 ");// 填充第一行第四个单元格的内容
				Label label70 = new Label(7, 0, "创建时间");// 填充第一行第五个单元格的内容
				Label label80 = new Label(8, 0, "创建人");// 填充第一行第五个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);// 将单元格加入表格
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);// 将单元格加入表格
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, len = list.size(); i < len; i++) {
						ActGiftExchangeApplyDto dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getAccount());
						Label label1 = new Label(1, i + 1, dto.getMobile());
						Label label2 = new Label(2, i + 1, dto.getActivity_id()+"");
						Label label3 = new Label(3, i + 1, dto.getActivityName());
						Label label4 = new Label(4, i + 1, dto.getGiftName());
						Label label5 = new Label(5, i + 1, getStatusStr(dto.getStatus()));
						Label label6 = new Label(6, i + 1, DateUtil.getDate(dto.getSend_time(), DateUtil.DATE_FORMAT_DATETIME));
						Label label7 = new Label(7, i + 1, DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label8 = new Label(8, i + 1, dto.getCreateUserName());
						
						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);// 将单元格加入表格
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
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
		map.put("activityName", request.getParameter("activityName"));
		map.put("giftName", request.getParameter("giftName"));
		map.put("status", request.getParameter("status"));

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
		case "1":
			result = "未发";
			break;
			
		case "2":
			result = "已发";
			break;
			
		case "3":
			result = "不予发放";
			break;

		default:
			break;
		}
		return result;
	}
}