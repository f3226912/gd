package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.IpAddressBlackDTO;
import com.gudeng.commerce.gd.customer.service.IpAddressBlackService;

@Service
public class IpAddressBlackServiceImpl implements IpAddressBlackService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public int countTotal(Map map) throws Exception {
		return (int)baseDao.queryForObject("IpAddressBlack.countTotal", map, Integer.class);
	}

	@Override
	public int save(IpAddressBlackDTO addressBlackDTO) throws Exception {
		return (int)baseDao.execute("IpAddressBlack.insert", addressBlackDTO);
	}

}
