package com.gudeng.commerce.gd.promotion.service;

import com.gudeng.commerce.gd.promotion.dto.WechatUserinfoDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatUserinfoEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface WechatUserinfoService extends BaseService<WechatUserinfoDTO> {
	public Long persist(WechatUserinfoEntity entity) throws Exception;
}