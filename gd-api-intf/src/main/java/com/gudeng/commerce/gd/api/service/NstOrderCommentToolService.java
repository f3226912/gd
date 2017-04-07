package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NstOrderCommentDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderCommentEntity;

public interface NstOrderCommentToolService {

	void insert(NstOrderCommentEntity nstOrderCommentEntity) throws Exception;

	Integer getAvgByMemberId(Long memberId) throws Exception;
	
	/**
	 * 获取用户评论信息列表（用户评论信息+用户被评论信息）分页
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstOrderCommentDTO> getUserCommentPage(Map<String, Object> map) throws Exception;
	
	public Integer getUserCommentCount(Map<String, Object> map) throws Exception;
}
