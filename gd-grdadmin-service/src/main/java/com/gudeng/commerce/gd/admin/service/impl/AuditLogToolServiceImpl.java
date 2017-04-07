package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AuditLogToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.AuditLogDTO;
import com.gudeng.commerce.gd.order.entity.AuditLogEntity;
import com.gudeng.commerce.gd.order.service.AuditLogService;

@Service
public class AuditLogToolServiceImpl implements AuditLogToolService {

	@Autowired
    public GdProperties gdProperties;
	
	private static AuditLogService auditLogService;
	
	/**
     * 功能描述: memberBaseinfoService 接口服务
     *
     * @param
     * @return
     */
    protected AuditLogService getHessianAuditLogService() throws MalformedURLException {
    	String url = gdProperties.getAuditLogUrl();
		if(auditLogService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled( true);
            auditLogService = (AuditLogService)factory.create(AuditLogService.class,url);
		}
		return auditLogService;
    }

	
	@Override
	public List<AuditLogDTO> getListByOrderNo(Long orderNo, String type) throws Exception {
		return this.getHessianAuditLogService().getListByOrderNo(orderNo, type);
	}
	
	@Override
	public Long insertEntity(AuditLogEntity obj) throws Exception {
		return getHessianAuditLogService().insertEntity(obj);
	}


	@Override
	public List<AuditLogDTO> getListByOrderNo(Long orderNo) throws Exception {
		return getHessianAuditLogService().getListByOrderNo(orderNo);
	}
	
	@Override
	public List<AuditLogEntity> getSYSAuditLogByOrderNo(Long orderNo) throws Exception {
		return getHessianAuditLogService().getSYSAuditLogByOrderNo(orderNo);
	}
}
