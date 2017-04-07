package com.gudeng.commerce.gd.admin.service.sysmgr;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.entity.SysResGroup;

/**
 * 资源组
 * @author wwj
 */
public interface SysResGroupAdminService{
	
	/**
	 * 依据传入的参数取得对象;
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<SysResGroup> getListSysResGroups(Map<String, Object> map) throws Exception ;

	/**
	 * 查询单个资源组信息
	 * 
	 * @param goodsID
	 *            资源组ID
	 * @return
	 */
	public SysResGroup get(String groupID) throws Exception ;

	/**
	 * 新增资源组
	 * 
	 * @param Goods
	 *            资源组实体
	 */
	public String insert(SysResGroup group)throws Exception ;

	/**
	 * 批量新增资源组
	 * 
	 * @param goodsList
	 */
	public void batchInsert(List<SysResGroup> groupList)throws Exception ;

	/**
	 * 修改资源组
	 * 
	 * @param Goods
	 *            资源组实体
	 */
	public String update(SysResGroup group)throws Exception ;
	
	/**
	 * 批量修改资源组
	 * 
	 * @param goodsList
	 */
	public void batchUpdate(List<SysResGroup> groupList)throws Exception ;

	/**
	 * 删除资源组
	 * 
	 * @param goodsID
	 *            资源组ID
	 */
	public String delete(String groupID)throws Exception ;

	/**
	 * 批量删除资源组
	 * 
	 * @param goodsIDList
	 *            资源组ID集合
	 */
	public void batchDelete(List<String> groupIDList)throws Exception ;

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	public List<SysResGroup> getByCondition(Map<String, Object> map)throws Exception ;

	/**
	 * 记录总数
	 * 
	 * @return
	 */
	public Integer getTotal(Map<String, Object> map)throws Exception ;
	
	/**
	 * 验证组名是否存在
	 * @param resGroupName
	 * @return
	 */
	public int checkGroupName(String resGroupName)throws Exception ;
	
}
