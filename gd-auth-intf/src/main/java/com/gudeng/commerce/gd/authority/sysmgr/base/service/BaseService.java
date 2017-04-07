package com.gudeng.commerce.gd.authority.sysmgr.base.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.base.BaseMapper;

/**   
 * @Description 基类接口 
 * @Project gd-auth-intf
 * @ClassName BaseService.java
 * @Author lidong(dli@cnagri-products.com)    
 * @CreationDate 2015年10月17日 下午2:35:26
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 *  Who        When                         What
 *  --------   -------------------------    -----------------------------------
 *  lidong     2015年10月17日 下午2:35:26       初始创建
 */
public interface BaseService<E, T extends BaseMapper> {

    public T getMapper();

    @SuppressWarnings("unchecked")
    public List<E> getAll(Map map) throws Exception;

    public List<E> getAll();

    @SuppressWarnings("unchecked")
    public List<E> getByCondition(Map<String, Object> map);

    public int getTotal(Map<String,Object> map);

    public int insert(E e) throws Exception;

    public int update(E e) throws Exception;

    public void delete(String id);

}
