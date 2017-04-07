package com.gudeng.commerce.gd.report.data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.report.ReportType;
import com.gudeng.commerce.gd.report.dto.DataCacheQuery;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.dto.TradeDBQuery;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.util.DateType;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午3:59:57
 */
public class TradeDataCacheServiceImpl extends ADataCacheService {
	
	@Autowired
	private TradeDataBuilder tradeDataBuilder;

	public void setTradeDataBuilder(TradeDataBuilder tradeDataBuilder) {
		this.tradeDataBuilder = tradeDataBuilder;
	}

	@Override
	public String getReportType() {
		return ReportType.TRADE_REPORT;
	}

	@Override
	public DataDTO queryDataFromDB(DateType dt, DataCacheQuery dataCacheQuery, DataServiceQuery dataServiceQuery) throws ServiceException {
		TradeDBQuery dataDBQuery = new TradeDBQuery();
		
		dataDBQuery.setStartTime(dt.getStartTime());
		dataDBQuery.setEndTime(dt.getEndTime());
		dataDBQuery.setMemberId(dataCacheQuery.getMemberId());
		if (StringUtils.isNotBlank(dataServiceQuery.getTimeStr()) && dataServiceQuery.getTimeStr().length() == 10) { //日
			dataDBQuery.setXaxisDataType("1");
		} else {
			dataDBQuery.setXaxisDataType("2");
		}
		
		return tradeDataBuilder.getData(dataDBQuery);
	}

}
