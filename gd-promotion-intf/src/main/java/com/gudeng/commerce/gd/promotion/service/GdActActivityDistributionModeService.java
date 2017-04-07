package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GdActActivityDistributionModeDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityDistributionModeEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GdActActivityDistributionModeService extends BaseService<GdActActivityDistributionModeDTO> {
	public Long insert(GdActActivityDistributionModeEntity entity) throws Exception;

	/**
	 * 物流配送方式列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<GdActActivityDistributionModeDTO> getModeList(Map<String, Object> map) throws Exception;
	
}