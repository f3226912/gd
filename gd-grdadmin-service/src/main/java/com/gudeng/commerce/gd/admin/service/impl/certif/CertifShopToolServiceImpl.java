package com.gudeng.commerce.gd.admin.service.impl.certif;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.certif.CertifShopToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.certif.CertifShopDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifShopEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifShopService;

public class CertifShopToolServiceImpl implements CertifShopToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static CertifShopService certifShopService;

	protected CertifShopService getHessianCertifShopService() throws MalformedURLException {
		String url = gdProperties.getCertifShopServiceUrl();
		if (certifShopService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			certifShopService = (CertifShopService) factory.create(CertifShopService.class, url);
		}
		return certifShopService;
	}

	@Override
	public CertifShopDTO getById(String id) throws Exception {
		return getHessianCertifShopService().getById(id);
	}

	@Override
	public List<CertifShopDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianCertifShopService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianCertifShopService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianCertifShopService().deleteBatch(list);
	}

	@Override
	public int update(CertifShopDTO t) throws Exception {
		return getHessianCertifShopService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCertifShopService().getTotal(map);
	}

	@Override
	public Long insert(CertifShopEntity entity) throws Exception {
		return getHessianCertifShopService().insert(entity);
	}

	@Override
	public List<CertifShopDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianCertifShopService().getListPage(map);
	}
}