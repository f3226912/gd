package com.gudeng.commerce.gd.authority.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.authority.dao.BaseDao;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysResGroup;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysResGroupService;
import com.gudeng.commerce.gd.authority.sysmgr.util.CommonConstant;
import com.gudeng.commerce.gd.authority.sysmgr.util.MessageUtil;

@Service
public class SysResGroupServiceImpl implements SysResGroupService {
	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private MessageUtil messageUtil;

	// @Autowired
	// private SysResGroupMapper sysResGroupMapper;

	@Override
	public void batchDelete(List<String> groupList) throws Exception {
		for (int i = 0; i < groupList.size(); i++) {
			// sysResGroupMapper.delete(groupList.get(i));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resGroupID", groupList.get(i));
			baseDao.execute("SysResGroup.delete", map);
		}
	}

	@Override
	public void batchInsert(List<SysResGroup> groupList) throws Exception {
		for (int i = 0; i < groupList.size(); i++) {
			// sysResGroupMapper.insert(groupList.get(i));
			baseDao.execute("SysResGroup.insert", groupList.get(i));
		}
	}

	@Override
	public void batchUpdate(List<SysResGroup> groupList) throws Exception {
		for (int i = 0; i < groupList.size(); i++) {
			// sysResGroupMapper.update(groupList.get(i));
			baseDao.execute("SysResGroup.update", groupList.get(i));
		}
	}

	@Override
	public String delete(String groupID) throws Exception {
		// 业务验证
		// int count = sysResGroupMapper.checkGroupRelated(groupID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resGroupID", groupID);
		int count = baseDao.execute("SysResGroup.checkGroupRelated", map);
		if (count > 0) {
			throw new Exception("删除失败！请先解除用户的关联！");
		}
		// sysResGroupMapper.delete(groupID);
		baseDao.execute("SysResGroup.delete", map);
		return CommonConstant.COMMON_AJAX_SUCCESS;
	}

	@Override
	public SysResGroup get(String groupID) throws Exception {
		// List<SysResGroup> list = sysResGroupMapper.get(groupID);
		// if (null != list && list.size() > 0) {
		// return list.get(0);
		// }
		// return null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resGroupID", groupID);
		List<SysResGroup> list = baseDao.queryForList("SysResGroup.get", map, SysResGroup.class);
		return (list != null) && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<SysResGroup> getByCondition(Map<String, Object> map) {
		// return sysResGroupMapper.getByCondition(map);
		List<SysResGroup> list = baseDao.queryForList("SysResGroup.getByCondition", map, SysResGroup.class);
		return list;
	}

	@Override
	public List<SysResGroup> getListSysResGroups(Map<String, Object> map) throws Exception {
		// return sysResGroupMapper.getAll(map);
		List<SysResGroup> list = baseDao.queryForList("SysResGroup.getAll", map, SysResGroup.class);
		return list;
	}

	@Override
	public Integer getTotal(Map<String, Object> map) throws Exception {
		// return sysResGroupMapper.getTotal(map);
		return (int) baseDao.queryForObject("SysResGroup.getTotal", map, Integer.class);
	}

	@Override
	public String insert(SysResGroup group) throws Exception {
		// 业务验证
		// int count =
		// sysResGroupMapper.checkGroupName(group.getResGroupName());
		Map<String, Object> map = new HashMap<>();
		map.put("resGroupName", group.getResGroupName());
		int count = baseDao.execute("SysResGroup.checkGroupName", map);
		if (count > 0) {
			return messageUtil.getMessage("sysmgr.sysgroup.nameExist");
		}
		// sysResGroupMapper.insert(group);
		baseDao.execute("SysResGroup.insert", group);
		return CommonConstant.COMMON_AJAX_SUCCESS;
	}

	@Override
	public String update(SysResGroup group) throws Exception {
		// 业务验证
		// int count = sysResGroupMapper.checkGroupIDAndName(group);
		int count = baseDao.execute("SysResGroup.checkGroupIDAndName", group);
		if (count > 0) {
			return messageUtil.getMessage("sysmgr.sysgroup.nameExist");
		}
		// sysResGroupMapper.update(group);
		baseDao.execute("SysResGroup.update", group);
		return CommonConstant.COMMON_AJAX_SUCCESS;
	}

	@Override
	public int checkGroupName(String resGroupName) throws Exception {
		// return sysResGroupMapper.checkGroupName(resGroupName);
		Map<String, Object> map = new HashMap<>();
		map.put("resGroupName", resGroupName);
		return baseDao.execute("SysResGroup.checkGroupIDAndName", map);
	}

}
