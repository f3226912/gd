package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.service.CarLineService;
import com.gudeng.commerce.gd.customer.service.MemberAddressService;
import com.gudeng.commerce.gd.home.service.QueryAreaToolService;
import com.gudeng.commerce.gd.home.service.TransportationService;
import com.gudeng.commerce.gd.home.util.GdProperties;

@Service
public class TransportationServiceImpl implements  TransportationService{

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static CarLineService carLineService;
	
	private static MemberAddressService memberAddressService;
	
	private static QueryAreaToolService QueryAreaToolService;
	
	/**
	 * 功能描述:线路管理接口服务
	 * 
	 * @param
	 * @return
	 */
	protected CarLineService getHessianCarLineService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.carLine.url");
		if(carLineService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			carLineService = (CarLineService) factory.create(CarLineService.class, url);
		}
		return carLineService;
	}
	
	
	
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
	 * 功能描述:接口服务
	 * 
	 * @param
	 * @return
	 */
	protected QueryAreaToolService getHessianQueryAreaToolService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.area.url");
		if(QueryAreaToolService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			QueryAreaToolService = (QueryAreaToolService) factory.create(QueryAreaToolService.class, url);
		}
		return QueryAreaToolService;
	}

	/**
	 * 线路查询
	 */
	@Override
	public List<CarLineDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		return  getHessianCarLineService().getByCondition(map);
	}

	/**
	 * 线路总数查询
	 */
	@Override
	public int getCarLineTotal(Map<String, Object> map) throws Exception {
		return getHessianCarLineService().getTotal(map);
	}

	
	/**
	 * 货源查询
	 */
	@Override
	public List<MemberAddressDTO> getGoodsListByCondition(Map<String, Object> map)
			throws Exception {
		return  getHessianMemberAddressService().getByCondition(map);
	}
	
	@Override
	public List<MemberAddressDTO> getGoodsListCompanyMobile(Map<String, Object> map)
			throws Exception {
		return  getHessianMemberAddressService().getGoodsListCompanyMobile(map);
	}
	
	
	/**
	 * 货源总数查询
	 */
	@Override
	public int getGoodsTotal(Map<String, Object> map) throws Exception {
		return getHessianMemberAddressService().getTotal(map);
	}

	
	@Override
	public AreaDTO getArea(String areaID) throws Exception {
		 return getHessianQueryAreaToolService().getArea(areaID);
	}
	
	
	@Override
	public List<CarLineDTO> getListByAreaId(Map<String, Object> map)
			throws Exception {
		return  getHessianCarLineService().getListByAreaId(map);
	}
	
	
	@Override
	public List<MemberAddressDTO> getGoodsListByAreaId(Map<String, Object> map)
			throws Exception {
		return  getHessianMemberAddressService().getGoodsListByAreaId(map);
	}
	
	
}
	
