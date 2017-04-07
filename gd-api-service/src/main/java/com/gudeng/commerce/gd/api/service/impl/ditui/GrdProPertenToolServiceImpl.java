package com.gudeng.commerce.gd.api.service.impl.ditui;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.ditui.GrdProPertenToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.bi.dto.GrdProPertenDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProPertenEntity;
import com.gudeng.commerce.gd.bi.service.GrdProPertenService;

public class GrdProPertenToolServiceImpl implements GrdProPertenToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdProPertenService grdProPertenService;

	protected GrdProPertenService getHessianGrdProPertenService() throws MalformedURLException {
		String url = gdProperties.getGrdProPertenServiceUrl();
		if (grdProPertenService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdProPertenService = (GrdProPertenService) factory.create(GrdProPertenService.class, url);
		}
		return grdProPertenService;
	}

	@Override
	public GrdProPertenDTO getById(String id) throws Exception {
		return getHessianGrdProPertenService().getById(id);
	}

	@Override
	public List<GrdProPertenDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdProPertenService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdProPertenService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdProPertenService().deleteBatch(list);
	}

	@Override
	public int update(GrdProPertenDTO t) throws Exception {
		return getHessianGrdProPertenService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdProPertenService().getTotal(map);
	}

	@Override
	public Long insert(GrdProPertenEntity entity) throws Exception {
		return getHessianGrdProPertenService().insert(entity);
	}

	@Override
	public List<GrdProPertenDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdProPertenService().getListPage(map);
	}
}