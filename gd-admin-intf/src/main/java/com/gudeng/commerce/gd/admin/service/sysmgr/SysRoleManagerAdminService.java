package com.gudeng.commerce.gd.admin.service.sysmgr;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.entity.SysMenu;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRightBtn;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRoleManager;

/**
 * 角色权限分配service
 * 
 * @author wwj
 * @version 1.0
 */
public interface SysRoleManagerAdminService {

	/**
	 * 得到所有的按钮和菜单（返回值有一个用户是否拥有此菜单和按钮功能的标志）
	 * 
	 * @return
	 */
	public List<SysRoleManager> get(Map<String, Object> map) throws Exception;

	/**
	 * 修改用户所拥有的按钮功能（根据rmID先删除相关数据，然后再添加数据到数据库中）
	 * 
	 * @param trCheckBox
	 * @param roleID
	 * @param createrID
	 * @return
	 * @throws Exception
	 */
	public String updateBtn(List<String> menuButtonList, String roleID, String createrID) throws Exception;

	/**
	 * @Description update 批量添加角色菜单
	 * @param menuList
	 * @param roleID
	 * @param createrID
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年10月19日 下午3:57:25
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public String update(List<String> menuList, String roleID, String createrID) throws Exception;

	/**
	 * @Description getButtonsByRole 根据角色查询按钮
	 * @param roleID
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月24日 上午10:41:05
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<SysRightBtn> getButtonsByRole(String roleID) throws Exception;
}
