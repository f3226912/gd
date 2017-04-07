package com.gudeng.commerce.gd.m.service;

import com.gudeng.commerce.gd.promotion.dto.WechatMemberDTO;
import com.gudeng.commerce.gd.promotion.entity.WechatMemberEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface WechatMemberToolService extends BaseToolService<WechatMemberDTO> {
	public Long insert(WechatMemberEntity entity) throws Exception;
}