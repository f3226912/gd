package com.gudeng.commerce.gd.admin.service.sysmgr;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.entity.SysMenu;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;

/**
 * 系统登录service接口;
 * @author wwj
 */
public interface SysLoginAdminService {

	/**
	 * 取得用户所有菜单;
	 * 
	 * @param userID
	 * @return List<菜单>;菜单map，二级菜单List
	 */
	public Object[] getUserAllMenu(String userID) throws Exception;

	/**
	 * 取得所有的菜单;
	 * 
	 * @return List<SysMenu>;
	 */
	public List<SysMenu> getAllMenu() throws Exception;

	/**
	 * 取得用户所有菜单按钮;
	 * 
	 * @param userID
	 * @return map<btnCode,'1'>;
	 */
	public Map<String, String> getUserAllMenuButton(String userID) throws Exception;


	/**
	 * 用户登录验证,并取得用户信息。
	 * 
	 * @param userCode
	 *            用户编号;
	 * @return SysRegisterUser 对象;
	 * @throws Exception
	 */
	public SysRegisterUser getLoginUserInfo(String userCode) throws Exception;

	/**
	 * 更新用户登录后信息;
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void updateLoginUser(SysRegisterUser user) throws Exception;
	
	/**
	 * 登录
	 * @author songhui
	 * @date 创建时间：2015年8月4日 下午4:45:20
	 * @param regUser
	 * @return
	 * @throws Exception
	 *
	 */
	public String processLogin(SysRegisterUser regUser)throws Exception;
}
