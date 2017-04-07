package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.NpsOfferPriceToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.entity.NpsOfferPriceEntity;
import com.gudeng.commerce.gd.customer.service.NpsOfferPriceService;

public class NpsOfferPriceToolServiceImpl implements NpsOfferPriceToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static NpsOfferPriceService npsOfferPriceService;

	protected NpsOfferPriceService getHessianNpsOfferPriceService() throws MalformedURLException {
		String url = gdProperties.getNpsOfferPriceServiceUrl();
		if (npsOfferPriceService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			npsOfferPriceService = (NpsOfferPriceService) factory.create(NpsOfferPriceService.class, url);
		}
		return npsOfferPriceService;
	}

	@Override
	public NpsOfferPriceDTO getById(String id) throws Exception {
		return getHessianNpsOfferPriceService().getById(id);
	}

	@Override
	public List<NpsOfferPriceDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianNpsOfferPriceService().getListPage(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianNpsOfferPriceService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianNpsOfferPriceService().deleteById(list);
	}

	@Override
	public int update(NpsOfferPriceDTO t) throws Exception {
		return getHessianNpsOfferPriceService().updateStatus(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianNpsOfferPriceService().getTotal(map);
	}

	@Override
	public Long insert(NpsOfferPriceEntity entity) throws Exception {
		return getHessianNpsOfferPriceService().insert(entity);
	}

	@Override
	public List<NpsOfferPriceDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianNpsOfferPriceService().getListPage(map);
	}
}