package com.gudeng.commerce.gd.admin.service.sysmgr;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;

/**
 * 用户service
 * @author wwj
 *
 */
public interface SysRegisterUserAdminService {

	/**
	 * 依据传入的参数取得对象;
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<SysRegisterUser> getListSysRegisterUser(Map<String,Object> map)
			throws Exception;

	/**
	 * 批量更新
	 * 
	 * @param ids
	 *            更新集合
	 * @param userId
	 * @param sqlMap
	 *            mybatis对应的ID
	 * @throws Exception 
	 */
	public void updateBatch(String ids,String userId) throws Exception;
	
	/**
	 * 查询单个用户 
	 * @param logId 用户ID
	 * @return
	 */
	public SysRegisterUser get(String userID) throws Exception;
	
	/**
	 * 新增一个用户信息
	 * @param SysRegisterUser 用户实体
	 * @throws Exception 
	 */
	public String insert(SysRegisterUser sysRegisterUser) throws Exception ;
	
	/**
	 * 修改一个用户信息
	 * @param SysRegisterUser 用户实体
	 * @throws Exception 
	 */
	public String update(SysRegisterUser sysRegisterUser) throws Exception ;
	
	/**
	 * 删除一个用户信息
	 * @param logId
	 */
	public void delete(String userID) throws Exception;
	
	/**
	 * 分页查询
	 * @return
	 */
	public List<SysRegisterUser> getByCondition(Map<String,Object> map) throws Exception;
	
	/**
	 * 记录总数
	 * @return
	 */
	public Integer getTotal(Map<String,Object> map) throws Exception;
	
	/**
	 * 锁定用户
	 * @param user
	 * @return
	 */
	public int updateLockUser(SysRegisterUser user) throws Exception;
	
	/**
	 * 解锁用户
	 * @param user
	 * @return
	 */
	public int updateUnlockUser(SysRegisterUser user) throws Exception;
	
	/**
	 * 密码重置
	 * @param user
	 * @return
	 */
	public int updateResetPassword(SysRegisterUser user) throws Exception;
	
	/**
	 * 检查用户编码是否存在
	 * @param userCode
	 * @return
	 */
	public int checkUserCode(String userCode) throws Exception;
	
	/**
	 * 检查用户名是否存在
	 * @param userName
	 * @return
	 */
	public int checkUserName(String userName) throws Exception;
	
	/**
	 * 查询买手list
	 * @param map
	 * @return
	 * @author tanjun
	 */
	public List<SysRegisterUser> getBuyerByCondition(Map<String,Object>map) throws Exception;
	
	/**
	 * 修改用户名密码
	 * @param map
	 * @author tanjun
	 * @return
	 * @throws Exception 
	 */
	public String updateUserPwd(Map<String,Object> map) throws Exception;

	/**
	 * 根据学校查找下面的采购员
	 * @param map
	 * @return
	 * @author tanjun
	 */
	public List<SysRegisterUser> getCanteenPurchase(Map<String,Object>map) throws Exception;
	
	/**
	 * 根据用户组织ID改变用户启用，禁用状态
	 * @author songhui
	 * @date 创建时间：2015年8月11日 下午4:58:05
	 * @param user
	 * @throws Exception 
	 *
	 */
	public void updateUserStatusByOrgID(SysRegisterUser user) throws Exception;
}






