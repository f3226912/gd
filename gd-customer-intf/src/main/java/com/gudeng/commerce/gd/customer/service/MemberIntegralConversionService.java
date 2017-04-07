package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberIntegralConversionDTO;
/**
 * 会员积分兑换礼物服务
 * @author xiaojun
 */
public interface MemberIntegralConversionService {
	
	/**
	 * 通过map查询出会员详情
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MemberIntegralConversionDTO> getMemberIntegralConversion(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询出总的记录数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据电话号码查询出会员兑换信息
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public MemberIntegralConversionDTO getByMobile(String mobile) throws Exception;
	
	
	/**
	 * 根据账号查询出会员兑换信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public MemberIntegralConversionDTO getByAccount(String account) throws Exception;
	
	
	/**
	 * 通过map礼物积分
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MemberIntegralConversionDTO> getGiftIntegralList(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询出礼物总的记录数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getGiftTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据memberId更新会员的积分
	 * @param map
	 */
	public int updateMemberIntegral(Map<String, Object> map) throws Exception;
	
	/**
	 * 插入到积分流水表中
	 * @param map
	 */
	public int insertIntegral(Map<String, Object> map) throws Exception;
	
}
