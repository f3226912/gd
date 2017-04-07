package com.gudeng.commerce.gd.report.data;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.report.ReportType;
import com.gudeng.commerce.gd.report.dto.DataCacheQuery;
import com.gudeng.commerce.gd.report.dto.DataDBQuery;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.util.DateType;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午3:59:57
 */
public class GoodsDataCacheServiceImpl extends ADataCacheService {

	@Autowired
	private GoodsDataBuilder goodsDataBuilder;

	public void setGoodsDataBuilder(GoodsDataBuilder goodsDataBuilder) {
		this.goodsDataBuilder = goodsDataBuilder;
	}

	@Override
	public String getReportType() {
		return ReportType.GOODS_REPORT;
	}

	@Override
	public DataDTO queryDataFromDB(DateType dt, DataCacheQuery dataCacheQuery, DataServiceQuery dataServiceQuery) throws ServiceException {
		DataDBQuery dataDBQuery = new DataDBQuery();
		
		dataDBQuery.setStartTime(dt.getStartTime());
		dataDBQuery.setEndTime(dt.getEndTime());
		dataDBQuery.setMemberId(dataCacheQuery.getMemberId());
		
		return goodsDataBuilder.getData(dataDBQuery);
	}

}
