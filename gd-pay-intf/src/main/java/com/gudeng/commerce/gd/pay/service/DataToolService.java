package com.gudeng.commerce.gd.pay.service;

import java.util.Date;

import com.gudeng.commerce.gd.report.dto.TimeCacheType;

public interface DataToolService {
	
	/**
	 * 清除缓存（交易类）
	 * @param memberId 会员ID
	 * @param timetype 枚举TimeCacheType
	 * @throws Exception
	 */
	public void cleanTradeCacheSpecial(Long memberId, TimeCacheType timetype) throws Exception;
	
	/**
	 * 清除过时缓存（交易类）
	 * @param memberId 会员ID
	 * @param timetype 枚举TimeCacheType
	 * @throws Exception
	 */
	public void cleanOldTradeCacheSpecial(Long memberId, Date oldTime) throws Exception;
	
	/**
	 * 清除缓存（商品类）
	 * @param memberId 会员ID
	 * @param timetype 枚举TimeCacheType
	 * @throws Exception
	 */
	public void cleanGoodsCacheSpecial(Long memberId, TimeCacheType timetype) throws Exception;
	
	/**
	 * 清除缓存（电话类）
	 * @param memberId 会员ID
	 * @param timetype 枚举TimeCacheType
	 * @throws Exception
	 */
	public void cleanPhoneCacheSpecial(Long memberId, TimeCacheType timetype) throws Exception;

}