package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.SysmessageToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.dto.SysmessageDTO;
import com.gudeng.commerce.info.customer.service.SysmessageService;

/**
 * @Description 系统消息-用户 关联
 * @Project info-customer-intf
 * @ClassName SysmessageuserService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年3月4日 上午9:07:04
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public class SysmessageToolServiceImpl implements SysmessageToolService {
    @Autowired
    public GdProperties gdProperties;

    private static SysmessageService sysmessageService;

    private SysmessageService hessianSysmessageService() throws MalformedURLException {
        String hessianUrl = gdProperties.getSysmessageServiceUrl();
        if (sysmessageService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            sysmessageService = (SysmessageService) factory.create(SysmessageService.class, hessianUrl);
        }
        return sysmessageService;
    }

    @Override
    public int insert(SysmessageDTO sysmessageDTO) throws Exception {
        return hessianSysmessageService().insert(sysmessageDTO);
    }

    @Override
    public int delete(Map<String, Object> map) throws Exception {
        return hessianSysmessageService().delete(map);
    }

    @Override
    public List<SysmessageDTO> getListByConditon(Map<String, Object> map) throws Exception {
        return hessianSysmessageService().getListByConditon(map);
    }

    @Override
    public int getTotalByCondition(Map<String, Object> map) throws Exception {
        return hessianSysmessageService().getTotalByCondition(map);
    }

}
