package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.NstOrderCommentToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.NstOrderCommentDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderCommentEntity;
import com.gudeng.commerce.gd.customer.service.NstOrderCommentService;

public class NstOrderCommentToolServiceImpl implements NstOrderCommentToolService{

	@Autowired
	private GdProperties gdProperties;
	
	private static NstOrderCommentService nstOrderCommentService;
	
	public NstOrderCommentService getHessianOrderCommentService() throws MalformedURLException{
		if(nstOrderCommentService == null){
			String hessianUrl = gdProperties.getNstOrderCommentServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstOrderCommentService = (NstOrderCommentService)factory.create(NstOrderCommentService.class, hessianUrl);
		}
		return nstOrderCommentService;
	}
	
	@Override
	public void insert(NstOrderCommentEntity nstOrderCommentEntity) throws Exception {
		getHessianOrderCommentService().insert(nstOrderCommentEntity);
	}

	@Override
	public Integer getAvgByMemberId(Long memberId) throws Exception {
		return getHessianOrderCommentService().getAvgByMemberId(memberId);
	}

	@Override
	public List<NstOrderCommentDTO> getUserCommentPage(Map<String, Object> map) throws Exception {
		return getHessianOrderCommentService().getUserCommentPage(map);
	}

	@Override
	public Integer getUserCommentCount(Map<String, Object> map) throws Exception{
		return getHessianOrderCommentService().getUserCommentCount(map);
	}

}
