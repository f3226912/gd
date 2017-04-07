package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AdAdvertiseToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;
import com.gudeng.commerce.gd.customer.entity.AdAdvertise;
import com.gudeng.commerce.gd.customer.service.AdAdvertiseService;

/**
 * @Description 广告管理服务
 * @Project gd-admin-service
 * @ClassName AdAdvertiseToolServiceImpl.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月13日 上午11:54:24
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public class AdAdvertiseToolServiceImpl implements AdAdvertiseToolService {

    @Autowired
    public GdProperties gdProperties;

    private static AdAdvertiseService adAdvertiseService;

    @Override
    public List<AdAdvertiseDTO> geAdAdvertiseDTOList(Map<String, Object> map) throws Exception {
        return getHessianService().geAdAdvertiseDTOList(map);
    }

    private AdAdvertiseService getHessianService() throws MalformedURLException {
        String hessianUrl = gdProperties.getAdAdvertiseServiceUrl();
        if (adAdvertiseService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            adAdvertiseService = (AdAdvertiseService) factory.create(AdAdvertiseService.class, hessianUrl);
        }
        return adAdvertiseService;
    }

    @Override
    public int getTotal(Map<String, Object> map) throws Exception {
        return getHessianService().getTotal(map);
    }

    @Override
    public Long insert(AdAdvertiseDTO adAdvertiseDTO) throws Exception {
        return getHessianService().insert(adAdvertiseDTO);
    }

    @Override
    public int delete(List<String> ids) throws Exception {
        return getHessianService().delete(ids);
    }

    @Override
    public Long persit(AdAdvertise adAdvertise) throws Exception {
        return getHessianService().persit(adAdvertise);
    }

    @Override
    public int update(AdAdvertise adAdvertise) throws Exception {
        return getHessianService().update(adAdvertise);
    }

    @Override
    public int updateState(AdAdvertiseDTO adAdvertiseDTO) throws Exception {
        return getHessianService().updateState(adAdvertiseDTO);
    }

}
