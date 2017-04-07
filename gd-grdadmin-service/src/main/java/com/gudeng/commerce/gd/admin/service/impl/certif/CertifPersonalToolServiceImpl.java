package com.gudeng.commerce.gd.admin.service.impl.certif;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.certif.CertifPersonalToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.certif.CertifPersonalDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifPersonalEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifPersonalService;

public class CertifPersonalToolServiceImpl implements CertifPersonalToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static CertifPersonalService certifPersonalService;

	protected CertifPersonalService getHessianCertifPersonalService() throws MalformedURLException {
		String url = gdProperties.getCertifPersonalServiceUrl();
		if (certifPersonalService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			certifPersonalService = (CertifPersonalService) factory.create(CertifPersonalService.class, url);
		}
		return certifPersonalService;
	}

	@Override
	public CertifPersonalDTO getById(String id) throws Exception {
		return getHessianCertifPersonalService().getById(id);
	}

	@Override
	public List<CertifPersonalDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianCertifPersonalService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianCertifPersonalService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianCertifPersonalService().deleteBatch(list);
	}

	@Override
	public int update(CertifPersonalDTO t) throws Exception {
		return getHessianCertifPersonalService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCertifPersonalService().getTotal(map);
	}

	@Override
	public Long insert(CertifPersonalEntity entity) throws Exception {
		return getHessianCertifPersonalService().insert(entity);
	}

	@Override
	public List<CertifPersonalDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianCertifPersonalService().getListPage(map);
	}
}