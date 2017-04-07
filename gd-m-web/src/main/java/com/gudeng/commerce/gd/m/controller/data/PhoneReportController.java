package com.gudeng.commerce.gd.m.controller.data;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.enums.ResponseCodeEnum;
import com.gudeng.commerce.gd.m.service.DataToolService;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;

@Controller
@RequestMapping("/phone")
public class PhoneReportController extends MBaseController{

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PhoneReportController.class);
	
	@Autowired
	private DataToolService dataToolService;
	
	/**
	 * 电话分析
	 */
	@RequestMapping("phoneStatistics")
	public String phoneStatistics(HttpServletRequest request, Long memberId, Integer timeType, String timeStr)
			throws Exception{
		logger.info("memberId=" + memberId + ",timeType=" + timeType + ",timeStr=" + timeStr);
		request.setAttribute("memberId", memberId);
		if (timeType != null) {
			request.setAttribute("timeType", timeType);
		}
		if (timeStr != null) {
			request.setAttribute("timeStr", timeStr);
		}
		return "H5/data/phoneStatistics";
	}
	
	@ResponseBody
	@RequestMapping("/getData")
	public String getData(HttpServletRequest request, Long memberId, Integer timeType, String timeStr) throws Exception{
		try{
			logger.info("memberId=" + memberId + ",timeType=" + timeType + ",timeStr=" + timeStr);
			if(memberId == null){
				return getResponseJson(ResponseCodeEnum.ACCOUNT_IS_NULL);
			}
			
			if(timeType == null && StringUtils.isBlank(timeStr)){
				return getResponseJson(ResponseCodeEnum.TIMETYPE_IS_NULL);
			}
			DataServiceQuery dataQuery = new DataServiceQuery();
			dataQuery.setMemberId(memberId);
			dataQuery.setTimeType(timeType);
			dataQuery.setTimeStr(timeStr);
			DataDTO dataDTO = dataToolService.queryPhoneData(dataQuery);
			
			return getResponseJson(dataDTO);
		}catch(Exception e){
			logger.error("[ERROR]获取电话统计数据异常:", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
	}
}
