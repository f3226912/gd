package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.InStoreDetailToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.InStoreDetailDTO;
import com.gudeng.commerce.gd.order.service.InStoreDetailService;

@Service
public class InStoreDetailToolServiceImpl implements InStoreDetailToolService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static InStoreDetailService inStoreDetailService;

	protected InStoreDetailService getHessianInStoreDetailService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.InStoreDetailService.url");
		if (inStoreDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			inStoreDetailService = (InStoreDetailService) factory.create(InStoreDetailService.class, url);
		}
		return inStoreDetailService;
	}
	
	@Override
	public List<InStoreDetailDTO> getInstoreProductList(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianInStoreDetailService().getInstoreProductList(map);
	}

	@Override
	public int getInstoreProductListTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianInStoreDetailService().getInstoreProductListTotal(map);
	}


}
