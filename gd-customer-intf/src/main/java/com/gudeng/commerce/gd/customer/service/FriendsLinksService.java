package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.FriendsLinksDTO;

public interface FriendsLinksService {	
	public int getCount(Map<String, Object> map);
	
	public List<FriendsLinksDTO> getFriendsLinksList(Map<String, Object> map) throws Exception;
	
	public int add(FriendsLinksDTO friendsLinksDTO) throws Exception;
	
	public FriendsLinksDTO getById(String id) throws Exception;
	
	public int update(FriendsLinksDTO friendsLinksDTO) throws Exception;
	
	public int view(FriendsLinksDTO friendsLinksDTO) throws Exception;
	
	public int delete(String id);
	
	public List<FriendsLinksDTO> viewFriendsAll() throws Exception;
	
	public List<FriendsLinksDTO> viewMediaLinksAll() throws Exception;
	
	public Integer applyFriendsUrl(FriendsLinksDTO friendsLinks)  throws Exception;
}
