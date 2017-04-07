package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.entity.NpsOfferPriceEntity;

public interface NpsOfferPriceToolService {

	/**
	 * 获取报价列表
	 * @param parsMap
	 * @return
	 * @throws Exception
	 */
	public List<NpsOfferPriceDTO> getList(Map<String, Object> parsMap) throws Exception;

	/**
	 * 添加供应商报价
	 * @param entity
	 * @return
	 */
	public Long insert(NpsOfferPriceEntity entity) throws Exception;
	/**
	 * 更新供应商报价
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer updateInfo(Map<String, Object> map) throws Exception;	
	
	/**
	 * 获取报价详情 我的报价
	 * @param parsMap
	 * @return
	 * @throws Exception
	 */
	public NpsOfferPriceDTO getOfferPriceId(Map<String, Object> parsMap) throws Exception;
	
	
	/**
	 * 获取报价列表
	 * @param parsMap
	 * @return
	 * @throws Exception
	 */
	public List<NpsOfferPriceDTO> getOfferPriceList(Map<String, Object> parsMap) throws Exception;
	
	/**
	 * 获取报价列表总条数
	 * @param parsMap
	 * @return
	 * @throws Exception
	 */
	public int getOfferPriceTotal(Map<String, Object> parsMap) throws Exception;
	
	/**
	 * 更新供应商报价信息状态
	 * @param t
	 * @return
	 */
	public int getUserAndOfferPriceCount(NpsOfferPriceEntity entity) throws Exception;
}
