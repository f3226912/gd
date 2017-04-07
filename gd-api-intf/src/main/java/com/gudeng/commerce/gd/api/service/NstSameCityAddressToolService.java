package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.commerce.gd.customer.entity.NstSameCityAddressEntity;
/**
 * 农速通同城货源发布Service
 * @author xiaojun
 */
public interface NstSameCityAddressToolService {

	/**
	 * 插入同城货源信息
	 * @return
	 * @throws Exception
	 */
	public Long insert(NstSameCityAddressEntity entity) throws Exception;
	
	/**
	 * 根据物流规则分配货源
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public boolean assign(Map<String, Object> map) throws Exception;
	
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
	
	
}
