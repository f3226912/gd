package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.PushOfflineToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.PushOfflineDTO;
import com.gudeng.commerce.gd.customer.service.PushOfflineService;
/**
 * @Description 线下推广统计服务
 * @Project gd-customer-intf
 * @ClassName PushOfflineService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年2月18日 上午11:59:45
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public class PushOfflineToolServiceImpl implements PushOfflineToolService {

    @Autowired
    public GdProperties gdProperties;
    private static PushOfflineService pushOfflineService;
    
    private PushOfflineService gethessianPushOfflineService()
            throws MalformedURLException {
        String hessianUrl = gdProperties.getPushOfflineServiceUrl();
        if (pushOfflineService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            pushOfflineService = (PushOfflineService) factory.create(PushOfflineService.class, hessianUrl);
        }
        return pushOfflineService;
    }
    
    @Override
    public int getTotal(Map<String, Object> map) throws Exception {
        return gethessianPushOfflineService().getTotal(map);
    }

    @Override
    public List<PushOfflineDTO> getPushOfflineDTOList(Map<String, Object> map) throws Exception {
        return gethessianPushOfflineService().getPushOfflineDTOList(map);
    }

}
