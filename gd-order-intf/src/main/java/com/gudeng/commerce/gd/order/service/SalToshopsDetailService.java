package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.SalToshopsDetailDTO;
import com.gudeng.commerce.gd.order.entity.SalToshopsDetailEntity;

/**
 * 货主出场关联销售商品表操作service
 * @author Ailen
 *
 */
public interface SalToshopsDetailService {
	
	/**
	 * 插入一条货主销售商品数据
	 * @param salToshopsDetailEntity
	 * @return
	 */
	public int insert(SalToshopsDetailEntity salToshopsDetailEntity);
	
	/**
	 * 更新一条数据
	 * @param salToshopsDetailDTO
	 * @return
	 */
	public int update(SalToshopsDetailDTO salToshopsDetailDTO);
	
	/**
	 * 按照查询条件查询 MAP对应DTO字段
	 * @param params
	 * @return
	 */
	public List<SalToshopsDetailDTO> queryByCondition(Map<String, Object> params);
	
	/**
	 * 按照查询条件查询 MAP对应DTO字段 分页
	 * :startRow :endRow
	 * @param params
	 * @return
	 */
	public List<SalToshopsDetailDTO> queryByConditionPage(Map<String, Object> params);
	
	/**
	 * 删除根据ID
	 * @param stdId
	 * @return
	 */
	public int deleteById(Long stdId);

}
