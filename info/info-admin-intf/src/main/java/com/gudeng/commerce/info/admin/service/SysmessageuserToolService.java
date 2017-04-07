package com.gudeng.commerce.info.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.SysmessageuserDTO;

/**
 * @Description 系统消息-用户 关联
 * @Project info-customer-intf
 * @ClassName SysmessageuserService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年3月4日 上午9:07:04
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public interface SysmessageuserToolService {
    public int insert(Map<String, Object> map) throws Exception;

    public int deleteByMessageID(Map<String, Object> map) throws Exception;
    public int deleteByUserID(Map<String, Object> map) throws Exception;
    public int deleteByUserIdAndMessageID(Map<String, Object> map) throws Exception;


    public List<SysmessageuserDTO> getListByConditon(Map<String, Object> map) throws Exception;

    public int update(SysmessageuserDTO sysmessageuserDTO) throws Exception;
}
