package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GrdInstorageDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdInstoragedetailDTO;

public interface GrdInstorageService {
	public Integer insert(GrdInstorageDTO dto,List<GrdInstoragedetailDTO> batchDetailDTO) throws Exception;

	public int getTodayTotal(Map<String, Object> map) throws Exception;
	
	public GrdInstoragedetailDTO queryGrdGiftStockInfo(Map<String, Object> map)throws Exception;
	
	public GrdInstoragedetailDTO queryPurchaseGift2InstorageInfo(Map<String, Object> map)throws Exception;
}