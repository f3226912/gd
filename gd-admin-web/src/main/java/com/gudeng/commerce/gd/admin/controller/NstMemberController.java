package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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
import com.gudeng.commerce.gd.admin.dto.MemberQueryBean;
import com.gudeng.commerce.gd.admin.service.AreaSettingToolService;
import com.gudeng.commerce.gd.admin.service.AreaToolService;
import com.gudeng.commerce.gd.admin.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.IntegralToolService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.MemberCertifiToolService;
import com.gudeng.commerce.gd.admin.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstMemberBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("nst_member")
public class NstMemberController extends AdminBaseController{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(NstMemberController.class);
	
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Autowired
	public MemberCertifiToolService memberCertifiToolService;
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService;
	
	@Autowired
	public AreaToolService areaToolService;
	
	@Autowired
	public AreaSettingToolService areaSettingToolService;
	
	@Autowired
	public IntegralToolService integralToolService;
	
	/**
	 * @return
	 */
	@RequestMapping("index")
	public String member(HttpServletRequest request){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			List<AreaSettingDTO> list = areaSettingToolService.getAllAreaName(map);
			request.setAttribute("areaNameList", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "nst_member/nst_memberBaseinfo_list";
	}
	 
	
	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		try {
            
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			//记录数
			map.put("total", memberBaseinfoToolService.getNstTotalBySearch(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<NstMemberBaseinfoDTO> list = memberBaseinfoToolService.getNstListBySearch(map);
			if(list !=null && list.size()>0)
			{
				for(NstMemberBaseinfoDTO dto : list)
				{
				  setDisplayValue(dto);
				}
			}
			
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	

	/**
	 * 打开show页面
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="showbyid/{memberId}")
    public String showbyid(@PathVariable("memberId") String memberId, HttpServletRequest request){      
		try {
			
			/** 获取会员信息*/
			NstMemberBaseinfoDTO dto=memberBaseinfoToolService.getNstMemberById(memberId);
			setDisplayValue(dto);
			putModel("dto",dto); 
			return "nst_member/nst_memberBaseinfo_show";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 打开修改推荐人手机号码页面
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="editMobileByid/{memberId}")
    public String editMobileByid(@PathVariable("memberId") String memberId){      
		try {
			String id = memberId.split("-")[0];
			String mobile = memberId.split("-")[1];
			putModel("id",id);
			putModel("mobile",mobile);
			return "nst_member/nst_memberBaseinfo_edit";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改推荐人手机号码
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save")
	@ResponseBody
    public String save(HttpServletRequest request){      
		try {
			String mobile =request.getParameter("phone");
			MemberBaseinfoDTO member =memberBaseinfoToolService.getByMobile(mobile);
			if(member !=null)
			{
				Long memberId = member.getMemberId();
				String memeberId_ed = request.getParameter("memberId");
				integralToolService.updateIntegralMemberId(memberId, Long.parseLong(memeberId_ed));
				return "success";
			}else{
			  return "not exist";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
    public String queryBysearch(MemberQueryBean mqb, HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("mobile", mqb.getMobile());
			map.put("account", mqb.getAccount());
			map.put("level", mqb.getLevel());
			map.put("isAuth", mqb.getIsAuth());
			map.put("startDate", mqb.getStartDate());
			map.put("endDate", mqb.getEndDate());
			map.put("regetype", mqb.getRegetype());
			map.put("status", mqb.getStatus());
			map.put("areaName", request.getParameter("areaName"));
			map.put("loginStartDate", request.getParameter("loginStartDate"));
			map.put("loginEndDate", request.getParameter("loginEndDate"));
			if (StringUtils.isNotEmpty(request.getParameter("cityName"))) {
				AreaDTO area = areaToolService.getByAreaName(request.getParameter("cityName"));
				// 设置查询参数
				if (area != null) {
					map.put("cCityId", area.getAreaID());
				} else {
					map.put("cCityId", "000000");
				}
			}
			//记录数
			map.put("total", memberBaseinfoToolService.getNstTotalBySearch(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<NstMemberBaseinfoDTO> list = memberBaseinfoToolService.getNstListBySearch(map);	
			if(list !=null && list.size()>0)
			{
				for(NstMemberBaseinfoDTO dto : list)
				{
				  setDisplayValue(dto);
				}
			}
			map.put("rows", list);//rows键 存放每页记录 list  
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)){
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
	
		String loginStartDate = request.getParameter("loginStartDate");
		String loginEndDate = request.getParameter("loginEndDate");
		if(StringUtils.isNotBlank(loginStartDate) || StringUtils.isNotBlank(loginEndDate)){
			if (DateUtil.isDateIntervalOverFlow(loginStartDate, loginEndDate, 31)){
				resultMap.put("status", 0);
				resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(resultMap);
			}
		}
		
		if(StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate) && StringUtils.isBlank(loginStartDate) && StringUtils.isBlank(loginEndDate)){
			resultMap.put("status", 0);
			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
			return JSONObject.toJSONString(resultMap);
		}
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account",request.getParameter("account"));
			map.put("mobile", request.getParameter("mobile"));
			map.put("level", request.getParameter("level"));
			map.put("isAuth", request.getParameter("isAuth"));
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("areaName", request.getParameter("areaName"));
			map.put("regetype",request.getParameter("regetype"));
			map.put("loginStartDate", loginStartDate);
			map.put("loginEndDate", loginEndDate);
			if (StringUtils.isNotEmpty(request.getParameter("cityName"))) {
				AreaDTO area = areaToolService.getByAreaName(request.getParameter("cityName"));
				// 设置查询参数
				if (area != null) {
					map.put("cCityId", area.getAreaID());
				} else {
					map.put("cCityId", "000000");
				}
			}
			int total = memberBaseinfoToolService.getNstTotalBySearch(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("物流管理-会员导出异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String account, String mobile,  String level,  String isAuth,String areaName,String startDate, String endDate,String regetype) {
		String loginStartDate = request.getParameter("loginStartDate");
		String loginEndDate = request.getParameter("loginEndDate");
		List<NstMemberBaseinfoDTO> list =null;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account",account);
			map.put("mobile", mobile);
			map.put("level", level);
			map.put("isAuth", isAuth);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("areaName", areaName);
			map.put("startRow", 0);
			map.put("endRow", 99999999);
			map.put("regetype",regetype);
			map.put("loginStartDate", loginStartDate);
			map.put("loginEndDate", loginEndDate);
			if (StringUtils.isNotEmpty(request.getParameter("cityName"))) {
				AreaDTO area = areaToolService.getByAreaName(request.getParameter("cityName"));
				// 设置查询参数
				if (area != null) {
					map.put("cCityId", area.getAreaID());
				} else {
					map.put("cCityId", "000000");
				}
			}
			list = memberBaseinfoToolService.getNstListBySearch(map);	
			if(list !=null && list.size()>0)
			{
				for(NstMemberBaseinfoDTO dto : list)
				{
				  setDisplayValue(dto);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = null;
			if(StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)){
				fileName = new String("农速通会员列表".getBytes(), "ISO8859-1")+startDate+"_"+endDate;
			}else if(StringUtils.isNotBlank(loginStartDate) || StringUtils.isNotBlank(endDate)){
				fileName = new String("农速通会员列表".getBytes(), "ISO8859-1")+loginStartDate+"_"+loginEndDate;
			}else{
				fileName = new String("农速通会员列表".getBytes(), "ISO8859-1");
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");			
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("会员列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "用户类型");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "账号");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "手机号");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "昵称");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "注册时间");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "所属区域");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "认证状态");// 填充第一行第六个单元格的内容
				Label label70 = new Label(7, 0, "激活状态");// 填充第一行第七个单元格的内容
				Label label80 = new Label(8, 0, "常用城市");// 填充第一行第八个单元格的内容
				Label label90 = new Label(9, 0, "个人/企业");// 填充第一行第9个单元格的内容
				Label label100 = new Label(10, 0, "第一次登陆时间");// 填充第一行第10个单元格的内容
        		Label label110 = new Label(11, 0, "推荐人姓名");// 
        		Label label120 = new Label(12, 0, "推荐人手机号码");// 

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
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						NstMemberBaseinfoDTO memberDTO = list.get(i);
						Label label0 =new Label(0, i + 1, memberDTO.getUserTypeName());
						Label label1 = new Label(1, i + 1, memberDTO.getAccount());
						Label label2 = new Label(2, i + 1, memberDTO.getMobile());
						Label label3 = new Label(3, i + 1, memberDTO.getNickName());
						Label label4 = new Label(4, i + 1, memberDTO.getCreateTime()==null?"":time.format(memberDTO.getCreateTime()));
						Label label5 = new Label(5, i + 1, memberDTO.getAreaName());
						Label label6 = new Label(6, i + 1, memberDTO.getNstStatus());
						Label label7 =new Label(7, i + 1, memberDTO.getAccountStatus());
						Label label8 =new Label(8, i + 1, memberDTO.getCityName());
						//用户类型
						Label label9 =new Label(9, i + 1, memberDTO.getNstUserType());
						Label label101 =new Label(10, i + 1, memberDTO.getNstCreateTime()==null?"":time.format(memberDTO.getNstCreateTime()));
						Label label102 =new Label(11, i + 1, memberDTO.getRefereeRealName());
						Label label103 =new Label(12, i + 1, memberDTO.getRefereeMobile());

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
						sheet.addCell(label101);
						sheet.addCell(label102);
						sheet.addCell(label103);
						
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.warn("Exception :"+e1.getMessage());
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
	
	
	private void setDisplayValue(NstMemberBaseinfoDTO dto) {
		if (dto.getLevel() != null) {
			if (dto.getLevel() == 1) {
				dto.setUserTypeName("谷登农批");
			} else if (dto.getLevel() == 2) {
				dto.setUserTypeName("农速通");
			} else if (dto.getLevel() == 3) {
				dto.setUserTypeName("农商友");
			} else if (dto.getLevel() == 4) {
				dto.setUserTypeName("产地供应商");
			} else if (dto.getLevel() == 5) {
				dto.setUserTypeName("农批友");
			}
		}

		if ("0".equals(dto.getNstStatus())) {
			dto.setNstStatus("待认证");
		} else if ("1".equals(dto.getNstStatus())) {
			dto.setNstStatus("已认证");
		} else if ("2".equals(dto.getNstStatus())) {
			dto.setNstStatus("已驳回");
		} else {
			dto.setNstStatus("未提交认证");
		}

		if (dto.getStatus() != null && dto.getStatus().equals("1")) {
			dto.setAccountStatus("启用");
		} else {
			dto.setAccountStatus("禁用");
		}

		if (dto.getUserType() != null) {
			if (1 == dto.getUserType()) {
				dto.setNstUserType("个人");
				dto.setCompanyName("");
			} else if (2 == dto.getUserType()) {
				dto.setNstUserType("企业");
			}
		}
		try {
			AreaDTO areaDTO = areaToolService.getArea(String.valueOf(dto.getcCityId()));
			dto.setCityName(areaDTO != null ? areaDTO.getArea() : "");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
