package com.gudeng.commerce.gd.m.controller.weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.service.ActWechatShareServiceTool;
import com.gudeng.commerce.gd.promotion.entity.ActWechatShareEntity;

/**
 * 微信分享活动
 * 
 * @author lidong
 *
 */
@Controller
@RequestMapping("share")
public class WeixinShareController extends MBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(WeixinShareController.class);
	@Autowired
	private ActWechatShareServiceTool actWechatShareService;

	/**
	 * 用户分享活动链接
	 * 
	 * @param activityId
	 * @param mv
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
				entity.setUpdateTime(new Date());
				Long result = actWechatShareService.save(entity);
				if (result > 0) {
					map.put("msg", "success");
				}
			}
		} catch (Exception e) {
			map.put("msg", "fail");
			logger.error("分享失败", e);
		}
		return map;
	}
}
