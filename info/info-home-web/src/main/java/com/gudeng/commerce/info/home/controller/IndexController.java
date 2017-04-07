package com.gudeng.commerce.info.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gudeng.commerce.info.customer.dto.BaseMenu;
import com.gudeng.commerce.info.customer.dto.BoardDTO;
import com.gudeng.commerce.info.customer.dto.ReportsDTO;
import com.gudeng.commerce.info.customer.entity.SysUserBoard;
import com.gudeng.commerce.info.customer.util.Constant;
import com.gudeng.commerce.info.customer.util.PathUtil;
import com.gudeng.commerce.info.home.service.BoardToolService;
import com.gudeng.commerce.info.home.service.ReportsToolService;
import com.gudeng.commerce.info.home.service.SysUserBoardHomeService;
import com.gudeng.commerce.info.home.util.LoginUserUtil;

/**
 * 类说明 首页
 **/
@Controller
@RequestMapping("")
public class IndexController extends AdminBaseController {
    @Autowired
    private BoardToolService boardService;
    @Autowired
    private ReportsToolService reportService;
    /** 用户看板Serivce */
    @Autowired
    private SysUserBoardHomeService sysUserBoardHomeService;

    /**
     * 首页
     * 
     * @param request
     * @return
     * 
     */
    @RequestMapping("H5/workbench/main")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "H5/workbench/main";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("H5/workbench/index")
    public ModelAndView H5Index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        try {
            if (LoginUserUtil.getLoginUserId(request) != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                List<BaseMenu> subMenuList = (List<BaseMenu>) request.getSession().getAttribute(Constant.SYSTEM_SENCONDMENU);
                List<BaseMenu> baseMenuList = (List<BaseMenu>) request.getSession().getAttribute(Constant.SYSTEM_ALLMENU);
                List<BaseMenu> trdMenuList = (List<BaseMenu>) request.getSession().getAttribute(Constant.SYSTEM_THIRDMENU);
                List<BaseMenu> dataMenu = (List<BaseMenu>) request.getSession().getAttribute(Constant.USER_DATAMENU);// 用户合法数据类型
                List<BaseMenu> menuList = new ArrayList<>();
                for (BaseMenu baseMenu : baseMenuList) {
                    menuList.add(baseMenu);
                }
                for (BaseMenu subMenu : subMenuList) {
                    menuList.add(subMenu);
                }
                for (BaseMenu subMenu : trdMenuList) {
                    menuList.add(subMenu);
                }
                map.put("userId", LoginUserUtil.getLoginUserId(request));
                // 看板开关数据
                map.put("userID", LoginUserUtil.getLoginUserId(request));
                List<SysUserBoard> userBoardList = sysUserBoardHomeService.getUserBoardList(map);
                // 看板数据
                List<BoardDTO> boardDTOListTmp = boardService.getListByUserId(map);
                // 过滤看板开关数据
                boardDTOListTmp = boardFilter(userBoardList, boardDTOListTmp);
                List<ReportsDTO> reportsDTOList = reportService.getListByUserId(map);
                // 组装分类下的看板和报表数据
                dataMenu = packageDataMenu(dataMenu, boardDTOListTmp, reportsDTOList);
                map.put("trdMenuList", trdMenuList);
                map.put("subMenuList", subMenuList);
                map.put("baseMenuList", baseMenuList);
                map.put("menuList", menuList);
                map.put("dataMenu2", dataMenu);
//                map.put("dataBoardList", getBoardList());
//                map.put("commonBoardList", commonBoardFilter(userBoardList));
                map.put("commonBoardList", boardService.getCommonListByUserId(map));
                if (request.getSession().getAttribute("menuList") != null) {
                    request.getSession().setAttribute("menuList", menuList);
                }
                if (request.getSession().getAttribute("dataMenu") != null) {
                    request.getSession().setAttribute("dataMenu", dataMenu);
                }
                request.setAttribute("index", map);
            } else {
                mv.setViewName("redirect:" + PathUtil.getBasePath(request));
                return mv;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.setViewName("H5/workbench/index");
        return mv;
    }

    /**
     * @Description 根据看板开关数据过滤出前台显示的看板数据
     * @param userBoardList
     * @param boardDTOListTmp
     * @return
     * @CreationDate 2016年3月10日 上午9:44:39
     * @Author lidong(dli@gdeng.cn)
     */
    protected List<BoardDTO> boardFilter(List<SysUserBoard> userBoardList, List<BoardDTO> boardDTOListTmp) {
        List<BoardDTO> list = new ArrayList<>();
        for (BoardDTO boardDTO : boardDTOListTmp) {
            for (SysUserBoard userBoard : userBoardList) {
                if ("2".equals(userBoard.getType())) {
                    if (userBoard.getBoardID().equals(boardDTO.getId())) {
                        // 关闭该看板数据
                        boardDTO.setStatus(Constant.USERBOARD_STATUS_CLOSE);
                        break;
                    }
                }
            }
            list.add(boardDTO);
        }
        return list;
    }

    /**
     * @Description 组装分类下的看板和报表数据
     * @param boardDTOList
     * @param reportsDTOList
     * @return
     * @CreationDate 2016年3月10日 上午9:52:59
     * @Author lidong(dli@gdeng.cn)
     */
    protected List<BaseMenu> packageDataMenu(List<BaseMenu> dataMenu, List<BoardDTO> boardDTOList, List<ReportsDTO> reportsDTOList) {
        if (dataMenu != null && dataMenu.size() > 0) {
            for (int i = 0; i < dataMenu.size(); i++) {
                List<BoardDTO> boardList = new ArrayList<>();
                for (BoardDTO boardDTO : boardDTOList) {
                    if (boardDTO.getMenuId().equals(dataMenu.get(i).getMenuID())) {
                        boardList.add(boardDTO);
                    }
                }
                List<ReportsDTO> reportList = new ArrayList<>();
                for (ReportsDTO reportsDTO : reportsDTOList) {
                    if (reportsDTO.getMenuId().equals(dataMenu.get(i).getMenuID())) {
                        reportList.add(reportsDTO);
                    }
                }
                dataMenu.get(i).setBoardList(boardList);
                dataMenu.get(i).setReportList(reportList);
            }
        }
        return dataMenu;
    }

    @RequestMapping("H5/workbench/report")
    public String report(HttpServletRequest request, HttpServletResponse response, String menuId) {
        BaseMenu baseMenu = getBaseMenu(menuId);
        putModel("menu", baseMenu);
        return "H5/report/report-main";
    }

    /**
     * @Description 综合看板数据过滤
     * @param userBoardList
     *            用户-综合看板数据关联数据
     * @return
     * @CreationDate 2016年3月14日 上午9:33:08
     * @Author lidong(dli@gdeng.cn)
     */
    protected List<BoardDTO> commonBoardFilter(List<SysUserBoard> userBoardList) {
        // 所有看板数据
        List<BoardDTO> boardListTmp = getBoardList();
        List<BoardDTO> boardList = new ArrayList<>();

        for (BoardDTO boardDTO : boardListTmp) {
            for (SysUserBoard userBoard : userBoardList) {
                if ("1".equals(userBoard.getType())) {
                    if (boardDTO.getId().equals(userBoard.getBoardID())) {
                        boardList.add(boardDTO);
                    }
                }
            }
        }
        return boardList;
    }
}
