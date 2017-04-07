package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.dto.NpsPurchaseDTO;
import com.gudeng.commerce.gd.customer.dto.OfferPriceDTO;
import com.gudeng.commerce.gd.customer.entity.NpsOfferPriceEntity;

public interface NpsOfferPriceService {
	
	/**
	 * 插入供应商报价
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Long insert(Map<String, Object> map) throws Exception; 
	/**
	 * 更新供应商报价
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer updateInfo(Map<String, Object> map) throws Exception;	
	/**
	 * 根据ID查供应商报价
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer findNpsOfferPriceById(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据ID查询供应商报价
	 * @param id
	 * @return
	 */
	public NpsOfferPriceDTO getById(String id);
	
	/**
	 * 根据id删除供应商报价
	 * @param id
	 * @return
	 */
	public int deleteById(String id);
	
	/**
	 * 根据id批量删除供应商报价
	 * @param id
	 * @return
	 */
	public int deleteById(List<String> list);
	
	/**
	 * 更新供应商报价
	 * @param t
	 * @return
	 */
	public int update(NpsOfferPriceDTO t) throws Exception;
	
	/**
	 * 获取总数
	 * @param map 查询参数
	 * @return
	 * @throws Exception 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 添加供应商报价
	 * @param entity
	 * @return
	 */
	public Long insert(NpsOfferPriceEntity entity);
	
	/**
	 * 根据条件分页获取报价列表
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<NpsOfferPriceDTO> getListPage(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件获取报价列表
	 * @param map
	 * @return
	 */
	public List<NpsOfferPriceDTO> getList(Map<String, Object> map);	
	
	
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
	public int updateStatus(NpsOfferPriceDTO t) throws Exception;
	
	
	/**
	 * 更新供应商报价信息状态
	 * @param t
	 * @return
	 */
	public int getUserAndOfferPriceCount(NpsOfferPriceEntity entity) throws Exception;
	
	   /**
     * 每天最小价格信息推送列表
     * 0：00-18：00
     * @return
     */
    public List<OfferPriceDTO> everyDayMinPriceList()throws Exception;
}
