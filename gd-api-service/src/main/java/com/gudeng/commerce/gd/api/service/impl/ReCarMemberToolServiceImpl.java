package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.ReCarMemberToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.order.entity.ReCarMemberEntity;
import com.gudeng.commerce.gd.order.service.ReCarMemberService;

public class ReCarMemberToolServiceImpl implements ReCarMemberToolService{

	@Autowired
	private GdProperties gdProperties;
	
	private static ReCarMemberService reCarMemberService;
	
	public ReCarMemberService getHessianReCarMemberService() throws MalformedURLException{
		if(reCarMemberService == null){
			String url = gdProperties.getReCarMemberServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reCarMemberService = (ReCarMemberService) factory.create(ReCarMemberService.class, url);
		}
		return reCarMemberService;
	}
	
	@Override
	public int addEntity(ReCarMemberEntity entity) throws Exception{
		return getHessianReCarMemberService().addEntity(entity);
	}

	@Override
	public boolean isExist(Long carId, Long memberId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("carId", carId);
		map.put("memberId", memberId);
		return getHessianReCarMemberService().countByCondition(map) > 0;
	}

}
