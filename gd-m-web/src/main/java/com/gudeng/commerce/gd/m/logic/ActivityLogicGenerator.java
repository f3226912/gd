package com.gudeng.commerce.gd.m.logic;

import java.util.HashMap;
import java.util.Map;

import com.gudeng.commerce.gd.m.logic.impl.WeixinRedPacActivityExcuteLogic;
import com.gudeng.commerce.gd.m.logic.itf.ActivityExcuteLogic;
import com.gudeng.commerce.gd.promotion.entity.ActActivityBaseinfoEntity;

public class ActivityLogicGenerator {

	private static Map<String, ActivityExcuteLogic> activityExcuteLogics = new HashMap<String, ActivityExcuteLogic>();

	static {
		registerActivityExcuteLogic(String.valueOf(ActActivityBaseinfoEntity.ACTIVITY_TYPE_QHB), new WeixinRedPacActivityExcuteLogic());
	}
	public static synchronized void registerActivityExcuteLogic(String type, ActivityExcuteLogic logic){
		activityExcuteLogics.put(type, logic);
	}

	public static ActivityExcuteLogic getActivityExcuteLogic(String type){
		return activityExcuteLogics.get(type);
	}
}
