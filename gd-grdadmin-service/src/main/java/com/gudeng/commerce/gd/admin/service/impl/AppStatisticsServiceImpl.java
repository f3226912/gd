package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.AppStatisticsService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.entity.AppVersion;
import com.gudeng.commerce.gd.customer.service.AppVersionService;

public class AppStatisticsServiceImpl implements AppStatisticsService {

    @Autowired
    public GdProperties gdProperties;
	

	
	

	@Override
	public void saveAppVersion(AppVersion appVersion) throws Exception {
		
		if(getHessianService().isExistNum(appVersion.getPlatform(),appVersion.getType(), appVersion.getNum())){
			
			throw new Exception("此APP类型的版本号"+appVersion.getNum()+"已存在！"); 
		}else{
			getHessianService().persistAppVersion(appVersion);
		}
		
	}

	@Override
	public void deleteAppVersion(String id) throws MalformedURLException {
		
        	
        	getHessianService().deleteAppVersion(id);
		
	}



	private AppVersionService getHessianService() throws MalformedURLException {

		String hessianUrl = gdProperties.getAppVersionServiceUrl();
		HessianProxyFactory factory = new HessianProxyFactory();
		AppVersionService appVersionService = (AppVersionService) factory.create(AppVersionService.class, hessianUrl);

		return appVersionService;
	}



	@Override
	public void updateAppVersion(AppVersion appVersion) throws MalformedURLException, Exception {
		if(appVersion==null )
			throw new Exception("版本信息不能为空");
		
		AppVersion version = getHessianService().getAppVersionById(appVersion.getId());
		
		if(version==null)
			throw new RuntimeException("出现异常，查询版本信息为空!");
		
//		version.setUpdateTime(new Date());
//		version.setUpdateUserId(appVersion.getUpdateUserId());
//		version.setRemark(appVersion.getRemark());
//		version.setNeedEnforce(appVersion.getNeedEnforce());
		getHessianService().updateAppVersion(appVersion);
	}



	@Override
	public AppVersion getAppVersion(String id) throws MalformedURLException,Exception {
			
		return getHessianService().getAppVersionById(id);
	}

	@Override
	public Integer getAppVersionCount(Map<String, Object> map) throws MalformedURLException, Exception {
		return getHessianService().getAppVersionCount(map);
	}

	@Override
	public List<AppVersion> getAppVersionList(Map<String, Object> map) throws MalformedURLException, Exception {
		return getHessianService().getAppVersionList(map);
	}

	@Override
	public AppVersion getAppLastVersion(String type, String platform) throws MalformedURLException, Exception {
		return getHessianService().getLastAppVersion(type,platform);
	}

}
