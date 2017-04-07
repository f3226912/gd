package com.gudeng.commerce.gd.customer.service;


import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AppshareDTO;

public interface AppshareService {
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
	
	/**
	 * 更新分享表中访问次数
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public int updateAppShareViewCount(AppshareDTO inputDTO) throws Exception;
	
	/**
	 * 新增一条share_visitorip数据
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public int addVisitorIpInfo(AppshareDTO inputDTO) throws Exception;
	
	/**
	 * 更新分享表中访问次数和新增ip数据
	 * @param inputDTO
	 * @return
	 * @throws Exception
	 */
	public int updateAppShareAndVisitorIp(AppshareDTO inputDTO) throws Exception;
	
	public int getTotalCountByCondtion(Map<String, Object> map) throws Exception;
	
	public List<AppshareDTO> queryListByCondition(Map<String, Object> map) throws Exception;
	
	public int updateStatus(AppshareDTO dto) throws Exception;
	
	public List<AppshareDTO> queryPageByCondition(Map<String, Object> map)throws Exception;
	
	public List<AppshareDTO> queryDetailPageByCondition(Map<String, Object> map)throws Exception;
	
	public int getDetailTotalCountByCondtion(Map<String, Object> map) throws Exception;
}
