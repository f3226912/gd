package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.input.AreaAppInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.ReMemberMarketDTO;

public interface MarketToolService {
	
	public List<MarketDTO> getByCondition2_5(String provinceId, String cityId,
			String marketType) throws Exception ;

	/**
	 * 
	 * @param provinceId
	 * @param cityId
	 * @param marketType
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getByConditionV3(Long provinceId, Long cityId, String marketType) throws Exception;
	
	/**
	 * 根据省市来查找市场信息
	 * @param provinceId 省份id
	 * @param cityId  城市id
	 * @param marketType  1为街市,2为市场(农批中心),3用户添加
	 * @return
	 * @throws Exception
	 */
	public List<MarketDTO> getByCondition(String provinceId, String cityId, String marketType) throws Exception;
	
	/**
	 * 根据会员id查找会员的市场
	 * @param memberId  会员id
	 * @return
	 * @throws Exception
	 */
	public List<ReMemberMarketDTO> getReMemberMarket(Long memberId) throws Exception;
	
	/**
	 * 增加会员关注市场
	 * @param memerbId 会员id
	 * @param marketId 市场id
	 * @return
	 * @throws Exception
	 */
	public int addReMemberMarket(Long memberId, Long marketId) throws Exception;
	
	/**
	 * 删除会员关注市场
	 * @param memberId  会员id
	 * @return
	 * @throws Exception
	 */
	public int deleteReMemberMarket(Long memberId) throws Exception;
	
	/**
	 * 增加市场信息
	 * @param m
	 * @return 插入的市场id
	 * @throws Exception
	 */
	public Long addMarket(MarketDTO m) throws Exception;
	
	/**
	 * 根据状态查询所有街市
     * @param status,传null查询所用启用和停用
	 * @return
	 * @throws Exception
	 */
	public List<MarketDTO> getAllByStatus(String status) throws Exception;
	
	/**
	 */
	public List<MarketDTO> getAllBySearch(Map<String, Object> params) throws Exception;

	/**
	 * 根据城市名或经纬度
	 * 获取附近的市场
	 * @param inputDTO
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum getNearbyMarket(AreaAppInputDTO inputDTO,
			Map<String, Object> map) throws Exception;
	
	/**
	 * 根据市场id获取对应的市场信息
	 * @return
	 */
	public MarketDTO getById(String id) throws Exception;
	
	/**
	 * 根据市场ID查询市场信息
	 * @param marketId
	 * @return
	 * @throws Exception
	 */
	public MarketDTO getMarketById(String marketId) throws Exception ;
	}
