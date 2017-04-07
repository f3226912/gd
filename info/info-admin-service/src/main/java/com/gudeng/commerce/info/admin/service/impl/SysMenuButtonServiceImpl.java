package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.SysMenuButtonAdminService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.entity.SysMenuButton;
import com.gudeng.commerce.info.customer.service.SysMenuButtonService;

/**
 * 按钮操作实现类;
 * 
 */
@Service
public class SysMenuButtonServiceImpl implements SysMenuButtonAdminService {
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static SysMenuButtonService sysMenuButtonService;
	
	
	private SysMenuButtonService hessianSysMenuButtonService() throws MalformedURLException {
		String hessianUrl = gdProperties.getSysMenuButtonServiceUrl();
		if(sysMenuButtonService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sysMenuButtonService = (SysMenuButtonService) factory.create(SysMenuButtonService.class, hessianUrl);
		}
		return sysMenuButtonService;
	}

	@Override
	public SysMenuButton getSysButtonByID(String btnId) throws Exception{
		return hessianSysMenuButtonService().getSysButtonByID(btnId);
	}
	
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return hessianSysMenuButtonService().getTotal(map);
	}

	@Override
	public List<SysMenuButton> getByCondition(Map<String, Object> map) throws Exception {
		return hessianSysMenuButtonService().getByCondition(map);
	}

	@Override
	public String insert(SysMenuButton sysMenuButton) throws Exception {
		return hessianSysMenuButtonService().insert(sysMenuButton);
	}

	@Override
	public String update(SysMenuButton sysMenuButton) throws Exception {
		return hessianSysMenuButtonService().update(sysMenuButton);
	}
	
	@Override
	public String delete(List<String> buttonList) throws Exception {
		return hessianSysMenuButtonService().delete(buttonList);
	}
}
