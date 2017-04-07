package com.gudeng.commerce.gd.admin.service.sysmgr;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.entity.SysParams;

/**
 * 系统参数设置Service
 * @author wwj
 * 
 */
public interface SysParamsAdminService {
    
	/**
	 * 分页查询数据总条数
	 * 
	 * @param map
	 * @return
	 * @throws CommException
	 */
	public int getTotal()throws Exception;
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<SysParams> getByCondition(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据ID查询
	 * @param sysParamsID
	 * @return
	 */
	public SysParams get(String sysParamsID)throws Exception;
	
	/**
	 * 修改系统参数值 
	 * @param sysParams
	 * @return
	 */
	public String update(SysParams sysParams) throws Exception;
}
