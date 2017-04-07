package com.gudeng.commerce.gd.report.service;

import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.dto.TimeCacheType;
import com.gudeng.commerce.gd.report.exception.ServiceException;

/**
 * 用于接口使用的查询服务
 * @author Ailen
 *
 */
public interface IDataService {
	
	/**
	 * @Description: 查询数据报表信息
	 * DataServiceQuery
	 * @param memberId 会员id
	 * 
	 * @param TimeType 时间类型 （空值时取timeStr）
	 * 	(1, "最近七天"),
		(2, "最近15天"),
		(3, "最近30天"),
		(4, "本月")
	 * @param timeStr 时间 yyyy-MM-dd OR yyyy-MM
	 * @return
	 * @throws Exception
	 */
	public DataDTO queryData(DataServiceQuery dataServiceQuery) throws ServiceException;
	
	/**
	 * 清除缓存
	 * @param memberId 会员ID
	 * @param timetype 枚举TimeCacheType
	 * @throws Exception
	 */
	public void cleanCacheSpecial(Long memberId, TimeCacheType timetype, String timeString) throws ServiceException;

}
