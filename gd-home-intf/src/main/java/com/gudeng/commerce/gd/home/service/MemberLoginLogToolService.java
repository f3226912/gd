package com.gudeng.commerce.gd.home.service;

import com.gudeng.commerce.gd.customer.dto.MemberLoginLogDTO;
import com.gudeng.commerce.gd.customer.entity.MemberLoginLogEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface MemberLoginLogToolService extends BaseToolService<MemberLoginLogDTO> {
	public Long insert(MemberLoginLogEntity entity) throws Exception;
}