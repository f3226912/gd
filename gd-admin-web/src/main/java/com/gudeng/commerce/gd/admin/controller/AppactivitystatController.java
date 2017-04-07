package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AppChannelLinkToolService;
import com.gudeng.commerce.gd.admin.service.AppactivitystatToolService;
import com.gudeng.commerce.gd.admin.service.GrdGdGiftteamToolService;
import com.gudeng.commerce.gd.admin.service.ProCategoryService;
import com.gudeng.commerce.gd.admin.service.SystemCodeToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.customer.dto.AppChannelLinkDTO;
import com.gudeng.commerce.gd.customer.dto.AppactivitystatDTO;
import com.gudeng.commerce.gd.customer.entity.SystemCode;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftteamDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 
 * 
 * @author lidong
 *
 */
@Controller
public class AppactivitystatController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(AppactivitystatController.class);

	@Autowired
	private AppactivitystatToolService appactivitystatToolService;
	
	@Autowired
	private AppChannelLinkToolService appChannelLinkToolService;

	@Autowired
	private SystemCodeToolService systemCodeService;
	
	@Autowired
	private ProCategoryService proCategoryService;
	
	
	@Autowired
	private GrdGdGiftteamToolService grdGdGiftteamToolService;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("appactivitystat/nsy")
	public String list(HttpServletRequest request,Model model) {
		List<AppChannelLinkDTO> list = null;
		try{
			List<SystemCode> listViaType  = systemCodeService.getListViaType("clientChannel");
			String clientCode = null;
			if(listViaType!=null&&listViaType.size()>0){
				for(SystemCode code:listViaType){
					if(code.getCodeValue().equals("农商友")){
						clientCode = code.getCodeKey();
						break;
					}
				}
			}
			if(clientCode!=null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("clientChannel", clientCode);
				map.put("state", 1);
				
				list = appChannelLinkToolService.getList(map);
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		model.addAttribute("channelList", list);
		return "appactivitystat/nsy";
	}
	
	@RequestMapping("appactivitystat/gys")
	public String gys(HttpServletRequest request,Model model) {
		List<ProductCategoryDTO> list = null;
		try{
			list = proCategoryService.listTopProductCategoryByMarketId(3l);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		model.addAttribute("catelist", list);
		return "appactivitystat/gys";
	}	
	
	@RequestMapping("appactivitystat/nps")
	public String nps(HttpServletRequest request,Model model) {
		return "appactivitystat/nps";
	}	
	
	/**
	 * 车主，货主，物流公司
	 * @param request
	 * @param nst
	 * @return
	 */
	@RequestMapping("appactivitystat/{nst}")
	public String cz(HttpServletRequest request,@PathVariable("nst") String nst ) {
		return "appactivitystat/"+nst;
	}	
	
	/**
	 * 获取各个市场的一级分类
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("appactivitystat/marketcate")
	@ResponseBody
	public String getMarketCage(HttpServletRequest request,Model model) {
		List<ProductCategoryDTO> list = null;
		try{
		String marketId = request.getParameter("marketId");
		Long marketIdl = Long.parseLong(marketId);
		list = proCategoryService.listTopProductCategoryByMarketId(marketIdl);
		}catch(Exception e){
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list, SerializerFeature.WriteDateUseDateFormat);
	}	
	
	/**
	 * 铁军团队
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("appactivitystat/team")
	@ResponseBody
	public String tjteam(HttpServletRequest request,Model model) {
		List<GrdGdGiftteamDTO> list = null;
		try{
			String marketId = request.getParameter("marketId");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("marketId", marketId);
			list = grdGdGiftteamToolService.getList(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list, SerializerFeature.WriteDateUseDateFormat);
	}	

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("appactivitystat/query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			String mobile = request.getParameter("mobile");
			String account = request.getParameter("account");
			String regetype = request.getParameter("regetype");
			String nsyUserType = request.getParameter("nsyUserType");
			String system = request.getParameter("system");
			String appVersion = request.getParameter("appVersion");
			String appChannel = request.getParameter("appChannel");
			String queryStartDate = request.getParameter("queryStartDate");
			String queryEndDate = request.getParameter("queryEndDate");
			String managementtype = request.getParameter("managementtype");
			String categoryId = request.getParameter("categoryId");
			String marketId = request.getParameter("marketId");
			String teamId = request.getParameter("teamId");
			String appType = request.getParameter("appType");
			if(StringUtils.isNotBlank(queryStartDate)){
				queryStartDate = queryStartDate+" 00:00:00";
			}
			if(StringUtils.isNotBlank(queryEndDate)){
				queryEndDate = queryEndDate+" 23:59:59";
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", mobile);
			map.put("regetype", regetype);
			map.put("nsyUserType", nsyUserType);
			map.put("system", system);
			map.put("appVersion", appVersion);
			map.put("appChannel", appChannel);
			map.put("managementtype", managementtype);
			map.put("categoryId", categoryId);
			map.put("marketId", marketId);
			map.put("teamId", teamId);
			map.put("startTime", queryStartDate);
			map.put("endTime", queryEndDate);
			map.put("appType", appType);
			map.put("account", account);
			// 设置查询参数
			// 记录数
			map.put("total", appactivitystatToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<AppactivitystatDTO> list = appactivitystatToolService.getListPage(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}


	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@ResponseBody
	@RequestMapping(value = "appactivitystat/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String mobile = request.getParameter("mobile");
			String regetype = request.getParameter("regetype");
			String nsyUserType = request.getParameter("nsyUserType");
			String system = request.getParameter("system");
			String appVersion = request.getParameter("appVersion");
			String appChannel = request.getParameter("appChannel");
			String queryStartDate = request.getParameter("queryStartDate");
			String queryEndDate = request.getParameter("queryEndDate");
			String managementtype = request.getParameter("managementtype");
			String categoryId = request.getParameter("categoryId");
			String marketId = request.getParameter("marketId");
			String teamId = request.getParameter("teamId");
			String appType = request.getParameter("appType");
			String account = request.getParameter("account");
			if(StringUtils.isNotBlank(queryStartDate)){
				queryStartDate = queryStartDate+" 00:00:00";
			}
			if(StringUtils.isNotBlank(queryEndDate)){
				queryEndDate = queryEndDate+" 23:59:59";
			}
			double d = 0;
			if(StringUtils.isNotBlank(queryStartDate)&&StringUtils.isNotBlank(queryEndDate)){
				d = DateUtil.getBetweenDays(queryStartDate,queryEndDate,"yyyy-MM-dd HH:mm:ss");
			}
			if(d>31){
				result.put("status", 0);
				result.put("message", "日期范围不能超过31天, 请缩减日期范围...");
				return JSONObject.toJSONString(result);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", account);
			map.put("mobile", mobile);
			map.put("regetype", regetype);
			map.put("nsyUserType", nsyUserType);
			map.put("system", system);
			map.put("appVersion", appVersion);
			map.put("appChannel", appChannel);
			map.put("managementtype", managementtype);
			map.put("categoryId", categoryId);
			map.put("marketId", marketId);
			map.put("teamId", teamId);
			map.put("startTime", queryStartDate);
			map.put("endTime", queryEndDate);
			map.put("appType", appType);
			int total = appactivitystatToolService.getTotal(map);
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
	 * @author lidong
	 */
	@RequestMapping(value = "appactivitystat/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			String mobile = request.getParameter("mobile");
			String regetype = request.getParameter("regetype");
			String nsyUserType = request.getParameter("nsyUserType");
			String system = request.getParameter("system");
			String appVersion = request.getParameter("appVersion");
			String appChannel = request.getParameter("appChannel");
			String queryStartDate = request.getParameter("queryStartDate");
			String queryEndDate = request.getParameter("queryEndDate");
			String managementtype = request.getParameter("managementtype");
			String categoryId = request.getParameter("categoryId");
			String marketId = request.getParameter("marketId");
			String teamId = request.getParameter("teamId");
			String appType = request.getParameter("appType");
			String account = request.getParameter("account");
			if(StringUtils.isNotBlank(queryStartDate)){
				queryStartDate = queryStartDate+" 00:00:00";
			}
			if(StringUtils.isNotBlank(queryEndDate)){
				queryEndDate = queryEndDate+" 23:59:59";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", mobile);
			map.put("regetype", regetype);
			map.put("nsyUserType", nsyUserType);
			map.put("system", system);
			map.put("appVersion", appVersion);
			map.put("appChannel", appChannel);
			map.put("managementtype", managementtype);
			map.put("categoryId", categoryId);
			map.put("marketId", marketId);
			map.put("teamId", teamId);
			map.put("startTime", queryStartDate);
			map.put("endTime", queryEndDate);
			map.put("appType", appType);
			map.put("account", account);
			
			int apptype = Integer.parseInt(appType);
			String excelName = "";
			switch(apptype){
				case 1:excelName = "农商友app启动数据列表";break;
				case 2:excelName = "农批商app启动数据列表";break;
				case 3:excelName = "供应商app启动数据列表";break;
				case 4:excelName = "铁军app启动数据列表";break;
				case 5:excelName = "车主app启动数据列表";break;
				case 6:excelName = "货主app启动数据列表";break;
				case 7:excelName = "物流公司app启动数据列表";break;
				default:excelName = "app启动数据列表";break;
			}
			
			// 设置输出响应头信息，
			excelName = excelName+DateUtil.getDate(new Date(), "yyyy-MM-dd");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String fileName  = new String(excelName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName+".xls");
			ouputStream = response.getOutputStream();
			
			
			// 查询数据
			List<AppactivitystatDTO> list = appactivitystatToolService.getList(map);
			//app类型：1农商友，2农批商，3供应商，4铁军，5车主，6货主，7物流公司
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet =wwb.createSheet(excelName, 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "手机号码");// 填充第一行第一个单元格的内容
				Label label10 = null;
				if(apptype==4){
					label10 = new Label(1, 0, "姓名");// 填充第一行第二个单元格的内容
				}else{
					label10 = new Label(1, 0, "用户账号");// 填充第一行第二个单元格的内容
				}
				
				Label label20 = new Label(2, 0, "UUID");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "MEID");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "IMEI");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "ICCID");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "手机型号");
				Label label70 = new Label(7, 0, "手机内存");
				Label label80 = new Label(8, 0, "手机容量");
				Label label90 = new Label(9, 0, "所属平台");
				Label label100 = new Label(10, 0, "平台版本号");
				Label label101 = new Label(11, 0, "客户端版本号");
				Label label102 = new Label(12, 0, "统计项目");
				Label label103 = new Label(13, 0, "登录状态");
				Label label104 = new Label(14, 0, "帐号创建时间");
				Label label105 = new Label(15, 0, "统计时间");


				
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(label90);
				sheet.addCell(label100);
				sheet.addCell(label101);
				sheet.addCell(label102);
				sheet.addCell(label103);
				sheet.addCell(label104);
				sheet.addCell(label105);
				//app类型：1农商友，2农批商，3供应商，4铁军，5车主，6货主，7物流公司
				if(apptype==1){
					Label label106 = new Label(16, 0, "所属渠道");
					Label label107 = new Label(17, 0, "注册来源");// 填充第一行第六个单元格的内容
					Label label108 = new Label(18, 0, "农商友用户类型");// 填充第一行第七个单元格的内容
					sheet.addCell(label106);
					sheet.addCell(label107);
					sheet.addCell(label108);
				}else if(apptype==2){
					Label label106 = new Label(16, 0, "所属市场");// 填充第一行第六个单元格的内容
					Label label107 = new Label(17, 0, "注册来源");
					Label label108 = new Label(18, 0, "主营分类");// 填充第一行第七个单元格的内容
					sheet.addCell(label106);
					sheet.addCell(label107);
					sheet.addCell(label108);
				}else if(apptype==3){
					Label label106 = new Label(16, 0, "经营类型");// 填充第一行第六个单元格的内容
					Label label108 = new Label(17, 0, "注册来源");
					Label label107 = new Label(18, 0, "主营分类");// 填充第一行第七个单元格的内容
					sheet.addCell(label106);
					sheet.addCell(label107);
					sheet.addCell(label108);
				}else if(apptype==4){
					Label label106 = new Label(16, 0, "所属市场");// 填充第一行第六个单元格的内容
					//Label label107 = new Label(17, 0, "所属团队");// 填充第一行第七个单元格的内容
					sheet.addCell(label106);
					//sheet.addCell(label107);
				}else{
					Label label106 = new Label(16, 0, "注册来源");
					sheet.addCell(label106);
				}
				
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						AppactivitystatDTO item = list.get(i);
						Label label0 = new Label(0, i + 1, item.getMobile());
						Label label1 = new Label(1, i + 1, item.getAccount());
						Label label2 = new Label(2, i + 1, item.getDeviceUUID());
						Label label3 = new Label(3, i + 1, item.getDeviceMEID());
						Label label4 = new Label(4, i + 1, item.getDeviceIMEI());
						Label label5 = new Label(5, i + 1, item.getDeviceICCID());
						Label label6 = new Label(6, i + 1, item.getCellphoneModel());
						Label label7 = new Label(7, i + 1, item.getCellphoneRAM()==null?"":item.getCellphoneRAM().toString());
						Label label8 = new Label(8, i + 1, item.getCellphoneROM()==null?"":item.getCellphoneROM().toString());
						Label label9 = new Label(9, i + 1, item.getSystem()==null?"":item.getSystem().equals("1")?"IOS":"Android");
						Label label010 = new Label(10, i + 1, item.getSystemVersion());
						Label label011 = new Label(11, i + 1, item.getAppVersion());
						Label label012 = new Label(12, i + 1, "app启动");
						Label label013 = new Label(13, i + 1, item.getIsLogin()?"登录":"未登录");
						Label label014 = new Label(14, i + 1, item.getUserCreateTime()==null?"":time.format(item.getUserCreateTime()));
						Label label015 = new Label(15, i + 1, item.getCreateTime()==null?"":time.format(item.getCreateTime()));
						
						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(label010);
						sheet.addCell(label011);
						sheet.addCell(label012);
						sheet.addCell(label013);
						sheet.addCell(label014);
						sheet.addCell(label015);
						
						
						//app类型：1农商友，2农批商，3供应商，4铁军，5车主，6货主，7物流公司
						if(apptype==1){
							Label label016 = new Label(16, i + 1, item.getAppChannel());
							Label label017 = new Label(17, i + 1, StringUtils.isBlank(item.getRegetype())?"":getRegResource(item.getRegetype()));// 填充第一行第六个单元格的内容
							Label label018 = new Label(18, i + 1, item.getNsyUserType()==null?"":getNsyUserType(item.getNsyUserType()));// 填充第一行第七个单元格的内容
							sheet.addCell(label016);
							sheet.addCell(label017);
							sheet.addCell(label018);
						}else if(apptype==2){
							Label label016 = new Label(16, i + 1, item.getMarketName());// 填充第一行第六个单元格的内容
							Label label017 = new Label(17, i + 1, StringUtils.isBlank(item.getRegetype())?"":getRegResource(item.getRegetype()));
							Label label018 = new Label(18, i + 1, item.getCategoryName());// 填充第一行第七个单元格的内容
							sheet.addCell(label016);
							sheet.addCell(label017);
							sheet.addCell(label018);
						}else if(apptype==3){
							Label label016 = new Label(16, i + 1,  item.getManagementtype()==null?"":item.getManagementtype()==1?"种养大户":item.getManagementtype()==2?"合作社":"基地");// 1表示种养大户，2表示合作社，3表示基地'
							Label label017 = new Label(17, i + 1, StringUtils.isBlank(item.getRegetype())?"":getRegResource(item.getRegetype()));
							Label label018 = new Label(18, i + 1, item.getCategoryName());// 填充第一行第七个单元格的内容
							sheet.addCell(label016);
							sheet.addCell(label017);
							sheet.addCell(label018);
						}else if(apptype==4){
							Label label016 = new Label(16, i + 1, item.getMarketName());// 填充第一行第六个单元格的内容
							//Label label017 = new Label(17, i + 1, item.getTeamName());// 填充第一行第七个单元格的内容
							sheet.addCell(label016);
							//sheet.addCell(label017);
						}else{
							Label label016 = new Label(16, i + 1, StringUtils.isBlank(item.getRegetype())?"":getRegResource(item.getRegetype()));
							sheet.addCell(label016);
							
						}						
						
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
	
	
	public String getRegResource(String c){
		//0管理后台,1谷登农批网,2农速通(旧),3农商友,4农商友-农批商,5农批友,6供应商,7POS刷卡 ,8微信授权,9农速通-货主,10农速通-司机,11农速通-物流公司
		if(c.equals("0")){
			return "管理后台";
		}else if(c.equals("1")){
			return "谷登农批网";
		}else if(c.equals("2")){
			return "农速通(旧)";
		}else if(c.equals("3")){
			return "农商友";
		}else if(c.equals("4")){
			return "农商友-农批商";
		}else if(c.equals("5")){
			return "农批商";
		}else if(c.equals("6")){
			return "供应商";
		}else if(c.equals("7")){
			return "POS刷卡";
		}else if(c.equals("8")){
			return "微信授权";
		}else if(c.equals("9")){
			return "货主";
		}else if(c.equals("10")){
			return "车主";
		}else if(c.equals("11")){
			return "物流公司";
		}else{
			return "其他";
		}
	}
	
	public String getNsyUserType(byte c){
		//1下游批发商,2生鲜超市,3学校食堂,4食品加工工厂,5社区门店,6餐厅,7垂直生鲜,8其它',
		if(c==1){
			return "下游批发商";
		}else if(c==2){
			return "生鲜超市";
		}else if(c==3){
			return "学校食堂";
		}else if(c==4){
			return "食品加工工厂";
		}else if(c==5){
			return "社区门店";
		}else if(c==6){
			return "餐厅";
		}else if(c==7){
			return "垂直生鲜";
		}else if(c==8){
			return "其他";
		}else{
			return "其他";
		}
	}
	
	public static void main(String[] args){
		System.out.println(DateUtil.getBetweenDays("2016-10-03 00:00:00","2016-11-03 23:59:59","yyyy-MM-dd HH:mm:ss"));
	}
	
}


