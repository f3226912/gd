package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GrdGiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftEntity;

public interface GrdGiftToolService extends BaseToolService<GrdGiftDTO> {
	public Long insert(GrdGiftEntity entity) throws Exception;

	public int batchDelete(List<String> list) throws Exception;
	
	public int getNoCount(String giftId) throws Exception;

	public int getGrdGiftRecordCount(Map<String, Object> map) throws Exception;
}