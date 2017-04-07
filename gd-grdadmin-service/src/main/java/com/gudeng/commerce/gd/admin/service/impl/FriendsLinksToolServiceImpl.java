package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.FriendsLinksToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.FriendsLinksDTO;
import com.gudeng.commerce.gd.customer.service.FriendsLinksService;

/**
 * 功能描述：
 */
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
	public int getCount(Map<String, Object> map) throws Exception {
		return getHessianFriendsLinksService().getCount(map);
	}
	
	@Override
	public List<FriendsLinksDTO> getFriendsLinksList(Map<String, Object> map)
			throws Exception {
		return getHessianFriendsLinksService().getFriendsLinksList(map);
	}
	
	@Override
	public int add(FriendsLinksDTO friendsLinksDTO) throws Exception {
		return getHessianFriendsLinksService().add(friendsLinksDTO);
	}
	
	@Override
	public FriendsLinksDTO getById(String id) throws Exception {
		return getHessianFriendsLinksService().getById(id);
	}
	
	@Override
	public int update(FriendsLinksDTO friendsLinksDTO) throws Exception {
		return getHessianFriendsLinksService().update(friendsLinksDTO);
	}
	
	@Override
	public int view(FriendsLinksDTO friendsLinksDTO) throws Exception {
		return getHessianFriendsLinksService().view(friendsLinksDTO);
	}
	
	@Override
	public int delete(String id) throws Exception {
		return getHessianFriendsLinksService().delete(id);
	}
}
