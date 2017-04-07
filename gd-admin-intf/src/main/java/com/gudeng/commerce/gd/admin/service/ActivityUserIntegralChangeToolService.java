package com.gudeng.commerce.gd.admin.service;

import com.gudeng.commerce.gd.customer.dto.ActivityUserIntegralChangeDTO;

/**
 * 现场采销活动用户积分变更服务类
 * 
 * @author jiangyanli
 *
 */
public interface ActivityUserIntegralChangeToolService extends BaseToolService<ActivityUserIntegralChangeDTO> 
{
	/**
	 * 新增活动用户积分变更记录
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int insert(ActivityUserIntegralChangeDTO entity) throws Exception;
	
}