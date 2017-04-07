package com.gudeng.commerce.info.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.info.admin.service.BoardToolService;
import com.gudeng.commerce.info.admin.service.SysMenuAdminService;
import com.gudeng.commerce.info.customer.dto.BoardDTO;
import com.gudeng.commerce.info.customer.entity.SysMenu;
import com.gudeng.commerce.info.customer.entity.SysRegisterUser;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("board")
public class BoardController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	public BoardToolService boardToolService;
	
	 /** 菜单service */
    @Autowired
    private SysMenuAdminService sysMenuService;
	
	/**
	 * 列表页面
	 * @return
	 */
	@RequestMapping("list")
	public String boardList(HttpServletRequest request,ModelMap map){
		try {
			Map<String, Object> map2 = new HashMap<String, Object>();
			// 条件参数
			map2.put("level", 1);
			map2.put("attribute", 3);
			map2.put("startRow", 0);
			map2.put("endRow", 999);
			List<SysMenu> list = sysMenuService.getByCondition(map2);
			map.put("menuList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "board/list";
	}
	
	/**
	 * 列表数据查询
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("query")
	@ResponseBody
	public String boardQuery(HttpServletRequest request) {
		try {
			String name = request.getParameter("name");
			String menuId = request.getParameter("menuId");
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (name != null && !"".equals(name)){
				map.put("name", name);
			}
			if (menuId != null && !"".equals(menuId)){
				map.put("menuId", menuId);
			}
			
			//获取条件记录总数
			map.put("total", boardToolService.getTotal(map));
			//设置分页参数
			setCommParameters(request, map);
			//数据集合
			List<BoardDTO> list = boardToolService.getListByConditionPage(map);
			if(null != list && list.size() > 0){
				for(BoardDTO tempdto : list){
					if(null != tempdto && null != tempdto.getMenuId()){
						SysMenu sm = sysMenuService.getSysMenuByID(tempdto.getMenuId());
						if(null != sm){
							tempdto.setMenuId(sm.getMenuName());
						}
					}
				}
			}
			//rows键 存放每页记录 list
			map.put("rows", list);
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 	 初始化 查看详细信息
	 * application/json
	 * @param request
	 * @return
	 */
	@RequestMapping("detailById/{id}")
	public String detailById(@PathVariable("id") String id, ModelMap map){
		try {
			BoardDTO boardDTO = boardToolService.getById(Long.valueOf(id));
			SysMenu sm = sysMenuService.getSysMenuByID(boardDTO.getMenuId());
			if(null != sm){
				boardDTO.setMenuId(sm.getMenuName());
			}
			map.put("dto", boardDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "board/detail";
	}
	
	
	
	/**
	 * 	  提交更新数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="edit/{id}-{state}" )
	@ResponseBody
	public String edit(@PathVariable("id") String id,@PathVariable("state") String state, HttpServletRequest request) {
		try {
			SysRegisterUser user = this.getUser(request);
			BoardDTO dto = new BoardDTO();
			dto.setId(Long.valueOf(id));
			dto.setState(state);
			dto.setUpdateUserID(user.getUserID());
			dto.setUpdateTime(new Date());
			int i = 0;
			i = boardToolService.updateDTO(dto);
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "failed";
	}
	
}
