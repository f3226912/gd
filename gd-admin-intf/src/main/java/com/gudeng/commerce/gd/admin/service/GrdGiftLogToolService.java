package com.gudeng.commerce.gd.admin.service;

import com.gudeng.commerce.gd.promotion.dto.GrdGiftLogDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftLogEntity;

public interface GrdGiftLogToolService extends BaseToolService<GrdGiftLogDTO> {
	public Long insert(GrdGiftLogEntity entity) throws Exception;
	
}