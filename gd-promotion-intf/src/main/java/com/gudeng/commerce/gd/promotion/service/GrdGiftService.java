package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GrdGiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftEntity;

public interface GrdGiftService extends BaseService<GrdGiftDTO> {
	public Long insert(GrdGiftEntity entity) throws Exception;

	/**
	 * 根据市场Id，获取当前可用的礼品列表
	 * @param queryMap 
	 * 
	 * */
	public List<GrdGiftDTO> getGiftList(String marketId, Map queryMap) throws Exception;

	public int batchDelete(List<String> list) throws Exception;

	public int getGiftTotal(String marketId) throws Exception;
	
	public int updateStock(Map<String, Object> mapStock);
	
	public int getNoCount(String giftId);
	
	public int getGrdGiftRecordCount(Map<String, Object> map) throws Exception;
	
	public List<GrdGiftDTO> getListPage2(Map queryMap) throws Exception;
	
	public int getTotal2( Map queryMap) throws Exception;


}