package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.BusinessBaseinfoService;


/** 
 *功能描述：
 */
@Service
public class BusinessBaseinfoToolServiceImpl implements BusinessBaseinfoToolService{ 
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static BusinessBaseinfoService businessBaseinfoService;

	/**
	 * 功能描述: businessBaseinfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected BusinessBaseinfoService getHessianBusinessBaseinfoService() throws MalformedURLException {
		String url = gdProperties.getBusinessBaseinfoUrl();
		if(businessBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			businessBaseinfoService = (BusinessBaseinfoService) factory.create(BusinessBaseinfoService.class, url);
		}
		return businessBaseinfoService;
	}

	@Override
	public int addBusinessBaseinfoDTO(BusinessBaseinfoDTO mb) throws Exception {
		return getHessianBusinessBaseinfoService().addBusinessBaseinfoDTO(mb);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianBusinessBaseinfoService().deleteById(id);
	}

	@Override
	public int updateBusinessBaseinfoDTO(BusinessBaseinfoDTO mb) throws Exception {
		return getHessianBusinessBaseinfoService().updateBusinessBaseinfoDTO(mb);
	}

	@Override
	public BusinessBaseinfoDTO getById(String id) throws Exception {
		return getHessianBusinessBaseinfoService().getById(id);
	}
	
	@Override
	public BusinessBaseinfoDTO getByUserId(String userId) throws Exception {
		return getHessianBusinessBaseinfoService().getByUserId(userId);
	}

	@Override
	public List<BusinessBaseinfoDTO> getBySearch(Map map) throws Exception {
		return getHessianBusinessBaseinfoService().getBySearch(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianBusinessBaseinfoService().getTotal(map);
	}

	@Override
	public int addBusinessBaseinfoByMap(Map<String, Object> map)
			throws Exception {
		return getHessianBusinessBaseinfoService().addBusinessBaseinfoByMap(map);
	}

	@Override
	public Long addBusinessBaseinfoEnt(BusinessBaseinfoEntity be)
			throws Exception {
		return getHessianBusinessBaseinfoService().addBusinessBaseinfoEnt(be);
	}

	@Override
	public BusinessBaseinfoDTO getByBusinessNo(String businessNo) throws Exception {
		return getHessianBusinessBaseinfoService().getByBusinessNo(businessNo);
	}
	
	
	@Override
	public BusinessBaseinfoDTO getByPosNumber(String posNumber) throws Exception {
		return getHessianBusinessBaseinfoService().getByPosNumber(posNumber);
	}
	
	 
}
