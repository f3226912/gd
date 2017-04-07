package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.EnPostLogManageService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.entity.EnPostLogEntity;
import com.gudeng.commerce.gd.order.service.EnPostLogService;
@Service
public class EnPostLogManageServiceImpl implements EnPostLogManageService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static EnPostLogService enPostLogService;
	
	
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return  getHessianCarsService().getTotal(map);
	}
	

	
	protected EnPostLogService getHessianCarsService() throws MalformedURLException {
		String url =  gdProperties.getProperties().getProperty("gd.enPostLog.url");
		if(enPostLogService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			enPostLogService = (EnPostLogService) factory.create(EnPostLogService.class, url);
		}
		return enPostLogService;
	}



	@Override
	public List<EnPostLogEntity> getByCondition(Map<String, Object> map)
			throws Exception {

		return getHessianCarsService().getByCondition(map);
	}

    @Override
    public EnPostLogEntity getById(Map<String, Object> map)
            throws Exception {

        return getHessianCarsService().getById(map);
    }

}
