package com.gudeng.commerce.gd.promotion.entity;

import java.io.Serializable;
import java.util.List;

import com.gudeng.framework.security.core.model.MenuDTO;

public class UserSummary  implements Serializable{
	public static  final String SESSION_USER_KEY="userSummaryForAuthority";
	/**
	 * 用户ID
	 */
	private String userid;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 角色
	 */
	private String role;
	
	private List<MenuDTO> menus;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public List<MenuDTO> getMenus() {
		return menus;
	}
	public void setMenus(List<MenuDTO> menus) {
		this.menus = menus;
	}
	
	
}
