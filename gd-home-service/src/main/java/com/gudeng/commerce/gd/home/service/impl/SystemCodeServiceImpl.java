package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.entity.SystemCode;
import com.gudeng.commerce.gd.home.service.SystemCodeService;
import com.gudeng.commerce.gd.home.util.GdProperties;

/**
 * @Description 数据字典操作服务接口类
 * @Project gd-home-intf
 * @ClassName SystemCodeService.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年10月22日 下午2:49:01
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Service
public class SystemCodeServiceImpl implements SystemCodeService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static SystemCodeService systemCodeService;
	
	protected SystemCodeService getHessianSystemCodeService() throws MalformedURLException {
		String url = gdProperties.getSystemCodeServiceUrl();
		if(systemCodeService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			systemCodeService = (SystemCodeService) factory.create(SystemCodeService.class, url);
		}
		return systemCodeService;
	}
	
	/**
	 * @Description showValueByCode 根据编码类型以及key值从数据字典中获取value值
	 * @param codeType
	 *            数据类型
	 * @param codeKey
	 *            键值
	 * @return
	 * @CreationDate 2015年10月22日 下午2:50:30
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public String showValueByCode(String codeType, String codeKey) throws Exception{
		return getHessianSystemCodeService().showValueByCode(codeType, codeKey);
	}

	@Override
	public List<SystemCode> getListViaType(String type) throws Exception {
		return  getHessianSystemCodeService().getListViaType(type);
	}
}
