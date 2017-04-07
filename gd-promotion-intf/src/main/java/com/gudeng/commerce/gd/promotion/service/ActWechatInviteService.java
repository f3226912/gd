package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActWechatInviteDTO;
import com.gudeng.commerce.gd.promotion.entity.ActWechatInviteEntity;

/**
 * 微信活动邀请表，用户点击邀请链接记录
 * 
 * @author lidong
 *
 */
public interface ActWechatInviteService {
    /**
     * 新增邀请记录
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    public Long save(ActWechatInviteEntity entity) throws Exception;

    /**
     * 查找邀请记录
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public List<ActWechatInviteDTO> getList(Map<String, Object> map) throws Exception;

    /**
     * 查询邀请记录总数
     * @param map
     * @return
     * @throws Exception
     */
    public Integer getTotal(Map<String, Object> map) throws Exception;

}
