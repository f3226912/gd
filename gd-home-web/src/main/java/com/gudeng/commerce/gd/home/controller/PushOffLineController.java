package com.gudeng.commerce.gd.home.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushOfflineDTO;
import com.gudeng.commerce.gd.customer.entity.PushOffline;
import com.gudeng.commerce.gd.home.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.PushOffLineToolService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
public class PushOffLineController  extends HomeBaseController{
	
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(PushOffLineController.class);
	
	@Autowired
	private PushOffLineToolService pushOffLineToolService;
	
	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;
	
	
	@ResponseBody
	@RequestMapping("pushOffLine/add")
	public String isProductDelete(PushOfflineDTO dto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String mobile = dto.getMemberMobile();
			if (StringUtils.isEmpty(mobile)){
				map.put("status", 0);
				map.put("errorCode", "1");
				return JSONObject.toJSONString(map);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberMobile", mobile);
			PushOffline res = pushOffLineToolService.getOffLinePushInCondition(params);
			if (null != res){
				map.put("status", 0);
				map.put("errorCode", "2");
				return JSONObject.toJSONString(map);
			}
			params = new HashMap<String, Object>();
			params.put("mobile", mobile);
			params.put("startRow", 0);
			params.put("endRow", 10);
			List<MemberBaseinfoDTO> list = memberBaseinfoToolService.getBySearch(params);
			if (null == list || list.isEmpty()){
				map.put("status", 0);
				//该会员手机号尚未注册谷登农批网, 请先注册
				map.put("errorCode", "4");
				return JSONObject.toJSONString(map);
			}
			PushOffline entity = new PushOffline();
			entity.setCreateTime(new Date());
			entity.setIndustry(dto.getIndustry());
			entity.setMemberMobile(dto.getMemberMobile());
			entity.setPushMobile(dto.getPushMobile());
			String pushName = dto.getPushName();
			entity.setPushName(pushName);
			entity.setSource(dto.getSource());
			pushOffLineToolService.saveOffLinePushInfo(entity);
			map.put("status", 1);
		} catch (Exception e) {
			logger.info("save pushOffLineInfo with ex : ", e);
			map.put("status", 0);
			map.put("errorCode", "3");
			return JSONObject.toJSONString(map);
		}
		return JSONObject.toJSONString(map);
	}
}
