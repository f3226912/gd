package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.SysrolereportsToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.dto.ReportsDTO;
import com.gudeng.commerce.info.customer.dto.SysrolereportsDTO;
import com.gudeng.commerce.info.customer.service.ReportsService;
import com.gudeng.commerce.info.customer.service.SysrolereportsService;

public class SysrolereportsToolServiceImpl implements SysrolereportsToolService {
    @Autowired
    private GdProperties gdProperties;

    private static SysrolereportsService sysrolereportsService;

    protected SysrolereportsService getReportsHessianService() throws MalformedURLException {
        if (sysrolereportsService == null) {
            String hessianUrl = gdProperties.getSysrolereportsServiceUrl();
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            sysrolereportsService = (SysrolereportsService) factory.create(SysrolereportsService.class, hessianUrl);
        }
        return sysrolereportsService;
    }

    @Override
    public int addBatch(List<ReportsDTO> list, String roleID, String userId) throws Exception {
        getReportsHessianService().delete(roleID);
        if (list != null && list.size() > 0) {
            return getReportsHessianService().addBatch(list, roleID, userId);
        }
        return 0;
    }

    @Override
    public int deleteBatch(List<?> list, String roleID) throws Exception {
        return getReportsHessianService().deleteBatch(list, roleID);
    }

    @Override
    public List<SysrolereportsDTO> getListByCondition(Map<String, Object> map) throws Exception {
        return getReportsHessianService().getListByCondition(map);
    }

    @Override
    public int delete(String roleID) throws Exception {
        return getReportsHessianService().delete(roleID);
    }

}
