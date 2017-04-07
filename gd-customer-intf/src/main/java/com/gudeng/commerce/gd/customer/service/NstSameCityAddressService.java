package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NstMemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.commerce.gd.customer.entity.NstSameCityAddressEntity;

/**
 * 农速通同城货源发布Service
 * 
 * @author xiaojun
 */
public interface NstSameCityAddressService {

	/**
	 * 插入同城货源信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long insert(NstSameCityAddressEntity entity) throws Exception;

	/**
	 * 获取可分配物流公司列表 日上限和月上限
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityAddressDTO> getAssaginCompanyList(Map<String, Object> map)
			throws Exception;
	/**
	 * 获取当前物流公司分配的日总数，月总数
	 * @param nstSameCityAddressDTO
	 * @return
	 * @throws Exception
	 */
	public NstSameCityAddressDTO getAssaginCount(Map<String, Object> map) throws Exception;
	/**
	 * 物流公司分配
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public boolean assign(Map<String, Object> map) throws Exception;
	/**
	 * 直发
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer direct(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取同城货源列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityAddressDTO> getNstSameCityAddressListByPage(Map<String, Object> map) throws Exception;
	/**
	 * 获取同城货源列表 总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getNstSameCityAddressListByPageCount(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据线路分配货源
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityAddressDTO> matchGoodsByCarline(NstSameCityCarlineEntityDTO dto) throws Exception;
	/**
	 * 获取会员  我发的同城货
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityAddressDTO> getMemberNSCAList(Map<String, Object> map) throws Exception;
	/**
	 * 获取会员 我发的同城货 总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberNSCAListCount(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改重发
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Integer updateMemberNSCA(NstSameCityAddressEntity entity) throws Exception;
	
	/**
	 * 修改重发
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Integer deleteMemberNSCA(NstSameCityAddressEntity entity) throws Exception;
	
	

	/**
	 * 管理后台查询同城货源记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalForConsole(Map<String,Object> map)throws Exception;

	/**
	 *  管理后台查询同城货源列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityAddressDTO> queryListForConsole(Map<String, Object> map) throws Exception;
	
	

	/**
	 * 同城货源分配统计列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstMemberAddressDTO> getDistributeAddressList(Map<String, Object> map) throws Exception;
	
	
	
	/**
	 * 同城货源分配统计总记录数
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getDistributeAddressTotal(Map<String,Object> map)throws Exception;
}
