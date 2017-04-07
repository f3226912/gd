package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActWechatShareDTO;
import com.gudeng.commerce.gd.promotion.entity.ActWechatShareEntity;

/**
 * 微信活动分享记录
 * 
 * @author lidong
 *
 */
public interface ActWechatShareService {

    /**
     * 新增微信活动分享记录
     * 
     * @param dto
     * @return
     * @throws Exception
     */
    public Long save(ActWechatShareEntity entity) throws Exception;

    /**
     * 查找微信活动分享记录
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public List<ActWechatShareDTO> getList(Map<String, Object> map) throws Exception;

}
