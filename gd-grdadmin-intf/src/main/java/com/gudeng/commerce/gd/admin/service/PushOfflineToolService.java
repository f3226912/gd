package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushOfflineDTO;

/**
 * @Description 线下推广统计服务
 * @Project gd-customer-intf
 * @ClassName PushOfflineService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年2月18日 上午11:59:45
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public interface PushOfflineToolService {

    public int getTotal(Map<String, Object> map) throws Exception;

    public List<PushOfflineDTO> getPushOfflineDTOList(Map<String, Object> map) throws Exception;

}