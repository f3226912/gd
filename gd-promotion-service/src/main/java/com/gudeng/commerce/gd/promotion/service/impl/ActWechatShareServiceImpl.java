package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.ActWechatShareDTO;
import com.gudeng.commerce.gd.promotion.entity.ActWechatShareEntity;
import com.gudeng.commerce.gd.promotion.service.ActWechatShareService;

/**
 * 微信活动分享记录
 * 
 * @author lidong
 *
 */
public class ActWechatShareServiceImpl implements ActWechatShareService {
    @Resource
    private BaseDao<?> baseDao;

    @Override
    public Long save(ActWechatShareEntity dto) throws Exception {
        return baseDao.persist(dto, Long.class);
    }

    @Override
    public List<ActWechatShareDTO> getList(Map<String, Object> map) throws Exception {
        return baseDao.queryForList("ActWechatShare.queryPageByCondition", map, ActWechatShareDTO.class);
    }
}
