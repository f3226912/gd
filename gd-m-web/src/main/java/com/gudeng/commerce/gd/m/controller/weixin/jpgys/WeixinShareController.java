package com.gudeng.commerce.gd.m.controller.weixin.jpgys;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.service.ActWechatShareServiceTool;
import com.gudeng.commerce.gd.m.service.ActivityToolService;
import com.gudeng.commerce.gd.m.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.entity.ActActivityScoreRecordEntity;
import com.gudeng.commerce.gd.promotion.entity.ActWechatShareEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信分享活动
 *
 * @author lidong
 */
@Controller(value = "jpgysWeixinShareController")
@RequestMapping("jpgys/share")
public class WeixinShareController extends MBaseController {
	/**
	 * 记录日志
	 */
	private static Logger logger = LoggerFactory.getLogger(WeixinShareController.class);
	@Autowired
	private ActWechatShareServiceTool actWechatShareService;
	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;
	@Autowired
	private ActivityToolService activityToolService;

	/**
	 * 用户分享活动链接
	 *
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "")
	@ResponseBody
	public Map<String, Object> index(ActWechatShareEntity entity) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isNotEmpty(entity.getOpenid())) {
				entity.setCreateTime(new Date());
				Long result = actWechatShareService.save(entity);
				if (result > 0) {
					map.put("msg", "success");
					Short shareType = entity.getShareType();
					if (shareType != null && shareType == 2) {//分享朋友圈加积分
						Long userId = entity.getUserid();
						if (userId != null && userId > 0) {
							map.put("memberId", userId);
							MemberBaseinfoDTO member = memberBaseinfoToolService.getById(userId.toString());
							map.put("userid", userId);
							map.put("activityId", entity.getActivityId());
							map.put("type", "2");
							Integer total = activityToolService.getScoreRecordTotal2(map);//分享得分记录次数
							if (total == null || total < 1) {
								ActActivityScoreRecordEntity record = new ActActivityScoreRecordEntity();
								record.setType("2");//分享类型积分
								record.setOpenId(entity.getOpenid());
								record.setActivityId(entity.getActivityId());
								record.setMobile(member.getMobile());
								record.setUserid(userId);
								record.setCreateTime(new Date());
								record.setScore(10);//分享得分
								record.setReceiveTime(new Date());
								/*Long id = activityToolService.insertReActivityScoreRecoreInfo(record);//增加分享得分
								if (id != null && id > 0) {
									//更新用户活动总积分记录
									ActReUserActivityDto actReUserActivityDto = activityToolService.getReUserActivityInfo(entity.getActivityId().toString(), userId.toString());
									actReUserActivityDto.setScore(actReUserActivityDto.getScore() + 10);
									activityToolService.updateReUserActivityInfo(actReUserActivityDto);
								}*/
							}
						}
					}
				}
			}
		} catch (Exception e) {
			map.put("msg", "fail");
			logger.error("分享失败", e);
		}
		return map;
	}
}
