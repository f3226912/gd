package com.gudeng.commerce.gd.admin.service.sysmgr.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRoleManagerAdminService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysMenu;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRightBtn;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRoleManager;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysRoleManagerService;

/**
 * 用户菜单按钮关系管理service实现类
 *
 */
@Service
public class SysRoleManagerServiceImpl implements SysRoleManagerAdminService {

	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;

	private static SysRoleManagerService sysRoleManagerService;

	private SysRoleManagerService hessianSysRoleManagerService() throws MalformedURLException {
		String hessianUrl = gdProperties.getSysRoleManagerServiceUrl();
		if (sysRoleManagerService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sysRoleManagerService = (SysRoleManagerService) factory.create(SysRoleManagerService.class, hessianUrl);
		}
		return sysRoleManagerService;
	}

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
	public String update(List<String> menuList, String roleID, String createrID) throws Exception {
		List<String> menus = new ArrayList<>();
		if (menuList != null && menuList.size() > 0) {
			for (String button : menuList) {
				if (StringUtils.isNotEmpty(button)) {
					menus.add(button);
				}
			}
		}
		return hessianSysRoleManagerService().update(menus, roleID, createrID);
	}

	public List<SysRoleManager> get(Map<String, Object> map) throws Exception {

		return hessianSysRoleManagerService().get(map);
	}

	@Override
	public String updateBtn(List<String> menuButtonList, String roleID, String createrID) throws Exception {
		List<String> buttons = new ArrayList<>();
		if (menuButtonList != null && menuButtonList.size() > 0) {
			for (String button : menuButtonList) {
				if (StringUtils.isNotEmpty(button)) {
					buttons.add(button);
				}
			}
		}
		return hessianSysRoleManagerService().updateBtn(buttons, roleID, createrID);
	}

	/**
	 * @Description getButtonsByRole 根据角色查询按钮
	 * @param roleID
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月24日 上午10:41:05
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<SysRightBtn> getButtonsByRole(String roleID) throws Exception {
		if (StringUtils.isEmpty(roleID)) {
			return null;
		}
		return hessianSysRoleManagerService().getButtonsByRole(roleID);
	}

}
