package com.gudeng.commerce.gd.api.service.impl.sinxin;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.sinxin.MemberInfoToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberSinxinDTO;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;

/**
 * 功能描述：
 */
@Service
public class MemberInfoToolServiceImpl implements MemberInfoToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static MemberBaseinfoService memberBaseinfoService;

	protected MemberBaseinfoService getHessianMemberBaseinfoService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.memberBaseinfoService.url");
		if (memberBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberBaseinfoService = (MemberBaseinfoService) factory.create(MemberBaseinfoService.class, url);
		}
		return memberBaseinfoService;
	}
	
	@Override
	public List<MemberSinxinDTO> queryMember(MemberSinxinDTO queryDTO) throws Exception {
		return getHessianMemberBaseinfoService().queryMemberForSinxin(queryDTO);
	}
	
}
