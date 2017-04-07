package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.NstOrderComplaintToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.NstOrderComplaintDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderComplaintEntity;
import com.gudeng.commerce.gd.customer.service.NstOrderComplaintService;

public class NstOrderComplaintToolServiceImpl implements NstOrderComplaintToolService{

	@Autowired
	private GdProperties gdProperties;
	
	public static NstOrderComplaintService nstOrderComplaintService;
	
	protected NstOrderComplaintService getHessianNstOrderComplaintService() throws MalformedURLException{
		if(nstOrderComplaintService == null){
			String hessianUrl = gdProperties.getNstOrderComplaintServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstOrderComplaintService = (NstOrderComplaintService) factory.create(NstOrderComplaintService.class, hessianUrl);
		}
		return nstOrderComplaintService;
	}
	
	@Override
	public Long save(NstOrderComplaintEntity nstOrderComplaintEntity) throws Exception {
		return getHessianNstOrderComplaintService().persist(nstOrderComplaintEntity);
	}

	@Override
	public NstOrderComplaintDTO getById(Long id) throws Exception {
		return getHessianNstOrderComplaintService().getById(id);
	}

}
