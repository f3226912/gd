package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;

public interface BusinessBaseinfoService {

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public BusinessBaseinfoDTO getById(String id)throws Exception;
	


	/**
	 * 根据用户ID查找 
	 * 
	 * 
	 * @param userId
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public BusinessBaseinfoDTO getByUserId(String userId)throws Exception;
	public BusinessBaseinfoDTO getBusinessInfoByUserId(Map<String,Object> map)throws Exception;
	/**
	 * 根据用户ID  和marketId 查找 
	 * 
	 * 
	 * @param userId
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public BusinessBaseinfoDTO getByUserIdAndMarketId(String userId,String marketId)throws Exception;

	
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
	public int addBusinessBaseinfoByMap(Map<String,Object> map)throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param BusinessBaseinfoDTO
	 * @return int
	 * 
	 */
	public int addBusinessBaseinfoDTO(BusinessBaseinfoDTO mc) throws Exception;
	
	
	/**
	 * 通过 BusinessBaseinfoEntity 对象插入数据库
	 * 
	 * @param BusinessBaseinfoEntity
	 * @return Long
	 * 
	 */
	public Long addBusinessBaseinfoEnt(BusinessBaseinfoEntity be) throws Exception;
	
	
	/**
	 * 通过对象更新数据库
	 * 
	 * @param BusinessBaseinfoDTO
	 * @return int
	 * 
	 */
	public int updateBusinessBaseinfoDTO(BusinessBaseinfoDTO mc) throws Exception;
	
	/**
	 * 通过查询返回 BusinessBaseinfoDTO 对象集合
	 * 
	 * @param  map
	 * @return list
	 * 
	 */
	public List<BusinessBaseinfoDTO> getBySearch(Map map) throws Exception;
	
	/**
	 * 通过查询返回 BusinessBaseinfoDTO 对象集合
	 * 
	 * @param  map
	 * @return list
	 * 
	 */
	public List<BusinessBaseinfoDTO> getAll(int startRow,int endRow) throws Exception;

	/**
	 * 根据条件获取农批商
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<BusinessBaseinfoDTO> getShops(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件获取农批商总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getShopsTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 添加一次浏览次数
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public void addBrowser(Long businessId) throws Exception;
	
	/**
	 * 获得类目
	 * 通过商铺ID
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public List<ReBusinessCategoryDTO> getCategoryList(Long businessId,int start, int size) throws Exception;
	
	/**
	 * 根据名字查询商品名称
	 * @param shopsName
	 * @return
	 * @throws Exception
	 */
	public List<BusinessBaseinfoDTO> getShop(BusinessBaseinfoDTO businessBaseinfoDTO) throws Exception;

	/**
	 * 根据
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BusinessBaseinfoDTO getByMap(Map<String, Object> map) throws Exception;
	
	public BusinessBaseinfoDTO getByBusinessNo(String businessNo) throws Exception;

	/**
	 * 根据posNumber查询对象
	 * 
	 * @param posNumber
	 * @return BusinessBaseinfoDTO
	 * 
	 */
	public BusinessBaseinfoDTO getByPosNumber(String posNumber)throws Exception;
	
	/**
	 * 查询秤mac地址
	 */
	public BusinessBaseinfoDTO getByMacAddr(String macAddr)throws Exception;
	
	public BusinessBaseinfoDTO getBusinessShortInfoBySearch(Map<String, Object> params) throws Exception;
	
	public Map<String, Object> getCertifForBusinessBySearch(Map<String, Object> params) throws Exception;
	
	public List<BusinessBaseinfoDTO> getSupplierShops(Map<String, String> map)
			throws Exception;
	public Integer getTotalShops(Map<String, String> map) throws Exception;
	
	/**
	 * 获取商铺交易
	 * @param map
	 * @return
	 */
	public List<BusinessBaseinfoDTO> getTradingDynamics(Map<String, Object> map);

	public List<BusinessBaseinfoDTO> getListForGdActivity(Map<String, Object> map) throws Exception;
	
}