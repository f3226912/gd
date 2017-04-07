package com.gudeng.commerce.gd.admin.service.impl.certif;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.certif.CertifLogToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.certif.CertifLogDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifLogEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifLogService;

public class CertifLogToolServiceImpl implements CertifLogToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static CertifLogService certifLogService;

	protected CertifLogService getHessianCertifLogService() throws MalformedURLException {
		String url = gdProperties.getCertifLogServiceUrl();
		if (certifLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			certifLogService = (CertifLogService) factory.create(CertifLogService.class, url);
		}
		return certifLogService;
	}

	@Override
	public CertifLogDTO getById(String id) throws Exception {
		return getHessianCertifLogService().getById(id);
	}

	@Override
	public List<CertifLogDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianCertifLogService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianCertifLogService().deleteById(id);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianCertifLogService().deleteBatch(list);
	}

	@Override
	public int update(CertifLogDTO t) throws Exception {
		return getHessianCertifLogService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCertifLogService().getTotal(map);
	}

	@Override
	public Long insert(CertifLogEntity entity) throws Exception {
		return getHessianCertifLogService().insert(entity);
	}

	@Override
	public List<CertifLogDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianCertifLogService().getListPage(map);
	}
}