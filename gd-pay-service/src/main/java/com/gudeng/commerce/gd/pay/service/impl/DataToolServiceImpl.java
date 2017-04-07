package com.gudeng.commerce.gd.pay.service.impl;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.pay.service.DataToolService;
import com.gudeng.commerce.gd.pay.util.DateUtil;
import com.gudeng.commerce.gd.pay.util.GdProperties;
import com.gudeng.commerce.gd.report.dto.TimeCacheType;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.service.IDataService;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月14日 下午8:58:02
 */
public class DataToolServiceImpl implements DataToolService {
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static IDataService tradeDataService;
	
	private static IDataService goodsDataService;
	
	private static IDataService phoneDataService;
	
	/**
	 * tradeDataService接口服务
	 * @return
	 */
	protected IDataService getHessianTradeService() throws ServiceException {
		String url = gdProperties.getProperties().getProperty("gd.tradeDataService.url");
		if(tradeDataService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			try {
				tradeDataService = (IDataService) factory.create(IDataService.class, url);
			} catch (MalformedURLException e) {
				throw new ServiceException("获取交易数据hessian服务", e);
			}
		}
		return tradeDataService;
	}
	
	/**
	 * goodsDataService接口服务
	 * @return
	 */
	protected IDataService getHessianGoodsService() throws ServiceException {
		String url = gdProperties.getProperties().getProperty("gd.goodsDataService.url");
		if(goodsDataService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			try {
				goodsDataService = (IDataService) factory.create(IDataService.class, url);
			} catch (MalformedURLException e) {
				throw new ServiceException("获取商品数据hessian服务", e);
			}
		}
		return goodsDataService;
	}
	
	/**
	 * phoneDataService接口服务
	 * @return
	 */
	protected IDataService getHessianPhoneService() throws ServiceException {
		String url = gdProperties.getProperties().getProperty("gd.phoneDataService.url");
		if(phoneDataService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			try {
				phoneDataService = (IDataService) factory.create(IDataService.class, url);
			} catch (MalformedURLException e) {
				throw new ServiceException("获取电话数据hessian服务", e);
			}
		}
		return phoneDataService;
	}

	private void cleanTradeCacheSpecial(Long memberId, TimeCacheType timetype, String timeString) throws ServiceException {
		getHessianTradeService().cleanCacheSpecial(memberId, timetype, timeString);
	}
	
	@Override
	public void cleanTradeCacheSpecial(Long memberId, TimeCacheType timetype) throws ServiceException {
		cleanTradeCacheSpecial(memberId, timetype, null);
	}
	
	@Override
	public void cleanOldTradeCacheSpecial(Long memberId, Date oldTime) throws ServiceException {
		Date nowDate = new Date();
		int days = daysOfTwo(nowDate, oldTime);
		if (days < 0) {
			return;
		}
		if (days == 0) { // 今天
			cleanTradeCacheSpecial(memberId, TimeCacheType.TODAY_CACHE);
		}
		if (days == 1) { // 昨天
			cleanTradeCacheSpecial(memberId, TimeCacheType.DAY_CACHE, DateUtil.getDate(oldTime, "yyyy-MM-dd"));
		}
		if (days <= 6) { // 最近7天
			cleanTradeCacheSpecial(memberId, TimeCacheType.SEVEN_CACHE);
		}
		if (days <= 14) { // 最近15天
			cleanTradeCacheSpecial(memberId, TimeCacheType.HALF_MONTH_CACHE);
		}
		if (days <= 29) { // 最近30天
			cleanTradeCacheSpecial(memberId, TimeCacheType.THIRTY_CACHE);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		int nowMonth = cal.get(Calendar.MONTH) + 1;
		
		cal.setTime(oldTime);
		int oldMonth = cal.get(Calendar.MONTH) + 1;
		if (nowMonth == oldMonth) { // 本月
			cleanTradeCacheSpecial(memberId, TimeCacheType.MONTH_CACHE);
		} else {
			cleanTradeCacheSpecial(memberId, TimeCacheType.MONTH_BEFORE_CACHE, DateUtil.getDate(oldTime, "yyyy-MM"));
		}
	}

	@Override
	public void cleanGoodsCacheSpecial(Long memberId, TimeCacheType timetype) throws ServiceException {
		getHessianGoodsService().cleanCacheSpecial(memberId, timetype, null);
	}

	@Override
	public void cleanPhoneCacheSpecial(Long memberId, TimeCacheType timetype) throws ServiceException {
		getHessianPhoneService().cleanCacheSpecial(memberId, timetype, null);
	}
	
	/**
	 * 计算两个日期相隔天数
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}
	
}
