package com.gudeng.commerce.info.home.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gudeng.commerce.info.customer.dto.BaseMenu;
import com.gudeng.commerce.info.customer.dto.BoardDTO;
import com.gudeng.commerce.info.customer.dto.ReportsDTO;
import com.gudeng.commerce.info.customer.entity.SysRegisterUser;
import com.gudeng.commerce.info.customer.util.Constant;
import com.gudeng.commerce.info.home.util.LoginUserUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.web.controller.BaseController;

public class AdminBaseController extends BaseController {
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public void putModel(String key, Object result) {
        HttpServletRequest request = this.getRequest();
        request.setAttribute(key, result);
    }

    /**
     * @Description getUser 获取登录用户的信息
     * @param request
     * @return
     * @CreationDate 2015年10月20日 下午4:55:44
     * @Author lidong(dli@cnagri-products.com)
     */
    public SysRegisterUser getUser() {
        return (SysRegisterUser) getRequest().getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
    }

    /**
     * @Description 获取登录用户的所有数据分类集合，每个分类对象中包含该分类下报表对象集合与看板数据集合
     * @return
     * @CreationDate 2016年3月7日 下午7:03:47
     * @Author lidong(dli@gdeng.cn)
     */
    @SuppressWarnings("unchecked")
    public List<BaseMenu> getAllBaseMenu() {
        return (List<BaseMenu>) getRequest().getSession().getAttribute(Constant.USER_DATAMENU);
    }

    /**
     * @Description 根据分类ID获取分类对象，该分类对象中包含该分类下报表对象集合与看板数据集合
     * @param request
     * @param menuId
     * @return
     * @CreationDate 2016年3月7日 下午7:08:41
     * @Author lidong(dli@gdeng.cn)
     */
    public BaseMenu getBaseMenuByMenuId(String menuId) {
        List<BaseMenu> allBaseMenu = getAllBaseMenu();
        for (BaseMenu baseMenu : allBaseMenu) {
            if (menuId.equals(baseMenu.getMenuID())) {
                return baseMenu;
            }
        }
        return null;
    }

    /**
     * @Description 根据数据分类ID获取该分类下的看板数据集合
     * @param request
     * @param menuId
     * @return
     * @CreationDate 2016年3月7日 下午7:15:36
     * @Author lidong(dli@gdeng.cn)
     */
    public List<BoardDTO> getBoardListByMenuId(String menuId) {
        BaseMenu baseMenus = getBaseMenuByMenuId(menuId);
        return baseMenus.getBoardList();
    }

    /**
     * @Description 获取登录用户的所有看板数据集合
     * @return
     * @CreationDate 2016年3月11日 下午6:16:36
     * @Author lidong(dli@gdeng.cn)
     */
    public List<BoardDTO> getBoardList() {
        List<BaseMenu> allBaseMenu = getAllBaseMenu();
        List<BoardDTO> boardList = new ArrayList<>();
        for (BaseMenu menu : allBaseMenu) {
            List<BoardDTO> boardListTmp = menu.getBoardList();
            if (boardListTmp != null) {
                for (BoardDTO boardDTO : boardListTmp) {
                    boardList.add(boardDTO);
                }
            }
        }
        return boardList;
    }

    /**
     * @Description 根据数据分类ID获取该分类下的报表数据集合
     * @param request
     * @param menuId
     * @return
     * @CreationDate 2016年3月7日 下午7:17:04
     * @Author lidong(dli@gdeng.cn)
     */
    public List<ReportsDTO> getReportListByMenuId(String menuId) {
        BaseMenu baseMenus = getBaseMenuByMenuId(menuId);
        return baseMenus.getReportList();
    }

    /**
     * @Description 获取登录用户的所有报表数据集合
     * @return
     * @CreationDate 2016年3月11日 下午6:19:01
     * @Author lidong(dli@gdeng.cn)
     */
    public List<ReportsDTO> getReportList() {
        List<BaseMenu> allBaseMenu = getAllBaseMenu();
        List<ReportsDTO> reportList = new ArrayList<>();
        for (BaseMenu menu : allBaseMenu) {
            List<ReportsDTO> reportListTmp = menu.getReportList();
            if (reportListTmp != null) {
                for (ReportsDTO reportDTO : reportListTmp) {
                    reportList.add(reportDTO);
                }
            }
        }
        return reportList;
    }

    /**
     * @Description 根据数据分类ID获取该分类下的menu信息
     * @param request
     * @param menuId
     * @return
     * @CreationDate 2016年3月10日 下午7:15:36
     * @Author liufan
     */
    public BaseMenu getBaseMenu(String menuId) {
        BaseMenu baseMenus = getBaseMenuByMenuId(menuId);
        return baseMenus;
    }

    /**
     * 获取异常信息
     * 
     * @author wwj
     * @date 创建时间：2015年7月23日 下午5:48:13
     * @param e
     * @param logger
     * @return
     *
     */
    public String getExceptionMessage(Exception e, GdLogger logger) {

        e.printStackTrace();
        logger.info(e.getMessage());
        if (e.getMessage() == null) {
            return "操作异常";
        }
        return e.getMessage();
    }

    /**
     * 获取异常信息
     * 
     * @author wwj
     * @date 创建时间：2015年7月29日 下午2:01:09
     * @param e
     * @return
     *
     */
    public String getExceptionMessage(Exception e) {

        e.printStackTrace();
        if (e.getMessage() == null) {
            return "操作异常";
        }
        return e.getMessage();
    }

}
