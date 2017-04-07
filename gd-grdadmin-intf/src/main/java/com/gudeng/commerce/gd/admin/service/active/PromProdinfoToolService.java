package com.gudeng.commerce.gd.admin.service.active;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;

public interface PromProdinfoToolService {

	/**
	 * 查询供应商列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<PromProdInfoDTO> querySupplerPageByCondition(Map<String,Object> params) throws Exception;
	
	Integer getSupplerTotalCountByCondition(Map<String, Object> map) throws Exception;
	
	List<PromProdInfoDTO> queryProductPageByCondition(Map<String, Object> map)throws Exception;
	
	Integer getProductTotalCountByCondition(Map<String, Object> map)throws Exception;
	
	Integer updatePromProdInfo(List<PromProdInfoDTO> list)throws Exception;
}
