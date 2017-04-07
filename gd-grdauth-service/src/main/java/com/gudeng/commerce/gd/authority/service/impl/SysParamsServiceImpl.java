package com.gudeng.commerce.gd.authority.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.authority.dao.BaseDao;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysParams;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysParamsService;
import com.gudeng.commerce.gd.authority.sysmgr.util.CommonConstant;

@Service
public class SysParamsServiceImpl implements SysParamsService {
	@Autowired
	private BaseDao<?> baseDao;

	// @Autowired
	// public SysParamsMapper sysParamsMapper;

	@Override
	public SysParams get(String sysParamsID) throws Exception {
		// List<SysParams> list=sysParamsMapper.get(sysParamsID);
		// if(list!=null){
		// return list.get(0);
		// }else{
		// return null;
		// }
		//
		Map<String, Object> map = new HashMap<>();
		map.put("sysParamsID", sysParamsID);
		return baseDao.queryForObject("SysParams.get", map, SysParams.class);
	}

	@Override
	public List<SysParams> getByCondition(Map<String, Object> map)
			throws Exception {
		// return sysParamsMapper.getByCondition(map);
		List<SysParams> list = baseDao.queryForList("SysParams.getByCondition",
				map, SysParams.class);
		return list;
	}

	@Override
	public String update(SysParams sysParams) throws Exception {
		// int back = sysParamsMapper.update(sysParams);
		// if (back == 1) {
		// return CommonConstant.COMMON_AJAX_SUCCESS;
		// } else {
		// return "fail";
		// }
		int i = baseDao.execute("SysParams.update", sysParams);
		return i > 0 ? CommonConstant.COMMON_AJAX_SUCCESS : "fail";
	}

	@Override
	public int getTotal() throws Exception {
		// return sysParamsMapper.getTotal();
		return (int) baseDao.queryForObject("SysParams.getTotal",
				new HashMap<>(), Integer.class);
	}

}
