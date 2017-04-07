package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.PublishCountDTO;
import com.gudeng.commerce.gd.customer.dto.TrunkCarLineDTO;


/**
 *功能描述：后台车辆专线管理
 */
public interface CarLineService{
	


	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return CarLineDTO
	 * 
	 */
	public CarLineDTO getById(String id)throws Exception;
	
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<CarLineDTO>
	 */
	public List<CarLineDTO> getByCondition(Map<String,Object> map)throws Exception;
	
	
	
	/**
	 * 根据市id查询List
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> getListByAreaId(Map<String, Object> map) throws Exception;


	/**
	 * 根据类型查询所有
     * @param sendGoodsType
	 * @return
	 * @throws Exception
	 */
	
	public List<CarLineDTO> getAllByType(String sendGoodsType)throws Exception ;	
	
	/**
	 * 	  根据name查询对象集合
	 * 
	 *   拓展后，实现多个条件查询
	 *   
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> getByName(Map<String, Object> map);

	
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
	public int addCarLineDTO(Map<String,Object> map)throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param CarLineDTO
	 * @return int
	 * 
	 */
	public int addCarLineDTO(CarLineDTO dto) throws Exception;
	
	/**
	 * 通过对象跟新数据库
	 * 
	 * @param CarLineDTO
	 * @return int
	 * 
	 */
	public int updateCarLineDTO(CarLineDTO dto) throws Exception;
     
	public List<CarLineDTO> getCarlineApiByCondition(CarLineDTO carLineDTO)throws Exception;

	public int repalyCarLine(CarLineDTO carLineDTO)throws Exception;

	//public int addCarline(CarLineDTO carLineDTO)throws Exception;

	/**
	 * 根据检索条件，导出查询数据
	 */
	public List<CarLineDTO> queryCarLineList(Map<String, Object> map) throws Exception;

	public int getCountByCondition(CarLineDTO carLineDTO)throws Exception;

	public List<CarLineDTO> getCarlineApiByConditionNew(CarLineDTO carLineDTO)throws Exception;

	public List<CarLineDTO> getCarlineApiMessage(CarLineDTO carLineDTO)throws Exception;

	public void setMebApiMessage(CarLineDTO carLineDTO, List<MemberAddressDTO> list)throws Exception;

	public Long getCarLineId(Long memberId)throws Exception;
	
	/**
	 * 根据ID删除线路信息
	 * @param carLineIds
	 * @return
	 * @throws Exception
	 */
	public Integer updateCarLineByid(String carLineIds) throws Exception;
    
	/**
	 * 更具Id查询线路信息
	 * @param clId
	 * @return
	 * @throws Exception
	 */
	public CarLineDTO getCarLIneById(Long clId)throws Exception;
    
	/**
	 * 农速通2.0 ,查询线路分页,带高级查询条件
	 * @param carLineDTO
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> getCarlineApiByConditionNewNst2(
			CarLineDTO carLineDTO)throws Exception;
    
	/**
	 * 农速通2.0.查询线路分页统计
	 * @param carLineDTO
	 * @return
	 * @throws Exception
	 */
	public int getCountByConditionNst2(CarLineDTO carLineDTO)throws Exception;
    
	/**
	 * 农速通2.0
	 * 线路详情
	 * @param carLineDTO
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> getCarlineApiByIdNst2(CarLineDTO carLineDTO)throws Exception;
	
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
	 * 会员发布车辆统计(同城)
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
	 * 会员发布车辆统计总数（同城）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberPublishCarCountSameCity(Map<String, Object> map) throws Exception;


	public List<CarLineDTO> getCarlineApiByConditionUserId(CarLineDTO carLineDTO)throws Exception;
	
	
	
	/**
	 * 管理后台查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalForConsole(Map<String,Object> map)throws Exception;
	
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
