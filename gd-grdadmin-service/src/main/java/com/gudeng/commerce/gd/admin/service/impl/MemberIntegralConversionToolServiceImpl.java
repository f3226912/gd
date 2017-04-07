package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.MemberIntegralConversionToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberIntegralConversionDTO;
import com.gudeng.commerce.gd.customer.service.MemberIntegralConversionService;


public class MemberIntegralConversionToolServiceImpl implements MemberIntegralConversionToolService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	
	private static MemberIntegralConversionService memberIntegralConversionService;
	
	/**
	 * 功能描述: MemberIntegralConversionService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MemberIntegralConversionService getHessianMemberIntegralConversionService() throws MalformedURLException {
		String url = gdProperties.getMemberIntegralConversionServiceUrl();
		if(memberIntegralConversionService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberIntegralConversionService = (MemberIntegralConversionService) factory.create(MemberIntegralConversionService.class, url);
		}
		return memberIntegralConversionService;
	}
	
	
	@Override
	public List<MemberIntegralConversionDTO> getMemberIntegralConversion(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberIntegralConversionService().getMemberIntegralConversion(map);
	}


	@Override
	public Integer getTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberIntegralConversionService().getTotal(map);
	}


	@Override
	public MemberIntegralConversionDTO getByMobile(String mobile)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberIntegralConversionService().getByMobile(mobile);
	}


	@Override
	public MemberIntegralConversionDTO getByAccount(String account)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberIntegralConversionService().getByAccount(account);
	}


	@Override
	public List<MemberIntegralConversionDTO> getGiftIntegralList(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberIntegralConversionService().getGiftIntegralList(map);
	}


	@Override
	public Integer getGiftTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberIntegralConversionService().getGiftTotal(map);
	}


	@Override
	public int updateMemberIntegral(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberIntegralConversionService().updateMemberIntegral(map);
	}


	@Override
	public int insertIntegral(Map<String, Object> map) throws Exception{
		// TODO Auto-generated method stub
		return getHessianMemberIntegralConversionService().insertIntegral(map);
	}

}
