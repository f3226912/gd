package com.gudeng.commerce.gd.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gudeng.commerce.gd.admin.service.AdMenuToolService;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.AdMenuDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * @Description 新版广告管理(第二版)
 * @Project gd-admin-web
 * @ClassName PushV2Controller.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月12日 下午8:52:00
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
@Controller
@RequestMapping("adMenu")
public class AdMenuController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(AdMenuController.class);
	@Autowired
	private AdMenuToolService adMenuService;

	/**
	 * 初始化添加页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("adMenuAdd")
	public ModelAndView adInfoAdd(ModelAndView mv, AdMenuDTO adMenuDTO, String fatherName) {
		mv.addObject("dto", adMenuDTO);
		mv.addObject("option", "add");
		mv.addObject("fatherName", fatherName);
		mv.setViewName("adMenu/adMenu_add");
		return mv;
	}

	/**
	 * 初始化编辑页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("adMenuUpate/{id}")
	public ModelAndView adInfoEditById(@PathVariable("id") String id, ModelAndView mv) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		AdMenuDTO adMenuDTO = null;
		try {
			adMenuDTO = adMenuService.getAdMenuByCondition(map).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("dto", adMenuDTO);
		mv.addObject("option", "update");
		mv.setViewName("adMenu/adMenu_add");
		return mv;
	}

	/**
	 * 初始化 查看详细信息 application/json
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("adMenuView/{id}")
	public ModelAndView adInfoDetailById(@PathVariable("id") String id, ModelAndView mv) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		AdMenuDTO adMenuDTO = null;
		try {
			adMenuDTO = adMenuService.getAdMenuByCondition(map).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("dto", adMenuDTO);
		mv.addObject("option", "view");
		mv.setViewName("adMenu/adMenu_add");
		return mv;
	}

	/**
	 * 提交新增数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "adMenuSaveAdd")
	@ResponseBody
	public Map<String, Object> adMenuAddSaveAdd(AdMenuDTO adMenuDTO) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = this.getUser(getRequest());
			adMenuDTO.setCreateUserId(user.getUserID());
			adMenuDTO.setCreateUserName(user.getUserName());
			adMenuDTO.setState("1");
			String menuSign = adMenuDTO.getMenuSign();// 菜单唯一标识
			if (!isLetterOrDigit(menuSign)) {
				map.put("msg", "菜单标识只能包含数字、字母");
				return map;
			}
			map.put("menuSign", menuSign);
			map.put("state", 1);// 启用菜单
			List<AdMenuDTO> adMenuList = adMenuService.getAdMenuByCondition(map);
			if (adMenuList != null && adMenuList.size() > 0) {
				map.put("msg", "操作失败,该菜单标识【" + menuSign + "】已存在，请重新填写菜单标识，或者修改已存在的标识");
				return map;
			}

			Long i = adMenuService.insert(adMenuDTO);
			if (i > 0) {
				adMenuDTO.setId(i);
				map.put("msg", "success");
				map.put("option", "add");
				map.put("adMenuDTO", adMenuDTO);
				return map;
			} else {
				map.put("msg", "操作失败");
				return map;
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		map.put("msg", "操作失败");
		return map;
	}

	/**
	 * 提交更新数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "adMenuSaveUpdate")
	@ResponseBody
	public Map<String, Object> adMenuSaveUpdate(AdMenuDTO adMenuDTO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = this.getUser(request);
			adMenuDTO.setUpdateUserId(user.getUserID());
			adMenuDTO.setUpdateUserName(user.getUserName());
			String menuSign = adMenuDTO.getMenuSign();// 菜单唯一标识
			map.put("menuSign", menuSign);
			map.put("state", 1);// 启用菜单
			List<AdMenuDTO> adMenuList = adMenuService.getAdMenuByCondition(map);
			if (adMenuList != null && adMenuList.size() > 0) {
				map.put("msg", "操作失败,该菜单标识【" + menuSign + "】已存在，请重新填写菜单标识，或者修改已存在的标识");
				return map;
			}
			Long i = adMenuService.update(adMenuDTO);
			if (i > 0) {
				map.put("msg", "success");
				map.put("option", "update");
				map.put("adMenuDTO", adMenuDTO);
				return map;
			} else {
				map.put("msg", "操作失败");
				return map;
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		map.put("msg", "操作失败");
		return map;
	}

	/**
	 * 根据ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam String id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("updateUserId", getUser(request).getUserID());
		map.put("updateUserName", getUser(request).getUserName());
		try {
			Long i = adMenuService.delete(map);
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Description isLetterOrDigit 验证字符串是否只包含数字和字母
	 * @param str
	 * @return 返回true表示只包含数字和字母，false表示包含除字母、数字意外的字符
	 * @CreationDate 2016年4月20日 下午1:47:45
	 * @Author lidong(dli@gdeng.cn)
	 */
	private static boolean isLetterOrDigit(String str) {
		String regex = "^[a-z0-9A-Z]+$";
		return str.matches(regex);
	}
}
