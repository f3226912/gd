package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;

/**
 * 货主入场商品
 * @author wind
 *
 */
public interface PreWeighCarDetailToolService {

	/**
	 * 根据过磅记录id获取某次过磅的入场商品
	 * @param weighCarId
	 * @return
	 * @throws Exception 
	 */
	public List<PreWeighCarDetailDTO> getByWeighCarId(Long weighCarId) throws Exception;
	
	public PreWeighCarDetailDTO getById(Long id) throws Exception;
	
	
	public List<PreWeighCarDetailDTO> getByBusinessUserId(Map map)throws Exception;
	
	public int getCountByUserId(Long userId) throws Exception ;
	
	public int getCountByMobile(String mobile) throws Exception ;

	
	public List<PreWeighCarDetailDTO> getByMobile(Map map)throws Exception;

	
//	public List<PreWeighCarDetailDTO> getByCategoryUserId(Long userId)throws Exception;
	
	public int update(PreWeighCarDetailDTO preWeighCarDetailDTO)throws Exception;
	
	public int deleteBusiness(Long weighCarId,Long businessId) throws Exception;
	/**
	 * 抢货，或者确认进货时，减掉热门好货的重量，直接生成一条入库单
	 * 
	 * */
	public int grapGoods(PreWeighCarDetailDTO pwd,Long inStoreNo,Long businessId)throws Exception ;
}
