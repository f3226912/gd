package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.ReCategoryBanelImgDTO;
import com.gudeng.commerce.gd.customer.service.ReCategoryBanelImgService;
import com.gudeng.commerce.gd.home.service.ReCategoryBanelImgToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;


/** 
 *功能描述：
 */
@Service
public class ReCategoryBanelImgToolServiceImpl implements ReCategoryBanelImgToolService{
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static ReCategoryBanelImgService reCategoryBanelImgService;

	/**
	 * 功能描述:businessBaseinfoService接口服务
	 * 
	 * @param
	 * @return
	 */
	protected ReCategoryBanelImgService getHessianReCategoryBanelImgService() throws MalformedURLException {
		String url = gdProperties.getReCategoryBanelImgServiceUrl();
		if(reCategoryBanelImgService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reCategoryBanelImgService = (ReCategoryBanelImgService) factory.create(ReCategoryBanelImgService.class, url);
		}
		return reCategoryBanelImgService;
	}

	@Override
	public List<ReCategoryBanelImgDTO> getByCategoryId(Long categoryId)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianReCategoryBanelImgService().getByCategoryId(categoryId);
	}

	@Override
	public int getCountByCategoryId(Long categoryId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianReCategoryBanelImgService().getCountByCategoryId(categoryId);
	}

	@Override
	public ReCategoryBanelImgDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getHessianReCategoryBanelImgService().getById(id);
	}

//	@Override
//	public int getCountByGroupId(Long grouId) throws Exception {
//		// TODO Auto-generated method stub
//		return getHessianReCategoryBanelImgService().getCountByGroupId(grouId);
//	}

	@Override
	public int getCount(Map map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianReCategoryBanelImgService().getCount(map);
	}

	@Override
	public List<ReCategoryBanelImgDTO> getAllByGroupId(Long groupId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianReCategoryBanelImgService().getAllByGroupId(groupId);
	}
	@Override
	public List<ReCategoryBanelImgDTO> getByAll() throws Exception {
		// TODO Auto-generated method stub
		return getHessianReCategoryBanelImgService().getByAll();
	}
	
	@Override
	public List<ReCategoryBanelImgDTO> getAllGroups() throws Exception {
		// TODO Auto-generated method stub
		return getHessianReCategoryBanelImgService().getAllGroups();
	}

	@Override
	public List<ReCategoryBanelImgDTO> getByAllByPage(Map map) throws Exception {
		// TODO Auto-generated method stub
		return getHessianReCategoryBanelImgService().getByAllByPage(map);
	}
	 
	 
}
