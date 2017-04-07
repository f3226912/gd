package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdUserCustomerToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdUserCustomerDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdUserCustomerEntity;
import com.gudeng.commerce.gd.promotion.service.GrdUserCustomerService;

public class GrdUserCustomerToolServiceImpl implements GrdUserCustomerToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdUserCustomerService grdUserCustomerService;

	protected GrdUserCustomerService getHessianGrdUserCustomerService() throws MalformedURLException {
		String url = gdProperties.getGrdUserCustomerServiceUrl();
		if (grdUserCustomerService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdUserCustomerService = (GrdUserCustomerService) factory.create(GrdUserCustomerService.class, url);
		}
		return grdUserCustomerService;
	}

	@Override
	public GrdUserCustomerDTO getById(String id) throws Exception {
		return getHessianGrdUserCustomerService().getById(id);
	}

	@Override
	public List<GrdUserCustomerDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianGrdUserCustomerService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianGrdUserCustomerService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianGrdUserCustomerService().deleteBatch(list);
	}

	@Override
	public int update(GrdUserCustomerDTO t) throws Exception {
		return getHessianGrdUserCustomerService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdUserCustomerService().getTotal(map);
	}

	@Override
	public int insert(GrdUserCustomerEntity entity) throws Exception {
		return getHessianGrdUserCustomerService().insert(entity);
	}

	@Override
	public List<GrdUserCustomerDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianGrdUserCustomerService().getListPage(map);
	}

	@Override
	public int getGrdMemberTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdUserCustomerService().getGrdMemberTotal(map);
	}

	@Override
	public List<GrdMemberDTO> getGrdMemberListPage(Map<String, Object> map)
			throws Exception {
		return getHessianGrdUserCustomerService().getGrdMemberListPage(map);
	}

	@Override
	public GrdUserCustomerDTO getUserCustomerCount(Map<String, Object> map) throws Exception {
		return getHessianGrdUserCustomerService().getUserCustomerCount(map);
	}

	@Override
	public int insertUserCustomerLog(GrdUserCustomerDTO dto)
			throws Exception {
		return getHessianGrdUserCustomerService().insertUserCustomerLog(dto);
	}
}