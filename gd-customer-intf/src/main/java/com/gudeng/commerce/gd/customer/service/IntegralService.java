package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.IntegralDTO;
import com.gudeng.commerce.gd.customer.entity.IntegralEntity;

/**
 * 积分流水记录
 * @author wind
 *
 */
public interface IntegralService {

	public List<IntegralDTO> getByCondition(Map<String, Object> map);
	
	public List<IntegralDTO> getByCondition2(Map<String, Object> map);
	
	public List<IntegralDTO> getByCondition3(Map<String, Object> map);
	
	public int getTotal(Map<String, Object> map);
	
	public int insertEntity(IntegralEntity entity);
	
	public IntegralEntity getIntegralEntityById(Long id);
	
	public int updateMemberIntegral(Long memberId, Long integral);
	
	/**修改积分明细记录isReturn字段*/
	public int updateIntegralIsReturn(Long integralId, Integer isReturn, String updateUserId);
	
	/** api接口查询积分流水*/
	public List<IntegralDTO> selectIntegralFlow(Long memberId) throws Exception;
	
	public int updateIntegralMemberId(Long memberId, Long memberId_ed);
}
