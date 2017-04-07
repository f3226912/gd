package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityUserRuleEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GdActActivityUserRuleService extends BaseService<GdActActivityUserRuleDTO> {
	public Long insert(GdActActivityUserRuleEntity entity) throws Exception;
	public List<GdActActivityUserRuleEntity> queryByMarketId(Map<String,Object> paraMap) throws Exception;
}