package com.gudeng.commerce.gd.admin.service;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GrdInstorageDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdInstoragedetailDTO;

public interface GrdInstorageToolService {
	
	public Integer insert(GrdInstorageDTO dto,List<GrdInstoragedetailDTO> batchDetailDTO) throws Exception;

	public int getTodayTotal(Map<String, Object> map) throws Exception;
	
	public GrdInstoragedetailDTO queryGrdGiftStockInfo(Map<String, Object> map)throws Exception;
}