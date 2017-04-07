package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdProOrderRecievedToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.bi.dto.GrdProOrderRecievedDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProOrderRecievedEntity;
import com.gudeng.commerce.gd.bi.service.GrdProOrderRecievedService;

public class GrdProOrderRecievedToolServiceImpl implements GrdProOrderRecievedToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdProOrderRecievedService grdProOrderRecievedService;

	protected GrdProOrderRecievedService getHessianGrdProOrderRecievedService() throws MalformedURLException {
		String url = gdProperties.getGrdProOrderRecievedServiceUrl();
		if (grdProOrderRecievedService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdProOrderRecievedService = (GrdProOrderRecievedService) factory.create(GrdProOrderRecievedService.class, url);
		}
		return grdProOrderRecievedService;
	}

	@Override
	public GrdProOrderRecievedDTO getById(String id) throws Exception {
		return getHessianGrdProOrderRecievedService().getById(id);
	}

	@Override
	public List<GrdProOrderRecievedDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdProOrderRecievedService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdProOrderRecievedService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdProOrderRecievedService().deleteBatch(list);
	}

	@Override
	public int update(GrdProOrderRecievedDTO t) throws Exception {
		return getHessianGrdProOrderRecievedService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdProOrderRecievedService().getTotal(map);
	}

	@Override
	public Long insert(GrdProOrderRecievedEntity entity) throws Exception {
		return getHessianGrdProOrderRecievedService().insert(entity);
	}

	@Override
	public List<GrdProOrderRecievedDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdProOrderRecievedService().getListPage(map);
	}
}