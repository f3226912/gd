package com.gudeng.commerce.gd.m.controller.weixin.jpgys;

import cn.gdeng.weixin.base.bean.api.UserInfo;
import cn.gdeng.weixin.base.bean.api.oauth.OAuthData;
import cn.gdeng.weixin.base.core.UserManageAPI;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.m.Constant;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.controller.weixin.WeiXinUtils;
import com.gudeng.commerce.gd.m.service.ActivityToolService;
import com.gudeng.commerce.gd.m.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.m.service.WechatStarsToolService;
import com.gudeng.commerce.gd.m.service.WechatUserinfoToolService;
import com.gudeng.commerce.gd.m.util.BaseWXUtil;
import com.gudeng.commerce.gd.promotion.dto.WechatMemberDTO;
import com.gudeng.commerce.gd.promotion.dto.WechatUserinfoDTO;
import com.gudeng.commerce.gd.promotion.entity.ActWechatInviteEntity;
import com.gudeng.commerce.gd.promotion.entity.WechatStarsEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.*;

/**
 * 微信分享邀请访问
 *
 * @author lidong
 */
@Controller(value = "jpgysWeixinInviteController")
@RequestMapping("jpgys/invite")
public class WeixinInviteController extends MBaseController {
	@Autowired
	private BaseWXUtil gdWXUtil;
	@Autowired
	private WeiXinUtils weiXinUtils;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Autowired
	private WechatUserinfoToolService wechatUserinfoToolService;
	@Autowired
	private WechatStarsToolService wechatStarsToolService;
	@Autowired
	private ActivityToolService activityToolService;
	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;


	/**
	 * 用户访问分享的链接进入活动
	 *
	 * @param activityId 活动id
	 * @param shareType  分享类型
	 * @param mv
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "{shareType}/{activityId}/{invitorOpenid}")
	public ModelAndView dispatcher(@PathVariable Integer activityId, @PathVariable Integer shareType, @PathVariable String invitorOpenid, ModelAndView mv) {
		// 回调地址
		String activityUrl = get80BasePath(getRequest()) + "jpgys/invite/index/" + activityId + "/" + shareType + "/" + invitorOpenid;
		// 跳转微信网页授权页面
		mv.setViewName("redirect:" + String.format(Constant.oauthUrl, gdWXUtil.getWeiXinConf().getAppId(), activityUrl, new Random().nextInt(100)));
		return mv;
	}

	/**
	 * 微信网页授权页面回调地址，微信公众号分享链接跳转首页
	 *
	 * @param mv
	 * @param grant  微信网页授权码
	 * @param entity 活动id
	 * @param attr   跳转参数
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
		// 记录分享链接访问数据,设置邀请人
		final UserInfo weixinUser = getWeixinUser(getRequest());
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				weiXinUtils.invite(entity, weixinUser);
			}
		});
		mv.setViewName("redirect:/jpgys/userManage/dispatcher");
		return mv;
	}

	/**
	 * 好友总点赞数
	 *
	 * @param invitorOpenid 邀请人openid
	 * @return
	 */
	@RequestMapping(value = "star/count")
	@ResponseBody
	public Map<String, Object> starCount(String invitorOpenid, Integer actId) {
		Map<String, Object> map = new HashMap<>();
		map.put("starCount", 0);
		if (invitorOpenid != null) {
			try {
				map.put("openId1", invitorOpenid);
				map.put("actId", actId);
				int total = wechatStarsToolService.getTotal(map);
				map.put("starCount", total);
				map.put("msg", "success");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 邀请人微信昵称
	 *
	 * @param invitorOpenid 邀请人openid
	 * @return
	 */
	@RequestMapping(value = "star/nickname")
	@ResponseBody
	public Map<String, Object> index(String invitorOpenid) {
		Map<String, Object> map = new HashMap<>();
		if (invitorOpenid != null) {
			map.put("openid", invitorOpenid);
			try {
				List<WechatUserinfoDTO> userList = wechatUserinfoToolService.getList(map);
				String nickname;
				if (userList != null && userList.size() == 1) {
					nickname = userList.get(0).getNickname();
					if (StringUtils.isEmpty(nickname)) {
						nickname = getNickName(invitorOpenid);
					} else {
						nickname = URLDecoder.decode(nickname, "UTF-8");
					}
				} else {
					nickname = getNickName(invitorOpenid);
				}
				map.put("nickname", nickname);//邀请人昵称
				map.put("msg", "success");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 从微信服务器获取用户信息的昵称
	 *
	 * @param openid 微信用户openid
	 * @return
	 */
	private String getNickName(String openid) {
		UserInfo user = new UserInfo();
		user.setAccess_token(gdWXUtil.getAccessToken());
		user.setOpenid(openid);
		UserInfo userInfo = UserManageAPI.getUserInfo(user);
		return userInfo.getNickname();
	}

	/**
	 * 给好友点赞
	 *
	 * @param entity 点赞数据模型
	 * @return
	 */
	@RequestMapping(value = "star/click")
	@ResponseBody
	public Map<String, Object> index(final WechatStarsEntity entity) {
		final Map<String, Object> map = new HashMap<>();
		if (entity.getOpenId1() != null && entity.getOpenId2() != null && entity.getActId() != null) {
			if (entity.getOpenId1().equals(entity.getOpenId2())) {
				//自己不可为自己点赞
				map.put("msg", "不可为自己点赞");
				return map;
			}
			try {
				map.put("openId1", entity.getOpenId1());
				map.put("actId", entity.getActId());
				map.put("openId2", entity.getOpenId2());
				int total = wechatStarsToolService.getTotal(map);
				if (total < 1) {
					final WechatMemberDTO user = getUser(getRequest());
					//未被点过赞，记录点赞数据,增加邀请人点赞积分
					//1 增加邀请人点赞积分
					taskExecutor.execute(new Runnable() {
						@Override
						public void run() {
							 String mobile = null;
							if (user != null && user.getMemberId() != null) {
								map.put("memberId", user.getMemberId());
								try {
									MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getById(user.getMemberId().toString());
									mobile = memberBaseinfoDTO.getMobile();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							//增加邀请人点赞积分，加上被邀请人手机号
							weiXinUtils.insertInvitorScore(entity.getActId(), entity.getOpenId2(), entity.getOpenId1(), mobile);
						}
					});
					entity.setCreateTime(new Date());
					//2 记录点赞数据
					Long id = wechatStarsToolService.insert(entity);
					if (id != null && id > 0) {
						map.put("msg", "success");
					}
				} else {
					map.put("msg", "您已为该好友点赞");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

}
