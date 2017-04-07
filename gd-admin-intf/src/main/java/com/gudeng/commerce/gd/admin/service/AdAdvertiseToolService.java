package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;
import com.gudeng.commerce.gd.customer.entity.AdAdvertise;

/**
 * @Description 广告管理
 * @Project gd-customer-intf
 * @ClassName AdAdvertiseService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月13日 上午11:19:45
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public interface AdAdvertiseToolService {
    public List<AdAdvertiseDTO> geAdAdvertiseDTOList(Map<String, Object> map) throws Exception;

    public int getTotal(Map<String, Object> map) throws Exception;

    public Long insert(AdAdvertiseDTO adAdvertiseDTO) throws Exception;

    public int delete(List<String> ids) throws Exception;

    public Long persit(AdAdvertise adAdvertise) throws Exception;

    public int update(AdAdvertise adAdvertise) throws Exception;

    public int updateState(AdAdvertiseDTO adAdvertiseDTO) throws Exception;
}
