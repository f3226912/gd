package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.TrunkCarLineDTO;

public interface CarLineApiService {
	/**
	 * 线路发布管理查询结果列表
	 * @输入参数 CarsDTO
	 * @返回对象 Int
	 */
	public List<CarLineDTO> getCarlineApiByCondition(CarLineDTO carLineDTO) throws Exception;
   
	public int addCarline(CarLineDTO carLineDTO) throws Exception;

	public int delCarline(Long id)throws Exception;

	public int repalyCarLine(CarLineDTO carLineDTO)throws Exception;

	public int updateCarLine(CarLineDTO carLineDTO)throws Exception;
    
	/**
	 * 获取分页查询时候的  总数
	 * @param carLineDTO
	 * @return
	 * @throws Exception
	 */
	public int getCountByCondition(CarLineDTO carLineDTO)throws Exception;
   
	/**
	 * 带分页查询的线路列表查询
	 * @param carLineDTO
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> getCarlineApiByConditionNew(CarLineDTO carLineDTO)throws Exception;
    
	/**
	 * 推送信息 ,线路查询
	 * @param carLineDTO
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> getCarlineApiMessage(CarLineDTO carLineDTO)throws Exception;
    
	/**
	 * 将发布线路,满足的推送货源货源,插入推送表
	 * @param carLineDTO
	 * @param list
	 * @throws Exception
	 */
	public void setMebApiMessage(CarLineDTO carLineDTO, List<MemberAddressDTO> list)throws Exception;
     
	/**
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public Long getCarLineId(Long memberId)throws Exception;
	
	/**
	 * 根据ID删除线路信息
	 * @param carLineIds
	 * @return
	 * @throws Exception
	 */
	public Integer updateCarLineByid(String carLineIds) throws Exception;
    
	/**
	 * 根据CarLine的Id获取信息学
	 * @param clId
	 * @return
	 * @throws Exception
	 */
	public CarLineDTO getCarLIneById(Long clId)throws Exception;
    
	/**
	 * 农速通2.0,线路查询分页
	 * @param carLineDTO
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> getCarlineApiByConditionNewNst2(
			CarLineDTO carLineDTO)throws Exception;
    
	/**
	 * 线路,农速通2.0分页总数统计
	 * @param carLineDTO
	 * @return
	 */
	public int getCountByConditionNst2(CarLineDTO carLineDTO)throws Exception;
    
	/**
	 * 获取线路详情
	 * @param carLineDTO
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> getCarlineApiByIdNst2(CarLineDTO carLineDTO)throws Exception;

	public List<CarLineDTO> getCarlineApiByConditionUser(CarLineDTO carLineDTO)throws Exception;
	
	/**
	 * 农速通2.1.3我要找车分页查询优化
	 *  获取线路列表总数
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	Integer getCarLineCount(Map<String, Object> paramMap) throws Exception;
	/**
	 * 农速通2.1.3我要找车分页查询优化
	 * 获取线路列表
	 * @return
	 * @throws Exception
	 */
	List<TrunkCarLineDTO> getCarLineList(Map<String, Object> paramMap) throws Exception;
}
