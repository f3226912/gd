package com.gudeng.commerce.gd.authority.sysmgr.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.entity.SysParams;

/**   
 * @Description 系统参数设置接口
 * @Project gd-auth-intf
 * @ClassName SysParamsService.java
 * @Author lidong(dli@cnagri-products.com)    
 * @CreationDate 2015年10月17日 下午2:41:37
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public interface SysParamsService {
    
	/**
	 * 分页查询数据总条数
	 * 
	 * @param map
	 * @return
	 * @throws CommException
	 * @date 2012-7-10
	 */
	public int getTotal()throws Exception;
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 * @date 2012-7-10
	 */
	public List<SysParams> getByCondition(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据ID查询
	 * @param sysParamsID
	 * @return
	 * @date 2012-7-10
	 */
	public SysParams get(String sysParamsID)throws Exception;
	
	/**
	 * 修改系统参数值 
	 * @param sysParams
	 * @return
	 * @date 2012-7-10
	 */
	public String update(SysParams sysParams)throws Exception;
}
