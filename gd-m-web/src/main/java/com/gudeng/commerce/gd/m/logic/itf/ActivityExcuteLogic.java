package com.gudeng.commerce.gd.m.logic.itf;

import java.util.Map;

import com.gudeng.commerce.gd.m.bean.activity.ActivityResult;

/**
 * 活动逻辑
 */
public interface ActivityExcuteLogic {

	/**
	 * 执行活动
	 * @param params
	 * @return
	 */
	ActivityResult doExcute(Map<String, Object> params) throws Exception;
}
