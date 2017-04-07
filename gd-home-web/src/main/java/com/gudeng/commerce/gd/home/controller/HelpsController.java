package com.gudeng.commerce.gd.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("help")
public class HelpsController extends HomeBaseController {
	
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(StoreController.class);
	
	@RequestMapping("/{option}")
	public String aboutUs(@PathVariable String option) {
		return "helps/"+option;
	}

}
