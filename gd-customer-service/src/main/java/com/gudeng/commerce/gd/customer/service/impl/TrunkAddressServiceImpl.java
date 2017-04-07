package com.gudeng.commerce.gd.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.TrunkAddressDTO;
import com.gudeng.commerce.gd.customer.service.TrunkAddressService;
/**
 * 干线物流货源实现
 * @author xiaojun
 *
 */
public class TrunkAddressServiceImpl implements TrunkAddressService{
	
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<TrunkAddressDTO> getTrunkAddressList(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("TrunkAddress.getTrunkAddressList", paramMap, TrunkAddressDTO.class);
	}

	@Override
	public Integer getTrunkAddressListCount(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("TrunkAddress.getTrunkAddressListCount", paramMap, Integer.class);
	}

	@Override
	public List<TrunkAddressDTO> getTrunkAddressListByMemberId(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("TrunkAddress.getTrunkAddressListByMemberId", paramMap, TrunkAddressDTO.class);
	}

	@Override
	public Integer getTrunkAddressListCountByMemberId(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("TrunkAddress.getTrunkAddressListCountByMemberId", paramMap, Integer.class);
	}

}
