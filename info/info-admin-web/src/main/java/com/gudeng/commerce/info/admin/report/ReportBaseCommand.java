package com.gudeng.commerce.info.admin.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.info.admin.service.ProBaiduToolService;
import com.gudeng.commerce.info.admin.service.ProBszbankToolService;
import com.gudeng.commerce.info.admin.service.ProOperateToolService;
import com.gudeng.commerce.info.admin.service.ReportsToolService;
import com.gudeng.commerce.info.admin.util.DateUtil;
import com.gudeng.commerce.info.customer.dto.ReportDataDTO;

public abstract class ReportBaseCommand {
	
	/**
	 * 查询参数开始时间
	 */
	protected final static String PARAM_QUERY_START_DATE = "queryStartDate";
	
	/**
	 * 查询参数结束时间
	 */
	protected final static String PARAM_QUERY_END_DATE = "queryEndDate";
	
	/**
	 * 查询参数市场ID
	 */
	protected final static String PARAM_MARKET_ID = "marketId";

	@Autowired
	protected ReportsToolService reportsToolService;

	@Autowired
	protected ProBszbankToolService proBszbankToolService;

	@Autowired
	protected ProBaiduToolService proBaiduToolService;
	
	@Autowired
	protected ProOperateToolService proOperateToolService;

	public abstract ReportDataDTO generateReportData(
			Map<String, Object> paramMap);

	/**
	 * 获取时间范围内的时间字符串
	 * 
	 * @param startDate
	 * @param endDate
	 * @param format
	 * @return
	 */
	protected List<String> getDateStrList(Date startDate, Date endDate, String format) {
		List<String> dateStrList = new ArrayList<String>();
		try {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			int days = DateUtil.daysBetween(startDate, endDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			dateStrList.add(sf.format(calendar.getTime()));
			for (int i = 0; i < days; i++) {
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
				dateStrList.add(sf.format(calendar.getTime()));
			}
		} catch (Exception e) {
			return null;
		}
		return dateStrList;
	}
}
