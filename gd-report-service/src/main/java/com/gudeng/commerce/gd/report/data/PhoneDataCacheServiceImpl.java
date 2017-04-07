package com.gudeng.commerce.gd.report.data;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.report.ReportType;
import com.gudeng.commerce.gd.report.dto.DataCacheQuery;
import com.gudeng.commerce.gd.report.dto.DataDBQuery;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.dto.UserPhoneDataDTO;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.util.DateType;

/**
 * @Description: 电话统计缓存服务
 * @author mpan
 * @date 2016年6月13日 下午3:59:57
 */
public class PhoneDataCacheServiceImpl extends ADataCacheService {
	
	@Autowired
	private PhoneDataBuilder phoneDataBuilder;
	
	public void setDataBuilder(PhoneDataBuilder phoneDataBuilder) {
		this.phoneDataBuilder = phoneDataBuilder;
	}

	@Override
	public String getReportType() {
		return ReportType.PHONE_REPORT;
	}

	@Override
	public UserPhoneDataDTO queryDataFromDB(DateType dateType, DataCacheQuery dataCacheQuery, DataServiceQuery dataServiceQuery) throws ServiceException {
		DataDBQuery dataDBQuery = new DataDBQuery();
		dataDBQuery.setStartTime(dateType.getStartTime());
		dataDBQuery.setEndTime(dateType.getEndTime());
		dataDBQuery.setMemberId(dataCacheQuery.getMemberId());
		return phoneDataBuilder.getData(dataDBQuery);
	}

}
