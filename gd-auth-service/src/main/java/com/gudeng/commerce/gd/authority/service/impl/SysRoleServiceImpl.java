package com.gudeng.commerce.gd.authority.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.authority.dao.BaseDao;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRole;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysUserRole;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysRoleService;
import com.gudeng.commerce.gd.authority.sysmgr.util.CommonConstant;
import com.gudeng.commerce.gd.authority.sysmgr.util.IdCreater;

/**
 * 角色操作实现类;
 * 
 */
@SuppressWarnings("unchecked")
@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private BaseDao<?> baseDao;

	// @Autowired
	// public SysRoleMapper sysRoleMapper;

	// @Autowired
	// private SysUserRoleMapper sysUserRoleMapper;

	// @Autowired
	// private SysRoleManagerMapper sysRoleManagerMapper;

	@Override
	public List<SysRole> getAll(Map map) throws Exception {
		// return sysRoleMapper.getAll(map);
		List<SysRole> list = baseDao.queryForList("SysRole.getAll", map,
				SysRole.class);
		return list;
	}

	@Override
	public List<SysRole> getAll() {
		// return sysRoleMapper.getAll();
		List<SysRole> list = baseDao.queryForList("SysRole.getAll",
				new HashMap<>(), SysRole.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> getByCondition(Map map) {
		// return sysRoleMapper.getByCondition(map);
		List<SysRole> list = baseDao.queryForList("SysRole.getByCondition",
				map, SysRole.class);
		return list;
	}

	@Override
	public int getTotal(Map<String, Object> map) {
		// return sysRoleMapper.getTotal(map);
		return (int) baseDao.queryForObject("SysRole.getTotal", map,
				Integer.class);
	}

	@Override
	public String insert(SysRole sysRole) {
		// 判断是否有重复角色名
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleName", sysRole.getRoleName());
		// SysRoleBizCheck srb = new SysRoleBizCheck();
		// // 检查角色名是否已经被占用了
		// srb.checkRoleName(sysRoleMapper, map);
		// if (srb.hasError()) {
		// return srb.getErrorMsg();
		// }
		List<SysRole> list = baseDao.queryForList("SysRole.getAll", map,
				SysRole.class);
		if (list != null && list.size() > 0) {
			return "角色已存在";
		}
		sysRole.setRoleID(IdCreater.newId());
		// 进行添加操作
		// sysRoleMapper.insert(sysRole);
		baseDao.execute("SysRole.insert", sysRole);
		return CommonConstant.COMMON_AJAX_SUCCESS;
	}

	@Override
	public String update(SysRole sysRole) throws Exception {

		// SysRoleBizCheck sysRoleBizCheck = new SysRoleBizCheck();
		// // 修改的时候判断数据是否存在（对数据进行验证）
		// sysRoleBizCheck.checkRoleID(sysRoleMapper, sysRole);
		// if (sysRoleBizCheck.hasError()) {
		// return sysRoleBizCheck.getErrorMsg();
		// }
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleName", sysRole.getRoleName());
		List<SysRole> list = baseDao.queryForList("SysRole.getAll", map,
				SysRole.class);
		if (list != null && list.size() > 0) {
			return "角色已存在";
		}

		// 执行修改动作
		// sysRoleMapper.update(sysRole);
		baseDao.execute("SysRole.update", sysRole);
		return CommonConstant.COMMON_AJAX_SUCCESS;
	}

	@Override
	@Transactional
	public void delete(String roleIDs) throws Exception {

		List<String> roleIdslist = Arrays.asList(roleIDs.split(","));
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysUserRole> sulist = null;
		SysRole role = null;
		for (String roleID : roleIdslist) {
			map.put("roleID", roleID);
			// 查询该角色下是否有分配用户
			// sulist = sysUserRoleMapper.getUserRoleList(map);
			sulist = baseDao.queryForList("SysUserRole.getUserRoleList", map,
					SysUserRole.class);
			// 如果删除的ID中有关联结果，则返回
			if (sulist.size() > 0) {
				// 根据角色ID查询角色
				role = this.getSysRoleById(roleID);
				throw new Exception("[" + role.getRoleName() + "]角色已分配用户不能删除！");
			} else {
				// 删除系统角色菜单按钮关系表原有的信息
				// sysRoleManagerMapper.deleteBtn(roleID);
				baseDao.execute("SysRoleManager.deleteBtn", map);
				// 删除系统角色菜单关系表原有的信息
				// sysRoleManagerMapper.deleteMenu(roleID);
				baseDao.execute("SysRoleManager.deleteMenu", map);
				// 再删除角色
				// sysRoleMapper.delete(roleID);
				baseDao.execute("SysRole.delete", map);
			}
		}
	}

	@Override
	public SysRole getSysRoleById(String roleID) throws Exception {
		// return sysRoleMapper.getSysRoleById(roleID);
		Map<String, Object> map = new HashMap<>();
		map.put("roleID", roleID);
		return baseDao.queryForObject("SysRole.getSysRoleById", map,
				SysRole.class);
	}
}
