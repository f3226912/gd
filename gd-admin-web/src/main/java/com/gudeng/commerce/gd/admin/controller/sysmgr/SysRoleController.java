package com.gudeng.commerce.gd.admin.controller.sysmgr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysMenuAdminService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRoleAdminService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRoleManagerAdminService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysUserRoleAdminService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.LoginUserUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysMenu;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRole;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRoleManager;
import com.gudeng.commerce.gd.authority.sysmgr.util.CommonConstant;
import com.gudeng.commerce.gd.authority.sysmgr.util.IdCreater;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 系统角色controller类
 * 
 * @version 1.0
 * 
 * @author 谭军
 * @date 2012-03-22
 * 
 */
@Controller
@RequestMapping("sysmgr")
public class SysRoleController extends AdminBaseController {

	// 日志
	private GdLogger logger = GdLoggerFactory.getLogger(SysRoleController.class);

	/** 角色service */
	@Autowired
	private SysRoleAdminService sysRoleService;
	/** 菜单service */
	@Autowired
	private SysMenuAdminService sysMenuService;
	/** 用户角色关系service */
	@Autowired
	private SysUserRoleAdminService sysUserRoleService;

	/** 用户角色菜单按钮关系管理service */
	@Autowired
	private SysRoleManagerAdminService sysRoleManagerService;

	@Autowired
	private GdProperties gdProperties;

	/**
	 * 角色列表初始化
	 * 
	 * @author songhui
	 * @date 创建时间：2015年7月24日 上午11:14:04
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("sysRole/list")
	public String list(HttpServletRequest request) {

		return "sysmgr/sysrole/rolelist";
	}

	/**
	 * 角色列表页面;
	 * 
	 * @param request
	 * @return path
	 * @throws Exception
	 * @update tanjun
	 */
	@RequestMapping("sysRole/query")
	@ResponseBody
	public String getListSysRole(HttpServletRequest request) throws Exception {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("roleName", StringUtils.trimToNull(request.getParameter("roleName")));
			// 记录数
			map.put("total", sysRoleService.getTotal(map));
//			// 非管理员只能查看当前登录用户自己的角色
//			if (!gdProperties.getSysSupperAdminId().equals(LoginUserUtil.getLoginUserId(request))) {
//				map.put("loginUserID", LoginUserUtil.getLoginUserId(request));
//			}
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<SysRole> list = sysRoleService.getByCondition(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			return getExceptionMessage(e, logger);
		}
	}

	/**
	 * 新增用户- 页面初始化;
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("sysRole/addInit")
	public String addInit(HttpServletRequest request) {

		request.setAttribute("actionUrl", "add");
		return "sysmgr/sysrole/roleedit";
	}

	/**
	 * 新增角色- 保存动作;
	 * 
	 * @param request
	 * @return
	 * @update 2012-5-8
	 */
	@RequestMapping("sysRole/add")
	@ResponseBody
	public String addSysRole(HttpServletRequest request) throws Exception {

		try {
			// 取得页面的参数
			String roleName = StringUtils.trimToNull(request.getParameter("roleName"));
			String remark = StringUtils.trimToNull(request.getParameter("remark"));

			// 构造对象
			SysRole sysRole = new SysRole();
			sysRole.setRoleID(IdCreater.newId());
			sysRole.setRoleName(roleName);
			sysRole.setRemark(remark);
			sysRole.setCreateUserID(LoginUserUtil.getLoginUserId(request));

			String message = sysRoleService.insert(sysRole);
			return message;
		} catch (Exception e) {
			// 记录日志;
			return getExceptionMessage(e, logger);
		}
	}

	/**
	 * 角色修改页面初始化
	 * 
	 * @author songhui
	 * @date 创建时间：2015年7月24日 上午11:50:19
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("sysRole/updateInit")
	public String updateInit(HttpServletRequest request) {

		request.setAttribute("actionUrl", "update");
		try {
			String roleID = request.getParameter("roleID");
			SysRole sysRole = sysRoleService.getSysRoleById(roleID);
			request.setAttribute("dto", sysRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sysmgr/sysrole/roleedit";
	}

	/**
	 * 修改角色- 保存动作;
	 * 
	 * @param request
	 * @return
	 * @update 2012-5-8
	 */
	@RequestMapping("sysRole/update")
	@ResponseBody
	public String updateSysRole(HttpServletRequest request) throws Exception {

		try {
			// 取得页面的参数
			String roleID = StringUtils.trimToNull(request.getParameter("roleID"));
			String roleName = StringUtils.trimToNull(request.getParameter("roleName"));
			String remark = StringUtils.trimToNull(request.getParameter("remark"));

			// 构造对象
			SysRole sysRole = new SysRole();
			sysRole.setRoleID(roleID);
			sysRole.setRoleName(roleName);
			sysRole.setRemark(remark);
			sysRole.setUpdateUserID(LoginUserUtil.getLoginUserId(request));
			// 修改
			String message = sysRoleService.update(sysRole);
			return message;
		} catch (Exception e) {
			// 记录日志;
			return getExceptionMessage(e, logger);
		}
	}

