package com.gudeng.commerce.info.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.info.admin.service.BoardToolService;
import com.gudeng.commerce.info.admin.service.ReportsToolService;
import com.gudeng.commerce.info.admin.service.SysMenuAdminService;
import com.gudeng.commerce.info.admin.service.SysRoleAdminService;
import com.gudeng.commerce.info.admin.service.SysRoleManagerAdminService;
import com.gudeng.commerce.info.admin.service.SysUserRoleAdminService;
import com.gudeng.commerce.info.admin.service.SysroleboardToolService;
import com.gudeng.commerce.info.admin.service.SysrolereportsToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.admin.util.LoginUserUtil;
import com.gudeng.commerce.info.customer.dto.BoardDTO;
import com.gudeng.commerce.info.customer.dto.ReportsDTO;
import com.gudeng.commerce.info.customer.dto.SysroleboardDTO;
import com.gudeng.commerce.info.customer.dto.SysrolereportsDTO;
import com.gudeng.commerce.info.customer.entity.SysMenu;
import com.gudeng.commerce.info.customer.entity.SysRole;
import com.gudeng.commerce.info.customer.entity.SysRoleManager;
import com.gudeng.commerce.info.customer.util.CommonConstant;
import com.gudeng.commerce.info.customer.util.IdCreater;
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
    /**
     * 报表服务
     */
    @Autowired
    private ReportsToolService reportService;
    @Autowired
    private BoardToolService boardservice;
    /** 用户角色菜单按钮关系管理service */
    @Autowired
    private SysRoleManagerAdminService sysRoleManagerService;

    @Autowired
    private GdProperties gdProperties;
    @Autowired
    private SysrolereportsToolService sysrolereportsService;
    @Autowired
    private SysroleboardToolService sysroleboardService;

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
            map.put("attribute", StringUtils.trimToNull(request.getParameter("attribute")));
            // 记录数
            map.put("total", sysRoleService.getTotal(map));
            // // 非管理员只能查看当前登录用户自己的角色
            // if (!gdProperties.getSysSupperAdminId().equals(LoginUserUtil.getLoginUserId(request))) {
            // map.put("loginUserID", LoginUserUtil.getLoginUserId(request));
            // }
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
    @RequestMapping(value = "sysRole/add", produces = "application/text;charset=UTF-8")
    @ResponseBody
    public String addSysRole(HttpServletRequest request) throws Exception {

        try {
            // 取得页面的参数
            String roleName = StringUtils.trimToNull(request.getParameter("roleName"));
            String remark = StringUtils.trimToNull(request.getParameter("remark"));
            String attribute = StringUtils.trimToNull(request.getParameter("attribute"));
            // 构造对象
            SysRole sysRole = new SysRole();
            sysRole.setRoleID(IdCreater.newId());
            sysRole.setRoleName(roleName);
            sysRole.setRemark(remark);
            sysRole.setAttribute(attribute);
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
    @RequestMapping(value = "sysRole/update", produces = "application/text;charset=UTF-8")
    @ResponseBody
    public String updateSysRole(HttpServletRequest request) throws Exception {

        try {
            // 取得页面的参数
            String roleID = StringUtils.trimToNull(request.getParameter("roleID"));
            String roleName = StringUtils.trimToNull(request.getParameter("roleName"));
            String attribute = StringUtils.trimToNull(request.getParameter("attribute"));
            String remark = StringUtils.trimToNull(request.getParameter("remark"));
            String oldAttribute = StringUtils.trimToNull(request.getParameter("oldAttribute"));
            // 修改角色类型则删除角色相关联的权限
            if (!attribute.equals(oldAttribute)) {
                sysroleboardService.delete(roleID);
                sysrolereportsService.delete(roleID);
                sysRoleManagerService.update(null, roleID, LoginUserUtil.getLoginUserId(request));
            }

            // 构造对象
            SysRole sysRole = new SysRole();
            sysRole.setRoleID(roleID);
            sysRole.setRoleName(roleName);
            sysRole.setAttribute(attribute);
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
    @RequestMapping(value = { "sysRole/delete" }, produces = { "application/html;charset=UTF-8" })
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

    /**
     * @Description 跳转到权限分配界面
     * @param request
     * @param roleID
     * @return
     * @CreationDate 2016年3月2日 下午4:02:19
     * @Author lidong(dli@gdeng.cn)
     */
    /**
     * 报表角色关联
     */

    @RequestMapping("sysRole/toAssignRight")
    public ModelAndView toAssignRight(HttpServletRequest request, String roleID) {
        ModelAndView mv = new ModelAndView();
        String boardName = getRequest().getParameter("boardName");
        String reportsName = getRequest().getParameter("reportsName");
        String boardClass = getRequest().getParameter("boardClass");
        String reportsClass = getRequest().getParameter("reportsClass");

        boolean boardQuery = false;// 显示看板数据
        boolean reportsQuery = false;// 显示报表数据
        if (StringUtils.isEmpty(boardName) && StringUtils.isEmpty(reportsClass) && StringUtils.isEmpty(boardClass) && StringUtils.isEmpty(reportsName)) {
            boardQuery = true;
            reportsQuery = true;
        } else {
            if (StringUtils.isNotEmpty(boardName) || StringUtils.isNotEmpty(boardClass)) {
                boardQuery = true;
            }
            if (StringUtils.isNotEmpty(reportsName) || StringUtils.isNotEmpty(reportsClass)) {
                reportsQuery = true;
            }
        }
        mv.addObject("boardQuery", boardQuery);
        mv.addObject("reportsQuery", reportsQuery);
        mv.addObject("boardName", boardName);
        mv.addObject("reportsName", reportsName);
        mv.addObject("boardClass", boardClass);
        mv.addObject("reportsClass", reportsClass);
        // 根据角色查询
        try {
            SysRole dto = sysRoleService.getSysRoleById(StringUtils.trimToNull(roleID));
            mv.addObject("dto", dto);
            mv.addObject("roleID", roleID);
            Map<String, Object> map = new HashMap<String, Object>();
            // 条件参数
            map.put("level", 1);
            map.put("attribute", 3);
            map.put("startRow", 0);
            map.put("endRow", 99999999);
            // 数据分类
            List<SysMenu> menuList = sysMenuService.getByCondition(map);
            List<SysMenu> menus = new ArrayList<>();
            if (StringUtils.isNotEmpty(boardClass) || StringUtils.isNotEmpty(reportsClass)) {
                for (SysMenu sysMenu : menuList) {
                    if (sysMenu.getMenuID().equals(boardClass) || sysMenu.getMenuID().equals(reportsClass)) {
                        menus.add(sysMenu);
                    }
                }
            } else {
                menus = menuList;
            }
            List<SysMenu> menuListTemp = new ArrayList<>();
            for (SysMenu sysMenu : menus) {
                map.put("state", 1);
                map.put("menuId", sysMenu.getMenuID());
                // 报表
                map.put("name", reportsName);
                List<ReportsDTO> reportList = reportService.getPageByCondition(map);
                // 看板
                map.put("name", boardName);
                List<BoardDTO> boardList = boardservice.getListByCondition(map);
                sysMenu.setReportList(reportList);
                sysMenu.setBoardList(boardList);
                menuListTemp.add(sysMenu);
            }
            mv.addObject("menuList", menuListTemp);
            map.put("roleID", roleID);
            // 角色报表关联数据
            List<SysrolereportsDTO> roleReoorts = sysrolereportsService.getListByCondition(map);
            mv.addObject("roleReoorts", roleReoorts);
            // 角色看板关联数据
            List<SysroleboardDTO> roleBoards = sysroleboardService.getListByCondition(map);
            mv.addObject("roleBoards", roleBoards);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.setViewName("sysmgr/sysrole/toAssignRoleMenu");
        return mv;
    }

    /**
     * @Description 分配前台角色的报表、看板权限
     * @param request
     * @param sysMenu
     * @param roleID
     * @return
     * @CreationDate 2016年3月3日 上午10:52:06
     * @Author lidong(dli@gdeng.cn)
     */
    @RequestMapping("sysRole/assignH5Right")
    public ModelAndView assignH5Right(HttpServletRequest request, SysMenu sysMenu, String roleID) {
        ModelAndView mv = new ModelAndView();
        // 根据角色查询
        try {
            Set<String> menus = new HashSet<>();
            if (sysMenu.getReportList() == null) {
                sysrolereportsService.addBatch(null, roleID, LoginUserUtil.getLoginUserId(request));
            } else {
                List<ReportsDTO> reportList = new ArrayList<>();
                for (ReportsDTO reportsDTO : sysMenu.getReportList()) {
                    if (StringUtils.isNotEmpty(reportsDTO.getIdStr())) {
                        reportsDTO.setId(Long.valueOf(reportsDTO.getIdStr().split("_")[0]));
                        reportList.add(reportsDTO);
                        menus.add(reportsDTO.getIdStr().split("_")[1]);
                    }
                }
                sysrolereportsService.addBatch(reportList, roleID, LoginUserUtil.getLoginUserId(request));
            }
            if (sysMenu.getBoardList() == null) {
                sysroleboardService.addBatch(null, roleID, LoginUserUtil.getLoginUserId(request));
            } else {
                List<BoardDTO> boardList = new ArrayList<>();
                for (BoardDTO boardDTO : sysMenu.getBoardList()) {
                    if (StringUtils.isNotEmpty(boardDTO.getIdStr())) {
                        boardDTO.setId(Long.valueOf(boardDTO.getIdStr().split("_")[0]));
                        boardList.add(boardDTO);
                        menus.add(boardDTO.getIdStr().split("_")[1]);
                    }
                }
                sysroleboardService.addBatch(boardList, roleID, LoginUserUtil.getLoginUserId(request));
            }
            List<String> menuList = new ArrayList<>(menus);
            Map<String, Object> map = new HashMap<>();
            map.put("roleID", roleID);
            List<SysMenu> allMenuRole = sysMenuService.getAllMenuRole(map);
            for (SysMenu sysMenu2 : allMenuRole) {
                if (StringUtils.isNotEmpty(sysMenu2.getMenuID()) && !sysMenu2.getAttribute().equals("3")) {
                    menuList.add(sysMenu2.getMenuID());
                }
            }
            sysRoleManagerService.update(menuList, roleID, LoginUserUtil.getLoginUserId(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.setViewName("redirect:/sysmgr/sysRole/toAssignRight?roleID=" + roleID);
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
