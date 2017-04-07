package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;

public interface AdToolService {
	
	public List<AdAdvertiseDTO> getAdByMenuId(int menuId)throws Exception;
	
	public List<AdAdvertiseDTO> getAdBySignAndMarketId(Map<String,Object> params)throws Exception;
	

}
