package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GrdGdGiftService extends BaseService<GrdGdGiftDTO> {
	public Long insert(GrdGdGiftEntity entity) throws Exception;
	
	public int getTotalByPurchase(Map<String, Object> map) throws Exception;


	public List<GrdGdGiftDTO> getListPageByPurchase(Map<String, Object> map) throws Exception;

	public Integer getMaxId();
}