package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.CallstatiSticsToolService;
import com.gudeng.commerce.gd.admin.service.CarLineManageService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.entity.CallstatiSticsEntity;
import com.gudeng.commerce.gd.customer.service.CallstatiSticsService;
import com.gudeng.commerce.gd.customer.service.CarLineService;

/**
 * 电话记录service
 * 
 * @author Ailen
 *
 */
@Service
public class CallstatiStatiSticsToolServiceImpl implements CallstatiSticsToolService {

    /** hessian 接口配置工具 **/
    @Autowired
    public GdProperties gdProperties;

    private static CallstatiSticsService callstatiSticsService;

    /**
     * 功能描述:电话记录管理接口服务
     * 
     * @param
     * @return
     */
    protected CallstatiSticsService getHessianCallstatiSticsService() throws MalformedURLException {
        String url = gdProperties.getProperties().getProperty("gd.callstatiSticsService.url");
        if (callstatiSticsService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            callstatiSticsService = (CallstatiSticsService) factory.create(CallstatiSticsService.class, url);
        }
        return callstatiSticsService;
    }

    /**
     * @Description 查询总数
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年5月17日 上午11:34:46
     * @Author lidong(dli@gdeng.cn)
     */
    public int getTotalCount(Map map) throws Exception {
        return getHessianCallstatiSticsService().getTotalCount(map);
    }

    /**
     * @Description 根据条件查询集合
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年5月17日 上午11:35:30
     * @Author lidong(dli@gdeng.cn)
     */
    public List<CallstatiSticsDTO> getList(Map map) throws Exception {
        return getHessianCallstatiSticsService().getList(map);
    }

    @Override
    public void insert(CallstatiSticsEntity callstatiSticsDTO) throws Exception {
        getHessianCallstatiSticsService().insert(callstatiSticsDTO);
    }

    @Override
    public int getTotal(Map map) throws Exception {
        return getHessianCallstatiSticsService().getTotal(map);
    }

    @Override
    public int getTotal2(Map map) throws Exception {
        return getHessianCallstatiSticsService().getTotal2(map);
    }

    @Override
    public List<CallstatiSticsDTO> getBySearch(Map map) throws Exception {
        return getHessianCallstatiSticsService().getBySearch(map);
    }

    @Override
    public List<CallstatiSticsDTO> getBySearch2(Map map) throws Exception {
        return getHessianCallstatiSticsService().getBySearch2(map);
    }

    @Override
    public List<CallstatiSticsDTO> getBySearchForSupplier(Map map) throws Exception {
        return getHessianCallstatiSticsService().getBySearchForSupplier(map);
    }

    @Override
    public int getTotal3(Map map) throws Exception {
        return getHessianCallstatiSticsService().getTotal3(map);
    }

}
