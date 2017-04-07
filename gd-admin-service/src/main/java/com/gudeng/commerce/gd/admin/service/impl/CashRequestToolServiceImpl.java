package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.CashRequestToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.MathUtil;
import com.gudeng.commerce.gd.order.dto.CashRequestDTO;
import com.gudeng.commerce.gd.order.service.CashRequestService;

@Service
public class CashRequestToolServiceImpl implements CashRequestToolService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	public static CashRequestService cashRequestService;
	
	
	
	public CashRequestService getCashRequestHessianService() throws MalformedURLException{
		String url = gdProperties.getCashRequestServiceUrl();
		if(cashRequestService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			cashRequestService = (CashRequestService) factory.create(CashRequestService.class, url);
		}
		return cashRequestService;
	}
	
	@Override
	public List<CashRequestDTO> getCashRequestInfo(Map<String, Object> map)
			throws Exception {
		
		return getCashRequestHessianService().getCashRequestInfo(map);
	}

	@Override
	public Integer getTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getCashRequestHessianService().getTotal(map);
	}

	@Override
	public List<CashRequestDTO> getAccountFlowInfo(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getCashRequestHessianService().getAccountFlowInfo(map);
	}

	@Override
	public Integer getAccountFolwTotal(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getCashRequestHessianService().getAccountFolwTotal(map);
	}

	@Override
	public CashRequestDTO getCashRequestByCashReqId(String cashReqId)
			throws Exception {
		// TODO Auto-generated method stub
		return getCashRequestHessianService().getCashRequestByCashReqId(cashReqId);
	}

	@Override
	public int insertAccTransinfo(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getCashRequestHessianService().insertAccTransinfo(map);
	}

	@Override
	public String flowDisposeAmt(Map<String, Object> map) throws Exception{

		return getCashRequestHessianService().flowDisposeAmt(map);
	
	}

	@Override
	public List<Long> getStatementIdList() throws Exception {
		// TODO Auto-generated method stub
		return getCashRequestHessianService().getStatementIdList();
	}

	@Override
	public Long getStatementId(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getCashRequestHessianService().getStatementId(map);
	}

}
