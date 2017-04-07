package com.gudeng.commerce.gd.api.service.impl.steelyard;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.steelyard.ReBusinessSteelyardToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.ReBusinessSteelyardDTO;
import com.gudeng.commerce.gd.customer.service.ReBusinessSteelyardService;

/**
 * 商铺智能秤和
 * 商铺关联service类
 */
public class ReBusinessSteelyardToolServiceImpl implements ReBusinessSteelyardToolService{
	
	@Autowired
	public GdProperties gdProperties;
	
	public ReBusinessSteelyardService reBusinessSteelyardService;
	
	private ReBusinessSteelyardService getHessianEnReBusinessSteelyardService() throws MalformedURLException{
		String hessianUrl = gdProperties.getProperties().getProperty("gd.reBusinessSteelyard.url");
		if (reBusinessSteelyardService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reBusinessSteelyardService = (ReBusinessSteelyardService) factory.create(ReBusinessSteelyardService.class, hessianUrl);
		}
		return reBusinessSteelyardService;
		
	}
	/**
	 * 根据商铺ID获取智能秤信息
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ReBusinessSteelyardDTO> getMacByBusId(String businessId) throws Exception {
		return getHessianEnReBusinessSteelyardService().getReBusinessSteelyardByBusinessId(Long.valueOf(businessId));
	}

	
}
