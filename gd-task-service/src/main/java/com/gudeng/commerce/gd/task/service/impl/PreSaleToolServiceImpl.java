package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.PreSaleDTO;
import com.gudeng.commerce.gd.order.service.PreSaleService;
import com.gudeng.commerce.gd.task.service.PreSaleToolService;
import com.gudeng.commerce.gd.task.util.GdProperties;

public class PreSaleToolServiceImpl implements PreSaleToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static PreSaleService preSaleService;

	protected PreSaleService getHessianService() throws ServiceException {
		try {
			String url = gdProperties.getPreSaleServiceDir();
			if (preSaleService == null) {
				HessianProxyFactory factory = new HessianProxyFactory();
				factory.setOverloadEnabled(true);
				preSaleService = (PreSaleService) factory.create(PreSaleService.class, url);
			}
			return preSaleService;
		} catch (MalformedURLException e) {
			throw new ServiceException("获取hessian服务失败", e);
		}
	}
	
	@Override
	public List<PreSaleDTO> getOverPreSale(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().getOverPreSale(map);
	}

	@Override
	public int updateDTO(PreSaleDTO obj) throws Exception {
		// TODO Auto-generated method stub
		return getHessianService().updateDTO(obj);
	}

}
