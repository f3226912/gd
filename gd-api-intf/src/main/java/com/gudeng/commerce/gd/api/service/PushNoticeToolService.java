package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.customer.entity.PushNoticeEntity;

public interface PushNoticeToolService {
	/**
	 * 添加
	 * 
	 * @param PushNoticeEntity
	 *            obj
	 * @return Long 添加成功后id
	 * 
	 */
	public Long insertEntity(PushNoticeEntity obj) throws Exception;

	/**
	 * 通过ID删除对象
	 * 
	 * @param id
	 * @return void
	 * 
	 */
	public int deleteById(Long id) throws Exception;

	/**
	 * 批量通过ID删除对象
	 * 
	 * @param idList
	 * @return void
	 * 
	 */
	public int batchDeleteById(List<Long> idList) throws Exception;
	
	/**
	 * 发送操作
	 * 
	 * @param PushNoticeDTO
	 * @return int
	 * 
	 */
	public int updateDTO(PushNoticeDTO obj) throws Exception;
	
	/**
	 * 编辑操作
	 * 
	 * @param PushNoticeDTO
	 * @return int
	 * 
	 */
	public int updateNoticeInfo(PushNoticeDTO obj) throws Exception;
	
	/**
	 * 批量通过对象更新数据库
	 * 
	 * @param PushNoticeDTO
	 * @return int
	 * 
	 */
	public int batchUpdateDTO(List<PushNoticeDTO> objList) throws Exception;
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String, Object> map) throws Exception;

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return PushNoticeDTO
	 * 
	 */
	public PushNoticeDTO getById(Long id) throws Exception;

	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<PushNoticeDTO>
	 */
	public List<PushNoticeDTO> getListByConditionPage(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查询list
	 * 
	 * @param map
	 * @return List<PushNoticeDTO>
	 */
	public List<PushNoticeDTO> getListByCondition(Map<String, Object> map) throws Exception;

	/**
	 * inputParamDTO 查询未读消息条数
	 * 
	 * @param map
	 * @return int 
	 */
	public Integer getUnReadMessageCount(PushNoticeDTO inputParamDTO) throws Exception;
	/**
	 *根据 userId 查询未读消息条数
	 * 
	 * @param userId
	 * @return int 
	 */
	public Integer getUnReadMessageCount(String userId,String client) throws Exception;
}
