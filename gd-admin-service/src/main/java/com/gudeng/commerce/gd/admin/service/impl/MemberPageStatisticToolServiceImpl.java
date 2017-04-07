package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.MemberPageStatisticToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.PageStatisMemberDTO;
import com.gudeng.commerce.gd.customer.service.statis.MemberPageStatisticService;
import com.gudeng.commerce.gd.customer.service.statis.PvStatisticBusinessService;

/**
 * 功能描述：
 */
@Service
public class MemberPageStatisticToolServiceImpl implements MemberPageStatisticToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static MemberPageStatisticService memberPageStatisticService;

	/**
	 * 功能描述: memberChangeLogService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MemberPageStatisticService getHessianMemberPageStatisticService() throws MalformedURLException {
		String url = gdProperties.getMemberPageStatisticUrl();
		if (memberPageStatisticService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberPageStatisticService = (MemberPageStatisticService) factory.create(MemberPageStatisticService.class, url);
		}
		return memberPageStatisticService;
	}
	
	@Override
	public int getTotal(Map map) throws Exception{
		return getHessianMemberPageStatisticService().getTotal(map);
	}
	
	@Override
	public List<PageStatisMemberDTO> getBySearch(Map map) throws Exception{
		return getHessianMemberPageStatisticService().getBySearch(map);
	}
}
