package com.gudeng.commerce.gd.promotion.service;

import com.gudeng.commerce.gd.promotion.dto.GrdGiftLogDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftLogEntity;

public interface GrdGiftLogService extends BaseService<GrdGiftLogDTO> {
	public Long insert(GrdGiftLogEntity entity) throws Exception;
	
}