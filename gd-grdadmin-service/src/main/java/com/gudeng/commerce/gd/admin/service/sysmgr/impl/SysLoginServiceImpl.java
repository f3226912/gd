package com.gudeng.commerce.gd.admin.service.sysmgr.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysLoginAdminService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysMenu;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysLoginService;

/**
 * 取得登录用户的全部信息serivce实现类;
 * 
 */
@Service
public class SysLoginServiceImpl implements SysLoginAdminService {

	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static SysLoginService sysLoginService;
	
	
	private SysLoginService hessianSysLoginService() throws MalformedURLException {
		String hessianUrl = gdProperties.getSysLoginServiceUrl();
		if(sysLoginService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sysLoginService = (SysLoginService) factory.create(SysLoginService.class, hessianUrl);
		}
		return sysLoginService;
	}
	
	@Override
	public Object[] getUserAllMenu(String userID) throws Exception {
		return hessianSysLoginService().getUserAllMenu(userID);
	}

	@Override
	public Map<String, String> getUserAllMenuButton(String userID) throws Exception  {

		return hessianSysLoginService().getUserAllMenuButton(userID);
	}

	@Override
	public List<SysMenu> getAllMenu() throws Exception  {
		return hessianSysLoginService().getAllMenu();
	}

	@Override
	public SysRegisterUser getLoginUserInfo(String userCode) throws Exception {
		return hessianSysLoginService().getLoginUserInfo(userCode);
	}

	@Override
	public void updateLoginUser(SysRegisterUser user) throws Exception {
		hessianSysLoginService().updateLoginUser(user);
	}

	@Override
	public String processLogin(SysRegisterUser regUser) throws Exception {
		return hessianSysLoginService().processLogin(regUser);
	}

}
