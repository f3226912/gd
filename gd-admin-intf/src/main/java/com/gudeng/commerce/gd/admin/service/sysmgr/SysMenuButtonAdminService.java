package com.gudeng.commerce.gd.admin.service.sysmgr;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.entity.SysMenuButton;

/**
 * 菜单按钮接口;
 * 
 * @author wwj
 * 
 */
public interface SysMenuButtonAdminService {
	
	public SysMenuButton getSysButtonByID(String btnId) throws Exception;
	
	/**
	 * 查询记录总数
	 * @param map
	 * @return
	 */
	public int getTotal(Map<String,Object> map) throws Exception;
	
	/**
	 * 查询分页数据列表
	 * @param map
	 * @return
	 */
	public List<SysMenuButton> getByCondition(Map<String,Object> map) throws Exception;
	
	/**
	 * 新增按钮
	 * @param sysMenuButton
	 * @return
	 */
	public String insert(SysMenuButton sysMenuButton) throws Exception;
	
	/**
	 * 修改按钮
	 * @param sysMenuButton
	 * @return
	 */
	public String update(SysMenuButton sysMenuButton) throws Exception;
	
	/**
	 * 删除按钮
	 * @param sysMenuButton
	 */
	public String delete(List<String> buttonList) throws Exception;
}
