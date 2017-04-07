package com.gudeng.commerce.gd.authority.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.authority.dao.BaseDao;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysUserRole;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysRegisterUserService;
import com.gudeng.commerce.gd.authority.sysmgr.util.CommonConstant;
import com.gudeng.commerce.gd.authority.sysmgr.util.CommonConstant.UserType;
import com.gudeng.commerce.gd.authority.sysmgr.util.CommonPropertiesUtil;

/**
 * 用户
 * 
 * @author songhui
 * 
 */
@Service
public class SysRegisterUserServiceImpl implements SysRegisterUserService {
	@Autowired
	private BaseDao<?> baseDao;
	// @Autowired
	// private SysRegisterUserMapper sysRegisterUserMapper;

	// @Autowired
	// private SysUserRoleMapper sysUserRoleMapper;

	@Autowired
	private CommonPropertiesUtil commonPropertiesUtil;

	@Override
	public void delete(String userID) {
		Map<String, Object> map = new HashMap<>();
		map.put("userID", userID);
		baseDao.execute("SysRegisterUser.delete", map);
	}

	@Override
	public SysRegisterUser get(String userID) {
		Map<String, Object> map = new HashMap<>();
		map.put("userID", userID);
		return baseDao.queryForObject("SysRegisterUser.get", map, SysRegisterUser.class);
	}

	@Override
	public List<SysRegisterUser> getListSysRegisterUser(Map<String, Object> map) throws Exception {
		List<SysRegisterUser> list = baseDao.queryForList("SysRegisterUser.getAll", map, SysRegisterUser.class);
		return list;
	}

	@Override
	public String insert(SysRegisterUser sysRegisterUser) throws Exception {
		// 验证当前登录用户是否有创建此类型的用户权限
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userCode", sysRegisterUser.getUserCode());
		map.put("userName", sysRegisterUser.getUserName());
		int i = (int) baseDao.queryForObject("SysRegisterUser.checkUserCode", map, Integer.class);
		if (i > 0) {
//		    return "该用户帐号已作废或已被占用！";
			return "sameUser";
		}
//		int j = (int) baseDao.queryForObject("SysRegisterUser.checkUserName", map, Integer.class);
//		if (j > 0) {
//			throw new Exception("该用户名称已作废或已被占用！");
//		}
		int k = baseDao.execute("SysRegisterUser.insert", sysRegisterUser);
		return k > 0 ? CommonConstant.COMMON_AJAX_SUCCESS : "fail";

	}

	@Override
	public String update(SysRegisterUser sysRegisterUser) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userID", sysRegisterUser.getUserID());
		map.put("userName", sysRegisterUser.getUserName());
//		int count = (int) baseDao.queryForObject("SysRegisterUser.checkUpdUserName", map, Integer.class);
//		if (count > 0) {
//			throw new Exception("该用户名已作废！");
//		}
		int i = baseDao.execute("SysRegisterUser.update", sysRegisterUser);
		return i > 0 ? CommonConstant.COMMON_AJAX_SUCCESS : "fail";
	}

	@Override
	public void updateBatch(String ids, String userId) throws Exception {
		List<String> userIdlist = Arrays.asList(ids.split(","));
		SysRegisterUser tempSysUser = null;
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < userIdlist.size(); i++) {
			if (commonPropertiesUtil.getSysSupperAdminId().equals(userIdlist.get(i))) {
				throw new Exception("用户帐号[admin]为系统超级管理员，不能删除！");
			}
			// 查看用户是否分配了角色
			map.put("userID", userIdlist.get(i));
			// 删除用户
			tempSysUser = new SysRegisterUser();
			tempSysUser.setDeleted("1");
			tempSysUser.setDeletedUserID(userId);
			tempSysUser.setUserID(userIdlist.get(i));
			map.put("deletedUserID", userId);
			baseDao.execute("SysRegisterUser.updateDelFlag", tempSysUser);
		}
	}

	@Override
	public List<SysRegisterUser> getByCondition(Map<String, Object> map) {
		List<SysRegisterUser> list = baseDao.queryForList("SysRegisterUser.getByCondition", map, SysRegisterUser.class);
		return list;
	}

	@Override
	public Integer getTotal(Map<String, Object> map) {
		return (int) baseDao.queryForObject("SysRegisterUser.getTotal", map, Integer.class);
	}

	@Override
	public int updateLockUser(SysRegisterUser user) {
		return baseDao.execute("SysRegisterUser.lockUser", user);
	}

	@Override
	public int updateUnlockUser(SysRegisterUser user) {
		return baseDao.execute("SysRegisterUser.unlockUser", user);
	}

	@Override
	public int updateResetPassword(SysRegisterUser user) {
		return baseDao.execute("SysRegisterUser.resetPassword", user);
	}

	@Override
	public int checkUserCode(String userCode) {
		Map<String, Object> map = new HashMap<>();
		map.put("userCode", userCode);
		return baseDao.execute("SysRegisterUser.checkUserCode", map);
	}

	@Override
	public int checkUserName(String userName) {
		Map<String, Object> map = new HashMap<>();
		map.put("userName", userName);
		return baseDao.execute("SysRegisterUser.checkUserName", map);
	}

	@Override
	public List<SysRegisterUser> getBuyerByCondition(Map<String, Object> map) {
		List<SysRegisterUser> list = baseDao.queryForList("SysRegisterUser.getBuyerByCondition", map, SysRegisterUser.class);
		return list;
	}

	@Override
	public String updateUserPwd(Map<String, Object> map) throws Exception {
		// 检查原始密码是否正确
		int count = (int) baseDao.queryForObject("SysRegisterUser.getUserByPWD", map, Integer.class);
		if (count == 1) {
			// 更新密码
			baseDao.execute("SysRegisterUser.updateUserPwd", map);
			return CommonConstant.COMMON_AJAX_SUCCESS;
		} else {
		    return "fail";
		}
	}

	@Override
	public List<SysRegisterUser> getCanteenPurchase(Map<String, Object> map) {
		List<SysRegisterUser> list = baseDao.queryForList("SysRegisterUser.getCanteenPurchase", map, SysRegisterUser.class);
		return list;
	}

	@Override
	public void updateUserStatusByOrgID(SysRegisterUser user) throws Exception {
		baseDao.execute("SysRegisterUser.updateUserStatusByOrgID", user);
	}
}