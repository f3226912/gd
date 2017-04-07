package com.gudeng.commerce.info.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.info.customer.dao.BaseDao;
import com.gudeng.commerce.info.customer.dto.SysmessageuserDTO;
import com.gudeng.commerce.info.customer.service.SysmessageuserService;
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
public class SysmessageuserServiceImpl implements SysmessageuserService {
	@Autowired
	private BaseDao<?> baseDao;
	
    @Override
    public int insert(Map<String, Object> map) throws Exception {
        
        return 0;
    }

    @Override
    public int deleteByMessageID(Map<String, Object> map) throws Exception {
        return baseDao.execute("Sysmessageuser.deleteByMessageID", map);
    }

    @Override
    public List<SysmessageuserDTO> getListByConditon(Map<String, Object> map) throws Exception {
        return baseDao.queryForList("Sysmessageuser.getListByConditon", map, SysmessageuserDTO.class);
    }

    @Override
    public int update(SysmessageuserDTO sysmessageuserDTO) throws Exception {
        baseDao.execute("Sysmessageuser.update", sysmessageuserDTO);
        return 0;
    }

	@Override
	public int updateMessageIsdel(Map<String, Object> map) throws Exception {
		return baseDao.execute("Sysmessageuser.updateSysmessageIsdel", map);
	}

	@Override
	public int updateMessageIsread(Map<String, Object> map) throws Exception {
		return baseDao.execute("Sysmessageuser.updateSysmessageIsread", map);
	}
    
    

    @Override
    public int deleteByUserID(Map<String, Object> map) throws Exception {
        return baseDao.execute("Sysmessageuser.deleteByUserID", map);
    }

    @Override
    public int deleteByUserIdAndMessageID(Map<String, Object> map) throws Exception {
        return baseDao.execute("Sysmessageuser.deleteByUserIdAndMessageID", map);
    }

}
