package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.DataToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.report.dto.DataCacheQuery;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
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

	@Override
	public DataDTO queryTradeData(DataServiceQuery dataQuery) throws ServiceException {
		return getHessianTradeService().queryData(dataQuery);
	}

	@Override
	public DataDTO queryGoodsData(DataServiceQuery dataQuery) throws ServiceException {
		return getHessianGoodsService().queryData(dataQuery);
	}

	@Override
	public DataDTO queryPhoneData(DataServiceQuery dataQuery) throws ServiceException {
		return getHessianPhoneService().queryData(dataQuery);
	}
	
	@Override
	public void cleanGoods(DataCacheQuery dataCacheQuery) throws ServiceException {
		getHessianGoodsService().cleanCacheSpecial(dataCacheQuery.getMemberId(), dataCacheQuery.getTimetype(), null);
	}

}
