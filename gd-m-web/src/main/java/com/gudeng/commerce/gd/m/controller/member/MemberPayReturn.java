package com.gudeng.commerce.gd.m.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.m.controller.MBaseController;

@Controller
@RequestMapping("member/payReturn")
public class MemberPayReturn extends MBaseController{

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MemberPayReturn.class);
	
	@RequestMapping("success")
	public String showBill(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		Thread.sleep(2000); //TODO 暂时处理异步通知问题，下期加到支付中心返回页面等待时间
		
		putModel("code", getString("respCode"));
		return "H5/member/paySuccess";
	}
	
	
}
