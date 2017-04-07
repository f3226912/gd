package com.gudeng.commerce.gd.m.controller.weixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gudeng.commerce.gd.m.Constant;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.util.BaseWXUtil;

import cn.gdeng.weixin.base.bean.api.UserInfo;
import cn.gdeng.weixin.base.bean.api.oauth.OAuthData;
import cn.gdeng.weixin.base.conf.JSSDKConf;
import cn.gdeng.weixin.base.core.JSSDKAPI;
import cn.gdeng.weixin.base.core.OAuthAPI;
import cn.gdeng.weixin.base.core.UserManageAPI;

/**
 * @Description 微信公众平台跳转
 * @Project gd-m-web
 * @ClassName BusinessMController.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月25日 下午5:14:22
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 * 
 */
@Controller
@RequestMapping("weixin")
public class WeixinOAuth2Controller extends MBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(WeixinOAuth2Controller.class);

	private static final String oauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";

	@Autowired
	private BaseWXUtil gdWXUtil;

	/**
	 * 微信活动授权认证入口,根据不同活动设置不同的回调地址
	 * 
	 * @param activityId
	 *            活动ID
	 * @param mv
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "activity/{activityId}")
	public ModelAndView dispatcher(@PathVariable Integer activityId, ModelAndView mv) {
		// 回调地址
		String activityUrl = get80BasePath(getRequest()) + "weixin/index/" + activityId;
		// 跳转活动控制器
		mv.setViewName("redirect:" + String.format(oauthUrl, gdWXUtil.getWeiXinConf().getAppId(), activityUrl, new Random().nextInt(100)));
		return mv;
	}

	/**
	 * 微信公众号菜单跳转首页
	 * 
	 * @param mv
	 * @param code
	 *            微信网页授权码
	 * @param activityId
	 *            活动id
	 * @param attr
	 *            跳转参数
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "index/{activityId}")
	public ModelAndView index(ModelAndView mv, OAuthData grant, @PathVariable Integer activityId, RedirectAttributes attr) {
		String openId = null;
		try {
			// 通过code换取网页授权access_token
			grant.setAppId(gdWXUtil.getWeiXinConf().getAppId());
			grant.setAppSecret(gdWXUtil.getWeiXinConf().getAppSecret());
			grant = OAuthAPI.getAccess_token(grant);
			if (grant != null && StringUtils.isNotEmpty(grant.getOpenid())) {
				openId = grant.getOpenid();
				UserInfo userInfo = new UserInfo();
				userInfo.setAccess_token(gdWXUtil.getAccessToken());
				userInfo.setOpenid(openId);
				userInfo = UserManageAPI.getUserInfo(userInfo);
				if (userInfo != null && StringUtils.isNotEmpty(userInfo.getUnionid())) {
					// 保存session
					getRequest().getSession().setAttribute(Constant.SYSTEM_WEIXINUSER, userInfo);
					attr.addAttribute("unionid", userInfo.getUnionid());
				} else {
					mv.setViewName("weixin/error/404");
					return mv;
				}
			} else {
				mv.setViewName("weixin/error/404");
				return mv;
			}
		} catch (Exception e) {
			mv.setViewName("weixin/error/404");
			logger.error("OAuthException获取用户信息出错：", e);
			return mv;
		}
		attr.addAttribute("activityId", activityId);
		attr.addAttribute("openId", openId);
		mv.setViewName("redirect:/userManage/dispatcher");
		return mv;

	}

	/**
	 * 微信JS-SDK权限验证
	 * 
	 * @param url
	 *            JS-SDK所在的当前页面地址
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "signatureJSSDK")
	@ResponseBody
	public Map<String, Object> signatureJSSDK(String url) {
		Map<String, Object> map = new HashMap<>();
		JSSDKConf signature = new JSSDKConf();
		try {
			signature.setAppId(gdWXUtil.getWeiXinConf().getAppId());
			signature.setTicket(gdWXUtil.getJsApiToken());
			signature.setUrl(url);
			signature = JSSDKAPI.signatureJSSDKConf(signature);
		} catch (Exception e) {
			map.put("msg", "验证错误");
		}
		map.put("result", signature);
		return map;
	}

	/**
	 * 非微信浏览器跳转地址
	 * 
	 * @param mv
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "invalidBS")
	public ModelAndView invalidBS(ModelAndView mv) {
		mv.setViewName("weixin/error/40001");
		return mv;
	}
}
