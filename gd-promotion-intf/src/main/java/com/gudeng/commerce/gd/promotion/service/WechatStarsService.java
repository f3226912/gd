package com.gudeng.commerce.gd.promotion.service;

import com.gudeng.commerce.gd.promotion.dto.WechatStarsDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatStarsEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface WechatStarsService extends BaseService<WechatStarsDTO> {
	public Long persist(WechatStarsEntity entity) throws Exception;
}