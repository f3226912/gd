package com.gudeng.commerce.gd.m.service.message;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;

public interface PushNoticeToolService {
	/**
	 * 未读消息记录总数
	 * @param inputParamDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getUnReadMessageCount(PushNoticeDTO inputParamDTO) throws Exception;
	
	/**
	 * 根据条件查询消息列表
	 * @param inputParamDTO
	 * @return
	 * @throws Exception
	 */
	public List<PushNoticeDTO> getMessageListByUser(PushNoticeDTO inputParamDTO) throws Exception;
	
	/**
	 * 消息详细信息
	 * @param inputParamDTO
	 * @return
	 * @throws Exception
	 */
	public PushNoticeDTO getMessageDetail(PushNoticeDTO inputParamDTO) throws Exception;
	
	/**
	 * 通过userID和noticeID查询push_notice_user关联表中是否已经存在记录
	 * @param inputParamDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getPushUserCount(PushNoticeDTO inputParamDTO) throws Exception;
	
	
	/**
	 * 修改此消息为已删除
	 * @param inputParamDTO
	 * @author liufan
	 * @return
	 * @throws Exception 
	 */
	public int updateMessageIsdel(PushNoticeDTO inputParamDTO) throws Exception;
	
	
	/**
	 * 修改此消息为已读
	 * @param inputParamDTO
	 * @author liufan
	 * @return
	 * @throws Exception 
	 */
	public int updateMessageIsread(PushNoticeDTO inputParamDTO) throws Exception;
	
	/**
	 * 查询当前用户的注册时间
	 * @param inputParamDTO
	 * @author liufan
	 * @return
	 * @throws Exception 
	 */
	public PushNoticeDTO getUserRegisteTime(PushNoticeDTO inputParamDTO) throws Exception;
	
	
}
