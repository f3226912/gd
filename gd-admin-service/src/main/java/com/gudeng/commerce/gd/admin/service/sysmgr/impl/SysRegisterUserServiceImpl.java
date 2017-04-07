
package com.gudeng.commerce.gd.admin.service.sysmgr.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysRegisterUserService;


/**
 * 用户
 *
 */
@Service
public class SysRegisterUserServiceImpl implements SysRegisterUserAdminService {

	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static  SysRegisterUserService  sysRegisterUserService;
	
	
	private SysRegisterUserService hessianSysRegisterUserService() throws MalformedURLException {
		String hessianUrl = gdProperties.getSysRegisterUserServiceUrl();
		if(sysRegisterUserService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			sysRegisterUserService = (SysRegisterUserService) factory.create(SysRegisterUserService.class, hessianUrl);
		}
		return sysRegisterUserService;
	}
	
	@Override
	public void delete(String userID) throws Exception{
		hessianSysRegisterUserService().delete(userID);
	}

	@Override
	public SysRegisterUser get(String userID) throws Exception {
		return hessianSysRegisterUserService().get(userID);
	}

	@Override
	public List<SysRegisterUser> getListSysRegisterUser(Map<String, Object> map)
			throws Exception {
		return hessianSysRegisterUserService().getListSysRegisterUser(map);
	}

	@Override
	public String insert(SysRegisterUser sysRegisterUser) throws Exception {
		
		return hessianSysRegisterUserService().insert(sysRegisterUser);
	}

	@Override
	public String update(SysRegisterUser sysRegisterUser) throws Exception {
		
		return hessianSysRegisterUserService().update(sysRegisterUser);
	}

	@Override
	public void updateBatch(String ids,String userId) throws Exception {
		hessianSysRegisterUserService().updateBatch(ids, userId);
	}

	@Override
	public List<SysRegisterUser> getByCondition(Map<String, Object> map)  throws Exception{
		return hessianSysRegisterUserService().getByCondition(map);
	}

	@Override
	public Integer getTotal(Map<String, Object> map)  throws Exception{
		return hessianSysRegisterUserService().getTotal(map);
	}

	@Override
	public int updateLockUser(SysRegisterUser user)  throws Exception{
		return hessianSysRegisterUserService().updateLockUser(user);
	}

	@Override
	public int updateUnlockUser(SysRegisterUser user)  throws Exception{
		return hessianSysRegisterUserService().updateUnlockUser(user);
	}

	@Override
	public int updateResetPassword(SysRegisterUser user)  throws Exception{
		return hessianSysRegisterUserService().updateResetPassword(user);
	}

	@Override
	public int checkUserCode(String userCode)  throws Exception{
		return hessianSysRegisterUserService().checkUserCode(userCode);
	}

	@Override
	public int checkUserName(String userName)  throws Exception{
		return hessianSysRegisterUserService().checkUserName(userName);
	}

	@Override
	public List<SysRegisterUser> getBuyerByCondition(Map<String, Object> map)  throws Exception{

		return hessianSysRegisterUserService().getBuyerByCondition(map);
	}

	@Override
	public String updateUserPwd(Map<String, Object> map) throws Exception {
		return hessianSysRegisterUserService().updateUserPwd(map);
	}

	@Override
	public List<SysRegisterUser> getCanteenPurchase(Map<String, Object> map)  throws Exception{
		return hessianSysRegisterUserService().getCanteenPurchase(map);
	}

	@Override
	public void updateUserStatusByOrgID(SysRegisterUser user) throws Exception{
		
		hessianSysRegisterUserService().updateUserStatusByOrgID(user);
	}
}
