package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessPosDTO;
import com.gudeng.commerce.gd.customer.entity.ReBusinessPosEntity;

/**
 * 功能描述：MemberBaseinfo增删改查实现类
 *
 */
public interface ReBusinessPosService {

	/**
	 * 通过MemberBaseinfo对象插入数据库
	 * 
	 * @param MemberBaseinfoDTO
	 * @return int
	 * 
	 */
	public Long addReBusinessPosEntity(ReBusinessPosEntity reBusinessPosEntity) throws Exception;

	/**
	 * 根据商铺设备号
	 * 查找商铺信息
	 * @param posDevNo
	 * @return
	 * @throws Exception
	 */
	public BusinessBaseinfoDTO getByPosDevNo(String posDevNo) throws Exception;
	/**
	 * 根据旧的终端号修改成 新的终端号
	 * @param paramMap 需要封装三个参数：oriPosNum,oriPosNum,businessId
	 * @return
	 * @throws Exception
	 */
	
	public int updateByOriNewPosNum(Map<String,String> paramMap) throws Exception;
	/**
	 * 根据商铺设备号
	 * 查找商铺信息
	 * @param posDevNo
	 * @return
	 * @throws Exception
	 */
	public BusinessBaseinfoDTO getByPosDevNo(String posDevNo, String businessNo) throws Exception;
	
	/**
	 * 根据商铺 businessId 删除
	 * 查找商铺信息
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public int deleteByBusinessId(Long businessId) throws Exception;
	
	
	/**
	 * 根据id 删除 
	 * 查找商铺信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int deleteById(Long id) throws Exception;
	
	
	/**
	 * 根据商铺ID
	 * 查找商铺pos关联信息
	 * @param  
	 * @return
	 * @throws Exception
	 */
	public List<ReBusinessPosDTO> getReBusinessPosByBusinessId(Long businessId) throws Exception;
	/**
	 * 根据商铺ID
	 * 查找商铺最新的数据
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public List<ReBusinessPosDTO> getNewReBusinessPosByBusinessId(Long businessId) throws Exception;
	
	/**
	 * 根据商铺ID和posNumber精确查找
	 * 查找商铺pos关联信息
	 * @param  
	 * @return
	 * @throws Exception
	 */
	public ReBusinessPosDTO getByBusinessIdAndPosNumber(Long businessId,String posNumber) throws Exception;
	
	
	/**
	 * 根据商铺ID查找
	 * 查找商铺pos关联信息
	 * @param  
	 * @return String 关联的信息ids(用,号隔开)
	 * @throws Exception
	 */
	public String getPosInfoByBusinessId(Long businessId) throws Exception;
}