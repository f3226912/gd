package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.SysroleboardToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.dto.BoardDTO;
import com.gudeng.commerce.info.customer.dto.SysroleboardDTO;
import com.gudeng.commerce.info.customer.service.SysroleboardService;

public class SysroleboardToolServiceImpl implements SysroleboardToolService {
    @Autowired
    private GdProperties gdProperties;

    private static SysroleboardService sysrolereportsService;

    protected SysroleboardService getBoardHessianService() throws MalformedURLException {
        if (sysrolereportsService == null) {
            String hessianUrl = gdProperties.getSysroleboardServiceUrl();
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            sysrolereportsService = (SysroleboardService) factory.create(SysroleboardService.class, hessianUrl);
        }
        return sysrolereportsService;
    }

    @Override
    public int addBatch(List<BoardDTO> list, String roleID, String userId) throws Exception {
        int result = getBoardHessianService().delete(roleID);
        if (list != null && list.size() > 0) {
            return getBoardHessianService().addBatch(list, roleID, userId);
        }
        return 0;
    }

    @Override
    public int deleteBatch(List<?> list, String roleID) throws Exception {
        return getBoardHessianService().deleteBatch(list, roleID);
    }

    @Override
    public List<SysroleboardDTO> getListByCondition(Map<String, Object> map) throws Exception {
        return getBoardHessianService().getListByCondition(map);
    }

    @Override
    public int delete(String roleID) throws Exception {
        return getBoardHessianService().delete(roleID);
    }

}
