package com.gudeng.commerce.info.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.info.customer.dao.BaseDao;
import com.gudeng.commerce.info.customer.dto.ReportsDTO;
import com.gudeng.commerce.info.customer.dto.SysrolereportsDTO;
import com.gudeng.commerce.info.customer.service.SysrolereportsService;
import com.gudeng.commerce.info.customer.util.IdCreater;

public class SysrolereportsServiceImpl implements SysrolereportsService {
    @Autowired
    private BaseDao<?> baseDao;

    @Override
    public int addBatch(List<ReportsDTO> list, String roleID, String userId) throws Exception {
        int len = list.size();
        Map<String, Object>[] batchValues = new HashMap[len];
        for (int i = 0; i < len; i++) {
            ReportsDTO reportsDTO = (ReportsDTO) list.get(i);
            Map<String, Object> mapTemp = new HashMap<String, Object>();
            mapTemp.put("rmID", IdCreater.newId());
            mapTemp.put("roleID", roleID);
            mapTemp.put("createUserID", userId);
            mapTemp.put("reportsID", reportsDTO.getId());
            batchValues[i] = mapTemp;
        }
        return baseDao.batchUpdate("Sysrolereports.insert", batchValues).length;
    }

    @Override
    public int deleteBatch(List<?> list, String roleID) throws Exception {
        int len = list.size();
        Map<String, Object>[] batchValues = new HashMap[len];
        for (int i = 0; i < len; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("roleID", StringUtils.trim(list.get(i).toString()));
            batchValues[i] = map;
        }
        return baseDao.batchUpdate("Sysrolereports.delete", batchValues).length;
    }

    /**
     * @Description 根据角色删除 角色、报表关联
     * @param roleID
     * @return
     * @throws Exception
     * @CreationDate 2016年3月3日 上午10:15:20
     * @Author lidong(dli@gdeng.cn)
     */
    public int delete(String roleID) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("roleID", roleID);
        return baseDao.execute("Sysrolereports.delete", map);
    }

    @Override
    public List<SysrolereportsDTO> getListByCondition(Map<String, Object> map) throws Exception {
        return baseDao.queryForList("Sysrolereports.getListByCondition", map, SysrolereportsDTO.class);
    }

}
