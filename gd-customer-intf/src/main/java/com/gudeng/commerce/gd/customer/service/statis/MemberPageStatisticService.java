package com.gudeng.commerce.gd.customer.service.statis;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PageStatisMemberDTO;
import com.gudeng.commerce.gd.customer.entity.PageStatisMemberEntity;

/**
 * 金牌会员统计业务
 * @author Ailen
 *
 */
public interface MemberPageStatisticService {
	
	/**
	 * 添加金牌会员页面统计数据
	 * @param pageStatisMemberEntity
	 */
	public void addMemberPage(PageStatisMemberEntity pageStatisMemberEntity) throws Exception;
	
	/**
	 * 根据map 集合，统计个数
	 *
	 *
	 * @return int
	 *
	 */
	public int getTotal(Map map) throws Exception;
	
	/**
	 * 根据多条件查询
	 *
	 * @return list
	 *
	 */
	public List<PageStatisMemberDTO> getBySearch(Map map) throws Exception;

}
