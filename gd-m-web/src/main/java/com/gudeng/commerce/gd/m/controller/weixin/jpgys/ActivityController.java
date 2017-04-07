package com.gudeng.commerce.gd.m.controller.weixin.jpgys;

import cn.gdeng.weixin.base.bean.api.UserInfo;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.service.ActActivityGiftExchangToolService;
import com.gudeng.commerce.gd.m.service.ActivityToolService;
import com.gudeng.commerce.gd.m.service.ReActivityGiftToolService;
import com.gudeng.commerce.gd.promotion.dto.*;
import com.gudeng.commerce.gd.promotion.entity.ActActivityBaseinfoEntity;
import com.gudeng.commerce.gd.promotion.entity.ActGiftExchangeApplyEntity;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller(value = "jpgysActivityController")
@RequestMapping("jpgys/activity")
public class ActivityController extends MBaseController {

	@Autowired
	private ActivityToolService activityToolService;
	@Autowired
	private ActActivityGiftExchangToolService actActivityGiftExchangToolService;

	@Autowired
	private ReActivityGiftToolService reActivityGiftToolService;
	
	private final Object exchangeLock = new Object();
	/**
	 * 记录日志
	 */
	private static Logger logger = LoggerFactory.getLogger(ActivityController.class);

	/**
	 * 进入活动首页
	 *
	 * @param activityId    活动id
	 * @param unionid       访问者unionid
	 * @param openId        访问者openid
	 * @param invitorOpenid 邀请人openid
	 * @param result        页面数据
	 * @return
	 */
	@RequestMapping("init")
	public String index(String activityId, String unionid, String openId, String invitorOpenid, Map<String, Object> result) {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			WechatMemberDTO member = getUser(getRequest());//当前登陆用户
			// 判断用户是否为空, 不为空则表示微信用户已绑定谷登平台用户
			if (member != null) {
				result.put("userid", member.getMemberId());
			} else {
				// 未绑定用户
				result.put("userid", null);
			}
			result.put("activityId", activityId);
			result.put("unionid", unionid);
			result.put("openId", openId);
			result.put("invitorOpenid", invitorOpenid);
		} catch (Exception e) {
			logger.info("initRedPacketActivityData with ex : ", e);
			result.put("status", 0);
			result.put("errorCode", 0);
			result.put("message", "初始化活动页面异常...");
		}
		return "weixin/jpgys/index";
	}
	/**
	 * 活动详情排行榜
	 * @param activityId
	 * @param userid
	 * @param result
	 * @return
	 */
	@RequestMapping("initRankingList")
	@ResponseBody
	public String index(String activityId, String userid, Map<String, Object> result) {
		Map<String, Object> params = new HashMap<String, Object>();
		ActReUserActivityDto reUserActivityinfo = null;
		try {
			if (StringUtils.isBlank(activityId)) {
				result.put("status", 0);
				throw new Exception("活动id为空...");
			}
			params.put("activityId", activityId);
			//用户的活动信息, 获取用户积分及用户在当前活动的排名
			if(StringUtils.isNotBlank(userid)){
				params.put("userid", userid);
				reUserActivityinfo = activityToolService.getUserActivityByUserid(params);
			}else{
				reUserActivityinfo = new ActReUserActivityDto();
			}
		
			result.put("curActivityinfo", reUserActivityinfo);
			//查询当前活动的所有用户活动信息
			params.put("endRow", 100);
			params.put("startRow", 0);
			List<ActReUserActivityDto> userActivityList = activityToolService.getWechatUserInfoByUserid(params);
			result.put("userActivityList", userActivityList);
			result.put("status", 1);
		} catch (Exception e) {
			logger.info("initRankingList with ex : ", e);
			result.put("status", 0);
			result.put("errorCode", 0);
			result.put("message", "初始化活动详情排行榜页面异常...");
		}
		return JSONObject.toJSONString(result);
	}
	/**
	 * 跳转兑换礼品界面
	 * 
	 * @param activityId
	 * @param result
	 * @return
	 */
	@RequestMapping("turnToExchangeGift")
	@ResponseBody
	public String turnToExchangeGift(String activityId, Map<String, Object> result) {
		try {
			if (StringUtils.isBlank(activityId)) {
				result.put("status", 0);
				throw new Exception("活动id为空...");
			}
			// 判断微信用户是否关注谷登公众号(农商友)
			UserInfo user = getWeixinUser(getRequest());
			// 0-未关注, 1-已关注
			/*if (user == null || 0 == user.getSubscribe()) {
				result.put("status", 0);
				result.put("errorCode", 2);
				result.put("message", "您还未关注我们的公众号..");
				return JSONObject.toJSONString(result);
			}*/
			
			// 判断微信用户是否绑定谷登平台用户, 为空表示未绑定
			WechatMemberDTO member = getUser(getRequest());//当前登陆用户
			String userid = "";
			if (member == null) {
				//未绑定用户
				result.put("status", 0);
				result.put("errorCode", 1);
				result.put("message", "您还未登录..");
				return JSONObject.toJSONString(result);
			}else{
				userid = member.getMemberId().toString();
			}
			
			int currentScore = getCurrentScore(activityId,userid);
			result.put("score", currentScore);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("activityId", activityId);
			// 获取活动的所有礼品信息
			List<ActReActivitityGiftDto> giftList = reActivityGiftToolService.getActivityGiftList(params);
			//活动的详细信息
			ActActivityBaseinfoDTO activity = activityToolService.getActivityInfo(activityId);
			// 兑换有效期
			if (null !=activity) {
				SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日");
				result.put("effectiveStartTime", myFmt.format(activity.getEffectiveStartTime()));
				result.put("effectiveEndTime", myFmt.format(activity.getEffectiveEndTime()));
			}
			params.put("endRow", 10);
			params.put("startRow", 0);
			params.put("usePage", 1);
			// 获取活动礼品兑换记录
			List<ActGiftExchangeApplyDto> recordlist = actActivityGiftExchangToolService.getActivityExchangeRecord(params);
			// 隐藏手机号信息
			replaceMoblie(recordlist);
			result.put("giftList", giftList);
			result.put("recordlist", recordlist);
			result.put("activityId", activityId);
			result.put("status", 1);
			result.put("openId", user.getOpenid());
			result.put("unionId", user.getUnionid());
		} catch (Exception e) {
			result.put("status", 0);
			result.put("message", "查询数据异常...");
			logger.info("exchangeGift with ex : ", e);
		}
		return JSONObject.toJSONString(result);
	}
	private void replaceMoblie(List<ActGiftExchangeApplyDto> recordlist) {
		String mobile = "";
		char[] chars = null;
		int start = 4, end = 8;
		for (ActGiftExchangeApplyDto item : recordlist) {
			mobile = item.getMobile();
			if (mobile != null && mobile.length() == 11) {
				chars = mobile.toCharArray();
				for (int i = start; i < end; i++) {
					chars[i] = '*';
				}
				item.setMobile(new String(chars));
			}
		}
	}

	/**
	 * 我的礼品
	 * 
	 * @param activityId
	 * @param result
	 * @return
	 */
	@RequestMapping("myGift")
	@ResponseBody
	public String myGift(String activityId, Map<String, Object> result) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (StringUtils.isBlank(activityId)) {
				result.put("status", 0);
				throw new Exception("活动id为空...");
			}
			params.put("activityId", activityId);
			// 判断微信用户是否关注谷登公众号(农商友)
			UserInfo user = getWeixinUser(getRequest());
			// 0-未关注, 1-已关注
			/*if (user == null || 0 == user.getSubscribe()) {
				result.put("status", 0);
				result.put("errorCode", 2);
				result.put("message", "您还未关注我们的公众号..");
				return JSONObject.toJSONString(result);
			}*/
			// 判断微信用户是否绑定谷登平台用户, 为空表示未绑定
			WechatMemberDTO member = getUser(getRequest());//当前登陆用户
			String userid = "";
			if (member == null) {
				//未绑定用户
				result.put("status", 0);
				result.put("errorCode", 1);
				result.put("message", "您还未登录..");
				return JSONObject.toJSONString(result);
			}else{
				userid = member.getMemberId().toString();
				params.put("userid", userid);
			}
			// 获取用户在本次活动的礼品兑换记录
			List<ActGiftExchangeApplyDto> recordlist = actActivityGiftExchangToolService.getActivityExchangeRecord(params);
			if (recordlist == null || recordlist.isEmpty()) {
				result.put("status", 0);
				result.put("errorCode", 3);
				result.put("message", "礼品记录为空");
			} else {
				result.put("status", 1);
				result.put("myGiftList", recordlist);
			}
			//当前可用积分
			int currentScore= getCurrentScore(activityId, userid);
			result.put("score", currentScore);
			result.put("activityId", activityId);
			result.put("userid", userid);
			
			result.put("openId", user.getOpenid());
			result.put("unionId", user.getUnionid());
		} catch (Exception e) {
			result.put("status", 0);
			result.put("errorCode", 0);
			result.put("message", "服务端异常...");
			logger.info("get myGift with ex : ", e);
		}
		return JSONObject.toJSONString(result);
	}
	/**
	 * 获取当前可用积分
	 * @param activityId
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int getCurrentScore(String activityId,String userid) throws Exception{
		int currentScore = 0;
		// 用户-活动(主要查询积分)--用户总积分
		ActReUserActivityDto userActivity = activityToolService.getReUserActivityInfo(activityId, userid);
		//查询当前用户已使用的积分
		int sumExchangeScore = actActivityGiftExchangToolService.getUserExchangeScore(activityId, userid);
		//当前用户当前剩余积分
		if(userActivity!=null){
			currentScore = (userActivity.getScore()==null?0:userActivity.getScore())-sumExchangeScore;
		}else {
			currentScore = 0;
		}
		return currentScore;
	}

	/**
	 * 兑换礼品
	 * 
	 * @param activityId
	 * @param userid
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping("exchangeGift")
	public String exchangeGift(String activityId, String userid, String giftId, Map<String, Object> result) {
		try {
			if (StringUtils.isBlank(activityId) || StringUtils.isBlank(userid) || StringUtils.isBlank(giftId)) {
				result.put("status", 0);
				throw new Exception("活动id为空或者userid为空或者giftId为空...");
			}
			Map<String, Object> params = new HashMap<String, Object>();
			int scoreNeeded = 0, giftLeft = 0, scoreLeft = 0;
			params.put("activityId", activityId);
			params.put("userid", userid);
			params.put("gift_id", giftId);
			// 礼品兑换记录
			List<ActGiftExchangeApplyDto> exchangeList = actActivityGiftExchangToolService.getActivityExchangeRecord(params);
			//礼品兑换次数限制不从数据库的表中读取，修改为写死。
			ActActivityBaseinfoDTO activity = activityToolService.getActivityInfo(activityId);

			// 兑换次数限制, 若无限制, 则跳过
			if (!(exchangeList == null || exchangeList.isEmpty())
					&& exchangeList.size() >= ActActivityBaseinfoEntity.EXCHANGE_TIME_ONE) {
				result.put("status", 0);
				result.put("errorCode", 1);
				result.put("message", "您已经兑换过此次礼品, 超出兑换次数限制...");
				return JSONObject.toJSONString(result);
			}
			Date now = new Date();
			// 兑换有效期
			if (null !=activity && !(now.after(activity.getEffectiveStartTime()) && now.before(activity.getEffectiveEndTime()))) {
				SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日");
				result.put("status", 0);
				result.put("errorCode", 5);
				result.put("effectiveStartTime", myFmt.format(activity.getEffectiveStartTime()));
				result.put("effectiveEndTime", myFmt.format(activity.getEffectiveEndTime()));
				result.put("message", "不在兑换时间, 不能兑换礼品...");
				return JSONObject.toJSONString(result);
			}
			//清空map
			params.clear();
			params.put("activityId", activityId);
			params.put("giftId", giftId);
			// 加锁
			synchronized (exchangeLock) {
				// 根据活动id和礼品id查询<活动-礼品>, 结果要么有一条记录, 要么为空
				List<ActReActivitityGiftDto> list = reActivityGiftToolService.getActivityGiftList(params);
				if (list == null || list.isEmpty()) {
					result.put("status", 0);
					result.put("errorCode", 2);
					result.put("message", "礼品记录异常...");
					return JSONObject.toJSONString(result);
				} else {
					// 活动-礼品
					ActReActivitityGiftDto activityGift = list.get(0);
					// 检测奖品余量
					giftLeft = list.get(0).getCost();
					if (giftLeft <= 0) {
						result.put("status", 0);
						result.put("errorCode", 3);
						result.put("message", "礼品已经被抢光啦，赶紧兑换其它礼品吧!");
						return JSONObject.toJSONString(result);
					}
					// 所需积分
					scoreNeeded = activityGift.getExchangeScore();
					int currentScore= getCurrentScore(activityId, userid);
					scoreLeft = currentScore - scoreNeeded;
					if (scoreLeft < 0) {
						result.put("status", 0);
						result.put("errorCode", 4);
						result.put("message", "你的红包金额不足,赶快邀请更多好友获得红包");
						return JSONObject.toJSONString(result);
					}

					// 更新用户积分---兑换礼品时不需要再更新用户总积分，因为updateReUserActivityInfo方法是更新总积分
					//userActivity.setScore(scoreLeft);
					//activityToolService.updateReUserActivityInfo(userActivity);

					// 新增活动的礼品兑换记录(即用户申请兑换礼品)
					ActGiftExchangeApplyEntity actGiftExchangeApply = new ActGiftExchangeApplyEntity();
					actGiftExchangeApply.setActivity_id(Integer.valueOf(activityId));
					actGiftExchangeApply.setUserid(Long.valueOf(userid));
					actGiftExchangeApply.setGift_id(activityGift.getGiftId());
					actGiftExchangeApply.setStatus("1");
					actGiftExchangeApply.setCreateUserId(userid);
					actGiftExchangeApply.setCreateTime(now);
					actGiftExchangeApply.setUpdateTime(now);
					actGiftExchangeApply.setUpdateUserId(userid);
					actGiftExchangeApply.setScore(scoreNeeded);
					//actActivityGiftExchangToolService.insertActivityExchangeRecord(actGiftExchangeApply);

					// 更新活动奖品余量
					//params = new HashMap<String, Object>();
					Map<String, Object> params1 = new HashMap<String, Object>();
					params1.put("activityId", activityId);
					params1.put("giftId", giftId);
					params1.put("amount", giftLeft - 1);
					//reActivityGiftToolService.updateActivityGift(params);
					// 更新礼品信息(当前库存)
					ActGiftBaseinfoDTO giftInfo = activityToolService.getByGiftBaseinfoId(Integer.valueOf(giftId));
					params.clear();
					Map<String, Object> params2 = new HashMap<String, Object>();
					params2.put("stockTotal", giftInfo.getStockTotal() - 1);
					params2.put("updateUserId", userid);
					params2.put("updateTime", now);
					params2.put("id", giftId);
					//activityToolService.updateGiftBaseInfo(params);
					//处理所有的兑换操作，加入事务控制
					activityToolService.updateAllActivityInfo(actGiftExchangeApply,params1,params2);
				}
			}
			//用于兑换礼品后显示当前积分
			int currentScore = getCurrentScore(activityId,userid);
			result.put("score", currentScore);
			result.put("status", 1);
		} catch (Exception e) {
			result.put("status", 0);
			result.put("errorCode", 0);
			result.put("message", "服务端异常...");
			logger.info("exchangeGift myGift with ex : ", e);
		}
		return JSONObject.toJSONString(result);
	}
}
