package com.gudeng.commerce.gd.authority.sysmgr.base;

import java.util.List;
import java.util.Map;

/**   
 * @Description mapper的基本类
 * @Project gd-auth-intf
 * @ClassName BaseMapper.java
 * @Author lidong(dli@cnagri-products.com)    
 * @CreationDate 2015年10月17日 下午2:35:12
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 *  Who        When                         What
 *  --------   -------------------------    -----------------------------------
 *  lidong     2015年10月17日 下午2:35:12       初始创建
 */
public interface BaseMapper<E> {

	/**
	 * 有参数的getAll
	 * 
	 * @param map
	 * @return List集合;
	 */
	public List<E> getAll(Map<String,Object> map);

	/**
	 * 无参数的getAll
	 * 
	 * @return List集合;
	 */
	public List<E> getAll();

	/**
	 * 根据PK查询结果集
	 * 
	 * @param pk
	 *            传入PK;
	 * 
	 * @return List;
	 */
	public List<E> get(String pk);

	/**
	 * 根据条件查询结果集
	 * 
	 * @param map
	 * @return	List
	 */
	public List<E> getByCondition(Map<String,Object> map);
	
	/**
	 * 查询记录总数
	 * 
	 * @return int
	 */
	public int getTotal();
	
	/**
	 * 查询记录总数(带参数)
	 * 
	 * @return int
	 * */
	public int getTotal(Map<String,Object> map);
	
	/**
	 * 根据PK查询结果集
	 * 
	 * @param pk
	 *            传入PK;
	 * 
	 * @return Object;
	 */
	public Object getObject(String pk);

	/**
	 * 新增
	 * 
	 * @param entity
	 *            实体;
	 * @return 影响的行数
	 */
	public int insert(E entity);

	/**
	 * 修改
	 * 
	 * @param entity
	 *            实体;
	 * @return 影响的行数
	 */
	public int update(E entity);


	/**
	 * 依据pk进行删除
	 * 
	 * @param pk
	 * @return 影响的行数
	 */
	public int delete(String pk);
}
