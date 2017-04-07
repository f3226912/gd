package com.gudeng.commerce.gd.admin.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.entity.AppVersion;

public interface AppStatisticsService {

	/**
	 * app统计管理接口
	 *
	 */
//	public AppRecordDTO count(String servicetype) throws Exception;
	
	
	/**
	 * 查看版本列表
	 * @param appVersion
	 * @param page
	 * @return
	 * @throws MalformedURLException
	 */
//	public AppVersionResult queryAppVersionPage(AppVersion appVersion, QueryParamExt<Map<String, Object>> page) throws MalformedURLException;
	
	/**
	 * 保存版本
	 * @param appVersion
	 * @throws MalformedURLException 
	 * @throws Exception 
	 */
	public void saveAppVersion(AppVersion appVersion) throws MalformedURLException, Exception;
	
	/**
	 * 删除版本
	 * @param idList
	 * @throws MalformedURLException 
	 */
	public void deleteAppVersion(String idList) throws MalformedURLException;
	/**
	 * 修改版本信息
	 * @author Administrator 2015年1月27日 上午11:03:00
	 * <p>Title: updateAppVersion </p>
	 * <p>Description: </p>
	 * @param appVersion
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public void updateAppVersion(AppVersion appVersion) throws MalformedURLException, Exception;
	/**
	 * 查询版本信息
	 * @author Administrator 2015年1月27日 上午11:43:02
	 * <p>Title: getAppVersion </p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public AppVersion getAppVersion(String id) throws MalformedURLException, Exception;
	
	public AppVersion getAppLastVersion(String type,String platform) throws MalformedURLException, Exception;
	
	public Integer getAppVersionCount(Map<String,Object> map)throws MalformedURLException, Exception;
	
	public List<AppVersion> getAppVersionList(Map<String,Object> map)throws MalformedURLException, Exception;
}
