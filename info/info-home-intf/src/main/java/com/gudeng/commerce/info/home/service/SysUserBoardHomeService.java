package com.gudeng.commerce.info.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.entity.SysUserBoard;

public interface SysUserBoardHomeService {
	/**
	 * 根据userID和看板ID查询在看板和用户的关系表中的对应关系
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<SysUserBoard> getUserBoardList(Map<String, Object> map)
			throws Exception;
	
	 /**
     * 新增
     * 
     * @param sysUserBoard
     * @return
     */
    public String insert(SysUserBoard sysUserBoard) throws Exception ;


    /**
     * 删除
     * 
     * @param boardID
     * @param userID
     * @throws Exception
     */
    public int delete(Map<String,Object> map) throws Exception;

}
