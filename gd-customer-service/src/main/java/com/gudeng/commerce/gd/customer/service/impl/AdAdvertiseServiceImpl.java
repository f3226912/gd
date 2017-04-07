package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;
import com.gudeng.commerce.gd.customer.entity.AdAdvertise;
import com.gudeng.commerce.gd.customer.service.AdAdvertiseService;

/**
 * @Description 广告管理
 * @Project gd-customer-service
 * @ClassName AdAdvertiseServiceImpl.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月13日 上午11:20:21
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public class AdAdvertiseServiceImpl implements AdAdvertiseService {

    @Autowired
    private BaseDao<?> baseDao;

    /**
     * @Description 列表集合查询
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年4月13日 下午12:07:11
     * @Author lidong(dli@gdeng.cn)
     */
    @Override
    public List<AdAdvertiseDTO> geAdAdvertiseDTOList(Map<String, Object> map) throws Exception {
        return baseDao.queryForList("AdAdvertise.geAdAdvertiseDTOList", map, AdAdvertiseDTO.class);
    }

    @Override
    public int getTotal(Map<String, Object> map) throws Exception {
        return baseDao.queryForObject("AdAdvertise.getTotal", map, Integer.class);
    }

    @Override
    public Long insert(AdAdvertiseDTO adAdvertiseDTO) throws Exception {
        return (long) baseDao.execute("AdAdvertise.insert", adAdvertiseDTO);
    }

    @Override
    public int delete(List<String> list) throws Exception {
        int len = list.size();
        Map<String, Object>[] batchValues = new HashMap[len];
        for (int i = 0; i < len; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", StringUtils.trim(list.get(i)));
            batchValues[i] = map;
        }
        return baseDao.batchUpdate("AdAdvertise.delete", batchValues).length;
    }

    @Override
    public Long persit(AdAdvertise adAdvertise) throws Exception {
        return baseDao.persist(adAdvertise, Long.class);
    }

    @Override
    public List<AdAdvertiseDTO> getAdByMenuId(int menuId) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("menuId", menuId);
        return baseDao.queryForList("AdAdvertise.getAdByMenuId", params, AdAdvertiseDTO.class);
    }

    @Override
    public int update(AdAdvertise adAdvertise) throws Exception {
        return baseDao.execute("AdAdvertise.update", adAdvertise);
    }

    @Override
    public int updateState(AdAdvertiseDTO adAdvertiseDTO) throws Exception {
        return baseDao.execute("AdAdvertise.updateState", adAdvertiseDTO);
    }
    
    @Override
    public List<AdAdvertiseDTO> getAdBySignAndMarketId(Map<String,Object> params) throws Exception  {
        return baseDao.queryForList("AdAdvertise.getAdBySignAndMarketId", params, AdAdvertiseDTO.class);
    }

	@Override
	public List<AdAdvertiseDTO> queryAdvertiseList(Map<String, Object> params) throws Exception {
		return baseDao.queryForList("AdAdvertise.queryAdvertiseList", params, AdAdvertiseDTO.class);
	}    
    
}
