package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;

public interface PromProdinfoService {
	
	

	/**
	 * 添加活动商品信息
	 * @param paramMap
	 * @return
	 */
	public Integer addProducts(Map<String, Object> paramMap);
	/**
	 * 根据活动ID查询供应商列表信息
	 * @param id
	 * @return
	 */
	List<PromProdInfoDTO> querySupplerPageByCondition(Map<String, Object> map);
	
	Integer getSupplerTotalCountByCondition(Map<String, Object> map);
	
	/**
	 * 根据活动ID查询供应商列表信息
	 * @param id
	 * @return
	 */
	List<PromProdInfoDTO> queryProductPageByCondition(Map<String, Object> map);
	
	Integer getProductTotalCountByCondition(Map<String, Object> map);
	
	Integer updatePromProdInfo(List<PromProdInfoDTO> list);
	/**
	 * 取消某个活动
	 * @param paramMap
	 * @return
	 */
	public int cancelPromotionActivity(Map<String, Object> paramMap);

}
