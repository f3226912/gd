package com.gudeng.commerce.gd.report.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gudeng.commerce.gd.report.dto.DataCacheQuery;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.dto.TimeCacheType;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.service.IDataCacheService;
import com.gudeng.commerce.gd.report.service.IDataService;
import com.gudeng.commerce.gd.report.util.DateUtil;

public abstract class ADataService implements IDataService {

	private IDataCacheService dataCacheService;

	public IDataCacheService getDataCacheService() {
		return dataCacheService;
	}

	public void setDataCacheService(IDataCacheService dataCacheService) {
		this.dataCacheService = dataCacheService;
	}

	/**
	 * 判断读取的是哪个时间范围统计
	 */
	@Override
	public DataDTO queryData(DataServiceQuery dataServiceQuery) throws ServiceException {
		try {
			if (dataServiceQuery.getTimeType() == null) {
				/*
				 * 判断时间timeString是表示月份 还是 日期
				 */
				if (dataServiceQuery.getTimeStr().length() == 7) { //月份
					String currMonth = DateUtil.getDate(new Date(), "yyyy-MM");
					if (currMonth.equals(dataServiceQuery.getTimeStr())) { // 本月
						return getMonth(dataServiceQuery);
					} else {
						return getMonthBefore(dataServiceQuery);
					}
				} else {
					//转换时间判断日期是否为昨天
					SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = dataFormat.parse(dataServiceQuery.getTimeStr());
					
					/*
					 * 如为今天，则获取当前小时+之前小时数据
					 */
					if(!isYeaterday(date,new Date())) {
						//今天则获得一小时前所有和当前小时的数据整合
						return getToday(dataServiceQuery);
					} else {
						return getYestoday(dataServiceQuery);
					}
				}
			} else {
				switch (dataServiceQuery.getTimeType()) {
				case 1:
					return getServen(dataServiceQuery);
				case 2:
					return getFiftheen(dataServiceQuery);
				case 3:
					return getThirty(dataServiceQuery);
				case 4:
					return getMonth(dataServiceQuery);
				}
			}
		} catch (Exception ex) {
			throw new ServiceException("查询数据失败", ex);
		}

		return null;
	}

	/**
	 * 获得某一小时 数据结构 List 某小时 1个DataDTO
	 * 
	 * @param dataQuery
	 * @return
	 */
	public DataDTO getHour(DataServiceQuery dataQuery) throws ServiceException {
		List<DataDTO> list = new ArrayList<>();
		// 设置查询参数
		DataCacheQuery dataCacheQuery = new DataCacheQuery();
		dataCacheQuery.setMemberId(dataQuery.getMemberId());
		dataCacheQuery.setTimetype(TimeCacheType.HOUR_CACHE);

		// 读取某小时数据
		list.add(this.getDataCacheService().queryData(dataCacheQuery, dataQuery));

		// 整合数据 排序
		return consolidateDataDTO(list, dataQuery);
	}

	/**
	 * 获得昨天 数据结构 List 昨天 1个DataDTO
	 * 
	 * @param dataQuery
	 * @return
	 */
	public DataDTO getYestoday(DataServiceQuery dataQuery) throws Exception {
		List<DataDTO> list = new ArrayList<>();
		// 设置查询参数
		DataCacheQuery dataCacheQuery = new DataCacheQuery();
		dataCacheQuery.setMemberId(dataQuery.getMemberId());
		dataCacheQuery.setTimetype(TimeCacheType.DAY_CACHE);
		
		//设置timeString
		dataCacheQuery.setTimeString(dataQuery.getTimeStr().replace("-", ""));

		// 读取昨天数据
		list.add(this.getDataCacheService().queryData(dataCacheQuery, dataQuery));

		return consolidateDataDTO(list, dataQuery);
	}

	/**
	 * 获得当天 数据结构 List 前几个小时 + 当前小时 2个DataDTO
	 * 
	 * @param dataQuery
	 * @return
	 */
	public DataDTO getToday(DataServiceQuery dataQuery) throws ServiceException {
		List<DataDTO> list = new ArrayList<>();
		// 设置查询参数
		DataCacheQuery dataCacheQuery = new DataCacheQuery();
		dataCacheQuery.setMemberId(dataQuery.getMemberId());
		dataCacheQuery.setTimetype(TimeCacheType.TODAY_CACHE);

		// 读取当小时之前数据
		list.add(this.getDataCacheService().queryData(dataCacheQuery, dataQuery));

		// 获得当前小时时间范围
		dataCacheQuery.setTimetype(TimeCacheType.HOUR_CACHE);

		// 获得数据
		list.add(this.getDataCacheService().queryData(dataCacheQuery, dataQuery));

		return consolidateDataDTO(list, dataQuery);
	}