	/**
	 * 删除角色;
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "sysRole/delete"}, produces = { "application/html;charset=UTF-8" })
	@ResponseBody
	public String deleteSysRole(HttpServletRequest request) throws Exception {

		try {
			// 获取页面id集合
			String roleIds = request.getParameter("delID");
			sysRoleService.delete(roleIds);
			return CommonConstant.COMMON_AJAX_SUCCESS;
		} catch (Exception e) {
			return e.getMessage().toString();
		}
	}

	/**
	 * 角色分配权限页面初始化
	 * 
	 * @author songhui
	 * @date 创建时间：2015年7月25日 上午11:52:32
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("sysRole/assignRightList")
	public ModelAndView assignRightList(HttpServletRequest request, String roleID) {
		ModelAndView mv = new ModelAndView();
		// 根据角色查询
		try {
			SysRole dto = sysRoleService.getSysRoleById(StringUtils.trimToNull(roleID));
			mv.addObject("dto", dto);
			// 详情标识
			String view = StringUtils.trimToNull(request.getParameter("view"));
			mv.addObject("view", view);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("sysmgr/sysrole/assignrightlist");
		return mv;
	}

	@RequestMapping("sysRole/assignMenuList")
	public ModelAndView assignMenuList(HttpServletRequest request, String roleID) {
		ModelAndView mv = new ModelAndView();
		// 根据角色查询
		try {
			SysRole dto = sysRoleService.getSysRoleById(StringUtils.trimToNull(roleID));
			mv.addObject("dto", dto);
			mv.addObject("roleID", roleID);
			// 详情标识
			String view = StringUtils.trimToNull(request.getParameter("view"));
			mv.addObject("view", view);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("sysmgr/sysrole/assignRoleMenu");
		return mv;
	}

	/**
	 * 角色权限分配的时候初始化表格
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("sysRole/assignRightQuery")
//	@ResponseBody
//	public String assignRightQuery(HttpServletRequest request) {
//
//		try {
//			Map<String, Object> map = new HashMap<String, Object>();
//			// 获取前台查询条件
//			map.put("menuCode", StringUtils.trimToNull(request.getParameter("menuCode")));
//			map.put("menuName", StringUtils.trimToNull(request.getParameter("menuName")));
//			map.put("roleID", StringUtils.trimToNull(request.getParameter("roleID")));
//			// // 查看标识
//			// map.put("view",StringUtils.trimToNull(request.getParameter("view")));
//			// // 如果当前用户ID为超级管理员ID则查出所有可分配权限，否则只能分配当前自己的权限
//			// if (!gdProperties.getSysSupperAdminId().equals(
//			// LoginUserUtil.getLoginUserId(request))) {
//			// map.put("menuTotal", 1);
//			// }
//			// // 查询所有的菜单和按钮信息到前台展示
//			// List<SysRoleManager> list = null;// sysRoleManagerService.get(map);
//			// map.put("rows", list);// rows键 存放每页记录 list
//			// return JSONObject.toJSONString(map,
//			// SerializerFeature.WriteDateUseDateFormat);
//			// } catch (Exception e) {
//			// return getExceptionMessage(e, logger);
//			// }
//
//			SysMenuDTO root = new SysMenuDTO();
//			List<SysMenuDTO> rootChildren = new ArrayList<>();// 子菜单
//			Map<String, Object> rootAttributes = new HashMap<String, Object>();// 被添加到节点的自定义属性。
//
//			List<SysMenu> list = sysMenuService.getFirstMenu(map);// 获取一级菜单
//			if (list == null) {
//				list = new ArrayList<SysMenu>();
//			}
//			root.setId("0");
//			root.setText("谷登运营后台系统菜单");
//			root.setState("open");// 菜单展开
//			root.setLeaf(true);
//			for (SysMenu sysMenu : list) {
//				SysMenuDTO topMenu = new SysMenuDTO();
//				Map<String, Object> childAttributes = new HashMap<String, Object>();// 被添加到节点的自定义属性。
//				topMenu.setId(sysMenu.getMenuID());
//				topMenu.setText(sysMenu.getMenuName());
//				topMenu.setState("closed");// 菜单展开
//				childAttributes.put("level", 1);
//				topMenu.setAttributes(childAttributes);
//				topMenu.setLeaf(true);
//				map.clear();
//				map.put("menuModuleID", sysMenu.getMenuID());
//				List<SysMenu> secondMenu = sysMenuService.getSecondMenu(map);
//				List<SysMenuDTO> secondMenuchildren = new ArrayList<>();// 子菜单
//				for (SysMenu sysMenu2 : secondMenu) {
//					SysMenuDTO secondMenuDTO = new SysMenuDTO();
//					Map<String, Object> secondMenuAttributes = new HashMap<String, Object>();// 被添加到节点的自定义属性。
//					secondMenuDTO.setId(sysMenu2.getMenuID());
//					secondMenuDTO.setText(sysMenu2.getMenuName());
//					secondMenuDTO.setState("open");// 菜单展开
//					secondMenuAttributes.put("level", 2);
//					secondMenuDTO.setAttributes(secondMenuAttributes);
//					secondMenuchildren.add(secondMenuDTO);
//				}
//				topMenu.setChildren(secondMenuchildren);
//				rootChildren.add(topMenu);
//			}
//			root.setChildren(rootChildren);
//			rootAttributes.put("level", 0);
//			root.setAttributes(rootAttributes);
//			List<SysMenuDTO> jsonList = new ArrayList<>();
//			jsonList.add(root);
//			return JSONObject.toJSONString(jsonList, SerializerFeature.WriteDateUseDateFormat);
//		} catch (Exception e) {
//			return getExceptionMessage(e, logger);
//		}
//	}

	@RequestMapping("sysRole/assignMenuQuery")
	@ResponseBody
	public String assignMenuQuery(HttpServletRequest request) {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 获取前台查询条件
			// map.put("menuCode",
			// StringUtils.trimToNull(request.getParameter("menuCode")));
			// map.put("menuName",
			// StringUtils.trimToNull(request.getParameter("menuName")));
			map.put("roleID", StringUtils.trimToNull(request.getParameter("roleID")));
			// 查看标识
			// map.put("view",
			// StringUtils.trimToNull(request.getParameter("view")));
			// 如果当前用户ID为超级管理员ID则查出所有可分配权限，否则只能分配当前自己的权限
			if (!gdProperties.getSysSupperAdminId().equals(LoginUserUtil.getLoginUserId(request))) {
				map.put("menuTotal", 1);
			}
			// 查询所有的菜单和按钮信息到前台展示
			List<SysRoleManager> list = sysRoleManagerService.get(map);
			// List<SysMenu> list = sysMenuService
			// .getAll(new HashMap<String, Object>());
			// sysRoleManagerService.get(map);

			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			return getExceptionMessage(e, logger);
		}

	}

	/**
	 * 角色权限分配更新
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	// @RequestMapping("sysRole/assignRight")
	// @ResponseBody
	// public String buttonManager(HttpServletRequest request) {
	//
	// try {
	// // 获取前台参数(获取所有的行头checkbox的组合字符串)
	// String trCheckBox = StringUtils.trimToNull(request.getParameter("trCheckBox"));
	// // 获取roleID
	// String roleID = request.getParameter("roleID");
	// // 修改用户的按钮数据
	// String message = sysRoleManagerService.update(trCheckBox, roleID, LoginUserUtil.getLoginUserId(request));
	// return message;
	// } catch (Exception e) {
	// return getExceptionMessage(e, logger);
	// }
	// }

	@RequestMapping(value = "sysRole/assignMenu")
	@ResponseBody
	public String menuManager(HttpServletRequest request) {
		try {
			// 获取前台参数(获取所有的行头menuIDs的组合字符串)
			String menuIDs = request.getParameter("menuIDs");
			String btnIDs = request.getParameter("btnIDs");
			List<String> menuList = Arrays.asList(menuIDs.split(","));
			List<String> buttonList = Arrays.asList(btnIDs.split(","));
			// 获取roleID
			String roleID = request.getParameter("roleID");
			// 修改角色的按钮数据
			sysRoleManagerService.updateBtn(buttonList, roleID, LoginUserUtil.getLoginUserId(request));
			// 修改角色的菜单数据
			sysRoleManagerService.update(menuList, roleID, LoginUserUtil.getLoginUserId(request));
		} catch (Exception e) {
			return getExceptionMessage(e, logger);
		}
		return "success";
	}
}
