package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.service.ReBusinessMarketService;
import com.gudeng.commerce.gd.m.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;


/** 
 *功能描述：
 */
@Service
public class ReBusinessMarketToolServiceImpl implements ReBusinessMarketToolService{ 
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static ReBusinessMarketService reBusinessMarketService;

	/**
	 * 功能描述: reBusinessMarketService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected ReBusinessMarketService getHessianReBusinessMarketService() throws MalformedURLException {
		String url = gdProperties.getReBusinessMarketServiceUrl();
		if(reBusinessMarketService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reBusinessMarketService = (ReBusinessMarketService) factory.create(ReBusinessMarketService.class, url);
		}
		return reBusinessMarketService;
	}

	@Override
	public int addReBusinessMarketDTO(ReBusinessMarketDTO rb) throws Exception {
		// TODO Auto-generated method stub
		return (int)getHessianReBusinessMarketService().addReBusinessMarketDTO(rb);
	}

	@Override
	public int deleteReBusinessMarketDTO(ReBusinessMarketDTO rb)
			throws Exception {
		return (int)getHessianReBusinessMarketService().deleteReBusinessMarketDTO(rb);
	}

	@Override
	public int getTotal(Map map) throws Exception {
		return (int)getHessianReBusinessMarketService().getTotal(map);
	}

	@Override
	public List<ReBusinessMarketDTO> getBySearch(Map map) throws Exception {
		return getHessianReBusinessMarketService().getBySearch(map);
	}
	
	@Override
	public List<ReBusinessMarketDTO> getAllBySearch(Map map) throws Exception {
		return getHessianReBusinessMarketService().getAllBySearch(map);
	}

	@Override
	public int deleteByBusinessId(Long businessId) throws Exception {
		return (int)getHessianReBusinessMarketService().deleteByBusinessId(businessId);
	}
 
	@Override
	public ReBusinessMarketDTO getByBusinessId(Long businessId)
			throws Exception {
		return getHessianReBusinessMarketService().getByBusinessId(businessId);
	}
	 
}
