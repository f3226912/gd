package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberIntegralConversionDTO;

/**
 * 功能描述：admin会员积分兑换礼物服务
 * @author xiaojun
 *
 */
public interface MemberIntegralConversionToolService {
	
	/**
	 * 通过传入map获取会员积分信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MemberIntegralConversionDTO> getMemberIntegralConversion(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据手机号码查询会员积分兑换信息
	 * @return mobile
	 * @throws Exception
	 */
	public MemberIntegralConversionDTO getByMobile(String mobile) throws Exception;
	
	/**
	 * 根据账号查询会员积分兑换信息
	 * @return account
	 * @throws Exception
	 */
	public MemberIntegralConversionDTO getByAccount(String account) throws Exception;
	
	
	
	/**
	 * 通过传入map获取礼物积分详情
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MemberIntegralConversionDTO> getGiftIntegralList(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 获取礼物总记录数
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
