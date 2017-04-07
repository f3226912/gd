package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MemberIntegralConversionDTO;
import com.gudeng.commerce.gd.customer.service.MemberIntegralConversionService;

@Service
public class MemberIntegralConversionServiceImpl implements MemberIntegralConversionService  {

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<MemberIntegralConversionDTO> getMemberIntegralConversion(
			Map<String, Object> map) throws Exception {
		return baseDao.queryForList("MemberIntegralConversion.getListByCondition", map, MemberIntegralConversionDTO.class);
	}

	@Override
	public Integer getTotal(Map<String, Object> map) throws Exception {
		return (Integer)baseDao.queryForObject("MemberIntegralConversion.getTatal", map, Integer.class);

	}
	
	@Override
	public MemberIntegralConversionDTO getByMobile(String mobile)
			throws Exception {
		Map<String, Object> map =new HashMap<>();
		map.put("mobile", mobile);
		return (MemberIntegralConversionDTO) baseDao.queryForObject("MemberIntegralConversion.getByMobile", map, MemberIntegralConversionDTO.class);
	}

	@Override
	public MemberIntegralConversionDTO getByAccount(String account)
			throws Exception {
		Map<String, Object> map =new HashMap<>();
		map.put("account", account);
		return (MemberIntegralConversionDTO) baseDao.queryForObject("MemberIntegralConversion.getByAccount", map, MemberIntegralConversionDTO.class);
	}
	
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<MemberIntegralConversionDTO> getGiftIntegralList(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("MemberIntegralConversion.getListGiftByCondition", map, MemberIntegralConversionDTO.class);
	}

	@Override
	public Integer getGiftTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)baseDao.queryForObject("MemberIntegralConversion.getGiftTatal", map, Integer.class);
	}

	@Override
	public int updateMemberIntegral(Map<String, Object> map) throws Exception{
		// TODO Auto-generated method stub
		
		return (int)baseDao.execute("MemberIntegralConversion.updateMemberIntegral", map);
	}

	@Override
	public int insertIntegral(Map<String, Object> map) throws Exception{
		
		return (int)baseDao.execute("MemberIntegralConversion.insertIntegral", map);
	}


	
}
