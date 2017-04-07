package com.gudeng.commerce.gd.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.service.DemoService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("demo")
public class DemoController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(DemoController.class);
	
	@Autowired
	public DemoService demoService;

	/**
	 * demo
	 * @return
	 */
	 

}
