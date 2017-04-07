package com.gudeng.commerce.gd.api.service.impl.engj;

import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.engj.ReBusinessPosToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessPosDTO;
import com.gudeng.commerce.gd.customer.service.ReBusinessPosService;

public class ReBusinessPosToolServiceImpl implements ReBusinessPosToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(ReBusinessPosToolServiceImpl.class);
	
	@Autowired
	public GdProperties gdProperties;
	
	private ReBusinessPosService reBusinessPosService;
	
	private ReBusinessPosService getHessianReBusinessPosService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.reBusinessPosService.url");
		if (reBusinessPosService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reBusinessPosService = (ReBusinessPosService) factory.create(ReBusinessPosService.class, hessianUrl);
		}
		return reBusinessPosService;
	}

	@Override
	public BusinessBaseinfoDTO getByPosDevNo(String posDevNo, String businessNo)
			throws Exception {
		return getHessianReBusinessPosService().getByPosDevNo(posDevNo, businessNo);
	}

	/**
	 * 根据商铺ID获取posNum
	 */
	@Override
	public List<ReBusinessPosDTO> getPostNumByBusId(String businessId) throws Exception {
		return getHessianReBusinessPosService().getReBusinessPosByBusinessId(Long.valueOf(businessId));
	}
	
	
}
