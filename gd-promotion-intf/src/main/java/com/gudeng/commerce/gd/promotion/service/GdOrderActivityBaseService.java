package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActActivityIntegralDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderPenaltyQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActivityQueryDTO;

/**
 * 订单活动基础服务类
 * @author TerryZhang
 */
public interface GdOrderActivityBaseService {

	/**
	 * 查询商铺/订单活动信息
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	public GdOrderActivityResultDTO queryOrderActivty(GdOrderActivityQueryDTO queryDTO) throws Exception;
	
	/**
	 * 批量查询商铺/订单活动信息
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	public List<GdOrderActivityResultDTO> batchQueryOrderActivty(List<GdOrderActivityQueryDTO> queryList) throws Exception;
	
	/**
	 * 清除活动缓存
	 * @param type 清除缓存类型 0全部 1刷卡补贴，2市场，3平台，4预付款违约金，5物流
	 * @return
	 */
	public boolean deleteCach(int type);
	
	/**
	 * 清除活动缓存
	 * @return
	 */
	public boolean deleteCach();
	
	/**
	 * 查询单个商品配送活动信息
	 * @return
	 */
	public GdProductActivityQueryDTO queryProductAct(GdProductActivityQueryDTO queryDTO) throws Exception ;
	
	/**
	 * 查询多个商品配送活动信息
	 * @return
	 */
	public List<GdProductActivityQueryDTO> batchQueryProductAct(List<GdProductActivityQueryDTO> queryDtoList);
	
	/**
	 * 查询订单违约金信息
	 * @return
	 */
	public GdOrderPenaltyQueryDTO queryOrderPenalty(GdOrderPenaltyQueryDTO queryDTO) throws Exception ;
	
	/**
	 * 根据市场id或用户id查询活动参与用户规则信息
	 * @param pareMap
	 * @return
	 * @throws Exception
	 */
	public ActActivityIntegralDTO queryActActivityIntegral(Map<String,Object> pareMap) throws Exception ;
}
