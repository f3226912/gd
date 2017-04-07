package com.gudeng.commerce.info.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.info.customer.dao.BaseDao;
import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;
import com.gudeng.commerce.info.customer.dto.ProBszbankDTO;
import com.gudeng.commerce.info.customer.dto.ProOperateDTO;
import com.gudeng.commerce.info.customer.dto.ReportsDTO;
import com.gudeng.commerce.info.customer.service.ReportsService;

public class ReportsServiceImpl implements ReportsService{

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<ReportsDTO> getPageByCondition(Map<String, Object> map) {
		return baseDao.queryForList("Reports.getPageByCondition", map, ReportsDTO.class);
	}

	@Override
	public Integer getTotalByCondition(Map<String, Object> map) {
		return baseDao.queryForObject("Reports.getTotalByCondition", map, Integer.class);
	}

	public Integer update(ReportsDTO reportsDTO){
		return baseDao.execute("Reports.update", reportsDTO);
	}

	@Override
	public ReportsDTO getById(Long reportsID) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", reportsID);
		return baseDao.queryForObject("Reports.getById", paramMap, ReportsDTO.class);
	}
	
	@Override
	public List<ReportsDTO> getReportsList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("Reports.getReportsList", map,ReportsDTO.class);
	}
	@Override
	public List<ProBszbankDTO> getProBszbankList(Map<String, Object> parm) {
		return baseDao.queryForList("Reports.getProBszbankList", parm,ProBszbankDTO.class);
	}
	@Override
	public List<ProBaiduEntityDTO> getbaiduList(Map<String, Object> parm) {
		return baseDao.queryForList("Reports.getbaiduList", parm,ProBaiduEntityDTO.class);
	}
    /**
     * @Description 根据用户ID查出该用户所拥有的所有报表权限
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年3月7日 下午3:17:50
     * @Author lidong(dli@gdeng.cn)
     */
    @Override
    public List<ReportsDTO> getListByUserId(Map<String, Object> map) throws Exception {
        return baseDao.queryForList("Reports.getListByUserId", map,ReportsDTO.class);
    }

	@Override
	public List<ProOperateDTO> getOperateList(Map<String, Object> parm) {
		return baseDao.queryForList("Reports.getOperateList", parm,ProOperateDTO.class);
	}
}
