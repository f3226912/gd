package com.gudeng.commerce.gd.authority.service.impl;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.base.BaseMapper;

/**
 * 类名称:
 * 类描述:
 * <p/>
 *
 * @verion 1.0
 */
public abstract class BaseServiceImpl<E, T extends BaseMapper<E>> {

    public abstract T getMapper();

    public List getAll(Map map) throws Exception {
        return getMapper().getAll(map);
    }

    public List getAll() {
        return getMapper().getAll();
    }

    public List<E> getByCondition(Map<String, Object> map) {
        return getMapper().getByCondition(map);
    }

    public int getTotal(Map<String, Object> map) {
        return getMapper().getTotal(map);
    }

    public int insert(E e) throws Exception {
        return getMapper().insert(e);
    }

    public int update(E e) throws Exception {
        return getMapper().update(e);
    }

    public void delete(String id) {
        getMapper().delete(id);
    }
}
