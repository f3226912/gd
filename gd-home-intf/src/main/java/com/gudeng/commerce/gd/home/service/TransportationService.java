package com.gudeng.commerce.gd.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;



/**
 * 物流服务
 * @author xiaodong
 *
 */
public interface TransportationService {
	


	/**
	 * 
	 * 货主找车 车辆查询
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<CarLineDTO>
	 */
	public List<CarLineDTO> getByCondition(Map<String, Object> map)
			throws Exception;

	
	public List<MemberAddressDTO> getGoodsListCompanyMobile(Map<String, Object> map)
			throws Exception;
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getCarLineTotal(Map<String, Object> map) throws Exception;
	
	
	

	/**
	 * 车主找货
	 * 货源查询
	 */
	public List<MemberAddressDTO> getGoodsListByCondition(Map<String, Object> map)
			throws Exception ;
	
	
	/**
	 * 货源总数查询
	 */
	public int getGoodsTotal(Map<String, Object> map) throws Exception ;



	/**
	 * 查询区域
	 * @param areaID
	 * @return
	 * @throws Exception
	 */
	public AreaDTO getArea(String areaID) throws Exception ;
	
	
	/**
	 * 根据市id查询线路List
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> getListByAreaId(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 根据城市ID查询货源List
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getGoodsListByAreaId(Map<String, Object> map)throws Exception;


}
