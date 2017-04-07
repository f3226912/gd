package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AuditInfoToolService;
import com.gudeng.commerce.gd.admin.service.AuditInfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.entity.AuditInfoEntity;
import com.gudeng.commerce.gd.customer.entity.AuditInfoEntity;
import com.gudeng.commerce.gd.customer.service.AuditInfoService;


/** 
 *功能描述：
 */
@Service
public class AuditInfoToolServiceImpl implements AuditInfoToolService{ 
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static AuditInfoService auditInfoService;

	/**
	 * 功能描述: auditInfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected AuditInfoService getHessianAuditInfoService() throws MalformedURLException {
		String url = gdProperties.getAuditInfoServiceUrl();
		if(auditInfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			auditInfoService = (AuditInfoService) factory.create(AuditInfoService.class, url);
		}
		return auditInfoService;
	}

	@Override
	public int addAuditInfoDTO(AuditInfoDTO mb) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAuditInfoService().addAuditInfoDTO(mb);
	}

	@Override
	public Long addAuditInfoEnt(AuditInfoEntity me) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAuditInfoService().addAuditInfoEnt(me);
	}

	@Override
	public int deleteById(String id) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAuditInfoService().deleteById(id);
	}

	@Override
	public AuditInfoDTO getById(String id) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAuditInfoService().getById(id);
	}

	@Override
	public int getTotal(Map map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAuditInfoService().getTotal(map);
	}

	@Override
	public List<AuditInfoDTO> getAll(Map map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAuditInfoService().getAll(map);
	}

	@Override
	public List<AuditInfoDTO> getBySearch(Map map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAuditInfoService().getBySearch(map);
	}
 
}
