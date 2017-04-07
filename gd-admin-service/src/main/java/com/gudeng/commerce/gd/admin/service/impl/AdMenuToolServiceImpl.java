package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AdMenuToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AdMenuDTO;
import com.gudeng.commerce.gd.customer.service.AdAdvertiseService;
import com.gudeng.commerce.gd.customer.service.AdMenuService;

public class AdMenuToolServiceImpl implements AdMenuToolService {
    @Autowired
    public GdProperties gdProperties;

    private static AdMenuService adMenuService;

    private AdMenuService getHessianService() throws MalformedURLException {
        String hessianUrl = gdProperties.getAdMenuServiceUrl();
        if (adMenuService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            adMenuService = (AdMenuService) factory.create(AdMenuService.class, hessianUrl);
        }
        return adMenuService;
    }

    @Override
    public List<AdMenuDTO> getAdMenuByCondition(Map<String, Object> map) throws Exception {
        return getHessianService().getAdMenuByCondition(map);
    }

    @Override
    public Long insert(AdMenuDTO adMenuDTO) throws Exception {
        return getHessianService().insert(adMenuDTO);
    }

    @Override
    public Long update(AdMenuDTO adMenuDTO) throws Exception {
        return getHessianService().update(adMenuDTO);
    }

    @Override
    public Long delete(Map<String, Object> map) throws Exception {
        return getHessianService().delete(map);
    }

	@Override
	public AdMenuDTO getById(Long id) throws Exception {
		return getHessianService().getById(id);
	}

}
