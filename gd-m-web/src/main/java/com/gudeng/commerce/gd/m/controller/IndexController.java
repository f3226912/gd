package com.gudeng.commerce.gd.m.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.m.entity.UserSummary;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.framework.security.core.mgr.SysMenuManager;
import com.gudeng.framework.security.core.mgr.SysRegisterUserManager;
import com.gudeng.framework.security.core.model.SysmenuDTO;
import com.gudeng.framework.security.core.model.SysregisteruserEntity;

@Controller
@RequestMapping("")
public class IndexController extends MBaseController{

	
}
