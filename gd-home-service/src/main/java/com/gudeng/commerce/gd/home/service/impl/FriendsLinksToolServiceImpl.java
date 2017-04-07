package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.FriendsLinksDTO;
import com.gudeng.commerce.gd.customer.service.FriendsLinksService;
import com.gudeng.commerce.gd.home.service.FriendsLinksToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;
@Service
public class FriendsLinksToolServiceImpl implements FriendsLinksToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	public static FriendsLinksService friendsLinksService;

	protected FriendsLinksService getHessianFriendsLinksService()
			throws MalformedURLException {
		String url = gdProperties.getFriendsLinksUrl();
		if (friendsLinksService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			friendsLinksService = (FriendsLinksService) factory.create(
					FriendsLinksService.class, url);
		}
		return friendsLinksService;

	}
	@Override
	public List<FriendsLinksDTO> viewFriendsAll() throws Exception {
		return getHessianFriendsLinksService().viewFriendsAll();
	}
	@Override
	public List<FriendsLinksDTO> viewMediaLinksAll() throws Exception{
		return getHessianFriendsLinksService().viewMediaLinksAll();
	}
	@Override
	public Integer applyFriendsUrl(FriendsLinksDTO friendsLinks) throws Exception{
		return getHessianFriendsLinksService().applyFriendsUrl(friendsLinks);
	}

}
