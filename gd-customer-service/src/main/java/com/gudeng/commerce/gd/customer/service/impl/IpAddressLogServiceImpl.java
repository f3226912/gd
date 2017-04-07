package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.IpAddressLogDTO;
import com.gudeng.commerce.gd.customer.service.IpAddressLogService;

@Service
public class IpAddressLogServiceImpl implements IpAddressLogService {
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public int countTotal(Map map) throws Exception {
		return (int)baseDao.queryForObject("IpAddressLog.countTotal", map, Integer.class);
	}

	@Override
	public int save(IpAddressLogDTO addressLogDTO) throws Exception {
		return (int)baseDao.execute("IpAddressLog.insert", addressLogDTO);
	}

}
