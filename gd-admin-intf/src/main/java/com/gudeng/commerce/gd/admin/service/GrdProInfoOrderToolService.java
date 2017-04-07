package com.gudeng.commerce.gd.admin.service;

import com.gudeng.commerce.gd.bi.dto.GrdProInfoOrderDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProInfoOrderEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GrdProInfoOrderToolService extends BaseToolService<GrdProInfoOrderDTO> {
	public Long insert(GrdProInfoOrderEntity entity) throws Exception;
}