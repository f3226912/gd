package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.RefCateSupNpsToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.supplier.dto.RefCateSupNpsDTO;
import com.gudeng.commerce.gd.supplier.service.RefCateSupNpsService;


/** 
 *功能描述：
 */
@Service
public class RefCateSupNpsToolServiceImpl implements RefCateSupNpsToolService{ 
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static RefCateSupNpsService refCateSupNpsService;

	/**
	 * 功能描述: businessBaseinfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected RefCateSupNpsService getHessianRefCateSupNpsServiceService() throws MalformedURLException {
		String url = gdProperties.getRefCateSupNpsServiceUrl();
		if(refCateSupNpsService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			refCateSupNpsService = (RefCateSupNpsService) factory.create(RefCateSupNpsService.class, url);
		}
		return refCateSupNpsService;
	}

	@Override
	public List<RefCateSupNpsDTO> getRefCateSupNpsByNpsCateId(Long cateId,String type) throws Exception {
		return getHessianRefCateSupNpsServiceService().getRefCateSupNpsByNpsCateId(cateId,type);
	}

	@Override
	public void updateRefCateSupNps(List<RefCateSupNpsDTO> refs) throws Exception {
		getHessianRefCateSupNpsServiceService().updateRefCateSupNps(refs);
	}
}
