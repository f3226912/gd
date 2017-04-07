package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.ActWechatInviteServiceTool;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.ActWechatInviteDTO;
import com.gudeng.commerce.gd.promotion.entity.ActWechatInviteEntity;
import com.gudeng.commerce.gd.promotion.service.ActWechatInviteService;

/**
 * 微信活动邀请表，用户点击邀请链接记录
 * 
 * @author lidong
 *
 */
public class ActWechatInviteServiceImpl implements ActWechatInviteServiceTool {
    /** hessian 接口配置工具 **/
    @Autowired
    public GdProperties gdProperties;
    private static ActWechatInviteService actWechatInviteService;

    protected ActWechatInviteService getHessianActWechatInviteService() throws MalformedURLException {
        String url = gdProperties.getProperties().getProperty("gd.actWechatInviteService.url");
        if (actWechatInviteService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            actWechatInviteService = (ActWechatInviteService) factory.create(ActWechatInviteService.class, url);
        }
        return actWechatInviteService;
    }

    @Override
    public Long save(ActWechatInviteEntity entity) throws Exception {
        return getHessianActWechatInviteService().save(entity);
    }

    @Override
    public List<ActWechatInviteDTO> getList(Map<String, Object> map) throws Exception {
        return getHessianActWechatInviteService().getList(map);
    }

    @Override
    public Integer getTotal(Map<String, Object> map) throws Exception {
        return getHessianActWechatInviteService().getTotal(map);
    }

}
