package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.ComplaintToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.customer.dto.ComplaintEntityDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller

public class ComplaintController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(ComplaintController.class);

	@Autowired
	public ComplaintToolService complaintToolService;

	/**
	 * complaintList
	 * @return
	 */
	@RequestMapping("complaint/complaintList")
	public String complaintList(HttpServletRequest request){
		return "complaint/complaintList";
	}

	/**
	 * complaintList
	 * @return
	 */
	@ResponseBody
	@RequestMapping("complaint/complaintListShow")
	public String complaintListShow(HttpServletRequest request,ComplaintEntityDTO complaintEntityDTO){
		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtil.isNotEmpty(complaintEntityDTO.getContent())) {
			map.put("content", complaintEntityDTO.getContent());
		}
		if (StringUtil.isNotEmpty(complaintEntityDTO.getSource())) {
			map.put("source", complaintEntityDTO.getSource());
		}
		if (StringUtil.isNotEmpty(complaintEntityDTO.getCreateTimeStart())) {
			map.put("createTimeStart", complaintEntityDTO.getCreateTimeStart());
		}
		if (StringUtil.isNotEmpty(complaintEntityDTO.getCreateTimeEnd())) {
			map.put("createTimeEnd", complaintEntityDTO.getCreateTimeEnd());
		}

		try {
			map.put("total", complaintToolService.getComplaintList(map).size());

		// 设定分页,排序
		setCommParameters(request, map);
		List<ComplaintEntityDTO> list = complaintToolService.getComplaintList(map);
		map.put("rows", list);// rows键 存放每页记录 list
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSONObject.toJSONString(map,
				SerializerFeature.WriteDateUseDateFormat);
	}

	/**
	 * 查看投诉内容
	 * @return
	 */
	@RequestMapping(value="complaint/complaintContentShow/{id}")
	public String complaintContentShow(@PathVariable("id") Long id,HttpServletRequest request){
		ComplaintEntityDTO ComplaintEntity;
		try {
			ComplaintEntity = complaintToolService.getComplaint(id);
			if(ComplaintEntity!=null){
				putModel("content",ComplaintEntity.getContent());
			}

		} catch (MalformedURLException e) {
			logger.warn("getComplaint error ");
			e.printStackTrace();
		}

		return "complaint/complaintContentShow";
	}

	/**
	 * 回复投诉内容
	 * @return
	 */
	@RequestMapping(value="complaint/replyComplaint/{id}")
	public String replyComplaint(@PathVariable("id") Long id,HttpServletRequest request){
		ComplaintEntityDTO ComplaintEntity;
		try {
			ComplaintEntity = complaintToolService.getComplaint(id);
			if(ComplaintEntity!=null){
				putModel("content",ComplaintEntity.getReplyComtent());
			}
			putModel("id",id);
		} catch (MalformedURLException e) {
			logger.warn("getComplaint error ");
			e.printStackTrace();
		}

		return "complaint/replyComplaint";
	}

	/**
	 * 保存回复内容
	 * @param request
	 * @param complaintEntityDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="complaint/replyComplaintSave")
	public String replyComplaintSave(HttpServletRequest request,ComplaintEntityDTO complaintEntityDTO){
		SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
		if(complaintEntityDTO!=null){
			complaintEntityDTO.setCustomer(regUser.getUserName());
		}
		try {
			complaintToolService.replyComplaintSave(complaintEntityDTO);
			return "success";
		} catch (Exception e) {
			logger.warn("getComplaint error");
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "complaint/checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request,ComplaintEntityDTO complaintEntityDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String startDate = complaintEntityDTO.getCreateTimeStart();
			String endDate = complaintEntityDTO.getCreateTimeEnd();
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", complaintEntityDTO.getContent());
			map.put("source", complaintEntityDTO.getSource());
			map.put("createTimeStart", complaintEntityDTO.getCreateTimeStart());
			map.put("createTimeEnd", complaintEntityDTO.getCreateTimeEnd());
			int total = complaintToolService.getTotal(map);
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
	 * 导出
	 * @param request
	 * @param complaintEntityDTO
	 * @return
	 */
	@RequestMapping(value="complaint/exportData")
	public void exportData(HttpServletRequest request,HttpServletResponse response,ComplaintEntityDTO complaintEntityDTO){
		Map<String, Object> map = new HashMap<String, Object>();
		List<ComplaintEntityDTO> list=null;
		if (StringUtil.isNotEmpty(complaintEntityDTO.getContent())) {
			map.put("content", complaintEntityDTO.getContent());
		}
		if (StringUtil.isNotEmpty(complaintEntityDTO.getSource())) {
			map.put("source", complaintEntityDTO.getSource());
		}
		if (StringUtil.isNotEmpty(complaintEntityDTO.getCreateTimeStart())) {
			map.put("createTimeStart", complaintEntityDTO.getCreateTimeStart());
		}
		if (StringUtil.isNotEmpty(complaintEntityDTO.getCreateTimeEnd())) {
			map.put("createTimeEnd", complaintEntityDTO.getCreateTimeEnd());
		}
			try {
				map.put("total",complaintToolService.getComplaintList(map).size());
			list = complaintToolService.getComplaintList(map);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			WritableWorkbook wwb = null;
			OutputStream ouputStream = null;
			try {
				// 设置输出响应头信息，
				response.setContentType("application/vnd.ms-excel");
				String fileName  = new String("投诉建议列表".getBytes(), "ISO8859-1");
				response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
				ouputStream = response.getOutputStream();
				// 查询数据
	 			// 在输出流中创建一个新的写入工作簿
				wwb = Workbook.createWorkbook(ouputStream);
				if (wwb != null) {
					WritableSheet sheet = wwb.createSheet("投诉建议列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
					// 第一个参数表示列，第二个参数表示行
					Label label0 = new Label(0, 0, "投诉建议内容");// 填充第一行第一个单元格的内容
					Label label1 = new Label(1, 0, "信息来源");// 填充第一行第二个单元格的内容
					Label label2 = new Label(2, 0, "角色类型");// 填充第一行第三个单元格的内容
					Label label3 = new Label(3, 0, "店铺所属市场");// 填充第一行第四个单元格的内容
					Label label4 = new Label(4, 0, "账号");// 填充第一行第五个单元格的内容
					Label label5 = new Label(5, 0, "手机号");// 填充第一行第六个单元格的内容
					Label label6 = new Label(6, 0, "创建时间");// 填充第一行第七个单元格的内容

					sheet.addCell(label0);// 将单元格加入表格
					sheet.addCell(label1);// 将单元格加入表格
					sheet.addCell(label2);
					sheet.addCell(label3);
					sheet.addCell(label4);
					sheet.addCell(label5);
					sheet.addCell(label6);

					/*** 循环添加数据到工作簿 ***/
					if (list != null && list.size() > 0) {
						SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						for (int i = 0, lenght = list.size(); i < lenght; i++) {
							ComplaintEntityDTO complaintEntityDto = list.get(i);
							Label label00 = new Label(0, i + 1, complaintEntityDto.getContent());
							Label label01 = new Label(1, i + 1, complaintEntityDto.getSource());
							Label label02 = new Label(2, i + 1, complaintEntityDto.getLevel());
							Label label03 = new Label(3, i + 1, complaintEntityDto.getMarketName());
							Label label04 = new Label(4, i + 1, complaintEntityDto.getMember());
							Label label05 = new Label(5, i + 1, complaintEntityDto.getMobile());
							Label label06 = new Label(6, i + 1, complaintEntityDto.getCreateTime()==null?"":time.format(complaintEntityDto.getCreateTime()));
							sheet.addCell(label00);// 将单元格加入表格
							sheet.addCell(label01);// 将单元格加入表格
							sheet.addCell(label02);
							sheet.addCell(label03);
							sheet.addCell(label04);
							sheet.addCell(label05);
							sheet.addCell(label06);
						}
					}
					wwb.write();
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

}
