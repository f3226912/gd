package com.gudeng.commerce.info.customer.service;

import java.util.List;
import java.util.Map;




import com.gudeng.commerce.info.customer.entity.SysUserBoard;

public interface SysUserBoardService {

	  public List<SysUserBoard> getUserBoardList(Map<String,Object> map) throws Exception;
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
