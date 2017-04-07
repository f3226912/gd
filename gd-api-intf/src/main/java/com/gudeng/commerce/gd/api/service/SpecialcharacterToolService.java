package com.gudeng.commerce.gd.api.service;

import com.gudeng.commerce.gd.customer.dto.SpecialcharacterDTO;
import com.gudeng.commerce.gd.customer.entity.SpecialcharacterEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface SpecialcharacterToolService extends BaseToolService<SpecialcharacterDTO> {
	public Long insert(SpecialcharacterEntity entity) throws Exception;
}