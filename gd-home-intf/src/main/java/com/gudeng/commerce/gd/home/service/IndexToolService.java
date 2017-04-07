package com.gudeng.commerce.gd.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;

public interface IndexToolService {
	/**
	 * 查询公告记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getPushNoticeTotal(Map<String, Object> map) throws Exception;

	/**
	 * 根据ID查询公告对象
	 * 
	 * @param id
	 * @return PushNoticeDTO
	 * 
	 */
	public PushNoticeDTO getByPushNotice(Long id) throws Exception;

	/**
	 * 根据条件查询公告list(分页查询)
	 * 
	 * @param map
	 * @return List<PushNoticeDTO>
	 */
	public List<PushNoticeDTO> getListByPushNoticePage(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查询公告list
	 * 
	 * @param map
	 * @return List<PushNoticeDTO>
	 */
	public List<PushNoticeDTO> getListByPushNotice(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件查询广告list
	 * 
	 * @param map
	 * @return List<PushNoticeDTO>
	 */
	public List<PushAdInfoDTO> getListByPushAdInfo(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件查询广告list
	 * 
	 * @param map
	 * @return List<PushNoticeDTO>
	 */
	public List<PushAdInfoDTO> getListByPushAdInfoShow(Map<String, Object> map) throws Exception;
}
