package com.gudeng.commerce.info.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.BoardDTO;
import com.gudeng.commerce.info.customer.dto.SysroleboardDTO;

/**
 * @Description 角色看板管理服务
 * @Project info-customer-intf
 * @ClassName SysrolereportsService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年3月3日 上午9:22:55
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public interface SysroleboardToolService {
    /**
     * @Description 批量添加角色和看板关联
     * @param list
     * @return
     * @throws Exception
     * @CreationDate 2016年3月3日 上午9:28:53
     * @Author lidong(dli@gdeng.cn)
     */
    public int addBatch(List<BoardDTO> list, String roleID, String userId) throws Exception;

    /**
     * @Description 批量删除角色和看板关联
     * @param list
     * @return
     * @throws Exception
     * @CreationDate 2016年3月3日 上午9:29:13
     * @Author lidong(dli@gdeng.cn)
     */
    public int deleteBatch(List<?> list, String roleID) throws Exception;

    /**
     * @Description 根据角色删除 角色、看板关联
     * @param roleID
     * @return
     * @throws Exception
     * @CreationDate 2016年3月3日 上午10:15:20
     * @Author lidong(dli@gdeng.cn)
     */
    public int delete(String roleID) throws Exception;

    /**
     * @Description 查询角色看板关联列表
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年3月3日 上午9:29:34
     * @Author lidong(dli@gdeng.cn)
     */
    public List<SysroleboardDTO> getListByCondition(Map<String, Object> map) throws Exception;
}
