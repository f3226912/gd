package com.gudeng.commerce.gd.admin.service.sysmgr.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysResGroupAdminService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysResGroup;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysResGroupService;
@Service
public class SysResGroupServiceImpl implements SysResGroupAdminService {
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static  SysResGroupService  SysResGroupService;
	
	
	private SysResGroupService hessianSysResGroupService() throws MalformedURLException {
		String hessianUrl = gdProperties.getSysResGroupServiceUrl();
		if(SysResGroupService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			SysResGroupService = (SysResGroupService) factory.create(SysResGroupService.class, hessianUrl);
		}
		return SysResGroupService;
	}
	
	
	@Override
	public void batchDelete(List<String> groupList)throws Exception  {
		hessianSysResGroupService().batchDelete(groupList);
	}

	@Override
	public void batchInsert(List<SysResGroup> groupList)throws Exception  {
		hessianSysResGroupService().batchInsert(groupList);
	}

	@Override
	public void batchUpdate(List<SysResGroup> groupList)throws Exception  {
		hessianSysResGroupService().batchUpdate(groupList);

	}

	@Override
	public String delete(String groupID)throws Exception  {
		return hessianSysResGroupService().delete(groupID);
	}

	@Override
	public SysResGroup get(String groupID)throws Exception  {
		return hessianSysResGroupService().get(groupID);
	}

	@Override
	public List<SysResGroup> getByCondition(Map<String, Object> map) throws Exception {
		return hessianSysResGroupService().getByCondition(map);
	}

	@Override
	public List<SysResGroup> getListSysResGroups(Map<String, Object> map)
			throws Exception  {
		
		return hessianSysResGroupService().getListSysResGroups(map);
	}

	@Override
	public Integer getTotal(Map<String, Object> map)throws Exception  {
		return hessianSysResGroupService().getTotal(map);
	}

	@Override
	public String insert(SysResGroup group)throws Exception  {
		return hessianSysResGroupService().insert(group);
	}

	@Override
	public String update(SysResGroup group)throws Exception  {
		return hessianSysResGroupService().update(group);
	}

	@Override
	public int checkGroupName(String resGroupName)throws Exception  {
		return hessianSysResGroupService().checkGroupName(resGroupName);
	}

}
