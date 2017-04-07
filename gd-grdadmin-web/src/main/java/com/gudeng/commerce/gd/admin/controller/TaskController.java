package com.gudeng.commerce.gd.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.task.service.StaticHtmlService;

/**
 * 
 * TaskController
 * semon
 * 2015年11月18日 上午10:32:05
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("task")
public class TaskController {
	
	/*@Autowired
	private StaticHtmlService staticHtmlService;
	
	*//**
	 * 生成静态页
	 * staticHtml(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @return 
	 *String
	 *//*
	@RequestMapping("/statichtml")
	public String staticHtml(){
		
		staticHtmlService.generatorHtml();

		return "task/statichtml";
		
	}*/

}
