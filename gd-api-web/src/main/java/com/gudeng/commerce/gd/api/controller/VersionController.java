package com.gudeng.commerce.gd.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AppStatisticsService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.entity.AppVersion;

@Controller
@RequestMapping("version")
public class VersionController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(VersionController.class);
	
	@Autowired
	private AppStatisticsService appStatisticsService;
	
	@RequestMapping("/check")
	public void check(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String appType = GSONUtils.getJsonValueStr(jsonStr, "appType");
			String clientVersion = GSONUtils.getJsonValueStr(jsonStr, "appVersion");
//			String appType = request.getParameter("appType");
//			String clientVersion = request.getParameter("appVersion");
			if(StringUtils.isBlank(appType) || StringUtils.isBlank(clientVersion)){
				setEncodeResult(result, ErrorCodeEnum.PARAM_IS_NULL, request, response);
				return;
			}
			//获取最新版
			Map<String,Object> map = new HashMap<String,Object>();
			AppVersion appVersion = appStatisticsService.getLastAppVersion(appType,"2");
			if(appVersion==null){
				map.put("update", "no");
				result.setObject(map);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			
			//2016-08-15
			Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("type", appType);
			queryMap.put("platform", "2");
			queryMap.put("needEnforce", "1");
			
			AppVersion lastNeedenForceAppVersion = appStatisticsService.getLastAppVersionByMap(queryMap);
			map.put("lastNeedenForceAppVersion", lastNeedenForceAppVersion==null?"":lastNeedenForceAppVersion.getNum());
			
			if(compareVersion(appVersion.getNum(),clientVersion)>0){//如果当前版本低于数据库的最新版本，可以更新
				map.put("update", "yes");
				map.put("newApp", appVersion);
			}else{
				map.put("update", "no");
				map.put("newApp", null);
			}
			result.setObject(map);
//			renderJson(result, request, response);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			e.printStackTrace();
//			renderJson(result, request, response);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}

	}
	
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
	/**
	 * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
	 * @param version1 最新版本
	 * @param version2 app客户端传入的版本
	 * @return
	 */
	public int compareVersion(String version1, String version2) throws Exception {
		if (version1 == null || version2 == null) {
			throw new Exception("compareVersion error:illegal params.");
		}
		if (!(version2.indexOf(".") > 0)) {
			return 1;
		}
		String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
		String[] versionArray2 = version2.split("\\.");
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
		int diff = 0;
		while (idx < minLength
				&& (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
				&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
			++idx;
		}
		//如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
		return diff;
	}
}
