package com.gudeng.commerce.gd.api.service.ditui;

import java.util.List;

import com.gudeng.commerce.gd.api.service.BaseToolService;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftRecordEntity;

public interface GrdGiftRecordToolService extends BaseToolService<GrdGiftRecordDTO> {
	public Long insert(GrdGiftRecordEntity entity) throws Exception;
	

}