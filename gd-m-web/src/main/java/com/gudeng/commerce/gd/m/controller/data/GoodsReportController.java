package com.gudeng.commerce.gd.m.controller.data;

import javax.servlet.http.HttpServletRequest;

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
import com.gudeng.commerce.gd.report.dto.DataCacheQuery;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.GoodsServiceQuery;
import com.gudeng.commerce.gd.report.dto.TimeCacheType;

@Controller
@RequestMapping("goods")
public class GoodsReportController extends MBaseController{
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(GoodsReportController.class);
	
	@Autowired
	private DataToolService dataToolService;
	
	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;
	
	/**
	 * 商品分析
	 */
	@RequestMapping("goodList")
	public String goodList(HttpServletRequest request, Long memberId, Integer timeType, String timeStr)
			throws Exception{
		logger.info("memberId=" + memberId + ",timeType=" + timeType + ",timeStr=" + timeStr);
		request.setAttribute("memberId", memberId);
		if (timeType != null) {
			request.setAttribute("timeType", timeType);
		}
		if (timeStr != null) {
			request.setAttribute("timeStr", timeStr);
		}
		return "H5/data/goodList";
	}
	
	/**
	 * 商品排名
	 */
	@RequestMapping("goodsRanking")
	public String goodsRanking(HttpServletRequest request, Long memberId, Integer timeType, String timeStr)
			throws Exception{
		logger.info("memberId=" + memberId + ",timeType=" + timeType + ",timeStr=" + timeStr);
		request.setAttribute("memberId", memberId);
		if (timeType != null) {
			request.setAttribute("timeType", timeType);
		}
		if (timeStr != null) {
			request.setAttribute("timeStr", timeStr);
		}
		return "H5/data/goodsRanking";
	}
	
	/**
	 * 商品分析 排名
	 * @param request
	 * @param memberId 用户ID
	 * @param timeType 时间类型 1 最近七天 2 最近15天 3 最近30天 4 最近一个月
	 * @param timeStr 指定时间查询 当没有timeType时 yyyy-MM 指定某个月 yyyy-MM-dd 指定某一天（主要用于昨天）商品分析排名不需要
	 * @param sortCode 排序 tradeAmt:DESC 默认 tradeAmt:ASC pv:DESC pv:ASC
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("analy")
	@ResponseBody
	public String analy(HttpServletRequest request, Long memberId, Integer timeType, String timeStr, String sortCode) throws Exception{
		try{
			logger.info("memberId=" + memberId + ",timeType=" + timeType + ",timeStr=" + timeStr);
			if(memberId == null){
				return getResponseJson(ResponseCodeEnum.ACCOUNT_IS_NULL);
			}
			
			MemberBaseinfoDTO member = memberBaseinfoToolService.getById(String.valueOf(memberId));
			if (member == null) {
				return getResponseJson(ResponseCodeEnum.ACCOUNT_ERROR);
			}
			
			if(timeType == null && timeStr==null){
				return getResponseJson(ResponseCodeEnum.TIMETYPE_IS_NULL);
			}
			GoodsServiceQuery dataQuery = new GoodsServiceQuery();
			dataQuery.setMemberId(memberId);
			dataQuery.setTimeType(timeType);
			
			if(dataQuery.getTimeType()==null) {
				dataQuery.setTimeStr(timeStr);
			}
			dataQuery.setSortCode(sortCode);
			DataDTO dataDTO = dataToolService.queryGoodsData(dataQuery);
			
			return getResponseJson(dataDTO);
		}catch(Exception e){
			logger.error("出错啦:", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
	}
	
	/**
	 * for test 清楚对应缓存
	 * @param memberId
	 * @param timeType
	 * @param timeStr
	 * @param sortCode
	 */
	public void clean(Long memberId, String timeType, String timeStr, String sortCode) {
		DataCacheQuery dataCacheQuery = new DataCacheQuery();
		dataCacheQuery.setMemberId(memberId);
		
		dataCacheQuery.setTimetype(TimeCacheType.valueOf(timeType));
		try {
			dataToolService.cleanGoods(dataCacheQuery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
