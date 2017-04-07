package com.gudeng.commerce.gd.m.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MarketDTO;

public interface MarketToolService {
	
	public List<MarketDTO> getMarketList(Map<String, Object> paramsMap) throws Exception ;
}
