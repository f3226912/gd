package com.gudeng.commerce.gd.api.service.appshare;

import com.gudeng.commerce.gd.customer.dto.AppshareDTO;


public interface AppshareToolService {
	
	/**
	 * 增加APP分享信息
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public int addAppShare(AppshareDTO inputDTO) throws Exception;
	
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
	
	
}
