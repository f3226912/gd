package com.gudeng.commerce.gd.m.service;

import com.gudeng.commerce.gd.customer.dto.AppshareDTO;


public interface AppshareToolService {
	
	
	/**
	 * 查询APP分享信息
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public AppshareDTO getAppShareByCondition(AppshareDTO inputDTO) throws Exception;
	
	/**
	 * 查询share_visitorip表数据
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public int getVisitorIpCount(AppshareDTO inputDTO) throws Exception;
	
	/**
	 * 更新分享表中访问次数
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public int updateAppShareViewCount(AppshareDTO inputDTO) throws Exception;
	
	/**
	 * 更新分享表中访问次数和新增ip数据
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public int updateAppShareAndVisitorIp(AppshareDTO inputDTO) throws Exception;
	
}
