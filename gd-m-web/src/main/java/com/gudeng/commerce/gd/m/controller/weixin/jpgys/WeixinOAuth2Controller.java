package com.gudeng.commerce.gd.m.controller.weixin.jpgys;

import cn.gdeng.weixin.base.bean.api.oauth.OAuthData;
import com.gudeng.commerce.gd.m.Constant;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.controller.weixin.WeiXinUtils;
import com.gudeng.commerce.gd.m.util.BaseWXUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Random;

/**
 * @Description 金牌供应商微信公众平台跳转
 * @Project gd-m-web
 * @ClassName BusinessMController.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月25日 下午5:14:22
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
@Controller(value = "jpgysWeixinOAuth2Controller")
@RequestMapping("jpgys/weixin")
public class WeixinOAuth2Controller extends MBaseController {
	@Autowired
	private BaseWXUtil gdWXUtil;
	@Autowired
	private WeiXinUtils weiXinUtils;

	/**
	 * 微信活动授权认证入口,根据不同活动设置不同的回调地址
	 *
	 * @param activityId 活动ID
	 * @param mv
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "activity/{activityId}")
	public ModelAndView dispatcher(@PathVariable Integer activityId, ModelAndView mv) {
		// 回调地址
		String activityUrl = get80BasePath(getRequest()) + "jpgys/weixin/index/" + activityId;
		// 跳转活动控制器
		mv.setViewName("redirect:" + String.format(Constant.oauthUrl, gdWXUtil.getWeiXinConf().getAppId(), activityUrl, new Random().nextInt(100)));
		return mv;
	}

	/**
	 * 微信公众号菜单跳转首页
	 *
	 * @param mv
	 * @param grant      code
	 *                   微信网页授权码
	 * @param activityId 活动id
	 * @param attr       跳转参数
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "index/{activityId}")
	public ModelAndView index(ModelAndView mv, OAuthData grant, @PathVariable Integer activityId, RedirectAttributes attr) {
		mv = weiXinUtils.oAuth2(mv, grant, null, attr);
		if (StringUtils.isNotEmpty(mv.getViewName())) {
			return mv;
		}
		attr.addAttribute("activityId", activityId);
		attr.addAttribute("openId", getWeixinUser(getRequest()).getOpenid());
		mv.setViewName("redirect:" + get80BasePath(getRequest()) + "jpgys/userManage/dispatcher");
		return mv;

	}
}
