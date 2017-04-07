package com.gudeng.commerce.gd.api.service;

import com.gudeng.commerce.gd.customer.entity.PageStatisMemberEntity;

/**
 * 金牌会员统计业务
 * @author Ailen
 *
 */
public interface MemberPageStatisticToolService {
	
	/**
	 * 添加金牌会员页面统计数据
	 * @param pageStatisMemberEntity
	 */
	public void addMemberPage(PageStatisMemberEntity pageStatisMemberEntity) throws Exception;

}
