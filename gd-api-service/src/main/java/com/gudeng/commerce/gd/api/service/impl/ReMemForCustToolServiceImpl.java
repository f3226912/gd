package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.ReMemForCustToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ReCustInfoDTO;
import com.gudeng.commerce.gd.supplier.dto.ReMemForCustDTO;
import com.gudeng.commerce.gd.supplier.service.ReMemForCustService;

public class ReMemForCustToolServiceImpl implements ReMemForCustToolService{

	@Autowired
	private GdProperties gdProperties;
	
	private static ReMemForCustService reMemForCustService;

	
	protected ReMemForCustService getReMemForCustService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.reMemForCustService.url");
		if(reMemForCustService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reMemForCustService = (ReMemForCustService) factory.create(ReMemForCustService.class, url);
		}
		return reMemForCustService;
	}


	@Override
	public int addCustomerMember(ReMemForCustDTO custDTO) throws Exception {
		return getReMemForCustService().addCustomerMember(custDTO);
	}


	@Override
	public ReMemForCustDTO getCustomerById(Long id) throws Exception {
		return getReMemForCustService().getCustomerById(id);
	}


	@Override
	public List<ReMemForCustDTO> getCustomerByBusiId(Long busiMemberId,
			String type) throws Exception {
		return getReMemForCustService().getCustomerByBusiId(busiMemberId, type);
	}


	@Override
	public List<ReMemForCustDTO> queryCustomer(Map<String, Object> map)
			throws Exception {
		return getReMemForCustService().queryCustomer(map);
	}


	@Override
	public List<ReMemForCustDTO> queryCustomerPage(Map<String, Object> map)
			throws Exception {
		return getReMemForCustService().queryCustomer(map);
	}


	@Override
	public int addMobileOrAddressForCustomer(ReCustInfoDTO custInfoDTO)
			throws Exception {
		return getReMemForCustService().addMobileOrAddressForCustomer(custInfoDTO);
	}


	@Override
	public int updateReMemForCust(ReMemForCustDTO reMemForCustDTO)
			throws Exception {
		return getReMemForCustService().updateReMemForCust(reMemForCustDTO);
	}


	@Override
	public List<ProductCategoryDTO> queryCategoriesBybusinessId(Long businessId)
			throws Exception {
		return getReMemForCustService().queryCategoriesBybusinessId(businessId);
	}


	@Override
	public int addMobileOrAddressForCustomerMor(List<ReCustInfoDTO> custInfoDTOs)
			throws Exception {
		return getReMemForCustService().addMobileOrAddressForCustomerMor(custInfoDTOs);
	}


	@Override
	public Integer getTotal(Map<String, Object> map) throws Exception {
		return getReMemForCustService().getTotal(map);
	}


	
	
}
