package com.gudeng.commerce.gd.api.service.ditui;

import com.gudeng.commerce.gd.api.service.BaseToolService;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftEntity;

public interface GrdGiftToolService extends BaseToolService<GrdGiftDTO> {
	public Long insert(GrdGiftEntity entity) throws Exception;
}