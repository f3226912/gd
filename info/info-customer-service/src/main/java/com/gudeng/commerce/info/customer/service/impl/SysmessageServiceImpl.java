package com.gudeng.commerce.info.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gudeng.commerce.info.customer.dao.BaseDao;
import com.gudeng.commerce.info.customer.dto.SysmessageDTO;
import com.gudeng.commerce.info.customer.entity.SysRegisterUser;
import com.gudeng.commerce.info.customer.entity.Sysmessage;
import com.gudeng.commerce.info.customer.service.SysmessageService;
import com.gudeng.commerce.info.customer.util.IdCreater;

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
public class SysmessageServiceImpl implements SysmessageService {
    @Autowired
    private BaseDao<?> baseDao;

    @Override
    @Transactional
    public int insert(SysmessageDTO sysmessageDTO) throws Exception {
        List<SysRegisterUser> list = baseDao.queryForList("SysRegisterUser.getAll", null, SysRegisterUser.class);
        Sysmessage sysmessage = new Sysmessage();
        sysmessage.setCreateUserId(sysmessageDTO.getCreateUserId());
        sysmessage.setCreateUserName(sysmessageDTO.getCreateUserName());
        sysmessage.setUpdateUserId(sysmessageDTO.getCreateUserId());
        sysmessage.setUpdateUserName(sysmessageDTO.getCreateUserName());
        sysmessage.setContent(sysmessageDTO.getContent());
        sysmessage.setTitle(sysmessageDTO.getTitle());
        sysmessage.setCreateTime(new Date());
        sysmessage.setUpdateTime(new Date());
        Long id = baseDao.persist(sysmessage, Long.class);
        int len = list.size();
        Map<String, Object>[] batchValues = new HashMap[len];
        for (int i = 0; i < len; i++) {
            SysRegisterUser user = (SysRegisterUser) list.get(i);
            Map<String, Object> mapTemp = new HashMap<String, Object>();
            mapTemp.put("messageID", id);
            mapTemp.put("userID", user.getUserID());
            mapTemp.put("createUserID", sysmessageDTO.getCreateUserId());
            batchValues[i] = mapTemp;
        }
        baseDao.batchUpdate("Sysmessageuser.insert", batchValues);
        return id == null ? 0 : id > 0 ? 1 : 0;
    }

    @Override
    public int delete(Map<String, Object> map) throws Exception {
        baseDao.execute("Sysmessageuser.deleteByMessageID", map);
        return baseDao.execute("Sysmessage.delete", map);
    }

    @Override
    public List<SysmessageDTO> getListByConditon(Map<String, Object> map) throws Exception {
        return baseDao.queryForList("Sysmessage.getListByConditon", map, SysmessageDTO.class);
    }

    @Override
    public int getTotalByCondition(Map<String, Object> map) throws Exception {
        return (int) baseDao.queryForObject("Sysmessage.getTotalByCondition", map, Integer.class);
    }
    @Override
	public List<SysmessageDTO> getMessageListByUser(Map<String, Object> map) {
		 List<SysmessageDTO> list = baseDao.queryForList("Sysmessage.getMessageListByUser", map, SysmessageDTO.class);
		return list;
	}

	@Override
	public Integer getUnReadMessageCount(String userID) {
		Map<String, Object> map = new HashMap<>();
	    map.put("userID", userID);
		Integer count = baseDao.queryForObject("Sysmessage.getUnReadMessageCount", map, Integer.class);
		return count;
	}

	@Override
	public SysmessageDTO getMessageDetail(Map<String, Object> map) {
		return baseDao.queryForObject("Sysmessage.getMessageDetail", map, SysmessageDTO.class);
	}
}
