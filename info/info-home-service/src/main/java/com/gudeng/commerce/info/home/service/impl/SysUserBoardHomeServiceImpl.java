package com.gudeng.commerce.info.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.customer.entity.SysUserBoard;
import com.gudeng.commerce.info.customer.service.SysUserBoardService;
import com.gudeng.commerce.info.home.service.SysUserBoardHomeService;
import com.gudeng.commerce.info.home.util.GdProperties;
@Service
public class SysUserBoardHomeServiceImpl implements SysUserBoardHomeService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static SysUserBoardService  sysUserBoardService;
	/**
	 * 功能描述:SysUserBoardService接口服务
	 * 
	 * @param
	 * @return
	 * @throws MalformedURLException 
	 */
	protected SysUserBoardService getHessionSysUserBoardService() throws MalformedURLException{
		String url =gdProperties.getSysUserBoardUrl();
		if(sysUserBoardService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sysUserBoardService=(SysUserBoardService)factory.create(SysUserBoardService.class, url);
		}
		return sysUserBoardService;
	}
	@Override
	public List<SysUserBoard> getUserBoardList(Map<String, Object> map)
			throws Exception {
		return getHessionSysUserBoardService().getUserBoardList(map);
	}
	@Override
	public String insert(SysUserBoard sysUserBoard) throws Exception {
		return getHessionSysUserBoardService().insert(sysUserBoard);
	}
	@Override
	public int delete(Map<String,Object> map) throws Exception {
		return getHessionSysUserBoardService().delete(map);
	}

}