	/**
	 * 获得最近七天 数据结构 List 前六天 + 当天 2个DataDTO
	 * 
	 * @param dataQuery
	 * @return
	 */
	public DataDTO getServen(DataServiceQuery dataQuery) throws Exception {
		List<DataDTO> list = new ArrayList<>();
		// 设置查询参数
		DataCacheQuery dataCacheQuery = new DataCacheQuery();
		dataCacheQuery.setMemberId(dataQuery.getMemberId());
		dataCacheQuery.setTimetype(TimeCacheType.SEVEN_CACHE);

		// 读取六天之前数据
		list.add(this.getDataCacheService().queryData(dataCacheQuery, dataQuery));

		// 获得今天
		list.add(this.getToday(dataQuery));

		return consolidateDataDTO(list, dataQuery);
	}

	/**
	 * 获得十五天 最近半月 数据结构 List 前14天半月 + 当天 2个DataDTO
	 * 
	 * @param dataQuery
	 * @return
	 */
	public DataDTO getFiftheen(DataServiceQuery dataQuery) throws ServiceException {
		List<DataDTO> list = new ArrayList<>();
		// 设置查询参数
		DataCacheQuery dataCacheQuery = new DataCacheQuery();
		dataCacheQuery.setMemberId(dataQuery.getMemberId());
		dataCacheQuery.setTimetype(TimeCacheType.HALF_MONTH_CACHE);

		// 读取十四天之前数据
		list.add(this.getDataCacheService().queryData(dataCacheQuery, dataQuery));

		// 获得今天
		list.add(this.getToday(dataQuery));

		return consolidateDataDTO(list, dataQuery);
	}

	/**
	 * 获得三十天前 数据结构 List 前29天 + 当天 2个DataDTO
	 * 
	 * @param dataQuery
	 * @return
	 */
	public DataDTO getThirty(DataServiceQuery dataQuery) throws Exception {
		List<DataDTO> list = new ArrayList<>();
		// 设置查询参数
		DataCacheQuery dataCacheQuery = new DataCacheQuery();
		dataCacheQuery.setMemberId(dataQuery.getMemberId());
		dataCacheQuery.setTimetype(TimeCacheType.THIRTY_CACHE);

		// 读取29天之前数据
		list.add(this.getDataCacheService().queryData(dataCacheQuery, dataQuery));

		// 获得今天
		list.add(this.getToday(dataQuery));

		return consolidateDataDTO(list, dataQuery);
	}

	/**
	 * 获得当月 数据结构 List 前一月不包含今天 + 当天 2个DataDTO
	 * 
	 * @param dataQuery
	 * @return
	 */
	public DataDTO getMonth(DataServiceQuery dataQuery) throws ServiceException {
		List<DataDTO> list = new ArrayList<>();
		// 设置查询参数
		DataCacheQuery dataCacheQuery = new DataCacheQuery();
		dataCacheQuery.setMemberId(dataQuery.getMemberId());
		dataCacheQuery.setTimetype(TimeCacheType.MONTH_CACHE);

		// 读取一个月之前数据
		list.add(this.getDataCacheService().queryData(dataCacheQuery, dataQuery));

		// 获得今天
		list.add(this.getToday(dataQuery));

		return consolidateDataDTO(list, dataQuery);
	}

	/**
	 * 获得之前某一月 数据结构 List 之前某月 1个DataDTO
	 * 
	 * @param dataQuery
	 * @return
	 */
	public DataDTO getMonthBefore(DataServiceQuery dataQuery) throws ServiceException {
		List<DataDTO> list = new ArrayList<>();
		// 设置查询参数
		DataCacheQuery dataCacheQuery = new DataCacheQuery();
		dataCacheQuery.setMemberId(dataQuery.getMemberId());
		dataCacheQuery.setTimetype(TimeCacheType.MONTH_BEFORE_CACHE);
		dataCacheQuery.setTimeString(dataQuery.getTimeStr().replace("-", ""));
		// 读取之前某月数据
		list.add(this.getDataCacheService().queryData(dataCacheQuery, dataQuery));

		return consolidateDataDTO(list, dataQuery);
	}

	/**
	 * 对外实现各自的数据整合 包括数据拼接,数据排序等
	 * 
	 * @param list
	 * @param dataServiceQuery
	 * @return
	 */
	public abstract DataDTO consolidateDataDTO(List<DataDTO> list, DataServiceQuery dataQuery)throws ServiceException;

	public boolean isYeaterday(Date oldDate, Date newDate) {
		Calendar oldCal = Calendar.getInstance();
		Calendar newCal = Calendar.getInstance();

		oldCal.setTime(oldDate);
		newCal.setTime(newDate);
		return Math.abs(newCal.get(Calendar.DAY_OF_YEAR) - oldCal.get(Calendar.DAY_OF_YEAR)) == 1;
	}
	
	@Override
	public void cleanCacheSpecial(Long memberId, TimeCacheType timetype, String timeString) throws ServiceException {
		DataCacheQuery dataCacheQuery = new DataCacheQuery();
		dataCacheQuery.setMemberId(memberId);
		dataCacheQuery.setTimetype(timetype);
		dataCacheQuery.setTimeString(timeString);
		getDataCacheService().cleanCacheSpecial(dataCacheQuery);
	}
}
