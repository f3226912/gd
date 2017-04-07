package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.PushAdAppDTO;
import com.gudeng.commerce.gd.customer.dto.NstNoticeEntityDTO;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;

public interface PushAdInfoToolService {

	public List<PushAdAppDTO> getAdList(Map<String,Object> map)throws Exception;
	
	/**
	 * 获取推荐产品列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<PushProductDTO> getProductList(Map<String,Object> map)throws Exception;

	/**
	 * 获取推荐产品总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getProductTotal(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 获取公告
	 * @return
	 * @throws Exception
	 */
	public List<NstNoticeEntityDTO> getNoticeList() throws Exception;
	
	
}
