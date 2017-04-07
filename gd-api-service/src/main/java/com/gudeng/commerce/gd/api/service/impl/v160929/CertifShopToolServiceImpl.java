package com.gudeng.commerce.gd.api.service.impl.v160929;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.v160929.CertifShopToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.certif.CertifShopDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifShopEntity;
import com.gudeng.commerce.gd.customer.service.certif.CertifShopService;

/**
 * 实体商铺认证
 * @author houxp
 *
 */
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
	public Long insert(CertifShopEntity entity) throws Exception {
		return getHessianCertifShopService().insert(entity);
	}
	
	@Override
	public CertifShopDTO getById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CertifShopDTO> getList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CertifShopDTO> getListPage(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(CertifShopDTO t) throws Exception {
		return getHessianCertifShopService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianCertifShopService().getTotal(map);
	}

	/**
	 * 根据用户ID获取实体认证信息
	 */
	@Override
	public CertifShopDTO getByUserId(String memberId) throws Exception {
		return getHessianCertifShopService().getByUserId(memberId);
	}
}