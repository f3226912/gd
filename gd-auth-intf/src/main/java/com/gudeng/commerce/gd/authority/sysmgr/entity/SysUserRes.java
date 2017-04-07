package com.gudeng.commerce.gd.authority.sysmgr.entity;

import java.io.Serializable;
import java.util.Date;
/**   
 * @Description 用户资源组
 * @Project gd-auth-intf
 * @ClassName SysUserRes.java
 * @Author lidong(dli@cnagri-products.com)    
 * @CreationDate 2015年10月17日 下午2:39:21
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 *  Who        When                         What
 *  --------   -------------------------    -----------------------------------
 *  lidong     2015年10月17日 下午2:39:21       初始创建
 */
public class SysUserRes implements Serializable {

	/**
	 * 序列号;
	 */
	private static final long serialVersionUID = -4848623906244405132L;
	/** 主键ID */
	private String uresID;
	/** 资源组ID */
	private String resGroupID;
	/** 用户ID */
	private String userID;
	/** 创建用户ID */
	private String createUserID;
	/** 创建时间 */
	private Date createTime;
	
	/** 用户名 */
	private String userName;
	/** 组名*/
	private String groupName;
	
	/**是否被选中标记*/
	private String isAuth;

	public String getUresID() {
		return uresID;
	}

	public void setUresID(String uresID) {
		this.uresID = uresID;
	}

	public String getResGroupID() {
		return resGroupID;
	}

	public void setResGroupID(String resGroupID) {
		this.resGroupID = resGroupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getCreateUserID() {
		return createUserID;
	}

	public void setCreateUserID(String createUserID) {
		this.createUserID = createUserID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	private String total;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

}
