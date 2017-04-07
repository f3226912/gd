package com.gudeng.commerce.gd.m.controller.weixin.jpgys;

import cn.gdeng.weixin.base.bean.api.UserInfo;

import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.controller.weixin.WeiXinUtils;
import com.gudeng.commerce.gd.m.service.ActActivityGiftExchangToolService;
import com.gudeng.commerce.gd.m.service.ActivityToolService;
import com.gudeng.commerce.gd.m.service.WechatMemberToolService;
import com.gudeng.commerce.gd.m.service.WechatStarsToolService;
import com.gudeng.commerce.gd.m.service.WechatUserinfoToolService;
import com.gudeng.commerce.gd.m.util.BaseWXUtil;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.dto.WechatMemberDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * 点赞相关
 *
 * @author lidong
 */
@Controller
@RequestMapping("jpgys/star")
public class StarController extends MBaseController {
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
	private WechatMemberToolService wechatMemberToolService;
	@Autowired
	private ActActivityGiftExchangToolService actActivityGiftExchangToolService;


	/**
	 * 好友给我的总点赞数
	 *
	 * @param openid 我的openid
	 * @return
	 */
	@RequestMapping(value = "count")
	@ResponseBody
	public Map<String, Object> starCount(String openid, Integer actId) {
		Map<String, Object> map = new HashMap<>();
		map.put("starCount", 0);
		if (openid != null) {
			try {
				map.put("openId1", openid);
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
	 * 当前用户活动总积分
	 * @param actId
	 * @return
	 */
	@RequestMapping(value = "score")
	@ResponseBody
	public Map<String, Object> score(String actId) {
		Map<String, Object> map = new HashMap<>();
		map.put("score", 0);
		WechatMemberDTO user = getUser(getRequest());
		if (user != null) {
			try {
				int currentScore = 0;
				// 用户-活动(主要查询积分)--用户总积分
				ActReUserActivityDto userActivity = activityToolService.getReUserActivityInfo(actId, user.getMemberId().toString());
				//查询当前用户已使用的积分
				int sumExchangeScore = actActivityGiftExchangToolService.getUserExchangeScore(actId, user.getMemberId().toString());
				//当前用户当前剩余积分
				if(userActivity!=null){
					currentScore = (userActivity.getScore()==null?0:userActivity.getScore())-sumExchangeScore;
				}else {
					currentScore = 0;
				}
				//ActReUserActivityDto reUserActivityInfo = activityToolService.getReUserActivityInfo(actId, user.getMemberId().toString());
				map.put("score", currentScore);
				map.put("msg", "success");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 邀请好友注册数
	 * @param actId
	 * @return
	 */
	@RequestMapping(value = "register")
	@ResponseBody
	public Map<String, Object> register(String actId) {
		Map<String, Object> map = new HashMap<>();
		map.put("register", 0);
		UserInfo weixinUser = getWeixinUser(getRequest());
		if (weixinUser != null) {
			try {
				map.put("type",1);//注册用户
				map.put("actId",actId);//活动id
				map.put("inviteId",weixinUser.getOpenid());//当前用户
				Integer total = wechatMemberToolService.getTotal(map);
				map.put("register", total);//总邀请注册数
				map.put("msg", "success");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}


}
