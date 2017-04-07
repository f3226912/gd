package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.RecommendedUserDTO;


/**
 *功能描述：后台车辆管理
 */
public interface CarsService{
	


	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return CarsDTO
	 * 
	 */
	public CarsDTO getById(String id)throws Exception;
	
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<CarsDTO>
	 */
	public List<CarsDTO> getByCondition(Map<String,Object> map)throws Exception;


	/**
	 * 根据状态查询所有
     * @param status,传null查询所
	 * @return
	 * @throws Exception
	 */
	
	public List<CarsDTO> getAllByType(String type) 	throws Exception;		
	
	

	/**
	 * 
	 *  多个条件查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CarsDTO> queryByParameters(Map<String, Object> map);

	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 通过ID删除对象
	 * 
	 * @param id
	 * @return void
	 * 
	 */
	public int deleteById(String id)throws Exception;
	
	/**
	 * 通过Map插入数据库
	 * 
	 * @param map
	 * @return int
	 * 
	 */
	public int addCarsDTO(Map<String,Object> map)throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param CarsDTO
	 * @return int
	 * 
	 */
	public int addCarsDTO(CarsDTO dto) throws Exception;
	
	/**
	 * 通过对象跟新数据库
	 * 
	 * @param CarsDTO
	 * @return int
	 * 
	 */
	public int updateCarsDTO(CarsDTO dto) throws Exception;

	public List<CarsDTO> listCarByUserId(Long userId)throws Exception;

	public List<CarsDTO> listCarNumber(CarsDTO carsDTO)throws Exception;

	public CarsDTO getCarTotal(Long userId)throws Exception;

	/**
	 * 根据车牌号码查询数据库是否存在相同
	 * @param String
	 * @author xiaojun
	 */
	public CarsDTO getCarByCarNumber(String carNumber) throws Exception;

	public int addActity(Map<String, Object> map)throws Exception;
	
	
	public CarsDTO getByEntUserId(String entUserId) throws Exception ;
	
	/**
	 * 查询被推荐人的车辆信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<RecommendedUserDTO> getRecommendedUserList(Map<String, Object> map) throws Exception;
	
	
	
	/**
	 * 查询记录数
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getRecommendedUserTotal(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 查询农速通没有推荐关系的会员
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<RecommendedUserDTO> getNotRelationUserList(Map<String, Object> map) throws Exception;
	
	public int getNotRelationUserTotal(Map<String, Object> map) throws Exception;
    
	/**
	 * 查询农速通会员发布车辆数量，线路数量，呼叫次数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public RecommendedUserDTO  getUserInfoCount(String mobile,
			String carStartDate, String carEndDate, String carLineStartDate,
			String carLineEndDate) throws Exception;
	
	/**
	 * 判断是否可以删除车辆信息
	 * @param id
	 * @return
	 */
	public int delTotalCars(Long id);
	
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
	 * 判断是否可以新增车辆
	 * @param carsDTO
	 * @return
	 */
	public int addCarMessageVity(CarsDTO carsDTO);
	
	/** 通过车牌号码查询。查询结果按创建时间升序。
	 * @param carNumber 车牌号码
	 * @param isDeleted 是否已经删除。true表示当前车辆已经删除，反之false。
	 * @return
	 * @throws Exception
	 */
	public List<CarsDTO> queryByCarNumber(String carNumber, boolean isDeleted) throws Exception;
}
