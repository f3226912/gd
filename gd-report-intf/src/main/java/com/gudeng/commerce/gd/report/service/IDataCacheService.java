package com.gudeng.commerce.gd.report.service;

import com.gudeng.commerce.gd.report.dto.DataCacheQuery;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.exception.ServiceException;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午3:50:43
 */
public interface IDataCacheService {
	
	/**
	 * @Description: 查询数据报表信息
	 * @param memberId 会员id
	 * @param TimeType 时间类型 （空值时取timeStr）
	 * @param timeStr 时间按照具体需求填写 例如 小时则填写两位小时数hh 月份则填写yyyyMM
	 * @param DataServiceQuery 特殊查询条件存放地 需要的东西
	 * @return
	 * @throws Exception
	 */
	public DataDTO queryData(DataCacheQuery dataQuery, DataServiceQuery dataServiceQuery) throws ServiceException ;
	
	/**
	 * 获得redis数据，如没有查数据库 AOP
	 * @param key
	 * @param dataCacheQuery
	 * @param dataServiceQuery
	 * @return
	 * @throws Exception
	 */
	public DataDTO getDataCache(String key,DataCacheQuery dataCacheQuery, DataServiceQuery dataServiceQuery) throws ServiceException;
	
	public void cleanCacheSpecial(DataCacheQuery dataCacheQuery) throws ServiceException;

}
