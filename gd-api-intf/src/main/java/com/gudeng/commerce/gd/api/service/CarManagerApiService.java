package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CarsDTO;

public interface CarManagerApiService {
	/**
	 * 个人中心进行车辆管理功能
	 * @输入参数 CarsDTO
	 * @返回对象 Int
	 */
	public int addCarMessage(CarsDTO carsDTO) throws Exception;
    
	/**
	 * 个人中心点击车辆管理修改时候,获取当前修改信息
	 * @param carsDTO
	 * @return
	 * @throws Exception
	 */
	public CarsDTO getCarMessageById(String id)throws Exception;

	public int updateCars(CarsDTO carsDTO)throws Exception;

	public int delCars(Long id)throws Exception;

	public List<CarsDTO> listCarByUserId(Long userId)throws Exception;

	public List<CarsDTO> getCarNumber(CarsDTO carsDTO) throws Exception;

	public CarsDTO getCarTotal(CarsDTO carsDTO)throws Exception;

	public int addActity(Map<String, Object> map)throws Exception;
	
	public CarsDTO getCarByCarNumber(String carNumber) throws Exception;
    
	public int delTotalCars(Long id) throws Exception;

	public int addCarMessageVity(CarsDTO carsDTO)throws Exception;
	
	/** 通过车牌号码查询。查询结果按创建时间升序。
	 * @param carNumber 车牌号码
	 * @param isDeleted 是否已经删除。true表示当前车辆已经删除，反之false。
	 * @return
	 * @throws Exception
	 */
	public List<CarsDTO> queryByCarNumber(String carNumber, boolean isDeleted) throws Exception;

}
