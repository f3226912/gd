package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarOrderDTO;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;

/**
 * 过磅管理
 * @author wind
 *
 */
public interface WeighCarToolService {

	public List<WeighCarDTO> getWeighCar(Map<String, Object> map) throws Exception;
	
	public Long insertWeighCar(WeighCarEntity entity) throws Exception;
	
	public WeighCarDTO getById(Long id) throws Exception;

	public int updateTotalWeight(WeighCarEntity entity) throws Exception;
	
	public int updateTareWeight(WeighCarEntity entity) throws Exception;

	/**
	 * 获取最新的一条过磅记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public WeighCarDTO getLastWeighCar(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取用户的五个车牌号码的最新的过磅记录
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public List<WeighCarDTO> getLastFiveWeighCar(Map<String, Object> map) throws Exception;


	/**
	 * 获取两天内的过磅订单
	 * @param memberId
	 * @return
	 * @throws Exception 
	 */
	public List<WeighCarOrderDTO> getLastTwoDayWeighCarOrder(Long memberId) throws Exception;
	
	public int getLastTwoDayWeighCarOrderTotal(Long memberId) throws Exception;

	public int updateStatus(WeighCarEntity weighCarEntity) throws Exception;

	public int updateStatusByCarId(String status, Long carId) throws Exception;

	public int updateStatusByWeiCarId(String status, Long weighCarId) throws Exception;
	
	/**
	 * 分页获取过磅订单列表
	 * @param map
	 * @return
	 */
	public List<WeighCarOrderDTO> getWeighCarOrderPage(Map<String, Object> map) throws Exception;
	
	public int getWeighCarOrderTotal(Map<String, Object> map) throws Exception;
}
