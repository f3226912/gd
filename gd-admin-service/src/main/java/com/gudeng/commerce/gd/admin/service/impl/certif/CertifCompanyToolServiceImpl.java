package com.gudeng.commerce.gd.admin.service.impl.certif;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.certif.CertifCompanyToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.certif.CertifCompanyDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifCompanyEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifCompanyService;

public class CertifCompanyToolServiceImpl implements CertifCompanyToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static CertifCompanyService certifCompanyService;

	protected CertifCompanyService getHessianCertifCompanyService() throws MalformedURLException {
		String url = gdProperties.getCertifCompanyServiceUrl();
		if (certifCompanyService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			certifCompanyService = (CertifCompanyService) factory.create(CertifCompanyService.class, url);
		}
		return certifCompanyService;
	}

	@Override
	public CertifCompanyDTO getById(String id) throws Exception {
		return getHessianCertifCompanyService().getById(id);
	}

	@Override
	public List<CertifCompanyDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianCertifCompanyService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianCertifCompanyService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianCertifCompanyService().deleteBatch(list);
	}

	@Override
	public int update(CertifCompanyDTO t) throws Exception {
		return getHessianCertifCompanyService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCertifCompanyService().getTotal(map);
	}

	@Override
	public Long insert(CertifCompanyEntity entity) throws Exception {
		return getHessianCertifCompanyService().insert(entity);
	}

	@Override
	public List<CertifCompanyDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianCertifCompanyService().getListPage(map);
	}
}