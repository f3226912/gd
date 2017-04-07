package com.gudeng.commerce.info.home.service;

import java.util.Map;

public interface SysmessageuserHomeService {
	/**
	 * 修改此消息为已删除
	 * @param map
	 * @author liufan
	 * @return
	 * @throws Exception 
	 */
	public int updateMessageIsdel(Map<String,Object> map) throws Exception;
	
	
	/**
	 * 修改此消息为已读
	 * @param map
	 * @author liufan
	 * @return
	 * @throws Exception 
	 */
	public int updateMessageIsread(Map<String,Object> map) throws Exception;
	
	
}
