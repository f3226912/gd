package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.service.MemberAddressService;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;
import com.gudeng.commerce.gd.home.service.MemberAddressManageService;
import com.gudeng.commerce.gd.home.util.GdProperties;


@Service
public class MemberAddressManageServiceImpl implements  MemberAddressManageService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static MemberAddressService memberAddressService;
	
	
	private static MemberBaseinfoService memberBaseinfoService;

	
	/**
	 * 功能描述:收发货管理接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MemberAddressService getHessianMemberAddressService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.memberAddress.url");
		if(memberAddressService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberAddressService = (MemberAddressService) factory.create(MemberAddressService.class, url);
		}
		return memberAddressService;
	}
	
	

	/**
	 * 功能描述: memberBaseinfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MemberBaseinfoService getHessianMemberBaseinfoService() throws MalformedURLException {
		String url =gdProperties.getProperties().getProperty("gd.memberAddress.url");
		if(memberBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberBaseinfoService = (MemberBaseinfoService) factory.create(MemberBaseinfoService.class, url);
		}
		return memberBaseinfoService;
	}
	
	@Override
	public MemberAddressDTO getById(String id) throws Exception {
		return getHessianMemberAddressService().getById(id);
	}

	@Override
	public List<MemberAddressDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		return  getHessianMemberAddressService().getByCondition(map);
	}

	@Override
	public List<MemberAddressDTO> getByName(Map<String, Object> map) throws Exception {
		return   getHessianMemberAddressService().getByName(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianMemberAddressService().getTotal(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		 return getHessianMemberAddressService().deleteById(id);
	}

	@Override
	public int addMemberAddressDTO(Map<String,Object> map)throws Exception{
		 return getHessianMemberAddressService().addMemberAddressDTO(map);
	}
	
	@Override
	public int addMemberAddressDTO(MemberAddressDTO memberAddress) throws Exception {
		 return getHessianMemberAddressService().addMemberAddressDTO(memberAddress);
	}
	
	@Override
	public int updateMemberAddressDTO(MemberAddressDTO memberAddress) throws Exception {
		 return getHessianMemberAddressService().updateMemberAddressDTO(memberAddress);
	}



	@Override
	public MemberBaseinfoDTO getByMobile(String mobile) throws Exception {
		return getHessianMemberBaseinfoService().getByMobile(mobile);
	}



	@Override
	public List<MemberAddressDTO> getListByUserId(Map<String, Object> map)
			throws Exception {
		return getHessianMemberAddressService().getListByUserId(map);
	}


	@Override
	public int getTotalByUserId(Map<String, Object> map) throws Exception {
		return getHessianMemberAddressService().getTotalByUserId(map);
	}
}
	
