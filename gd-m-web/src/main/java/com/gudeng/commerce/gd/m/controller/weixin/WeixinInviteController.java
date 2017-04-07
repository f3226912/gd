package com.gudeng.commerce.gd.m.controller.weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gudeng.commerce.gd.m.Constant;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.service.ActWechatInviteServiceTool;
import com.gudeng.commerce.gd.m.util.BaseWXUtil;
import com.gudeng.commerce.gd.promotion.dto.ActWechatInviteDTO;
import com.gudeng.commerce.gd.promotion.entity.ActWechatInviteEntity;

import cn.gdeng.weixin.base.bean.api.UserInfo;
import cn.gdeng.weixin.base.bean.api.oauth.OAuthData;
import cn.gdeng.weixin.base.core.OAuthAPI;
import cn.gdeng.weixin.base.core.UserManageAPI;

import javax.annotation.Resource;

/**
 * 微信分享邀请访问
 * 
 * @author lidong
 *
 */
@Controller
@RequestMapping("invite")
public class WeixinInviteController extends MBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(WeixinInviteController.class);

	private static final String oauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";

	@Autowired
	private ActWechatInviteServiceTool actWechatInviteService;
	@Autowired
	private BaseWXUtil gdWXUtil;
	@Autowired
	private WeiXinUtils weiXinUtils;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	/**
	 * 用户访问分享的链接进入活动
	 * 
	 * @param activityId
	 *            活动id
	 * @param shareType
	 *            分享类型
	 * @param mv
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "{shareType}/{activityId}/{invitorOpenid}")
	public ModelAndView dispatcher(@PathVariable Integer activityId, @PathVariable Integer shareType, @PathVariable String invitorOpenid, ModelAndView mv) {
		// 回调地址
		String activityUrl = get80BasePath(getRequest()) + "invite/index/" + activityId + "/" + shareType + "/" + invitorOpenid;
		// 跳转微信网页授权页面
		mv.setViewName("redirect:" + String.format(oauthUrl, gdWXUtil.getWeiXinConf().getAppId(), activityUrl, new Random().nextInt(100)));
		return mv;
	}

	/**
	 * 微信网页授权页面回调地址，微信公众号分享链接跳转首页
	 * 
	 * @param mv
	 * @param grant
	 *            微信网页授权码
	 * @param entity
	 *            活动id
	 * @param attr
	 *            跳转参数
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "index/{avtivityId}/{shareType}/{invitorOpenid}")
	public ModelAndView index(ModelAndView mv, OAuthData grant, final ActWechatInviteEntity entity, RedirectAttributes attr) {
		mv = weiXinUtils.oAuth2(mv, grant, entity, attr);
		if (StringUtils.isNotEmpty(mv.getViewName())) {
			return mv;
		}
		attr.addAttribute("activityId", entity.getAvtivityId());
		attr.addAttribute("openId", getWeixinUser(getRequest()).getOpenid());
		attr.addAttribute("invitorOpenid", entity.getInvitorOpenid());
		// 记录分享链接访问数据
		/*taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				weiXinUtils.invite(entity);
			}
		});*/
		mv.setViewName("redirect:/userManage/dispatcher");
		return mv;
	}

}
