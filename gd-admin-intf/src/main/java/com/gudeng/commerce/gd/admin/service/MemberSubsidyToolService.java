package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.MemberSubsidyDTO;

/**
 * @Description 会员补贴统计信息
 * @Project gd-admin-intf
 * @ClassName MemberSubsidyToolService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月9日 下午2:14:35
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public interface MemberSubsidyToolService {

	/**
	 * @Description queryList 根据条件查询会员统计信息列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月9日 下午2:14:31
	 * @Author lidong(dli@gdeng.cn)
	 */
	public List<MemberSubsidyDTO> queryList(Map<String, Object> map) throws Exception;

	/**
	 * @Description queryTotal 根据条件查询会员信息列表记录条数
	 * @param map
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月9日 下午2:15:10
	 * @Author lidong(dli@gdeng.cn)
	 */
	public int queryTotal(Map<String, Object> map) throws Exception;

	/**
	 * @Description queryStatistic 根据条件查询会员统计信息列表中的订单总数、补贴金额等总数统计信息
	 * @param map
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月9日 下午2:15:27
	 * @Author lidong(dli@gdeng.cn)
	 */
	public MemberSubsidyDTO queryStatistic(Map<String, Object> map) throws Exception;

}
