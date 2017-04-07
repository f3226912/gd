package com.gudeng.commerce.gd.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.PushOfflineDTO;
import com.gudeng.commerce.gd.customer.entity.PushOffline;
import com.gudeng.commerce.gd.customer.service.PushOfflineService;

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
public class PushOfflineServiceImpl implements PushOfflineService {
	
    @Autowired
    private BaseDao<?> baseDao;

    @Override
    public int getTotal(Map<String, Object> map) throws Exception {
        return baseDao.queryForObject("PushOffline.getTotal", map, Integer.class);
    }

    @Override
    public List<PushOfflineDTO> getPushOfflineDTOList(Map<String, Object> map) throws Exception {
        return baseDao.queryForList("PushOffline.getPushOfflineDTOList", map, PushOfflineDTO.class);
    }

	
	@Override
	public Long saveOffLinePushInfo(PushOffline pushOffline)
			throws Exception {
		return baseDao.persist(pushOffline, Long.class);
	}

	@Override
	public PushOffline getOffLinePushInCondition(Map<?, ?> map)
			throws Exception {
		return baseDao.queryForObject("PushOffline.getOffLinePushInCondition", map, PushOffline.class);
	}
}
