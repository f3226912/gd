package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AdSpaceToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AdSpaceDTO;
import com.gudeng.commerce.gd.customer.entity.AdSpaceEntity;
import com.gudeng.commerce.gd.customer.service.AdSpaceService;

public class AdSpaceToolServiceImpl implements AdSpaceToolService{
	
	@Autowired
	private GdProperties gdProperties;

	private AdSpaceService adSpaceService;
	
	protected AdSpaceService getAdSpaceHessianService() throws MalformedURLException{
		if(adSpaceService == null){
			String url = gdProperties.getAdSpaceServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			adSpaceService = (AdSpaceService) factory.create(AdSpaceService.class, url);
		}
		return adSpaceService;
	}
	
	@Override
	public Long insert(AdSpaceEntity entity) throws Exception {
		return getAdSpaceHessianService().persist(entity);
	}

	@Override
	public List<AdSpaceDTO> queryByCondition(Map<String, Object> map) throws Exception {
		return getAdSpaceHessianService().queryByCondition(map);
	}

	@Override
	public Long countByCondition(Map<String, Object> map) throws Exception {
		return getAdSpaceHessianService().countByCondition(map);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return getAdSpaceHessianService().deleteById(id);
	}

	@Override
	public AdSpaceDTO getById(Long id) throws Exception {
		return getAdSpaceHessianService().getById(id);
	}

	@Override
	public int update(AdSpaceEntity entity) throws Exception {
		return getAdSpaceHessianService().update(entity);
	}

	@Override
	public boolean isExist(Long menuId, String spaceSign) throws Exception {
		return getAdSpaceHessianService().isExist(menuId, spaceSign);
	}

	@Override
	public boolean canDelete(Long adSpaceId) throws Exception {
		return getAdSpaceHessianService().canDelete(adSpaceId);
	}
	
	@Override
	public int deleteAdSpace(Long id, String updateUserId, String updateUserName) throws Exception {
		return getAdSpaceHessianService().deleteAdSpace(id, updateUserId, updateUserName);
	}

}
