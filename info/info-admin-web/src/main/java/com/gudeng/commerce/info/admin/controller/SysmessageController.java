package com.gudeng.commerce.info.admin.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.info.admin.service.SysRegisterUserAdminService;
import com.gudeng.commerce.info.admin.service.SysUserRoleAdminService;
import com.gudeng.commerce.info.admin.service.SysmessageToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.admin.util.LoginUserUtil;
import com.gudeng.commerce.info.customer.dto.SysmessageDTO;
import com.gudeng.commerce.info.customer.entity.SysRegisterUser;
import com.gudeng.commerce.info.customer.util.CommonConstant;
import com.gudeng.commerce.info.customer.util.CommonConstant.UserLowType;
import com.gudeng.commerce.info.customer.util.CommonConstant.UserType;
import com.gudeng.commerce.info.customer.util.IdCreater;
import com.gudeng.commerce.info.customer.util.StringUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 用户controller
 * 
 * @author wwj
 * 
 */
@Controller
@RequestMapping("message")
public class SysmessageController extends AdminBaseController {

    private static final GdLogger logger = GdLoggerFactory.getLogger(SysmessageController.class);

    /** 用户Serivce */
    @Autowired
    private SysmessageToolService sysmessageService;

    @RequestMapping("list")
    public String list(HttpServletRequest request) {
        return "message/index";
    }

    /**
     * 用户列表;
     * 
     * @param request
     * @return path
     * @throws Exception
     */
    @RequestMapping("query")
    @ResponseBody
    public String query(HttpServletRequest request, HttpServletResponse response) {

        try {
            Map<String, Object> map = new HashMap<String, Object>();
            // 设置查询参数
            map.put("title", StringUtils.trimToNull(request.getParameter("title")));
            map.put("total", sysmessageService.getTotalByCondition(map));
            // 设定分页,排序
            setCommParameters(request, map);
            // list
            List<SysmessageDTO> list = sysmessageService.getListByConditon(map);
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
    @RequestMapping("addInit")
    public String addInit(HttpServletRequest request) {
        request.setAttribute("action", "add");
        return "message/addDto";
    }

    /**
     * 新增用户- 保存动作;
     * 
     * @param request
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public String add(HttpServletRequest request, SysmessageDTO sysmessageDTO) {
        try {
            // 参数
            sysmessageDTO.setCreateUserName(LoginUserUtil.getLoginUserName(request));
            sysmessageDTO.setCreateUserId(LoginUserUtil.getLoginUserId(request));
            sysmessageDTO.setTitle(request.getParameter("title"));
            sysmessageDTO.setContent(request.getParameter("content"));
            // 新增
            int result = sysmessageService.insert(sysmessageDTO);
            if (result > 0) {
                return "success";
            }
        } catch (Exception e) {
            return getExceptionMessage(e, logger);
        }
        return null;
    }

    @RequestMapping("detail")
    public String detail(HttpServletRequest request, Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("startRow", 0);
        map.put("endRow", 1);
        try {
            List<SysmessageDTO> list = sysmessageService.getListByConditon(map);
            if (list != null && list.size() > 0) {
                putModel("dto", list.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("action", "view");
        return "message/addDto";
    }

    /**
     * 根据ID进行删除操作
     * 
     * @param id
     * @param request
     * @return
     * @author 李冬
     * @time 2015年10月13日 下午5:01:37
     */
    @RequestMapping(value = "marketprice/delete")
    @ResponseBody
    public String deleteById(String ids, HttpServletRequest request) {
        // try {
        // if (StringUtils.isEmpty(ids)) {
        // return "failed";
        // }
        // List<String> list = Arrays.asList(ids.split(","));
        // int i = pricesToolService.deletePrices(list);
        // return i > 0 ? "success" : "failed";
        // } catch (Exception e) {
        //
        // }
        return null;
    }

}
