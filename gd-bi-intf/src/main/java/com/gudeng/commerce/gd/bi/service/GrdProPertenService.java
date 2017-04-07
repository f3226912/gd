package com.gudeng.commerce.gd.bi.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.bi.dto.GrdProPertenDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProPertenEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GrdProPertenService extends BaseService<GrdProPertenDTO> {
	public Long insert(GrdProPertenEntity entity) throws Exception;

	public int batchUpdate(String customerId, List<Map> nstOrderMaps)throws Exception;
}