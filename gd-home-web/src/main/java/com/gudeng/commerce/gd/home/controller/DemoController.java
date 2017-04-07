package com.gudeng.commerce.gd.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.home.service.DemoService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("demo")
public class DemoController extends HomeBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(DemoController.class);
	
	@Autowired
	public DemoService demoService;

	/**
	 * demo
	 * @return
	 */
	@RequestMapping("")
	public String demo(){
		try {
			putModel("dictDTO",demoService.getById("1"));
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "demo/demo";
	}

}
