package com.gudeng.commerce.gd.authority.sysmgr.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.authority.sysmgr.base.BaseMapper;

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
