package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.IntegralToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.IntegralDTO;
import com.gudeng.commerce.gd.customer.entity.IntegralEntity;
import com.gudeng.commerce.gd.customer.service.IntegralService;

public class IntegralToolServiceImpl implements IntegralToolService{

	@Autowired
	public GdProperties gdProperties;

	private static IntegralService integralService;
	
	protected IntegralService getIntegralService() throws MalformedURLException{
		String url = gdProperties.getIntegralServiceUrl();
		if (integralService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			integralService = (IntegralService) factory.create(IntegralService.class, url);
		}
		return integralService;
	}
	
	@Override
	public List<IntegralDTO> getByCondition(Map<String, Object> map) throws Exception {
		return getIntegralService().getByCondition(map);
	}
	
	@Override
	public List<IntegralDTO> getByCondition2(Map<String, Object> map) throws Exception {
		return getIntegralService().getByCondition2(map);
	}
	
	@Override
	public List<IntegralDTO> getByCondition3(Map<String, Object> map) throws Exception {
		return getIntegralService().getByCondition3(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getIntegralService().getTotal(map);
	}

	@Override
	public int addIntegral(IntegralEntity entity) throws Exception {
		return getIntegralService().insertEntity(entity);
	}

	@Override
	public IntegralEntity getIntegralEntityById(Long id) throws Exception {
		return getIntegralService().getIntegralEntityById(id);
	}

	@Override
	public int updateMemberIntegral(Long memberId, Long integral) throws Exception {
		return getIntegralService().updateMemberIntegral(memberId, integral);
	}

	@Override
	public int updateIntegralIsReturn(Long integralId, Integer isReturn, String updateUserId) throws Exception {
		return getIntegralService().updateIntegralIsReturn(integralId, isReturn, updateUserId);
	}
	
	public int updateIntegralMemberId(Long memberId, Long memberId_ed) throws Exception{
		return getIntegralService().updateIntegralMemberId(memberId, memberId_ed);
	}


}
