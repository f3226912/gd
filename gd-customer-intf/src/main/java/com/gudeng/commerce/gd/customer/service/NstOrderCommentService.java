package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NstOrderCommentDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderCommentEntity;

public interface NstOrderCommentService {

	void insert(NstOrderCommentEntity nstOrderCommentEntity);
	
	Integer getAvgByMemberId(Long memberId);
	
	public NstOrderCommentDTO getById(String id) throws Exception ;
	
	/**
	 * 获取用户评论列表（用户评论信息+用户被评论信息）分页
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstOrderCommentDTO> getUserCommentPage(Map<String, Object> map);
	
	public Integer getUserCommentCount(Map<String, Object> map);
}
