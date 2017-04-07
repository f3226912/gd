package com.gudeng.commerce.gd.report.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.report.dto.DataCacheQuery;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.service.IDataCacheService;
import com.gudeng.commerce.gd.report.util.DateType;
import com.gudeng.commerce.gd.report.util.DateUtil;

/**
 * @Description: 统一处理缓存方法，模板模式 如不存在，则执行对应的查询方法
 * @author mpan
 * @date 2016年6月13日 下午3:50:43
 */
public abstract class ADataCacheService implements IDataCacheService {
	
	@Autowired
	private RedisUtil redisUtil;
	
	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

	public abstract String getReportType();
	
	private SimpleDateFormat dataFormat = new SimpleDateFormat("MMdd");
	
	private SimpleDateFormat dataFormatHH = new SimpleDateFormat("HH");
	
	// 缓存方案统一处理实现
	@Override
	public DataDTO queryData(DataCacheQuery dataQuery, DataServiceQuery dataServiceQuery) throws ServiceException {
		
		String key = null;
		key = getKey(dataQuery);
		
		//获得数据 返回 redis
		return redisUtil.ReadWriteDataRedisCache(key, this, dataQuery, dataServiceQuery);
		
	}

	/**
	 * 获得所需redis key值
	 * @param dataQuery 
	 * @return
	 */
	private String getKey(DataCacheQuery dataQuery) {
		String key = null;
		Date today = new Date();
		
		switch (dataQuery.getTimetype()) {
		case HOUR_CACHE: //只能获得当前小时
			//memberId_trade_report_mmddhh
			key = dataQuery.getMemberId()+"_"+getReportType()+"_report_"+dataFormat.format(today)+dataFormatHH.format(today);
			break;
		case DAY_CACHE:
			//memberId_trade_report_mmdd
			key = dataQuery.getMemberId()+"_"+getReportType()+"_report_"+dataFormat.format(DateUtil.formateDate(dataQuery.getTimeString(), new SimpleDateFormat("yyyyMMdd")));
			break;
		case TODAY_CACHE:
			//memberId_trade_report_mmddhh_day
			key = dataQuery.getMemberId()+"_"+getReportType()+"_report_"+dataFormat.format(today)+dataFormatHH.format(today)+"_day";
			break;
		case SEVEN_CACHE:
			//memberId_trade_report_week_mmdd
			key = dataQuery.getMemberId()+"_"+getReportType()+"_report_week_"+dataFormat.format(today);
			break;
		case HALF_MONTH_CACHE:
			//memberId_trade_report_half_month_mmdd
			key = dataQuery.getMemberId()+"_"+getReportType()+"_report_half_month_"+dataFormat.format(today);
			break;
		case MONTH_CACHE:
			//memberId_trade_report_month_mmdd
			key = dataQuery.getMemberId()+"_"+getReportType()+"_report_month_"+dataFormat.format(today);
			break;
		case THIRTY_CACHE:
			//memberId_trade_report_30_mmdd
			key = dataQuery.getMemberId()+"_"+getReportType()+"_report_30_"+dataFormat.format(today);
			break;
		case MONTH_BEFORE_CACHE:
			//memberId_trade_report_month_before_yyyymm
			key = dataQuery.getMemberId()+"_"+getReportType()+"_report_month_before_"+dataQuery.getTimeString();
			break;
		}
		return key;
	}
	
	/**
	 * 
	 * @param key
	 * @param dataCacheQuery 用于存储特殊查询条件
	 * @return
	 */
	@Override
	public DataDTO getDataCache(String key,DataCacheQuery dataCacheQuery, DataServiceQuery dataServiceQuery) throws ServiceException {
		//存储时间范围
		DateType dateType = null;
		try {
			/*
			 * 计算查询时间范围
			 */
			switch (dataCacheQuery.getTimetype()) {
			case HOUR_CACHE:
				//memberId_trade_report_mmddhh
				dateType = DateType.getDateType(DateType.TODAY_NOW);
				break;
			case DAY_CACHE:
				dateType = DateType.getDateType(DateType.DAY_OF_BEFORE, dataServiceQuery.getTimeStr());
				break;
			case TODAY_CACHE:
				//memberId_trade_report_mmddhh_day
				dateType = DateType.getDateType(DateType.TODAY_BEFORE);
				break;
			case SEVEN_CACHE:
				//memberId_trade_report_week_mmdd
				dateType = DateType.getDateType(DateType.SIX_DAY_BEFORE);
				break;
			case HALF_MONTH_CACHE:
				//memberId_trade_report_half_month_mmdd
				dateType = DateType.getDateType(DateType.FOURTEENTH_DAY_BEFORE);
				break;
			case MONTH_CACHE:
				//memberId_trade_report_month_mmdd
				dateType = DateType.getDateType(DateType.MONTH_DAY_BEFORE);
				break;
			case THIRTY_CACHE:
				//memberId_trade_report_30_mmdd
				dateType = DateType.getDateType(DateType.TWENTYNIGHT_DAY_BEFORE);
				break;
			case MONTH_BEFORE_CACHE:
				//memberId_trade_report_month_before_mmdd
				dateType = DateType.getDateType(DateType.MONTH_OF_BEFORE, dataServiceQuery.getTimeStr());
				break;
			}
		} catch (Exception e) {
			throw new ServiceException("设置startTime EndTime", e);
		}
		
		
		
		return queryDataFromDB(dateType,dataCacheQuery, dataServiceQuery);
	}
	
	/**
	 * 清楚对应redis缓存
	 * @param key
	 * @param dataCacheQuery
	 * @throws Exception
	 */
	public void delDataCache(String key) throws Exception {
		redisUtil.delRedis(key);
	}
	
	/**
	 * 如redis得不到数据，调用对应获取数据的方法
	 * 各自实现对应的查数据方法
	 * @param DateType 存储查询时间范围
	 * @param dataCacheQuery
	 * @param DataServiceQuery 查询条件
	 * @return
	 * @throws Exception
	 */
	public abstract DataDTO queryDataFromDB(DateType dateType,DataCacheQuery dataCacheQuery, DataServiceQuery dataServiceQuery) throws ServiceException;
	
	/**
	 * 清空缓存redis方法
	 * @param type 各方法自定义 不同type 不同缓存清空
	 * 缓存key可使用对应 reportType
	 * 调用delDataCache方法清除
	 */
	@Override
	public void cleanCacheSpecial(DataCacheQuery dataCacheQuery) throws ServiceException {
		try {
			dataCacheQuery.setReportType(getReportType());
			delDataCache(getKey(dataCacheQuery));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
