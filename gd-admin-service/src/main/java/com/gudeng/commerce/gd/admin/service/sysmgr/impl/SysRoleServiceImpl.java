package com.gudeng.commerce.gd.admin.service.sysmgr.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRoleAdminService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRole;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysRoleService;

/**
 * 角色操作实现类;
 * 
 */
@Service
public class SysRoleServiceImpl implements SysRoleAdminService{

	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static  SysRoleService  sysRoleService;
	
	
	private SysRoleService hessianSysRoleService() throws MalformedURLException {
		String hessianUrl = gdProperties.getSysRoleServiceUrl();
		if(sysRoleService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sysRoleService = (SysRoleService) factory.create(SysRoleService.class, hessianUrl);
		}
		return sysRoleService;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> getAll(Map map) throws Exception{
		return hessianSysRoleService().getAll(map);
	}

	@Override
	public List<SysRole> getAll () throws Exception{
		return hessianSysRoleService().getAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> getByCondition(Map map)  throws Exception{
		return hessianSysRoleService().getByCondition(map);
	}
	
	@Override
	public int getTotal(Map<String,Object> map) throws Exception {
		return hessianSysRoleService().getTotal(map);
	}

	@Override
	public String insert(SysRole sysRole) throws Exception{
		return hessianSysRoleService().insert(sysRole);
	}

	@Override
	public String update(SysRole sysRole)throws Exception {
		return hessianSysRoleService().update(sysRole);
	}
	
	@Override
	public void delete(String roleIDs) throws Exception {
		hessianSysRoleService().delete(roleIDs);
	}

	@Override
	public SysRole getSysRoleById(String roleID) throws Exception{
		
		return hessianSysRoleService().getSysRoleById(roleID);
	}
	
}
