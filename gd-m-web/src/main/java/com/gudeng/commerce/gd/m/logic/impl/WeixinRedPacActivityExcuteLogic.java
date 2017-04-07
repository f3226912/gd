package com.gudeng.commerce.gd.m.logic.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.m.bean.activity.ActivityResult;
import com.gudeng.commerce.gd.m.logic.itf.ActivityExcuteLogic;
import com.gudeng.commerce.gd.m.logic.utils.RandomScoreGenerator;
import com.gudeng.commerce.gd.m.service.ActivityToolService;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.entity.ActActivityScoreRecordEntity;
import com.gudeng.commerce.gd.promotion.entity.ActReUserActivityEntity;

/**
 * 微信抢红包活动
 */
public class WeixinRedPacActivityExcuteLogic implements ActivityExcuteLogic {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(WeixinRedPacActivityExcuteLogic.class);

	@Override
	public ActivityResult doExcute(Map<String, Object> params) throws Exception {

		ActivityResult result = new ActivityResult();
		Map<String, Object> map = new HashMap<String, Object>();
		String activityId = (String) params.get("activityId");
		String userid = (String) params.get("userid");
		int type = (Integer) params.get("type");
		result.setType(type);
		try {
			ActivityToolService activityToolService = (ActivityToolService) params.get("activityToolService");
			//用户-活动  关系信息
			ActReUserActivityDto reUserActivityinfo = activityToolService.getReUserActivityInfo(activityId, userid);
			//检查剩余次数
			if (reUserActivityinfo.getJoinTimesLeft() <= 0) {
				map.put("status", 0);
				map.put("errorCode", 1);
				map.put("message", "您的抢红包机会已经用完了");
				result.setResult(map);
				return result;
			}
			int score = 0 ;
			String isFirst = reUserActivityinfo.getFirstJoin() ;
			//是否是第一次参与
			if (ActReUserActivityEntity.IS_FIRST_JOIN_YES.equalsIgnoreCase(isFirst)){
				score = 2;
				reUserActivityinfo.setFirstJoin(ActReUserActivityEntity.IS_FIRST_JOIN_NO);
			}else {
				//根据设定的比率随机获取积分
				score = RandomScoreGenerator.randomGenScore();
			}
			//抢红包次数减一
			int joinTimesLeft = reUserActivityinfo.getJoinTimesLeft() - 1;
			reUserActivityinfo.setJoinTimesLeft(joinTimesLeft);
			//	score --> 所获积分, userScore --> <用户-活动>积分
			int userScore = reUserActivityinfo.getScore() + score ;
			reUserActivityinfo.setScore(userScore);
			//更新
			int res = activityToolService.updateReUserActivityInfo(reUserActivityinfo);
			//新增积分获取记录
			Date now = new Date();
			ActActivityScoreRecordEntity entity = new ActActivityScoreRecordEntity();
			entity.setActivityId(Integer.valueOf(activityId));
			entity.setUserid(Long.valueOf(userid));
			entity.setReceiveTime(now);
			entity.setScore(score);
			entity.setCreateTime(now);
			entity.setUpdateTime(now);
			entity.setCreateUserId(userid);
			entity.setUpdateUserId(userid);
			activityToolService.insertReActivityScoreRecoreInfo(entity);
			//
			map.put("status", 1);
			map.put("score", score);
			map.put("userScore", userScore);
			map.put("joinTimesLeft", joinTimesLeft);
			map.put("isFirst", isFirst);
			map.put("result", res);
		} catch (Exception e) {
			logger.info("doExcute activity[type = {}, activityId = {}] with ex : {}", new Object[]{type, activityId, e});
			map.put("status", 0);
			map.put("errorCode", 0);
			map.put("message", "抢红包活动执行异常");
			result.setResult(map);
			return result;
		}
		result.setResult(map);
		return result;
	}

}
