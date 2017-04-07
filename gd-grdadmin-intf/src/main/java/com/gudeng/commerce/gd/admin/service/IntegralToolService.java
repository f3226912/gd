package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.IntegralDTO;
import com.gudeng.commerce.gd.customer.entity.IntegralEntity;

public interface IntegralToolService {

	public List<IntegralDTO> getByCondition(Map<String, Object> map) throws Exception;
	
	public List<IntegralDTO> getByCondition2(Map<String, Object> map) throws Exception;

	public List<IntegralDTO> getByCondition3(Map<String, Object> map) throws Exception;

	public int getTotal(Map<String, Object> map) throws Exception;
	
	public int addIntegral(IntegralEntity entity) throws Exception;
	
	public IntegralEntity getIntegralEntityById(Long id) throws Exception;
	
	public int updateMemberIntegral(Long memberId, Long integral) throws Exception;
	
	/**修改积分明细记录isReturn字段*/
	public int updateIntegralIsReturn(Long integralId, Integer isReturn, String updateUserId) throws Exception;;
	
	public int updateIntegralMemberId(Long memberId, Long memberId_ed) throws Exception;
	
}
