package com.gudeng.commerce.gd.admin.service.sysmgr.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysParamsAdminService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysParams;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysParamsService;
@Service
public class SysParamsServiceImpl implements SysParamsAdminService{
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static  SysParamsService  sysParamsService;
	
	
	private SysParamsService hessianSysParamsService() throws MalformedURLException {
		String hessianUrl = gdProperties.getSysParamsServiceUrl();
		if(sysParamsService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sysParamsService = (SysParamsService) factory.create(SysParamsService.class, hessianUrl);
		}
		return sysParamsService;
	}
	
	@Override
	public SysParams get(String sysParamsID)throws Exception{
		 return hessianSysParamsService().get(sysParamsID);
	}

	@Override
	public List<SysParams> getByCondition(Map<String, Object> map)throws Exception{
		return hessianSysParamsService().getByCondition(map);
	}

	@Override
	public String update(SysParams sysParams) throws Exception{
		return hessianSysParamsService().update(sysParams);
	}

	@Override
	public int getTotal()throws Exception {
		return hessianSysParamsService().getTotal();
	}

}
