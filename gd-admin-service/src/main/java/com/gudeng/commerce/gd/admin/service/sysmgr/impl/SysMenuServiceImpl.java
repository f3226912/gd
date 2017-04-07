package com.gudeng.commerce.gd.admin.service.sysmgr.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysMenuAdminService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysMenu;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysMenuService;

/**
 * 菜单操作实现类;
 * 
 */
@Service
public class SysMenuServiceImpl implements SysMenuAdminService {
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;

	private static SysMenuService sysMenuService;

	private SysMenuService hessianSysMenuService() throws MalformedURLException {
		String hessianUrl = gdProperties.getSysMenuServiceUrl();
		if (sysMenuService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sysMenuService = (SysMenuService) factory.create(SysMenuService.class, hessianUrl);
		}
		return sysMenuService;
	}

	@Override
	public List<SysMenu> getAll(Map<String, Object> map) throws Exception {
		return hessianSysMenuService().getAll(map);
	}

	@Override
	public List<SysMenu> getByCondition(Map<String, Object> map) throws Exception {

		return hessianSysMenuService().getByCondition(map);
	}

	/**
	 * 检查此菜单是否已经分配给对应的角色用
	 */
	@Override
	public List<SysMenu> getAllMenuRole(Map<String, Object> map) throws Exception {
		if (StringUtils.isEmpty(map.get("roleID").toString())) {
			return null;
		}
		return hessianSysMenuService().getAllMenuRole(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return hessianSysMenuService().getTotal(map);
	}

	@Override
	public SysMenu getSysMenu(String menuCode) throws Exception {
		return hessianSysMenuService().getSysMenu(menuCode);
	}

	@Override
	public SysMenu getSysMenuByID(String menuId) throws Exception {
		return hessianSysMenuService().getSysMenuByID(menuId);
	}

	@Override
	public String insert(SysMenu sysMenu) throws Exception {
		return hessianSysMenuService().insert(sysMenu);
	}

	@Override
	public String update(SysMenu sysMenu) throws Exception {
		return hessianSysMenuService().update(sysMenu);
	}

	@Override
	public String delete(String menuIDs) throws Exception {
		return hessianSysMenuService().delete(menuIDs);
	}

	@Override
	public List<SysMenu> getFirstMenu(Map<String, Object> map) throws Exception {
		return hessianSysMenuService().getFirstMenu(map);
	}

	@Override
	public List<SysMenu> getSecondMenu(Map<String, Object> map) throws Exception {
		return hessianSysMenuService().getSecondMenu(map);
	}
    /**
     * @Description 获取父级菜单
     * @param map
     * @return
     * @throws Exception 
     * @CreationDate 2016年1月12日 下午12:20:55
     * @Author lidong(dli@gdeng.cn)
    */
    public SysMenu getParentMenu(Map<String, Object> map) throws Exception{
        return hessianSysMenuService().getParentMenu(map);
    }
	@Override
	public int checkMenuHasChildren(Map<String, Object> map) throws Exception {
		return hessianSysMenuService().checkMenuHasChildren(map);
	}
}