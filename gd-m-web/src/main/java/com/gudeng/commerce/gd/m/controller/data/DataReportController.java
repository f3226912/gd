package com.gudeng.commerce.gd.m.controller.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.enums.ResponseCodeEnum;
import com.gudeng.commerce.gd.m.service.DataToolService;
import com.gudeng.commerce.gd.m.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.dto.UserTradeDataDTO;

@Controller
@RequestMapping("data")
public class DataReportController extends MBaseController{
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(DataReportController.class);
	
	@Autowired
	private DataToolService dataToolService;
	
	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;
	
	/**
	 * 首页
	 */
	@RequestMapping("showIndex")
	public String showBill(HttpServletRequest request, Long memberId, Integer timeType, String timeStr)
			throws Exception{
		logger.info("memberId=" + memberId + ",timeType=" + timeType + ",timeStr=" + timeStr);
		request.setAttribute("memberId", memberId);
		if (timeType != null) {
			request.setAttribute("timeType", timeType);
		}
		if (timeStr != null) {
			request.setAttribute("timeStr", timeStr);
		}
		return "H5/data/index";
	}
	
	/**
	 * 交易数据
	 */
	@RequestMapping("dealStat")
	public String dealStat(HttpServletRequest request, Long memberId, Integer timeType, String timeStr)
			throws Exception{
		logger.info("memberId=" + memberId + ",timeType=" + timeType + ",timeStr=" + timeStr);
		request.setAttribute("memberId", memberId);
		if (timeType != null) {
			request.setAttribute("timeType", timeType);
		}
		if (timeStr != null) {
			request.setAttribute("timeStr", timeStr);
		}
		return "H5/data/dealStat";
	}
	
	/**
	 * 交易分析
	 */
	@RequestMapping("tradeAnalysis")
	public String tradeAnalysis(HttpServletRequest request, Long memberId, Integer timeType, String timeStr)
			throws Exception{
		logger.info("memberId=" + memberId + ",timeType=" + timeType + ",timeStr=" + timeStr);
		request.setAttribute("memberId", memberId);
		if (timeType != null) {
			request.setAttribute("timeType", timeType);
		}
		if (timeStr != null) {
			request.setAttribute("timeStr", timeStr);
		}
		return "H5/data/tradeAnalysis";
	}
	
	/**
	 * 交易分析
	 */
	@RequestMapping("tradeAnaly")
	@ResponseBody
	public String tradeAnaly(HttpServletRequest request, Long memberId, Integer timeType, String timeStr) throws Exception{
		try{
			logger.info("memberId=" + memberId + ",timeType=" + timeType + ",timeStr=" + timeStr);
			if(memberId == null){
				return getResponseJson(ResponseCodeEnum.ACCOUNT_IS_NULL);
			}
			MemberBaseinfoDTO member = memberBaseinfoToolService.getById(String.valueOf(memberId));
			if (member == null) {
				return getResponseJson(ResponseCodeEnum.ACCOUNT_ERROR);
			}
			if(timeType == null && StringUtils.isBlank(timeStr)){
				return getResponseJson(ResponseCodeEnum.TIMETYPE_IS_NULL);
			}
			DataServiceQuery dataQuery = new DataServiceQuery();
			dataQuery.setMemberId(memberId);
			dataQuery.setTimeType(timeType);
			dataQuery.setTimeStr(timeStr);
			DataDTO dataDTO = dataToolService.queryTradeData(dataQuery);
			
			return getResponseJson(dataDTO);
		}catch(Exception e){
			logger.error("交易分析:", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
	}
	
	/**
	 * 查询首页数据
	 */
	@RequestMapping("index")
	@ResponseBody
	public String index(HttpServletRequest request, Long memberId) throws Exception{
		try{
			logger.info("memberId=" + memberId);
			if(memberId == null){
				return getResponseJson(ResponseCodeEnum.ACCOUNT_IS_NULL);
			}
			MemberBaseinfoDTO member = memberBaseinfoToolService.getById(String.valueOf(memberId));
			if (member == null) {
				return getResponseJson(ResponseCodeEnum.ACCOUNT_ERROR);
			}
			DataServiceQuery dataQuery = new DataServiceQuery();
			dataQuery.setMemberId(memberId);
			dataQuery.setTimeStr(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
			UserTradeDataDTO dataDTO = (UserTradeDataDTO)dataToolService.queryTradeData(dataQuery);
			Map map = new HashMap();
			map.put("orderNumToday", dataDTO.getPayedOrderNum());
			map.put("tradeAmtToday", String.format("%.2f", Double.parseDouble(String.valueOf(dataDTO.getPayedTradeAmt()))));
			dataQuery.setTimeStr(DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd"));
			dataDTO = (UserTradeDataDTO)dataToolService.queryTradeData(dataQuery);
			map.put("tradeAmtYestoday", String.format("%.2f", Double.parseDouble(String.valueOf(dataDTO.getPayedTradeAmt()))));
			dataQuery.setTimeStr(null);
			dataQuery.setTimeType(4);
			dataDTO = (UserTradeDataDTO)dataToolService.queryTradeData(dataQuery);
			map.put("tradeAmtMonth", String.format("%.2f", Double.parseDouble(String.valueOf(dataDTO.getPayedTradeAmt()))));
			return getResponseJson(map);
		}catch(Exception e){
			logger.error("查询首页数据异常:", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
	}

	/**
	 * 查询交易数据
	 */
	@RequestMapping("tradeInfo")
	@ResponseBody
	public String tradeInfo(HttpServletRequest request, Long memberId, Integer timeType, String timeStr) throws Exception{
		try{
			logger.info("memberId=" + memberId + ",timeType=" + timeType + ",timeStr=" + timeStr);
			if(memberId == null){
				return getResponseJson(ResponseCodeEnum.ACCOUNT_IS_NULL);
			}
			MemberBaseinfoDTO member = memberBaseinfoToolService.getById(String.valueOf(memberId));
			if (member == null) {
				return getResponseJson(ResponseCodeEnum.ACCOUNT_ERROR);
			}
			if(timeType == null && StringUtils.isBlank(timeStr)){
				return getResponseJson(ResponseCodeEnum.TIMETYPE_IS_NULL);
			}
			DataServiceQuery dataQuery = new DataServiceQuery();
			dataQuery.setMemberId(memberId);
			dataQuery.setTimeType(timeType);
			dataQuery.setTimeStr(timeStr);
			Map map = new HashMap();
			UserTradeDataDTO dataDTO = (UserTradeDataDTO)dataToolService.queryTradeData(dataQuery);
			map.put("orderNum", dataDTO.getPayedOrderNum());
			map.put("tradeAmt", String.format("%.2f", Double.parseDouble(String.valueOf(dataDTO.getPayedTradeAmt()))));
			map.put("buyerNum", dataDTO.getBuyerNum());
			map.put("goodsNum", dataDTO.getGoodsNum());
			map.put("orderAvgTradeAmt", String.format("%.2f", Double.parseDouble(String.valueOf(dataDTO.getOrderAvgTradeAmt()))));
			map.put("buyerAvgTradeAmt", String.format("%.2f", Double.parseDouble(String.valueOf(dataDTO.getBuyerAvgTradeAmt()))));
			return getResponseJson(map);
		}catch(Exception e){
			logger.error("查询交易数据异常:", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
	}

}
