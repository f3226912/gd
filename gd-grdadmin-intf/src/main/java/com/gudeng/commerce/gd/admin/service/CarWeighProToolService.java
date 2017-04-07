package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.CarWeighProDTO;

/**
 * 车载重数据
 * @author Ailen
 *
 */
public interface CarWeighProToolService {
	
	/**
	 * 获取车载重数据
	 * @param map
	 * @return
	 */
	public List<CarWeighProDTO> getCarWeighProList(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改车载重数据
	 * @param carWeighProDTO
	 * @return
	 */
	public int updateCarWeighPro(CarWeighProDTO carWeighProDTO) throws Exception;
	
	/**
	 * 添加车载重数据
	 * @param carWeighProDTO
	 * @return
	 */
	public int insertCarWeighPro(CarWeighProDTO carWeighProDTO) throws Exception;

}
