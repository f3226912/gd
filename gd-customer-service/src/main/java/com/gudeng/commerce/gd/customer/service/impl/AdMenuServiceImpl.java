package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AdMenuDTO;
import com.gudeng.commerce.gd.customer.dto.AdSpaceDTO;
import com.gudeng.commerce.gd.customer.entity.AdMenu;
import com.gudeng.commerce.gd.customer.service.AdMenuService;

public class AdMenuServiceImpl implements AdMenuService {
    @Autowired
    private BaseDao<?> baseDao;

    @Override
    public List<AdMenuDTO> getAdMenuByCondition(Map<String, Object> map) throws Exception {
        return baseDao.queryForList("AdMenu.getAdMenuByCondition", map, AdMenuDTO.class);
    }

    @Override
    public Long insert(AdMenuDTO adMenuDTO) throws Exception {
        AdMenu adMenu = new AdMenu();
        adMenu.setCreateTime(new Date());
        adMenu.setCreateUserId(adMenuDTO.getCreateUserId());
        adMenu.setCreateUserName(adMenuDTO.getCreateUserName());
        adMenu.setFatherId(adMenuDTO.getFatherId());
        adMenu.setMenuSign(adMenuDTO.getMenuSign());
        adMenu.setMenuName(adMenuDTO.getMenuName());
        adMenu.setState("1");
        return baseDao.persist(adMenu, Long.class);
    }

    @Override
    public Long update(AdMenuDTO adMenuDTO) throws Exception {
        return (long) baseDao.execute("AdMenu.update", adMenuDTO);
    }

    /**
     * @Description delete 删除菜单，状态更新为2，并将子菜单和子广告位状态变更
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年4月14日 下午7:31:06
     * @Author lidong(dli@gdeng.cn)
     */
    @Override
    @Transactional
    public Long delete(Map<String, Object> map) throws Exception {
        // 递归删除菜单下的菜单
        String id = map.get("id").toString();
        map.put("id", id);
        long result = baseDao.execute("AdMenu.delete", map);
        map.remove("id");
        map.put("fatherId", id);
        List<AdMenuDTO> adMenuList = baseDao.queryForList("AdMenu.getAdMenuByCondition", map, AdMenuDTO.class);
        if (adMenuList != null) {
            for (AdMenuDTO adMenuDTO : adMenuList) {
                map.put("id", adMenuDTO.getId());
                result += delete(map);
            }
        }
        map.remove("id");
        map.put("menuId", id);
        // 删除广告位
        result += deleteAdSpaceByMenu(map);
        return result;
    }

    /**
     * @Description deleteAdSpaceByMenu删除菜单下的广告位，将状态改为2
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年4月14日 下午7:41:31
     * @Author lidong(dli@gdeng.cn)
     */
    private Long deleteAdSpaceByMenu(Map<String, Object> map) throws Exception {
        // 删除广告位
        long result = baseDao.execute("AdSpace.deleteByMenuId", map);
        // 根据menuId查找广告位
        List<AdSpaceDTO> adSpaceList = baseDao.queryForList("AdSpace.queryByCondition", map, AdSpaceDTO.class);
        if (adSpaceList != null) {
            for (AdSpaceDTO adSpaceDTO : adSpaceList) {
                // 停用所有广告位下的广告
                map.put("adSpaceId", adSpaceDTO.getId());
                result += baseDao.execute("AdAdvertise.deleteByAdSpace", map);
            }
        }
        return result;
    }

    @Override
    public AdMenuDTO getById(Long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        return baseDao.queryForObject("AdMenu.getById", paramMap, AdMenuDTO.class);
    }

}
