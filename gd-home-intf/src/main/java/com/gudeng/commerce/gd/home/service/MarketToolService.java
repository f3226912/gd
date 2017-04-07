package com.gudeng.commerce.gd.home.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.MarketDTO;

public interface MarketToolService {

	/**
	 * 获取农批中心 type=2
	 * 
	 * @throws Exception
	 */
	public List<MarketDTO> getAllByType(String marketType) throws Exception;

	/**
	 * 
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<MarketDTO> getAllByStatusAndType(String status, String type) throws Exception;
	
	
	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return MarketDTO
	 * 
	 */
	public MarketDTO getById(String id)throws Exception;

}
