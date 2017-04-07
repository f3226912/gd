package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.ActWechatInviteDTO;
import com.gudeng.commerce.gd.promotion.entity.ActWechatInviteEntity;
import com.gudeng.commerce.gd.promotion.service.ActWechatInviteService;

/**
 * 微信活动邀请表，用户点击邀请链接记录
 * 
 * @author lidong
 *
 */
public class ActWechatInviteServiceImpl implements ActWechatInviteService {
    @Resource
    private BaseDao<?> baseDao;

    @Override
    public Long save(ActWechatInviteEntity entity) throws Exception {
        return baseDao.persist(entity, Long.class);
    }

    @Override
    public List<ActWechatInviteDTO> getList(Map<String, Object> map) throws Exception {
        return baseDao.queryForList("ActWechatInvite.queryPageByCondition", map, ActWechatInviteDTO.class);
    }

    @Override
    public Integer getTotal(Map<String, Object> map) throws Exception {
        return baseDao.queryForObject("ActWechatInvite.getTotalCountByCondition",map,Integer.class);
    }

}
