package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.ActWechatShareServiceTool;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.ActWechatShareDTO;
import com.gudeng.commerce.gd.promotion.entity.ActWechatShareEntity;
import com.gudeng.commerce.gd.promotion.service.ActWechatShareService;

/**
 * 微信活动分享记录
 * 
 * @author lidong
 *
 */
public class ActWechatShareServiceImpl implements ActWechatShareServiceTool {
    /** hessian 接口配置工具 **/
    @Autowired
    public GdProperties gdProperties;
    private static ActWechatShareService actWechatShareService;

    protected ActWechatShareService getHessianActWechatShareService() throws MalformedURLException {
        String url = gdProperties.getProperties().getProperty("gd.actWechatShareService.url");
        if (actWechatShareService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            actWechatShareService = (ActWechatShareService) factory.create(ActWechatShareService.class, url);
        }
        return actWechatShareService;
    }

    @Override
    public Long save(ActWechatShareEntity dto) throws Exception {
        return getHessianActWechatShareService().save(dto);
    }

    @Override
    public List<ActWechatShareDTO> getList(Map<String, Object> map) throws Exception {
        return getHessianActWechatShareService().getList(map);
    }
}
