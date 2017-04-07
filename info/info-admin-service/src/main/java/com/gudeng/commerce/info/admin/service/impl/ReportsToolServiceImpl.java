package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.ReportsToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.dto.ReportsDTO;
import com.gudeng.commerce.info.customer.service.ReportsService;

public class ReportsToolServiceImpl implements ReportsToolService{
	
	@Autowired
	private GdProperties gdProperties;

	private static ReportsService reportsService;
	
	protected ReportsService getReportsHessianService() throws MalformedURLException{
		if(reportsService == null){
			String hessianUrl = gdProperties.getReportsServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reportsService = (ReportsService) factory.create(ReportsService.class, hessianUrl);
		}
		return reportsService;
	}
	
	@Override
	public List<ReportsDTO> getPageByCondition(Map<String, Object> map) throws Exception {
		return getReportsHessianService().getPageByCondition(map);
	}

	@Override
	public Integer getTotalByCondition(Map<String, Object> map) throws Exception {
		return getReportsHessianService().getTotalByCondition(map);
	}

	@Override
	public Integer update(ReportsDTO reportsDTO) throws Exception {
		return getReportsHessianService().update(reportsDTO);
	}

	@Override
	public Integer updateState(Map<String, Object> map) throws Exception {
		ReportsDTO reportsDTO = new ReportsDTO();
		reportsDTO.setId((Long)map.get("id"));
		reportsDTO.setState((String)map.get("state"));
		return getReportsHessianService().update(reportsDTO);
	}

	@Override
	public ReportsDTO getById(Long reportsID) throws Exception {
		return getReportsHessianService().getById(reportsID);
	}

}
