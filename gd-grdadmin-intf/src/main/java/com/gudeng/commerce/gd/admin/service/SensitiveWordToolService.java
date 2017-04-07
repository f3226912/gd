package com.gudeng.commerce.gd.admin.service;

import com.gudeng.commerce.gd.customer.dto.SensitiveWordDTO;
import com.gudeng.commerce.gd.customer.entity.SensitiveWordEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface SensitiveWordToolService extends BaseToolService<SensitiveWordDTO> {
	public Long insert(SensitiveWordEntity entity) throws Exception;
}