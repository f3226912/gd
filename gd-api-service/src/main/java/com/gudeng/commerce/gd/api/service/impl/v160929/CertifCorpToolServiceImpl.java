package com.gudeng.commerce.gd.api.service.impl.v160929;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.v160929.CertifCorpToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.certif.CertifCorpDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifCorpEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifCorpService;

public class CertifCorpToolServiceImpl implements CertifCorpToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static CertifCorpService certifCorpService;

	protected CertifCorpService getHessianCertifCorpService() throws MalformedURLException {
		String url = gdProperties.getCertifCorpServiceUrl();
		if (certifCorpService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			certifCorpService = (CertifCorpService) factory.create(CertifCorpService.class, url);
		}
		return certifCorpService;
	}

	@Override
	public CertifCorpDTO getById(String id) throws Exception {
		return getHessianCertifCorpService().getById(id);
	}

	@Override
	public List<CertifCorpDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianCertifCorpService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianCertifCorpService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianCertifCorpService().deleteBatch(list);
	}

	@Override
	public int update(CertifCorpDTO t) throws Exception {
		return getHessianCertifCorpService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCertifCorpService().getTotal(map);
	}

	@Override
	public Long insert(CertifCorpEntity entity) throws Exception {
		return getHessianCertifCorpService().insert(entity);
	}

	@Override
	public List<CertifCorpDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianCertifCorpService().getListPage(map);
	}

	@Override
	public CertifCorpDTO getOneBySearch(Map<String, Object> params) throws Exception {
		return getHessianCertifCorpService().getOneBySearch(params);
	}
}