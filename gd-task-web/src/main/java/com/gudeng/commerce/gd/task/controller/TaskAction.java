package com.gudeng.commerce.gd.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.task.service.PushMessageTaskService;
import com.gudeng.commerce.gd.task.service.StaticHtmlService;

@Controller
public class TaskAction {
	
	@Autowired
	private StaticHtmlService staticHtmlService;
	@Autowired
	private PushMessageTaskService pushMessageTaskService;
	
	@RequestMapping("/statichtml")
	public @ResponseBody String staticHtml(){
		
		staticHtmlService.generatorHtml();

		return "success";
		
	}
	
	@RequestMapping("/push")
	public @ResponseBody String push(){
		
		try {
			pushMessageTaskService.PushMessageTask();
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "发送失败";
		}
		
		
		
	}
	
	

}
