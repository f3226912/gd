package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.IntegralScoreToolService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActActivityScoreRecordDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 积分获取记录
 * @author dengjianfeng
 *
 */
@Controller
@RequestMapping("activityintegral")
public class PromotionIntegralController extends AdminBaseController{
	
	private final GdLogger logger = GdLoggerFactory.getLogger(PromotionIntegralController.class);
	
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Autowired
	public IntegralScoreToolService integralScoreToolService;
	
	@Autowired
	public SysRegisterUserAdminService sysRegisterUserAdminService;
	
	@RequestMapping("list")
	public String initList(){
		return "actIntegral/integeral_list";
	}
	
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
	//		System.out.println(request.getParameter("type"));
			Map<String, Object> map = getSearchParams(request);
			Integer total = integralScoreToolService.getTotal(map);
			resultMap.put("total", total);
			
			setCommParameters(request, map);
			List<ActActivityScoreRecordDTO> list = integralScoreToolService.queryPageByCondition(map);
			for(ActActivityScoreRecordDTO dto : list){
				SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getCreateUserId());
				dto.setCreateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
			}
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.info("查询积分获取记录异常", e);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 进入列表页面
	 * @return
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request){
		return "actIntegral/integral_add";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id){
		try{
			ActActivityScoreRecordDTO dto = integralScoreToolService.getById(id);
			putModel("dto", dto);
		}catch(Exception e){
			logger.info("查询记录记录异常", e);
		}
		return "actIntegral/integral_add";
	}
	

	@RequestMapping("saveAdd")
	@ResponseBody
	public String saveAdd(ActActivityScoreRecordDTO dto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getById(dto.getUserid()+"");
			if(memberBaseinfoDTO != null){
				dto.setAccount(memberBaseinfoDTO.getAccount());
				dto.setMobile(memberBaseinfoDTO.getMobile());
			}
			SysRegisterUser sysRegisterUser = getUser(request);
			dto.setCreateUserId(sysRegisterUser == null ? null : sysRegisterUser.getUserID());
			integralScoreToolService.addIntegralRecord(dto);
			
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("保存积分记录异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "操作失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping("saveEdit")
	@ResponseBody
	public String saveEdit(ActActivityScoreRecordDTO dto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			SysRegisterUser sysRegisterUser = getUser(request);
			dto.setUpdateUserId(sysRegisterUser == null ? null : sysRegisterUser.getUserID());
			int result = integralScoreToolService.updateIntegralRecord(dto);
			if(result < 1){
				resultMap.put("status", -1);
				resultMap.put("msg", "操作失败");
			}else{
				resultMap.put("status", 0);
				resultMap.put("msg", "success");
			}
			
		}catch(Exception e){
			logger.info("保存积分记录异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "操作失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable("id") Integer id){
		try{
			ActActivityScoreRecordDTO dto = integralScoreToolService.getById(id);
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
			logger.info("查询记录记录异常", e);
		}
		return "actIntegral/integral_view";
	}
	
	@RequestMapping("/memberSelect")
	public String memberSelect(HttpServletRequest request){
		return "actIntegral/memberSelect";
	}
	
	@ResponseBody
	@RequestMapping("/queryMemberList")
	public String query(HttpServletRequest request, String status,String account, String userGroup){
		try {
			String id = request.getParameter("id");

			Map<String, Object> map = new HashMap<String, Object>();
			if(null==id || id.equals("")){
			}else{
				map.put("id", id);
			}
			// 设置查询参数
			map.put("status", status);
			map.put("account", account);
			map.put("userGroup", userGroup);
			//记录数
			map.put("total", memberBaseinfoToolService.getMemberSelectTotalByCondition(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<MemberBaseinfoDTO> list = memberBaseinfoToolService.queryMemberSelectPageByCondition(map);
			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {

		}
		return null;
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
			Integer total = integralScoreToolService.getTotal(map);
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
		List<ActActivityScoreRecordDTO> list = null;
		try{
			Map<String, Object> map = getSearchParams(request);
			list = integralScoreToolService.queryListByCondition(map);
			if(list != null){
				for(ActActivityScoreRecordDTO giftDto : list){
					SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(giftDto.getCreateUserId());
					giftDto.setCreateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
				}
			}
		}catch(Exception e){
			logger.info("导出积分获取记录异常", e);
		}
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = new String("积分获取记录列表".getBytes(), "ISO8859-1")+startDate+"_"+endDate;
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("积分获取记录列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "会员账号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "手机号码 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "活动编号 ");// 填充第一行第四个单元格的内容
				Label label30 = new Label(3, 0, "活动名称 ");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "所获积分");// 填充第一行第七个单元格的内容
				Label label50= new Label(5, 0, "积分来源");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "积分获取时间 ");// 填充第一行第四个单元格的内容
				Label label70 = new Label(7, 0, "创建人");// 填充第一行第五个单元格的内容
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
						ActActivityScoreRecordDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getAccount());
						Label label1 = new Label(1, i + 1, dto.getMobile());
						Label label2 = new Label(2, i + 1, dto.getActivityId()+"");
						Label label3 = new Label(3, i + 1, dto.getActivityName());
						Label label4 = new Label(4, i + 1, dto.getScore()+"");
						Label label5 = new Label(5, i + 1, dto.getTypeValue());
						Label label6 = new Label(6, i + 1, DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
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
	 * 查询参数
	 * @param request
	 * @return
	 */
	private Map<String, Object> getSearchParams(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", request.getParameter("account"));
		map.put("mobile", request.getParameter("mobile"));
		map.put("activityName", request.getParameter("activityName"));
		map.put("type", request.getParameter("type"));
		if(request.getParameter("type")!=null){
		   if(request.getParameter("type").equals("4")){
			  map.put("flag", "0"); 
		   }	
		   else   if(request.getParameter("type").equals("5")){
				  map.put("flag", "1"); 
		   }else{
				  map.put("flag", "-1"); 
 
		   }
			   
		}

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

//	@RequestMapping(value="save" )
//	@ResponseBody
//    public String saveOrUpdate(ActActivityScoreRecordDTO dto, HttpServletRequest request){      
//		try {
//			
//			String memberAccount=request.getParameter("memberAccount");
//			String activityId=request.getParameter("activityId");
//			String integralNum=request.getParameter("integralNum");
//			
//			Map map =new HashMap();
//			map.put("id", dto.getId());
//			int count= integralScoreToolService.getTotal(map);// 查询用户是否存在
//			if(count>0){//根据id判断是否存在，存在即为更新，不存在即为增加
//				dto.setActivity_id(Integer.valueOf(activityId));
//				dto.setUserid(Long.valueOf(memberAccount));
//				dto.setScore(Integer.valueOf(integralNum));
//				dto.setUpdateTime(new Date());//设置updateTime
//				integralScoreToolService.updatentegralDto(dto);
//			}else{
//				ActActivityScoreRecordEntity bb=new ActActivityScoreRecordEntity();
//				bb.setActivity_id(Integer.valueOf(activityId));
//				bb.setUserid(Long.valueOf(memberAccount));
//				bb.setScore(Integer.valueOf(integralNum));
//				bb.setCreateTime(new Date());
//				integralScoreToolService.addIntegralEntity(bb);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
