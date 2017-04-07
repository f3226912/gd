package com.gudeng.commerce.gd.admin.service.sysmgr;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.entity.SysMenu;

/**
 * 系统菜单接口;
 * 
 * @author wwj
 * 
 */
public interface SysMenuAdminService {
	
	public List<SysMenu> getAll(Map<String,Object> map) throws Exception;
	
	/**
	 * 检查此菜单是否已经分配给对应的角色用
	 */
	public List<SysMenu> getAllMenuRole(Map<String,Object> map) throws Exception;
	    
	public List<SysMenu> getByCondition(Map<String,Object> map) throws Exception;

	public int getTotal(Map<String,Object> map) throws Exception;
	
	public SysMenu getSysMenu(String menuCode) throws Exception;
	
	public SysMenu getSysMenuByID(String menuId) throws Exception;
	
	/**
	 * 新增菜单
	 * @param sysMenu
	 * @return
	 */
	public String insert(SysMenu sysMenu) throws Exception;
	
	/**
	 * 修改菜单
	 * @param sysMenu
	 * @return
	 */
	public String update(SysMenu sysMenu) throws Exception;
	
	/**
	 * 删除
	 * @param menuID
	 * @throws Exception 
	 */
	public String delete(String menuID) throws Exception;

	/**
	 * 查询一级菜单
	 * @author songhui
	 * @date 创建时间：2015年7月24日 下午2:58:09
	 * @param map
	 * @return
	 *
	 */
	public List<SysMenu> getFirstMenu(Map<String,Object> map) throws Exception;
	
	/**
	 * @Description getSecondMenu 根据父菜单ID查询二级菜单，
	 * @param map
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月17日 上午10:42:15
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public List<SysMenu> getSecondMenu(Map<String,Object> map) throws Exception;
	 /**
     * @Description 获取父级菜单
     * @param map
     * @return
	 * @throws Exception 
     * @CreationDate 2016年1月12日 下午12:20:55
     * @Author lidong(dli@gdeng.cn)
    */
    public SysMenu getParentMenu(Map<String, Object> map) throws Exception;
	/**
	 * @Description checkMenuHasChildren 检查菜单下是否有下级菜单
	 * @param map
	 * @return
	 * @CreationDate 2015年11月17日 上午11:47:15
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public int checkMenuHasChildren(Map<String, Object> map) throws Exception; 
}