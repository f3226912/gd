package com.gudeng.commerce.gd.admin.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.SalToshopsDetailDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;

/**
 *功能描述：WeighCar增删改查实现类
 *
 */
public interface WeighCarToolService {

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public WeighCarDTO getById(String id)throws Exception;
	
	
	/**
	 * 根据条件查询对象
	 * 
	 * @param params
	 * @return WeighCarDTO
	 * 
	 */
	public List<WeighCarDTO> getByConditionPage(Map<String, Object> params)throws Exception;


	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String,Object> map)throws Exception;
	
	/**
	 * 通过对象更新数据库
	 * 
	 * @param BusinessBaseinfoDTO
	 * @return int
	 * 
	 */
	public int updateWeighCarEntity(WeighCarEntity wc) throws Exception;


	public Map<String, Object> getParamsMap(WeighCarDTO weighCarDTO);

	
	/**
	 * 获取货主入场过磅信息分页
	 * @return
	 * @throws Exception
	 */
	public List<WeighCarDTO> getEntranceWeighList(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取货主入场过磅信息总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getEntranceWeighListTotal(Map<String, Object> map) throws Exception;
		
	

	
	/**
	 * 通过ID的到过磅信息
	 * @param weighCarId
	 * @return
	 */
	public WeighCarDTO getByIdForAdmin(Long weighCarId) throws Exception;

	/**
	 * 根据过磅表Id查询
	 * @return
	 * @throws Exception
	 */
	public WeighCarDTO getEntranceWeighById(String weighCarId) throws Exception;
	
	/**
	 * 根据过磅ID查询出过磅的商品信息
	 * @param weighCarId
	 * @return
	 * @throws Exception
	 */
	public List<PreWeighCarDetailDTO> getProductListByWeighCarId(String weighCarId) throws Exception;
	
	/**
	 * 根据过磅ID查询出场商品信息
	 * @param weighCarId
	 * @return
	 * @throws Exception
	 */
	public List<SalToshopsDetailDTO> getOutProductListByWeighCarId(String weighCarId) throws Exception;
}