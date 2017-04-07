package com.gudeng.commerce.gd.promotion.service;

import java.util.List;

import com.gudeng.commerce.gd.promotion.dto.PromFeeByActDTO;
import com.gudeng.commerce.gd.promotion.entity.PromFeeByActEntity;

/**
 * @author TerryZhang
 */
public interface PromFeeByActService {

	/**
	 * 根据会员id
	 * 查找付费情况
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public List<PromFeeByActDTO> getByMemberId(Integer memberId) throws Exception;
	
	/**
	 * 根据活动id
	 * 查找付费情况
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	public List<PromFeeByActDTO> getByActId(Integer actId) throws Exception;
	
	/**
	 * 根据会员id和活动id
	 * 查找付费情况
	 * @param memberId
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	public List<PromFeeByActDTO> getByMemberIdAndActId(Integer memberId, Integer actId) throws Exception;
	
	/**
	 * 根据会员id列表和活动id
	 * 查找付费情况
	 * @param memberIdList
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	public List<PromFeeByActDTO> getByMemberIdListAndActId(List<Integer> memberIdList, Integer actId) throws Exception;
	
	/**
	 * 新增
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Integer addEntity(PromFeeByActEntity entity) throws Exception;
}
