package com.gudeng.commerce.gd.authority.sysmgr.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRole;

/**   
 * @Description 系统角色接口
 * @Project gd-auth-intf
 * @ClassName SysRoleService.java
 * @Author lidong(dli@cnagri-products.com)    
 * @CreationDate 2015年10月17日 下午2:42:55
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public interface SysRoleService{
	
	@SuppressWarnings("unchecked")
	public List<SysRole> getAll(Map map) throws Exception;
	
	public List<SysRole> getAll();
	
	@SuppressWarnings("unchecked")
	public List<SysRole> getByCondition(Map map);
	
	public int getTotal(Map<String,Object> map);
	
	public String insert(SysRole sysRole);
	
	public String update(SysRole sysRole)throws Exception;
	
	public void delete(String roleIDs) throws Exception;
	
	/**
	 * 根据角色ID获取角色信息
	 * @param roleID
	 * @return
	 */
	public SysRole getSysRoleById(String roleID) throws Exception;
}
