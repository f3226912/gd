package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.SysmessageuserToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.dto.SysmessageuserDTO;
import com.gudeng.commerce.info.customer.service.SysmessageuserService;

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
public class SysmessageuserToolServiceImpl implements SysmessageuserToolService {
    @Autowired
    public GdProperties gdProperties;

    private static SysmessageuserService sysmessageuserService;

    private SysmessageuserService hessianSysmessageuserService() throws MalformedURLException {
        String hessianUrl = gdProperties.getSysmessageuserServiceUrl();
        if (sysmessageuserService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            sysmessageuserService = (SysmessageuserService) factory.create(SysmessageuserService.class, hessianUrl);
        }
        return sysmessageuserService;
    }

    @Override
    public int insert(Map<String, Object> map) throws Exception {
        return hessianSysmessageuserService().insert(map);
    }

    public int deleteByMessageID(Map<String, Object> map) throws Exception {
        return hessianSysmessageuserService().deleteByMessageID(map);
    }

    public int deleteByUserID(Map<String, Object> map) throws Exception {
        return hessianSysmessageuserService().deleteByUserID(map);
    }

    public int deleteByUserIdAndMessageID(Map<String, Object> map) throws Exception {
        return hessianSysmessageuserService().deleteByUserIdAndMessageID(map);
    }

    @Override
    public List<SysmessageuserDTO> getListByConditon(Map<String, Object> map) throws Exception {
        return hessianSysmessageuserService().getListByConditon(map);
    }

    @Override
    public int update(SysmessageuserDTO sysmessageuserDTO) throws Exception {
        return hessianSysmessageuserService().update(sysmessageuserDTO);
    }

}
