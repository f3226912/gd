package com.gudeng.commerce.gd.home.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.FriendsLinksDTO;

/**
 * 友情链接服务
 * @author gcwu
 *
 */
public interface FriendsLinksToolService {

	/**
	 * 查询审核通过的友情链接
	 * @return
	 */
	public List<FriendsLinksDTO> viewFriendsAll() throws Exception;
	/**
	 * 查询合作媒体链接
	 * @return
	 */
	public List<FriendsLinksDTO> viewMediaLinksAll() throws Exception;
	/**
	 * 新增申请友情链接
	 * @param friendsLinks
	 * @return
	 */
	public Integer applyFriendsUrl(FriendsLinksDTO friendsLinks)  throws Exception;

}
