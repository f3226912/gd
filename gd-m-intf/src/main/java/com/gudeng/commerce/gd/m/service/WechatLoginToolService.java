package com.gudeng.commerce.gd.m.service;

import com.gudeng.commerce.gd.promotion.dto.WechatLoginDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatLoginEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface WechatLoginToolService extends BaseToolService<WechatLoginDTO> {
	public Long insert(WechatLoginEntity entity) throws Exception;
}