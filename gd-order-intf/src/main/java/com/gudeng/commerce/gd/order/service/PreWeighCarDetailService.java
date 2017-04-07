package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;

/**
 * 货主入场商品表
 * @author wind
 *
 */
public interface PreWeighCarDetailService {
	
	/**
	 * 根据过磅id获取入场商品
	 * @param weighCarId
	 * @return
	 * @throws Exception 
	 */
	public List<PreWeighCarDetailDTO> getByWeighCarId(Long weighCarId);

	public PreWeighCarDetailDTO getById(Long id);
	
	/**
	 * 根据过磅userid获取 推送给当前用户的热门好货
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public List<PreWeighCarDetailDTO> getByBusinessUserId(Map map);
	
	public int getCountByUserId(Long userId);
	
	public int getCountByMobile(String mobile);

	/**
	 * 根据过磅userid获取根据分类推送给当前用户的热门好货
	 * @param  userId
	 * @return
	 * @throws Exception 
	 */
	public List<PreWeighCarDetailDTO> getByCategoryUserId(Long userId);
	
	
	
	/**
	 * 根据过磅产地供应商的手机号，搜索热门好货 
	 * @param  userId
	 * @return
	 * @throws Exception 
	 */
	public List<PreWeighCarDetailDTO> getByMobile(Map map);
	
	
	public int update(PreWeighCarDetailDTO preWeighCarDetailDTO);
	
	public int deleteBusiness(Long weighCarId,Long businessId) ;
	
	/**
	 * 抢货，或者确认进货时，减掉热门好货的重量，直接生成一条入库单
	 * 
	 * */
	public int grapGoods(PreWeighCarDetailDTO pwd,Long inStoreNo,Long businessId);
	
	/**
	 * ADMIN 获得总数
	 * @param preWeighCarDetailDTO
	 * @return
	 */
	public int getTotalForAdmin(Map<String, Object> params);
	
	/**
	 * ADMIN 通过条件查询对应数据
	 * @param preWeighCarDetailDTO
	 * @return
	 */
	public List<PreWeighCarDetailDTO> getByConditionPageForAdmin(Map<String, Object> params);
	
	/**
	 * 批量修改补贴状态
	 * @param pwds
	 * @return
	 */
	public int batchUpdatePaymentStatus(String pwds,String updateUserId);
}
