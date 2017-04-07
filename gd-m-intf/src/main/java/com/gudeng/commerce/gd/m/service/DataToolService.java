package com.gudeng.commerce.gd.m.service;

import com.gudeng.commerce.gd.report.dto.DataCacheQuery;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.exception.ServiceException;

public interface DataToolService {
	
	public DataDTO queryTradeData(DataServiceQuery dataQuery) throws ServiceException;
	
	public DataDTO queryGoodsData(DataServiceQuery dataQuery) throws ServiceException;
	
	public DataDTO queryPhoneData(DataServiceQuery dataQuery) throws ServiceException;
	
	public void cleanGoods(DataCacheQuery dataCacheQuery) throws ServiceException;

}