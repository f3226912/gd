package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdUserCustomerDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdUserCustomerLogDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdUserCustomerEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GrdUserCustomerService extends BaseService<GrdUserCustomerDTO> {
	public int insert(GrdUserCustomerEntity entity) throws Exception;
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getGrdMemberTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<GrdMemberDTO> getGrdMemberListPage(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public GrdUserCustomerDTO getUserCustomerCount(Map<String, Object> map) throws Exception;
	
	public int insertUserCustomerLog(GrdUserCustomerDTO dto) throws Exception;
	
	/**
	 * 批量指派地推人员
	 * @param list
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public void batchUpdate(List<String> list,GrdUserCustomerDTO dto) throws Exception;
	
	/**
	 * 根据条件查询list(不分页查询)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<GrdUserCustomerLogDTO> getUserCustomerLogList(Map<String, Object> map) throws Exception;
}