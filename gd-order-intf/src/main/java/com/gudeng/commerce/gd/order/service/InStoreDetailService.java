package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.InStoreDetailDTO;
import com.gudeng.commerce.gd.order.entity.InStoreDetailEntity;

public interface InStoreDetailService {
	/**
	 * 	根据id查找实体对象
	 * 
	 * */
	public InStoreDetailDTO getById(Long id)throws Exception;
	
	/**
	 * 	根据id删除实体对象
	 * 
	 * */
	public int deleteById(Long id)throws Exception;
	
	
	
	/**
	 * 	根据dto对象，更新数据
	 * 
	 * */
	public int updateByDto(InStoreDetailDTO inStoreDetailDTO)throws Exception;
	
	/**
	 * 	插入实体对象
	 * 
	 * */
	public Long addByEntity(InStoreDetailEntity inStoreDetailEntity)throws Exception;
	
	/**
	 * 	根据Map查询条件查询结果集合
	 * 
	 * */
	public List<InStoreDetailDTO> getBySearch(Map map) throws Exception;
	
	
	/**
	 * 	根据Map查询条件查询结果集合--分页展示
	 * 
	 * */
	public List<InStoreDetailDTO> getPageBySearch(Map map) throws Exception;
	
	public List<InStoreDetailDTO> getByBusinessId(Map map) throws Exception;
	
	public int getCountByBusinessId(Map map) throws Exception ;

	
	
	/**
	 * 查询入库单列表
	 * @return
	 * @throws Exception
	 */

	public List<InStoreDetailDTO> getInstoreProductList(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询入库单列表总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getInstoreProductListTotal(Map<String, Object> map) throws Exception;
		
}
