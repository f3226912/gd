package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.commerce.gd.customer.dto.PublishCountDTO;

public interface CarLineManageService {
	
	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return CarLineDTO
	 * 
	 */
	public CarLineDTO getById(String id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<CarLineDTO>
	 */
	public List<CarLineDTO> getByCondition(Map<String, Object> map)
			throws Exception;



	/**
	 *  通过name获list集合
	 *  拓展实现多条件查询
	 *  
	 * @param map
	 * @return List<CarLineDTO>
	 * @throws Exception
	 */
	public List<CarLineDTO> getByName(Map<String, Object> map) throws Exception;

	/**
	 * 根据运货方式查询list
	 * @param sendGoodsType
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> getAllByType(String sendGoodsType) throws Exception ;
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;

	/**
	 * 通过ID删除对象
	 * 
	 * @param id
	 * @return void
	 * 
	 */
	public int deleteById(String id) throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param CarLineDTO
	 * @return int
	 * 
	 */
	public int addCarLineDTO(Map<String, Object> map) throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param CarLineDTO
	 * @return int
	 * 
	 */
	public int addCarLineDTO(CarLineDTO dto) throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param CarLineDTO
	 * @return int
	 * 
	 */
	public int updateCarLineDTO(CarLineDTO car) throws Exception;
	
	
	/**
	 * 根据检索条件，导出查询数据
	 */
	public List<CarLineDTO> queryCarLineList(Map<String, Object> map) throws Exception;
	

	/**
	 * 根据ID删除线路信息
	 * @param carLineIds
	 * @return
	 * @throws Exception
	 */
	public Integer updateCarLineByid(String carLineIds) throws Exception;
	
	/**
	 * 会员发布线路统计
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<PublishCountDTO> memberPublishCarLine(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 会员发布线路统计(同城)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<PublishCountDTO> memberPublishCarLineSameCity(Map<String, Object> map) throws Exception;
	
	/**
	 * 会员发布线路统计总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberPublishCarLineCount(Map<String, Object> map) throws Exception;
	
	/**
	 * 会员发布线路统计总数(同城)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberPublishCarLineCountSameCity(Map<String, Object> map) throws Exception;
	
	/**
	 * 会员发布车辆统计
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<PublishCountDTO> memberPublishCar(Map<String, Object> map) throws Exception;
	
	/**
	 * 会员发布车辆统计（同城）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<PublishCountDTO> memberPublishCarSameCity(Map<String, Object> map) throws Exception;
	
	/**
	 * 会员发布车辆统计总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberPublishCarCount(Map<String, Object> map) throws Exception;
	
	/**
	 * 会员发布车辆统计总数(同城)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberPublishCarCountSameCity(Map<String, Object> map) throws Exception;
	

	/**
	 * 管理后台查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalForConsole(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 管理后台查询同城线路记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalForSameCityList(Map<String,Object> map)throws Exception;

	/**
	 *  管理后台查询同城线路列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityCarlineEntityDTO> queryForSameCityList(Map<String, Object> map) throws Exception;
	
}
