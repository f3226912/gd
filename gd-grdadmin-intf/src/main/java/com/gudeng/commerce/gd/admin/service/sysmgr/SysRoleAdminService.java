package com.gudeng.commerce.gd.admin.service.sysmgr;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRole;


/**
 * 系统角色接口;
 * @author wwj
 * 
 */
public interface SysRoleAdminService{
	
	@SuppressWarnings("unchecked")
	public List<SysRole> getAll(Map map) throws Exception;
	
	public List<SysRole> getAll() throws Exception;
	
	@SuppressWarnings("unchecked")
	public List<SysRole> getByCondition(Map map) throws Exception;
	
	public int getTotal(Map<String,Object> map) throws Exception;
	
	public String insert(SysRole sysRole) throws Exception;
	
	public String update(SysRole sysRole)throws Exception;
	
	public void delete(String roleIDs) throws Exception;
	
	/**
	 * 根据角色ID获取角色信息
	 * @param roleID
	 * @return
	 */
	public SysRole getSysRoleById(String roleID) throws Exception;
}
