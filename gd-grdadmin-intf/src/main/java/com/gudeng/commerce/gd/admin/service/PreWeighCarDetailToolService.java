package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;

public interface PreWeighCarDetailToolService {
	/**
	 * 根据过磅id获取入场商品
	 * 
	 * @param weighCarId
	 * @return
	 * @throws Exception
	 */
	public List<PreWeighCarDetailDTO> getByWeighCarId(Long weighCarId) throws Exception;

	public PreWeighCarDetailDTO getById(Long id) throws Exception;

	/**
	 * 根据过磅userid获取 推送给当前用户的热门好货
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<PreWeighCarDetailDTO> getByBusinessUserId(Map map) throws Exception;

	public int getCountByUserId(Long userId) throws Exception;

	public int getCountByMobile(String mobile) throws Exception;

	/**
	 * 根据过磅userid获取根据分类推送给当前用户的热门好货
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<PreWeighCarDetailDTO> getByCategoryUserId(Long userId) throws Exception;

	/**
	 * 根据过磅产地供应商的手机号，搜索热门好货
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<PreWeighCarDetailDTO> getByMobile(Map map) throws Exception;

	public int update(PreWeighCarDetailDTO preWeighCarDetailDTO) throws Exception;

	public int deleteBusiness(Long weighCarId, Long businessId) throws Exception;

	/**
	 * 抢货，或者确认进货时，减掉热门好货的重量，直接生成一条入库单
	 * 
	 * */
	public int grapGoods(PreWeighCarDetailDTO pwd, Long inStoreNo,
			Long businessId) throws Exception;

	/**
	 * ADMIN 获得总数
	 * 
	 * @param preWeighCarDetailDTO
	 * @return
	 */
	public int getTotalForAdmin(Map<String, Object> params) throws Exception;

	/**
	 * ADMIN 通过条件查询对应数据
	 * 
	 * @param preWeighCarDetailDTO
	 * @return
	 */
	public List<PreWeighCarDetailDTO> getByConditionPageForAdmin(
			Map<String, Object> params) throws Exception;
	
	/**
	 * 批量更新补贴状态
	 * @param pwds
	 * @return
	 */
	public int batchUpdatePaymentStatus(String pwds,String updateUserId) throws Exception;
	
	public Map<String, Object> getParamsMap(PreWeighCarDetailDTO dto);
	
}
