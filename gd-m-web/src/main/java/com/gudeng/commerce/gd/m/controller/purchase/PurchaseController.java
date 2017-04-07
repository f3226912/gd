package com.gudeng.commerce.gd.m.controller.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.controller.marketing.PromotionAcitivityController;

@Controller
@RequestMapping("purchase")
public class PurchaseController extends MBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PromotionAcitivityController.class);
	@RequestMapping("promotionOwner")
	public String showBill(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		//return "forword:/WEB-INF/jsp/h5/marketing/gys-detail.htm";
		//request.getRequestDispatcher("/h5/marketing/gys-detail.html").forward(request, response);
		//System.out.println("redirect:" + PathUtil.getBasePath(request) + "gys-detail.html");
		//return "redirect:" + PathUtil.getBasePath(request) + "gys-detail.html";
		return "H5/purchase/cgh-my";
	}
	
	@RequestMapping("promotionDetail")
	public String promotionDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		//return "forword:/WEB-INF/jsp/h5/marketing/gys-detail.htm";
		//request.getRequestDispatcher("/h5/marketing/gys-detail.html").forward(request, response);
		//System.out.println("redirect:" + PathUtil.getBasePath(request) + "gys-detail.html");
		//return "redirect:" + PathUtil.getBasePath(request) + "gys-detail.html";
		return "H5/purchase/cgh-details";
	}
}
