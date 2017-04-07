package com.gudeng.commerce.info.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.SysmessageDTO;

/**
 * @Description 系统消息
 * @Project info-customer-intf
 * @ClassName SysmessageService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年3月4日 上午9:06:53
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public interface SysmessageToolService {
    public int insert(SysmessageDTO sysmessageDTO) throws Exception;

    public int delete(Map<String, Object> map) throws Exception;

    public List<SysmessageDTO> getListByConditon(Map<String, Object> map) throws Exception;

    public int getTotalByCondition(Map<String, Object> map) throws Exception;
}
