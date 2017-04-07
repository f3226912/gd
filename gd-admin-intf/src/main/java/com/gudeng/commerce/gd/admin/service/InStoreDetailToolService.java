package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.InStoreDetailDTO;

/**
 * 产品入库信息查询
 * @author xiaojun
 *
 */
public interface InStoreDetailToolService {
	
	/**
	 * 产品入库信息列表查询
	 * @return
	 */
	public List<InStoreDetailDTO> getInstoreProductList(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询入库信息列表总数
	 * @return
	 * @throws Exception
	 */
	public int getInstoreProductListTotal(Map<String, Object> map) throws Exception;
}
