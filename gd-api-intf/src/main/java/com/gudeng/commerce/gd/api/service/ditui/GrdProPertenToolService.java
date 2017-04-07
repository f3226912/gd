package com.gudeng.commerce.gd.api.service.ditui;

import com.gudeng.commerce.gd.api.service.BaseToolService;
import com.gudeng.commerce.gd.bi.dto.GrdProPertenDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProPertenEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GrdProPertenToolService extends BaseToolService<GrdProPertenDTO> {
	public Long insert(GrdProPertenEntity entity) throws Exception;
}