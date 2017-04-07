package com.gudeng.commerce.gd.api.service.impl.v160929;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.v160929.CertifBaseToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.certif.CertifBaseDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifBaseEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifBaseService;

public class CertifBaseToolServiceImpl implements CertifBaseToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static CertifBaseService certifBaseService;

	protected CertifBaseService getHessianCertifBaseService() throws MalformedURLException {
		String url = gdProperties.getCertifBaseServiceUrl();
		if (certifBaseService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			certifBaseService = (CertifBaseService) factory.create(CertifBaseService.class, url);
		}
		return certifBaseService;
	}

	@Override
	public CertifBaseDTO getById(String id) throws Exception {
		return getHessianCertifBaseService().getById(id);
	}

	@Override
	public List<CertifBaseDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianCertifBaseService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianCertifBaseService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianCertifBaseService().deleteBatch(list);
	}

	@Override
	public int update(CertifBaseDTO t) throws Exception {
		return getHessianCertifBaseService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCertifBaseService().getTotal(map);
	}

	@Override
	public Long insert(CertifBaseEntity entity) throws Exception {
		return getHessianCertifBaseService().insert(entity);
	}

	@Override
	public List<CertifBaseDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianCertifBaseService().getListPage(map);
	}

	@Override
	public CertifBaseDTO getOneBySearch(Map<String, Object> params) throws Exception {
		return getHessianCertifBaseService().getOneBySearch(params);
	}

	@Override
	public Map<String, Object> getStatusCombination(Map<String, Object> params) throws Exception {
		return getHessianCertifBaseService().getStatusCombination(params);
	}
}