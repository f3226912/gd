package com.gudeng.commerce.gd.api.service.impl.v160929;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.v160929.CertifSpProductToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.certif.CertifSpProductDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifSpProductEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifSpProductService;

public class CertifSpProductToolServiceImpl implements CertifSpProductToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static CertifSpProductService certifSpProductService;

	protected CertifSpProductService getHessianCertifSpProductService() throws MalformedURLException {
		String url = gdProperties.getCertifSpProductServiceUrl();
		if (certifSpProductService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			certifSpProductService = (CertifSpProductService) factory.create(CertifSpProductService.class, url);
		}
		return certifSpProductService;
	}

	@Override
	public CertifSpProductDTO getById(String id) throws Exception {
		return getHessianCertifSpProductService().getById(id);
	}

	@Override
	public List<CertifSpProductDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianCertifSpProductService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianCertifSpProductService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianCertifSpProductService().deleteBatch(list);
	}

	@Override
	public int update(CertifSpProductDTO t) throws Exception {
		return getHessianCertifSpProductService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCertifSpProductService().getTotal(map);
	}

	@Override
	public Long insert(CertifSpProductEntity entity) throws Exception {
		return getHessianCertifSpProductService().insert(entity);
	}

	@Override
	public List<CertifSpProductDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianCertifSpProductService().getListPage(map);
	}
}