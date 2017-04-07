package com.gudeng.commerce.gd.customer.service;

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
public interface AdAdvertiseService {

    List<AdAdvertiseDTO> geAdAdvertiseDTOList(Map<String, Object> map) throws Exception;

    int getTotal(Map<String, Object> map) throws Exception;

    Long insert(AdAdvertiseDTO adAdvertiseDTO) throws Exception;

    int delete(List<String> list) throws Exception;

    Long persit(AdAdvertise adAdvertise) throws Exception;

    List<AdAdvertiseDTO> getAdByMenuId(int menuId);

    int update(AdAdvertise adAdvertise) throws Exception;

    int updateState(AdAdvertiseDTO adAdvertiseDTO) throws Exception;
    
    public List<AdAdvertiseDTO> getAdBySignAndMarketId(Map<String,Object> params)throws Exception;
    
    public List<AdAdvertiseDTO> queryAdvertiseList(Map<String,Object> params)throws Exception;

}
