package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.InvoiceInfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.InvoiceInfoDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.InvoiceInfoService;

@Service
public class InvoiceInfoToolServiceImpl implements InvoiceInfoToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static InvoiceInfoService invoiceInfoService;
	
	private InvoiceInfoService gethessianInvoiceInfoService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getInvoiceInfoUrl();
		if (invoiceInfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			invoiceInfoService = (InvoiceInfoService) factory.create(InvoiceInfoService.class, hessianUrl);
		}
		return invoiceInfoService;
	}

	@Override
	public InvoiceInfoDTO getBySearch(Map map) throws Exception {
		return gethessianInvoiceInfoService().getBySearch(map);
	}
	
	@Override
	public int insertInvoiceInfo(Map map) throws Exception {
		return gethessianInvoiceInfoService().insertInvoiceInfo(map);
	}
}
