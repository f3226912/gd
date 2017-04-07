package com.gudeng.commerce.gd.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AppshareDTO;
import com.gudeng.commerce.gd.customer.service.AppshareService;

@Service
public class AppshareServiceImpl implements AppshareService {
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public int addAppShare(AppshareDTO inputDTO) throws Exception {
		return baseDao.execute("appshare.insert", inputDTO);
	}

	@Override
	public AppshareDTO getAppShareByCondition(AppshareDTO inputDTO)
			throws Exception {
		return (AppshareDTO)baseDao.queryForObject("appshare.getAppShareByCondition", inputDTO, AppshareDTO.class);
	}

	@Override
	public int getVisitorIpCount(AppshareDTO inputDTO) throws Exception {
		return this.baseDao.queryForObject("appshare.getVisitorIpCount", inputDTO, Integer.class);
	}

	@Override
	public int updateAppShareViewCount(AppshareDTO inputDTO) throws Exception {
		int count = this.baseDao.execute("appshare.updateAppShareViewCount", inputDTO);
		return count;
	}

	@Override
	public int addVisitorIpInfo(AppshareDTO inputDTO) throws Exception {
		return baseDao.execute("appshare.addVisitorIpInfo", inputDTO);
	}

	@Override
	public int updateAppShareAndVisitorIp(AppshareDTO inputDTO)
			throws Exception {
		updateAppShareViewCount(inputDTO);
		int visitorIpCount = addVisitorIpInfo(inputDTO);
		return visitorIpCount;
	}

	@Override
	public int getTotalCountByCondtion(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForObject("appshare.getTotalCountByCondition", map, Integer.class);
	}

	@Override
	public List<AppshareDTO> queryListByCondition(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("appshare.queryListByCondition", map, AppshareDTO.class);
	}

	@Override
	public int updateStatus(AppshareDTO dto) throws Exception {
		return this.baseDao.execute("appshare.updateStatus", dto);
	}
	
	@Override
	public List<AppshareDTO> queryPageByCondition(Map<String, Object> map) {
		return baseDao.queryForList("appshare.queryPageByCondition", map, AppshareDTO.class);
	}

	@Override
	public List<AppshareDTO> queryDetailPageByCondition(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("appshare.queryDetailPageByCondition", map, AppshareDTO.class);
	}

	@Override
	public int getDetailTotalCountByCondtion(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForObject("appshare.getDetailTotalCountByCondition", map, Integer.class);
	}

}
