package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.RecommendedUserDTO;

public interface CarsManageService {
	
	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return CarsDTO
	 * 
	 */
	public CarsDTO getById(String id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<CarsDTO>
	 */
	public List<CarsDTO> getByCondition(Map<String, Object> map)
			throws Exception;



	/**
	 *  通过name获list集合
	 *  拓展实现多条件查询
	 *  
	 * @param map
	 * @return List<CarsDTO>
	 * @throws Exception
	 */
	public List<CarsDTO> queryByParameters(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 根据车辆类型查询
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<CarsDTO> getAllByType(String type) 	throws Exception ;

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
	 * @param CarsDTO
	 * @return int
	 * 
	 */
	public int addCarsDTO(Map<String, Object> map) throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param CarsDTO
	 * @return int
	 * 
	 */
	public int addCarsDTO(CarsDTO dto) throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param CarsDTO
	 * @return int
	 * 
	 */
	public int updateCarsDTO(CarsDTO car) throws Exception;
	
	
	/**
	 * 通过车牌查询车辆
	 * @param String
	 * @author xiaojun
	 */
	public CarsDTO selectCarByCarNumber(String carNumber) throws Exception;
	
	
	
	public CarsDTO getByEntUserId(String entUserId) throws Exception;
	
	/**
	 * 查询被推荐人的车辆信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<RecommendedUserDTO> getRecommendedUserList(Map<String, Object> map) throws Exception;
	
	public int getRecommendedUserTotal(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 查询农速通没有推荐关系的会员
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<RecommendedUserDTO> getNotRelationUserList(Map<String, Object> map) throws Exception;
	
	public int getNotRelationUserTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 通过拨打电话时间查询会员列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<RecommendedUserDTO> getRecommendedUserListByCallTime(Map<String, Object> map) throws Exception;
	
	
	
	/**
	 * 查询记录数
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalByCallTime(Map<String,Object> map)throws Exception;

	
	/**
	 * 查询农速通会员发布车辆数量，线路数量，呼叫次数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public RecommendedUserDTO  getUserInfoCount(String mobile,
			String carStartDate, String carEndDate, String carLineStartDate,
			String carLineEndDate) throws Exception;

}
