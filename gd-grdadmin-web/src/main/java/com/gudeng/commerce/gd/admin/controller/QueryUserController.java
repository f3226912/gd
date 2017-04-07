package com.gudeng.commerce.gd.admin.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AreaSettingToolService;
import com.gudeng.commerce.gd.admin.service.CarsManageService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.dto.RecommendedUserDTO;
import com.gudeng.commerce.gd.customer.enums.MemberCertificationStatusEnum;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping("queryUser")
public class QueryUserController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(QueryUserController.class);

	@Autowired
	public CarsManageService carsManageService;

	@Autowired
	public AreaSettingToolService areaSettingToolService;



	/**
	 * 列表页面
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			/*String mobile =this.getUser(request).getMobile();
			map.put("mobile", mobile);*/
			List<AreaSettingDTO> list = areaSettingToolService.getAllAreaName(map);
			request.setAttribute("areaNameList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryUser/userList";
	}


	/**
	 * 默认查询和id条件查询结合
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		try {
			String  carStartDate =request.getParameter("queryCarStartDate");
			String  carEndDate =request.getParameter("queryCarEndDate");
			String  carLineStartDate =request.getParameter("queryCarLineStartDate");
			String  carLineEndDate =request.getParameter("queryCarLineEndDate");
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("mobile",request.getParameter("mobile"));
			map.put("recommendedMobile",request.getParameter("recommendedMobile"));
			map.put("areaName",request.getParameter("areaName"));
			map.put("startDate", request.getParameter("startDate"));
			map.put("endDate", request.getParameter("endDate"));

			map.put("carStartDate", carStartDate);
			map.put("carEndDate", carEndDate);
			map.put("carLineStartDate", carLineStartDate);
			map.put("carLineEndDate",carLineEndDate);
			map.put("nstStatus", request.getParameter("nstStatus"));
						//记录数
			map.put("total", carsManageService.getRecommendedUserTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<RecommendedUserDTO> list = carsManageService.getRecommendedUserList(map);
          if(list !=null && list.size()>0)
			{
				for(RecommendedUserDTO dto : list)
				{
					RecommendedUserDTO  temp =carsManageService.getUserInfoCount(dto.getRecommendedMobile(),carStartDate,carEndDate,carLineStartDate,carLineEndDate);
				    dto.setCarCount(temp.getCarCount());
				    dto.setCarLineCount(temp.getCarLineCount());
					dto.setCallCount(temp.getCallCount());
					dto.setNstStatus(MemberCertificationStatusEnum.getValue(dto.getNstStatus()));
					if ("个人".equals(dto.getUserType())) {
						dto.setCompanyName("");
					}
				}

			}			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.debug("Exception is :"+e);
			e.printStackTrace();
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
	public String checkExportParams(HttpServletRequest request, HttpServletResponse response, @RequestParam String mobile , @RequestParam String recommendedMobile , @RequestParam String areaName ,
			@RequestParam String startDate,@RequestParam String endDate ,@RequestParam String queryCarStartDate,@RequestParam String queryCarEndDate ,
			@RequestParam String queryCarLineStartDate,@RequestParam String queryCarLineEndDate  ) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的被推荐人注册日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();
	        //推荐人
			map.put("mobile",mobile);
			//被推荐人
			map.put("recommendedMobile",recommendedMobile);
			map.put("areaName",areaName);
			map.put("startDate", startDate);
			map.put("endDate", endDate);

			map.put("carStartDate", queryCarStartDate  );
			map.put("carEndDate", queryCarEndDate );
			map.put("carLineStartDate", queryCarLineStartDate);
			map.put("carLineEndDate", queryCarLineEndDate);

			int total = carsManageService.getRecommendedUserTotal(map);
			if (total > 10000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("checkExportParams with ex : {} ", new Object[]{e});
		}
		return JSONObject.toJSONString(result);
	}



	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, @RequestParam String mobile , @RequestParam String recommendedMobile , @RequestParam String areaName ,
			@RequestParam String startDate,@RequestParam String endDate ,@RequestParam String queryCarStartDate,@RequestParam String queryCarEndDate ,
			@RequestParam String queryCarLineStartDate,@RequestParam String queryCarLineEndDate  ,@RequestParam String nstStatus ) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询参数
        //推荐人
		map.put("mobile",mobile);
		//被推荐人
		map.put("recommendedMobile",recommendedMobile);
		map.put("areaName",areaName);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("carStartDate", queryCarStartDate  );
		map.put("carEndDate", queryCarEndDate );
		map.put("carLineStartDate", queryCarLineStartDate);
		map.put("carLineEndDate", queryCarLineEndDate);
		map.put("nstStatus", nstStatus);
				map.put("startRow", 0);
		map.put("endRow", 100000000);

		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + StringUtil.GenerateRandomStr() + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据list
			List<RecommendedUserDTO> list = carsManageService.getRecommendedUserList(map);

			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("被推荐人统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "推荐人手机号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "推荐人姓名");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "被推荐人手机号");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "被推荐人姓名");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "用户类型");// 填充第一行第四个单元格的内容
				Label label50 = new Label(5, 0, "公司名称");//
				Label label60 = new Label(6, 0, "联系人");//
				Label label70 = new Label(7, 0, "发布车辆数量");//
				Label label80 = new Label(8, 0, "发布线路数量");//
				Label label90 = new Label(9, 0, "被推荐人拨号次数");//
				Label label100 = new Label(10, 0, "被推荐人注册时间");//
				Label label110 = new Label(11, 0, "车辆发布时间");//
				Label label120 = new Label(12, 0, "线路发布时间");//
			    Label label130 = new Label(13, 0, "被推荐人用户角色");//
			    Label label140 = new Label(14, 0, "所属区域");//
				Label label150 = new Label(15, 0, "被推荐人认证状态");//


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
				sheet.addCell(label140);
				sheet.addCell(label150);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {

						RecommendedUserDTO dto = list.get(i);
						RecommendedUserDTO  temp =carsManageService.getUserInfoCount(dto.getRecommendedMobile(),queryCarStartDate,queryCarEndDate,queryCarLineStartDate,queryCarLineEndDate);
						dto.setCarCount(temp.getCarCount());
						dto.setCarLineCount(temp.getCarLineCount());
						dto.setCallCount(temp.getCallCount());
						Label label0 = new Label(0, i + 1, dto.getMobile());
						Label label1 = new Label(1, i + 1, dto.getUserName());
						Label label2 = new Label(2, i + 1, dto.getRecommendedMobile());
						Label label3 = new Label(3, i + 1, dto.getRecommendedUserName());
						Label label4 = new Label(4, i + 1, dto.getUserType());
						Label label5 = new Label(5, i + 1, "个人".equals(dto.getUserType())? "":dto.getCompanyName());
						Label label6 = new Label(6, i + 1, dto.getLinkMan());
						Label label7 = new Label(7, i + 1, dto.getCarCount()+"");
						Label label8 = new Label(8, i + 1, dto.getCarLineCount()+"");
						Label label9 = new Label(9, i + 1, dto.getCallCount()+"");
						Label label101 = new Label(10, i + 1, DateUtil.toString(dto.getCreateUserTime(),DateUtil.DATE_FORMAT_DATETIME));
						Label label102 = new Label(11, i + 1, DateUtil.toString(dto.getCreateCarTime(),DateUtil.DATE_FORMAT_DATETIME));
						Label label103 = new Label(12, i + 1, DateUtil.toString(dto.getCreateCarLineTime(),DateUtil.DATE_FORMAT_DATETIME));
						Label label104= new Label(13, i + 1, dto.getLevelType());
						Label label105= new Label(14, i + 1, dto.getAreaName());
						Label label106= new Label(15, i + 1, MemberCertificationStatusEnum.getValue(dto.getNstStatus()));

												sheet.addCell(label0);//将单元格加入表格
						sheet.addCell(label1);//
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
						sheet.addCell(label104);
						sheet.addCell(label105);
						sheet.addCell(label106);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e1) {
			logger.debug("Exception is :"+e1);
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


	/**
	 * 列表页面
	 * @return
	 */
	@RequestMapping("list")
	public String notRelationUser(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<AreaSettingDTO> list = areaSettingToolService.getAllAreaName(map);
			request.setAttribute("areaNameList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryUser/notRelationUserList";
	}

	/**
	 * 查询农速通非绑定关系会员列表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("queryNotRelationUserList")
	@ResponseBody
	public String queryNotRelationUserList(HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("areaName",request.getParameter("areaName"));
			map.put("startDate", request.getParameter("startDate"));
			map.put("endDate", request.getParameter("endDate"));

			map.put("carStartDate", request.getParameter("queryCarStartDate"));
			map.put("carEndDate", request.getParameter("queryCarEndDate"));
			map.put("carLineStartDate", request.getParameter("queryCarLineStartDate"));
			map.put("carLineEndDate", request.getParameter("queryCarLineEndDate"));

			//记录数
			map.put("total", carsManageService.getNotRelationUserTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<RecommendedUserDTO> list = carsManageService.getNotRelationUserList(map);
			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.debug("Exception is :"+e);
			e.printStackTrace();
		}
		return null;
	}

}
