package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ProductBaseinfoDTO;

public interface ProductBaseinfoService {

	public List<ProductBaseinfoDTO> getProductsByUser(Map<String, Object> map);

	public int getProductTotalByUser(Map<String, Object> paramMap);

	public int getProductTotalByParticipants(Map<String, Object> paramMap);

	public List<ProductBaseinfoDTO> getUserParticipantsProducts(Map<String, Object> paramMap);
}
