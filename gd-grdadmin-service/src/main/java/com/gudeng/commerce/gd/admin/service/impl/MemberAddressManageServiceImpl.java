package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.MemberAddressManageService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstMemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.RecommendedUserDTO;
import com.gudeng.commerce.gd.customer.service.MemberAddressService;
import com.gudeng.commerce.gd.customer.service.NstSameCityAddressService;


@Service
public class MemberAddressManageServiceImpl implements  MemberAddressManageService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static MemberAddressService memberAddressService;
	
	private static NstSameCityAddressService  nstSameCityAddressService;
	 
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
	 * 同城货源接口服务
	 * @return
	 * @throws MalformedURLException
	 */
	protected NstSameCityAddressService getHessianSameCityAddressService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.sameCity.memberAddress.url");
		if(nstSameCityAddressService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstSameCityAddressService = (NstSameCityAddressService) factory.create(NstSameCityAddressService.class, url);
		}
		return nstSameCityAddressService;
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
	public int getTotalByName(Map<String, Object> map) throws Exception {
		 return getHessianMemberAddressService().getTotalByName(map);
	}

	@Override
	public Integer updateMemberAdressStatusByid(String memberAdressIds)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberAddressService().updateMemberAdressStatusByid(memberAdressIds);
	}

	@Override
	public List<RecommendedUserDTO> getListByAddress(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberAddressService().getListByAddress(map);
	}

	@Override
	public int getTotalByAddress(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberAddressService().getTotalByAddress(map);
	}
	
	@Override
	public List<NstMemberAddressDTO> getDistributeAddressList(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberAddressService().getDistributeAddressList(map);
	}

	@Override
	public int getDistributeAddressTotal(Map<String, Object> map)
			throws Exception {
		return getHessianMemberAddressService().getDistributeAddressTotal(map);
	}

	@Override
	public int getTotalForConsole(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianSameCityAddressService().getTotalForConsole(map);
	}

	@Override
	public List<NstSameCityAddressDTO> queryListForConsole(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianSameCityAddressService().queryListForConsole(map);
	}

	@Override
	public List<NstMemberAddressDTO> getDistributeSameCityAddressList(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianSameCityAddressService().getDistributeAddressList(map);
	}

	@Override
	public int getDistributeSameCityAddressTotal(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianSameCityAddressService().getDistributeAddressTotal(map);
	}

	@Override
	public List<RecommendedUserDTO> getListByAddressSameCity(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberAddressService().getListByAddressSameCity(map);
	}

	@Override
	public int getTotalByAddressSameCity(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberAddressService().getTotalByAddressSameCity(map);
	}

}
	
