package com.gudeng.commerce.gd.admin.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.GiftDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.GiftEntity;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;

/**
 *功能描述：实现类
 *
 */
public interface GiftToolService {
	/**
	 * 根据map 集合，统计个数
	 * 
	 *  
	 * @return int
	 * 
	 */
	public int getTotal(Map map)throws Exception;
	
	public int getCountByName(Map map)throws Exception;
	
	public int getCountByType(Map map)throws Exception;
	
	/**
	 * 查询
	 * 
	 * @return list
	 * 
	 */
	public List<GiftDTO> getBySearch(Map map) throws Exception;
	
	public int add(GiftEntity giftEntity) throws Exception ;
	
	/**
	 * 根据ID查询Gift对象
	 * 
	 * @param id
	 * @return GiftDTO
	 * 
	 */
	public GiftDTO getById(String id) throws Exception;
	
	/**
	 * 通过Gift对象更新数据库
	 * 
	 * @param GiftDTO
	 * @return int
	 * 
	 */
	public int update(GiftDTO giftDTO) throws Exception;
}