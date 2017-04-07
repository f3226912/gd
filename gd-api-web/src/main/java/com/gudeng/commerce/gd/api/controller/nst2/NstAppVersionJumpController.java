package com.gudeng.commerce.gd.api.controller.nst2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 农速通版本 跳转接口
 * @author xiaojun
 *
 */
@Controller
@RequestMapping("update/appVersion")
public class NstAppVersionJumpController extends GDAPIBaseController{
	private static final GdLogger logger = GdLoggerFactory.getLogger(NstOrderBaseinfoController.class);
	@Autowired
	private MemberToolService memberToolService;
	@Autowired
	private GdProperties gdProperties;
	/**
	 * 更新跳转地址
	 * @param request
	 * @return
	 */
	@RequestMapping("/jump")
	public String jump(HttpServletRequest request) {
		try {
			String memberId=request.getParameter("memberId");
			Integer appType=memberToolService.getAppTypeByMemberId(memberId);
			if (appType!=null) {
				//货主下载页
				if (appType.intValue()==1) {
					return "redirect:"+gdProperties.getGdHomeUrl()+"nst/goods.html";
				//车主下载页
				}else if (appType.intValue()==2) {
					return "redirect:"+gdProperties.getGdHomeUrl()+"nst/car.html";
				//物流公司下载页
				}else if (appType.intValue()==3) {
					return "redirect:"+gdProperties.getGdHomeUrl()+"nst/company.html";
				}
			}
		} catch (Exception e) {
			logger.warn("出错了"+e);
		}
		//默认全部下载页
		return "redirect:"+gdProperties.getGdHomeUrl()+"nst/nst.html";
	}
}
