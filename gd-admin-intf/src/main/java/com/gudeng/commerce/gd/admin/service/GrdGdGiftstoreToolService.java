package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDataDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftInStorageDataDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftOutStorageDataDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftstoreDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftstoreEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GrdGdGiftstoreToolService extends BaseToolService<GrdGdGiftstoreDTO> {
	public Long insert(GrdGdGiftstoreEntity entity) throws Exception;
	
	
	/**
	 * 各仓库礼品入库统计
	 */
	public List<GrdGdGiftInStorageDataDTO> getGiftInStorageData(Map<String, Object> map) throws Exception;

	/**
	 * 各仓库礼品入库统计数量
	 */
	public Integer getGiftInStorageDataCount(Map<String, Object> map) throws Exception;
	
	/**
	 * 各仓库礼品出库统计
	 */
	public List<GrdGdGiftOutStorageDataDTO> getGiftOutStorageData(Map<String, Object> map) throws Exception;

	/**
	 * 各仓库礼品出库统计数量
	 */
	public Integer getGiftOutStorageDataCount(Map<String, Object> map) throws Exception;


	/**
	 * 各仓库礼品统计
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<GrdGdGiftDataDTO> getGrdGdGiftData(Map<String, Object> map) throws Exception;

	/**
	 * 各仓库礼品统计數量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getGrdGdGiftDataCount(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 合计數量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getGrdGdGiftDataSum(Map<String, Object> map) throws Exception;
}