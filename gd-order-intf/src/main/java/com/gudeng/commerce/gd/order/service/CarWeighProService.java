package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.CarWeighProDTO;

/**
 * 车辆默认皮重 总重操作
 * @author Ailen
 *
 */
public interface CarWeighProService {


	List<CarWeighProDTO> getCarWeighProList(Map<String, Object> map);
	
	public int updateCarWeighPro(CarWeighProDTO carWeighProDTO);
	
	public int insertCarWeighPro(CarWeighProDTO carWeighProDTO) throws Exception;

}
