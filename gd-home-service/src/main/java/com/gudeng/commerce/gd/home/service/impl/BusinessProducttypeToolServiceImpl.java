package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.BusinessProducttypeDTO;
import com.gudeng.commerce.gd.customer.service.BusinessProducttypeService;
import com.gudeng.commerce.gd.home.service.BusinessProducttypeToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;


/** 
 *功能描述：
 */
@Service
public class BusinessProducttypeToolServiceImpl implements BusinessProducttypeToolService{
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static BusinessProducttypeService businessProducttypeService;
	/*@Autowired
	private BaseDao baseDao;*/

	/**
	 * 功能描述:businessProducttypeService接口服务
	 * 
	 * @param
	 * @return
	 */
	protected BusinessProducttypeService getHessianBusinessProducttypeService() throws MalformedURLException {
		String url = gdProperties.getBusinessProducttypeServiceUrl();
		if(businessProducttypeService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			businessProducttypeService = (BusinessProducttypeService) factory.create(BusinessProducttypeService.class, url);
		}
		return businessProducttypeService;
	}

	@Override
	public List<BusinessProducttypeDTO> getByBusinessId(String businessId) throws Exception {
		return getHessianBusinessProducttypeService().getByBusinessId(businessId);
	}

	@Override
	public List<BusinessProducttypeDTO> getByParentId(Long parentId)
			throws Exception {
		return getHessianBusinessProducttypeService().getByParentId(parentId);
	}
	
}
