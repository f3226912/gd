package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.FriendsLinksDTO;

public interface FriendsLinksToolService {
	/**
	 * 友情链接管理记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getCount(Map<String,Object> map) throws Exception;
	
	/**
	 * 友情链接管理list(分页查询)
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<FriendsLinksDTO> getFriendsLinksList(Map<String, Object> map) throws Exception;
	
	public int add(FriendsLinksDTO friendsLinksDTO) throws Exception;
	
	public FriendsLinksDTO getById(String id) throws Exception;
	
	public int update(FriendsLinksDTO friendsLinksDTO) throws Exception;
	
	public int view(FriendsLinksDTO friendsLinksDTO) throws Exception;
	
	public int delete(String id) throws Exception;
}
